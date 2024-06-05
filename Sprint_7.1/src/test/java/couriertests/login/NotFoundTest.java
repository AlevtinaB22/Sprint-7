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

public class NotFoundTest {
    CourierMetods courierMetods = new CourierMetods();
    Faker faker = new Faker();
    private final String login = faker.name().lastName() + faker.number().numberBetween(1, 100);
    private final String password = faker.number().numberBetween(0, 9999) + "";
    private final String textError = "Учетная запись не найдена";

    @Before
    public void setUp() {
        RestAssured.baseURI = Constants.BASE_URI;
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    @Test
    @DisplayName("status code 404 with wrong password")
    @Description("status code 404, textError = \"Учетная запись не найдена\"")
    public void statusCode404WithWrongPassword() {
        createCourierForTestWithWrongPassword();
        loginCourierForTestWithWrongPassword();
    }

    @Step("Create courier")
    public void createCourierForTestWithWrongPassword() {
        courierMetods
                .createCourier(login, password, null);
    }

    @Step("Login courier")
    public void loginCourierForTestWithWrongPassword() {
        courierMetods
                .login(login, faker.number().numberBetween(0, 9999) + "")
                .body("message", equalTo(textError));
    }

    @Test
    @DisplayName("status code 404 with not found parameters")
    @Description("status code 404, textError = \"Учетная запись не найдена\"")
    public void statusCode404WithAllWrongParameters() {
        courierMetods
                .login(faker.name().lastName() + faker.number().numberBetween(1, 100), faker.number().numberBetween(0, 9999) + "")
                .body("message", equalTo(textError));
    }

    @After
    public void deleteData() {
        Integer id = courierMetods.login(login, password).extract().body().path("id");
        if (id != null) {
            courierMetods.delete(id);
        }
    }
}
