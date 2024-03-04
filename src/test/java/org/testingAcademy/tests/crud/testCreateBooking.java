package org.testingAcademy.tests.crud;

import io.qameta.allure.*;
import io.restassured.RestAssured;
import jdk.jfr.Description;
import org.testingAcademy.base.BaseTest;
import org.testingAcademy.endPoints.APIConstants;
import org.testingAcademy.modules.PayloadManager;
import org.testingAcademy.pojos.BookingResopns;
import org.testng.annotations.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class testCreateBooking extends BaseTest {


    @Test
    @Owner("Promode")
    @Severity(SeverityLevel.NORMAL)
    @Description("TC#1 - Verify that the Booking can be Created")
    public void testCreateBooking() {
        requestSpecification.basePath(APIConstants.CREATE_UPDATE_BOOKING_URL);


        response = RestAssured.given().spec(requestSpecification)
                .when().body(PayloadManager.createPayloadGSON()).post();



        validatableResponse = response.then().log().all();
        BookingResopns.BookingRespons bookingRespons = payloadManager.bookingResponseJava(response.asString());

        // Validatable Default
        validatableResponse.statusCode(200);

        // Assert J
        assertThat(bookingRespons.getBookingid()).isNotNull();
        assertThat(bookingRespons.getBooking().getFirstname()).isNotNull().isNotBlank();
        assertThat(bookingRespons.getBooking().getFirstname()).isEqualTo("Pramod");

        // TestNG Assertions
        assertActions.verifyStatusCode(response);

    }

    @Test
    @Owner("Promode")
    @Severity(SeverityLevel.NORMAL)
    @Description("TC#2 - Verify that the Booking not created when Payload is missing")
    public void testCreateBookingNegative() {
        requestSpecification.basePath(APIConstants.CREATE_UPDATE_BOOKING_URL);
        response = RestAssured.given().spec(requestSpecification)
                .when().body("{}").post();
        validatableResponse = response.then().log().all();
        // TestNG Assertions
        assertActions.verifyStatusCodeInvalidReq(response);
    }


}
