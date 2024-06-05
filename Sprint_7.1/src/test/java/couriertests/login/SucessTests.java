package couriertests.login;

import base.Constants;
import courier.CourierMetods;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import net.datafaker.Faker;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class SucessTests {
    CourierMetods courierMetods = new CourierMetods();
    Faker faker = new Faker();
    private final String login = faker.name().lastName() + faker.number().numberBetween(1, 100);
    private final String password = faker.number().numberBetween(0, 9999) + "";

    @Before
    public void setUp() {
        RestAssured.baseURI = Constants.BASE_URI;
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    @Test
    @DisplayName("login courier")
    @Description("status code 201, response include id courier")
    public void loginCourier() {
        createCourierForThisTest();
        loginCourierForThisTest();
    }

    @Step("Create courier")
    public void createCourierForThisTest() {
        courierMetods
                .createCourier(login, password, null);
    }

    @Step("Login courier")
    public void loginCourierForThisTest() {
        courierMetods
                .login(login, password)
                .body("id", notNullValue());
    }

    @After
    public void deleteData() {
        Integer id = courierMetods.login(login, password).extract().body().path("id");
        if (id != null) {
            courierMetods.delete(id).body("ok", equalTo(true));
        }
    }
}
