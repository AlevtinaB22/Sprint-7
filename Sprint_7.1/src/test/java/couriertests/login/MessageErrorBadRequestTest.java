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

public class MessageErrorBadRequestTest {
    CourierMetods courierMetods = new CourierMetods();
    static Faker faker = new Faker();
    private final String login = faker.name().lastName() + faker.number().numberBetween(1, 100);
    private final String password = faker.number().numberBetween(0, 9999) + "";
    private final String textError = "Недостаточно данных для входа";

    @Before
    public void setUp() {
        RestAssured.baseURI = Constants.BASE_URI;
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    @Test
    @DisplayName("status code 400 without all parameters")
    @Description("status code 400, textError = \"Недостаточно данных для входа\"")
    public void statusCode400WithoutAllParameters() {
        courierMetods
                .login("", "")
                .body("message", equalTo(textError));
    }

    @Test
    @DisplayName("status code 400 without login")
    @Description("status code 400, textError = \"Недостаточно данных для входа\"")
    public void statusCode400WithoutLogin() {
        createCourierForTestWithoutLogin();
        loginCourierForTestWithoutLogin();
    }

    @Step("Create courier")
    public void createCourierForTestWithoutLogin() {
        courierMetods
                .createCourier(login, password, null);
    }

    @Step("Login without login")
    public void loginCourierForTestWithoutLogin() {
        courierMetods
                .login("", password)
                .body("message", equalTo(textError));
    }

    @Test
    @DisplayName("status code 400 without password")
    @Description("status code 400, textError = \"Недостаточно данных для входа\"")
    public void statusCode400WithoutPassword() {
        createCourierWithoutPassword();
        loginCourierForTestWithoutPassword();
    }

    @Step("Create courier")
    public void createCourierWithoutPassword() {
        courierMetods
                .createCourier(login, password, null);
    }

    @Step("Login without password")
    public void loginCourierForTestWithoutPassword() {
        courierMetods
                .login(login, "")
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
