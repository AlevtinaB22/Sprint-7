package order;

import base.Constants;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import order.dto.OrderRequest;

import static io.restassured.RestAssured.given;

public class OrderMetods {
    public ValidatableResponse create(String firstName, String lastName, String address, String metroStation, String phone, int rentTime, String deliveryDate, String comment, String color) {
        OrderRequest order = new OrderRequest();
        order.setFirstName(firstName);
        order.setLastName(lastName);
        order.setAddress(address);
        order.setMetroStation(metroStation);
        order.setPhone(phone);
        order.setRentTime(rentTime);
        order.setDeliveryDate(deliveryDate);
        order.setComment(comment);
        if (color == null) {
            order.setColor(null);
        } else {
            order.setColor(color.split(","));
        }
        return given()
                .header("Content-Type", "application/json")
                .and()
                .body(order)
                .when()
                .post(Constants.API_FOR_CREATE_ORDER)
                .then();
    }

    public ValidatableResponse cancel(int track) {
        return given()
                .header("Content-Type", "application/json")
                .pathParam("track", track)
                .when()
                .put(Constants.API_FOR_CANCEL_ORDER + "?track=" + "{track}")
                .then();
    }

    public Response getOrders() {
        return given()
                .get(Constants.API_FOR_GET_ORDERS);
    }
}
