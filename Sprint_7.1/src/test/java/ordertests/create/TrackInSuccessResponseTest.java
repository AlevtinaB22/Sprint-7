package ordertests.create;

import base.Constants;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import net.datafaker.Faker;
import order.OrderMetods;
import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.notNullValue;

public class TrackInSuccessResponseTest {
    Faker faker = new Faker(new Locale("ru"));
    private OrderMetods orderMetods = new OrderMetods();
    private String firstName = faker.name().firstName();
    private String lastName = faker.name().lastName();
    private String address = faker.address().fullAddress();
    private String metroStation = faker.number().numberBetween(1, 237) + "";
    private String phone = faker.phoneNumber().subscriberNumber(11);
    private int rentTime = faker.number().numberBetween(1, 10);
    private String deliveryDate = faker.date().future(7, TimeUnit.DAYS, "YYYY-MM-dd");
    private String comment = faker.number() + "";
    private int track;

    @Before
    public void setUp() {
        RestAssured.baseURI = Constants.BASE_URI;
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    @Test
    @DisplayName("status code 201 with different color")
    @Description("status code 201, track in response")
    public void trackTest() {
        track = orderMetods.create(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, null).extract().body().path("track");
        MatcherAssert.assertThat(track, notNullValue());

    }

    @After
    public void cancellOrder() {
        orderMetods.cancel(track);
    }
}