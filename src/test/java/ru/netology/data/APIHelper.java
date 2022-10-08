
package ru.netology.data;

import com.google.gson.Gson;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;



public class APIHelper {
    private static final Gson gson = new Gson();
    private static Data.CardInfo cardInfo;
    private static final RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://185.119.57.64")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    public static void payDebitCard(Data.CardInfo approvedCardInfo){
        cardInfo = Data.getApprovedCard();
        var body = gson.toJson(cardInfo);
        given()
                .spec(requestSpec)
                .body(body)
                .when()
                .post("/payment")
                .then()
                .statusCode(200);
    }

    public static void payCreditCard(Data.CardInfo approvedCardInfo){
        cardInfo = Data.getApprovedCard();
        given()
                .spec(requestSpec)
                .body(cardInfo)
                .when()
                .post("/credit")
                .then()
                .statusCode(200);

    }

    public static void createPaymentError(Data.CardInfo declinedCardInfo){
        cardInfo = Data.getDeclinedCard();
        given()
                .spec(requestSpec)
                .body(cardInfo)
                .when()
                .post("/payment")
                .then()
                .statusCode(200);
    }

    public static void createCreditError(Data.CardInfo declinedCardInfo){
        cardInfo = Data.getDeclinedCard();
        given()
                .spec(requestSpec)
                .body(cardInfo)
                .when()
                .post("/credit")
                .then()
                .statusCode(200);
    }
}