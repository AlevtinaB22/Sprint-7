package couriertests.create;

import base.Constants;
import courier.CourierMetods;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import net.datafaker.Faker;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;

public class MessageErrorConflictTest {
    CourierMetods courierMetods = new CourierMetods();
    Faker faker = new Faker();
    private final String login = faker.name().lastName() + faker.number().numberBetween(1, 100);
    private final String password = faker.number().numberBetween(0, 9999) + "";
    private final String firstName = faker.name().lastName();

    @Before
    public void setUp() {
        RestAssured.baseURI = Constants.BASE_URI;
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        courierMetods
                .createCourier(login, password, firstName);
    }

    @Test
    @DisplayName("status code 409 with duplicate login ")
    @Description("status code 409, textError = \"Этот логин уже используется. Попробуйте другой.\"")
    public void conflictTest() {
        courierMetods
                .createCourier(login, password, firstName)
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."))
                .and()
                .statusCode(409);
    }

    @After
    public void deleteData() {
        Integer id = courierMetods.login(login, password).extract().body().path("id");
        if (id != null) {
            courierMetods.delete(id).body("ok", equalTo(true));
        }
    }
}
