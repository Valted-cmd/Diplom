package test.ui;

import com.codeborne.selenide.logevents.SelenideLogger;
import data.*;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import page.MainPage;
import page.PayPage;

import static com.codeborne.selenide.Selenide.open;
import static data.DataBaseAssistant.*;
import static data.DataGenerator.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UIPayPageTest {

    static PayPage page;
    static Card card;

    @BeforeAll
    public static void setUpClass() {
        SelenideLogger.addListener(
                "allure", new AllureSelenide()
                        .screenshots(true)
                        .savePageSource(true));
    }

    @BeforeEach
    public void openPageAndCleanDB() {
        open("http://localhost:8080/");
        page = new MainPage().getPayPage();
        cleanData();
    }
    @AfterAll
    public static void setDownClass() {
        SelenideLogger.removeListener("allure");
    }

    @Test
    @DisplayName("Оплата по карте: дебетовая со статусом APPROVED")
    public void testDebitPayApprovedCard() {
        card = getValidApprovedCard();
        page.pay(card);
        page.checkErrorNotificationAbsent();
        page.checkSuccessNotificationPresent();

        OrderEntity order = getOrderEntity();
        PaymentEntity payment = getPaymentEntity();

        assertNotNull(order);
        assertNotNull(payment);

        assertEquals(order.getPayment_id(), payment.getTransaction_id());
        assertEquals("approved", payment.getStatus().toLowerCase());
        assertEquals(45_000, payment.getAmount());
    }

    @Test
    @DisplayName("Оплата по карте: дебетовая со статусом DECLINED")
    public void testDebitPayDeclinedCard() {
        card = getValidDeclinedCard();
        page.pay(card);
        page.checkSuccessNotificationAbsent();
        page.checkErrorNotificationPresent();

        OrderEntity order = getOrderEntity();
        PaymentEntity payment = getPaymentEntity();

        assertNotNull(order);
        assertNotNull(payment);

        assertEquals(order.getPayment_id(), payment.getTransaction_id());
        assertEquals("declined", payment.getStatus().toLowerCase());
        assertEquals(45_000, payment.getAmount());
    }

    @Test
    @DisplayName("Оплата по карте, заполненная данными карты, отсутствующими в БД банка")
    public void testDebitPayCardNotInDB() {
        card = getCardNotInDB();
        page.pay(card);
        page.checkErrorNotificationPresent();
        page.checkSuccessNotificationAbsent();

        OrderEntity order = getOrderEntity();
        PaymentEntity payment = getPaymentEntity();

        assertNotNull(order);
        assertNotNull(payment);

        assertEquals(order.getPayment_id(), payment.getTransaction_id());
        assertEquals("declined", payment.getStatus().toLowerCase());
        assertEquals(45_000, payment.getAmount());
    }

    @Test
    @DisplayName("Оплата по карте, значение поля \"Номер карты\" пустое")
    public void testDebitPayEmptyCardNumber() {
        card = getEmptyCardNumberCard();
        page.pay(card);
        page.checkErrorNotificationAbsent();
        page.checkSuccessNotificationAbsent();
        page.wrongFormatError("Номер карты");
    }

    @Test
    @DisplayName("Оплата по карте, значение поля \"Номер карты\" содержит одну цифру")
    public void testDebitPayOneDigitCardNumber() {
        card = getOneDigitCardNumberCard();
        page.pay(card);
        page.checkErrorNotificationAbsent();
        page.checkSuccessNotificationAbsent();
        page.wrongFormatError("Номер карты");
    }

    @Test
    @DisplayName("Оплата по карте, значение поля \"Номер карты\" содержит нули")
    public void testDebitPayCardNumberOfZeros() {
        card = getCardNumberOfZerosCard();
        page.pay(card);
        page.checkErrorNotificationAbsent();
        page.checkSuccessNotificationAbsent();
        page.wrongFormatError("Номер карты");
    }

    @Test
    @DisplayName("Оплата по карте, значение поля \"Номер карты\" из 15 цифр")
    public void testDebitPayFifteenDigitsCardNumber() {
        card = getFifteenDigitsCardNumberCard();
        page.pay(card);
        page.checkErrorNotificationAbsent();
        page.checkSuccessNotificationAbsent();
        page.wrongFormatError("Номер карты");
    }

    @Test
    @DisplayName("Оплата по карте, значение поля \"Месяц\" пустое")
    public void testDebitPayEmptyMonth() {
        card = getEmptyMonthCard();
        page.pay(card);
        page.checkErrorNotificationAbsent();
        page.checkSuccessNotificationAbsent();
        page.fieldIsEmptyError("Месяц");
    }

    @Test
    @DisplayName("Оплата по карте, значение поля \"Месяц\" больше \"12\"")
    public void testDebitPayMonthValueIsGreaterTwelve() {
        card = getMonthGreaterTwelveCard();
        page.pay(card);
        page.checkErrorNotificationAbsent();
        page.checkSuccessNotificationAbsent();
        page.invalidDateError("Месяц");
    }

    @Test
    @DisplayName("Оплата по карте, значение поля \"Месяц\" меньше \"01\"")
    public void testDebitPayMonthValueIsLessOne() {
        card = getMonthLessOneCard();
        page.pay(card);
        page.checkErrorNotificationAbsent();
        page.checkSuccessNotificationAbsent();
        page.invalidDateError("Месяц");
    }

    @Test
    @DisplayName("Оплата по карте, значение поля \"Месяц\" - месяц, предыдущий от текущего")
    public void testDebitPayPreviousMonth() {
        card = getPreviousMonthCard();
        page.pay(card);
        page.checkErrorNotificationAbsent();
        page.checkSuccessNotificationAbsent();
        page.invalidDateError("Месяц");
    }

    @Test
    @DisplayName("Оплата по карте, значение поля \"Год\" пустое")
    public void testDebitPayEmptyYear() {
        card = getEmptyYearCard();
        page.pay(card);
        page.checkErrorNotificationAbsent();
        page.checkSuccessNotificationAbsent();
        page.fieldIsEmptyError("Год");
    }

    @Test
    @DisplayName("Оплата по карте, значение поля \"Год\" 00")
    public void testDebitPayYearOfZeros() {
        card = getYearOfZerosCard();
        page.pay(card);
        page.checkErrorNotificationAbsent();
        page.checkSuccessNotificationAbsent();
        page.expiredDateError("Год");
    }

    @Test
    @DisplayName("Оплата по карте, значение поля \"Год\" прошлого периода")
    public void testDebitPreviousYear() {
        card = getPreviousYearCard();
        page.pay(card);
        page.checkErrorNotificationAbsent();
        page.checkSuccessNotificationAbsent();
        page.expiredDateError("Год");
    }

    @Test
    @DisplayName("Оплата по карте, значение поля \"Год\" далекого будущего периода")
    public void testDebitFarFutureYear() {
        card = getFarFutureYearCard();
        page.pay(card);
        page.checkErrorNotificationAbsent();
        page.checkSuccessNotificationAbsent();
        page.invalidDateError("Год");
    }

    @Test
    @DisplayName("Оплата по карте, значение поля \"CVC/CVV\" пусто")
    public void testDebitEmptyCvv() {
        card = getEmptyCvvCard();
        page.pay(card);
        page.checkErrorNotificationAbsent();
        page.checkSuccessNotificationAbsent();
        page.fieldIsEmptyError("CVC/CVV");
    }

    @Test
    @DisplayName("Оплата по карте, значение поля \"CVC/CVV\" 1 цифра")
    public void testDebitOneDigitCvv() {
        card = getOneDigitCvvCard();
        page.pay(card);
        page.checkErrorNotificationAbsent();
        page.checkSuccessNotificationAbsent();
        page.wrongFormatError("CVC/CVV");
    }

    @Test
    @DisplayName("Оплата по карте, значение поля \"CVC/CVV\" из 2 цифр")
    public void testDebitTwoDigitCvv() {
        card = getTwoDigitsCvvCard();
        page.pay(card);
        page.checkErrorNotificationAbsent();
        page.checkSuccessNotificationAbsent();
        page.wrongFormatError("CVC/CVV");
    }

    @Test
    @DisplayName("Оплата по карте, значение поля \"CVC/CVV\"  равно \"000\"")
    public void testDebitCvvOfZeros() {
        card = getCvvOfZerosCard();
        page.pay(card);
        page.checkErrorNotificationAbsent();
        page.checkSuccessNotificationPresent();
    }

    @Test
    @DisplayName("Оплата по карте, значение поля \"Владелец\" оформлено в верхнем регистре")
    public void testDebitCapitalOwner() {
        card = getCapitalOwnerCard();
        page.pay(card);
        page.checkErrorNotificationAbsent();
        page.checkSuccessNotificationPresent();
    }

    @Test
    @DisplayName("Оплата по карте, значение поля \"Владелец\" через дефис")
    public void testDebitOwnerWithDash() {
        card = getOwnerWithDashCard();
        page.pay(card);
        page.checkErrorNotificationAbsent();
        page.checkSuccessNotificationPresent();
    }

    @Test
    @DisplayName("Оплата по карте, значение поля \"Владелец\" пустое")
    public void testDebitEmptyOwner() {
        card = getEmptyOwnerCard();
        page.pay(card);
        page.checkErrorNotificationAbsent();
        page.checkSuccessNotificationAbsent();
        page.fieldIsEmptyError("Владелец");
    }

    @Test
    @DisplayName("Оплата по карте, значение поля \"Владелец\" одним словом на латинице")
    public void testDebitOneWordOwner() {
        card = getOneWordOwnerCard();
        page.pay(card);
        page.checkErrorNotificationAbsent();
        page.checkSuccessNotificationAbsent();
        page.wrongFormatError("Владелец");
    }

    @Test
    @DisplayName("Оплата по карте, значение поля \"Владелец\" фамилия и имя на кириллице")
    public void testDebitCyrillicOwner() {
        card = getCyrillicOwnerCard();
        page.pay(card);
        page.checkErrorNotificationAbsent();
        page.checkSuccessNotificationAbsent();
        page.wrongFormatError("Владелец");
    }

    @Test
    @DisplayName("Оплата по карте, значение поля \"Владелец\" заполнено спец.символами")
    public void testDebitSymbolsOwner() {
        card = getSymbolsOwnerCard();
        page.pay(card);
        page.checkErrorNotificationAbsent();
        page.checkSuccessNotificationAbsent();
        page.wrongFormatError("Владелец");
    }
}