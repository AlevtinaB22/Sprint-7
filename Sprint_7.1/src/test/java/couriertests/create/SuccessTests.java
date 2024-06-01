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

public class SuccessTests {
    CourierMetods courierMetods = new CourierMetods();
    Faker faker = new Faker();
    private String login = faker.name().lastName() + faker.number().numberBetween(1, 100);
    private String password = faker.number().numberBetween(0, 9999) + "";
    private String firstName = faker.name().firstName();

    @Before
    public void setUp() {
        RestAssured.baseURI = Constants.BASE_URI;
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    @Test
    @DisplayName("create courier with all parameters")
    @Description("status code 201, ok = true")
    public void createCourierWithAllParameters() {

        courierMetods
                .createCourier(login, password, firstName)
                .statusCode(201)
                .assertThat().body("ok", equalTo(true));
    }

    @Test
    @DisplayName("create courier with mandatory parameters")
    @Description("status code 201, ok = true")
    public void createCourierWithMandatoryParameters() {
        courierMetods
                .createCourier(login, password, null)
                .statusCode(201)
                .assertThat().body("ok", equalTo(true));
    }

    @After
    public void deleteData() {
        Integer id = courierMetods.login(login, password).extract().body().path("id");
        if (id != null) {
            courierMetods.delete(id).body("ok", equalTo(true));
        }
    }
}
