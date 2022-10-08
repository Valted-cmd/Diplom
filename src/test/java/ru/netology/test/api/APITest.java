package ru.netology.test.api;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.data.APIHelper;
import ru.netology.data.Data;

public class APITest {
    Data.CardInfo approvedCardInfo = Data.getApprovedCard();
    Data.CardInfo declinedCardInfo = Data.getDeclinedCard();

    @BeforeAll
    static void setUp() {
        RestAssured.filters(
                new RequestLoggingFilter(),
                new ResponseLoggingFilter(),
                new AllureRestAssured());
    }

    @DisplayName("Запрос на покупку по карте со статусом APPROVED")
    @Test
    void shouldApprovePayment() {
        APIHelper.payDebitCard((approvedCardInfo));
    }

    @DisplayName("Запрос на кредит по карте со статусом APPROVED")
    @Test
    void shouldApproveCredit() {
        APIHelper.payCreditCard(approvedCardInfo);
    }

    @DisplayName("Запрос на покупку по карте со статусом DECLINED")
    @Test
    void shouldDeclinePayment() {
        APIHelper.createPaymentError(declinedCardInfo);
    }

    @DisplayName("Запрос на кредит по карте со статусом DECLINED")
    @Test
    void shouldDeclineCredit() {
        APIHelper.createCreditError(declinedCardInfo);
    }
}