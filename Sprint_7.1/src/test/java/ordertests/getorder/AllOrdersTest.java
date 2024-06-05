package ordertests.getorder;

import base.Constants;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import order.OrderMetods;
import order.dto.OrderResponse;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;

public class AllOrdersTest {
    private final OrderMetods orderMetods = new OrderMetods();

    @Before
    public void setUp() {

        RestAssured.baseURI = Constants.BASE_URI;
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    @Test
    @DisplayName("status code 200")
    @Description("status code 200")
    public void getOrdersTest() {
        OrderResponse orderResponse = orderMetods.getOrders()
                .as(OrderResponse.class);
        MatcherAssert.assertThat(orderResponse, notNullValue());
    }
}
