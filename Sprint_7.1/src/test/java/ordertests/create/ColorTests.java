package ordertests.create;

import base.Constants;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import net.datafaker.Faker;
import order.OrderMetods;
import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class ColorTests {
    Faker faker = new Faker(new Locale("ru"));
    private final OrderMetods orderMetods = new OrderMetods();
    private final String firstName = faker.name().firstName();
    private final String lastName = faker.name().lastName();
    private final String address = faker.address().fullAddress();
    private final String metroStation = faker.number().numberBetween(1, 237) + "";
    private final String phone = faker.phoneNumber().subscriberNumber(11);
    private final int rentTime = faker.number().numberBetween(1, 10);
    private final String deliveryDate = faker.date().future(7, TimeUnit.DAYS, "YYYY-MM-dd");
    private final String comment = faker.number() + "";
    private int track;
    @Parameterized.Parameter(0)
    public String colorName;
    @Parameterized.Parameter(1)
    public String colorNameForDescription;
    @Parameterized.Parameter(2)
    public int statusCode;

    @Before
    public void setUp() {
        RestAssured.baseURI = Constants.BASE_URI;
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    @Parameterized.Parameters(name = "{index}.colorName={1}")
    public static Object[][] getData() {
        return new Object[][]{
                {null, "without color", 201},
                {"BLACK", "with black color", 201},
                {"BLACK,GREY", "with black and gray color", 201}
        };
    }

    @Test
    public void colorTests() {
        track = orderMetods.create(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, colorName).extract().body().path("track");
        MatcherAssert.assertThat(track, notNullValue());
    }

    @After
    public void cancellOrder() {
        orderMetods.cancel(track);
    }
}
