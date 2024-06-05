package courier;

import base.Constants;
import courier.model.Courier;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class CourierMetods {
    public ValidatableResponse createCourier(String login, String password, String firstName) {
        Courier courier = new Courier();
        courier.setLogin(login);
        courier.setPassword(password);
        courier.setFirstName(firstName);
        return given()
                .header("Content-Type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(Constants.API_FOR_ADD_COURIER)
                .then();
    }

    public ValidatableResponse login(String login, String password) {
        Courier courier = new Courier();
        courier.setLogin(login);
        courier.setPassword(password);
        return given()
                .header("Content-Type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(Constants.API_FOR_LOGIN_COURIER)
                .then();
    }

    public ValidatableResponse delete(int id) {
        return given()
                .header("Content-Type", "application/json")
                .pathParam("id", id)
                .when()
                .delete(Constants.API_FOR_DELETE_COURIER + "{id}")
                .then();
    }
}
