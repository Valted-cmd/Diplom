package data;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DataGenerator {

    private static final Faker faker = new Faker();
    private static final String approvedCardNumber = "4444 4444 4444 4441";
    private static final String declinedCardNumber = "4444 4444 4444 4442";

    private static String generateMonth(int i) {
        return LocalDate.now().plusMonths(i).format(DateTimeFormatter.ofPattern("MM"));
    }

    private static String generateYear(int i) {
        return LocalDate.now().plusYears(i).format(DateTimeFormatter.ofPattern("yy"));
    }

    private static String generateHolder() {
        return faker.name().fullName().toUpperCase();
    }

    private static String generateCVC() {
        return faker.numerify("###");
    }

    public static Card getValidApprovedCard() {
        return new Card(
                approvedCardNumber,
                generateMonth(0),
                generateYear(0),
                generateHolder(),
                generateCVC()
        );
    }

    public static Card getValidDeclinedCard() {
        return new Card(
                declinedCardNumber,
                generateMonth(0),
                generateYear(0),
                generateHolder(),
                generateCVC()
        );
    }

    public static Card getCardNotInDB() {
        return new Card(
                "1111 1111 1111 1111",
                generateMonth(0),
                generateYear(0),
                generateHolder(),
                generateCVC()
        );
    }

    public static Card getEmptyCardNumberCard() {
        return new Card(
                "",
                generateMonth(0),
                generateYear(0),
                generateHolder(),
                generateCVC()
        );
    }

    public static Card getOneDigitCardNumberCard() {
        return new Card(
                faker.numerify("#"),
                generateMonth(0),
                generateYear(0),
                generateHolder(),
                generateCVC()
        );
    }

    public static Card getCardNumberOfZerosCard() {
        return new Card(
                "0000 0000 0000 0000",
                generateMonth(0),
                generateYear(0),
                generateHolder(),
                generateCVC()
        );
    }

    public static Card getFifteenDigitsCardNumberCard() {
        return new Card(
                faker.numerify("#### #### #### ###"),
                generateMonth(0),
                generateYear(0),
                generateHolder(),
                generateCVC()
        );
    }

    public static Card getEmptyMonthCard() {
        return new Card(
                approvedCardNumber,
                "",
                generateYear(0),
                generateHolder(),
                generateCVC()
        );
    }

    public static Card getMonthGreaterTwelveCard() {
        return new Card(
                approvedCardNumber,
                "55",
                generateYear(0),
                generateHolder(),
                generateCVC()
        );
    }

    public static Card getMonthLessOneCard() {
        return new Card(
                approvedCardNumber,
                "00",
                generateYear(0),
                generateHolder(),
                generateCVC()
        );
    }

    public static Card getPreviousMonthCard() {
        return new Card(
                approvedCardNumber,
                generateMonth(-1),
                generateYear(0),
                generateHolder(),
                generateCVC()
        );
    }

    public static Card getEmptyYearCard() {
        return new Card(
                approvedCardNumber,
                generateMonth(0),
                "",
                generateHolder(),
                generateCVC()
        );
    }

    public static Card getYearOfZerosCard() {
        return new Card(
                approvedCardNumber,
                generateMonth(0),
                "00",
                generateHolder(),
                generateCVC()
        );
    }

    public static Card getPreviousYearCard() {
        return new Card(
                approvedCardNumber,
                generateMonth(0),
                generateYear(-1),
                generateHolder(),
                generateCVC()
        );
    }

    public static Card getFarFutureYearCard() {
        return new Card(
                approvedCardNumber,
                generateMonth(0),
                generateYear(6),
                generateHolder(),
                generateCVC()
        );
    }

    public static Card getEmptyCvvCard() {
        return new Card(
                approvedCardNumber,
                generateMonth(0),
                generateYear(0),
                generateHolder(),
                ""
        );
    }

    public static Card getOneDigitCvvCard() {
        return new Card(
                approvedCardNumber,
                generateMonth(0),
                generateYear(0),
                generateHolder(),
                faker.numerify("#")
        );
    }

    public static Card getTwoDigitsCvvCard() {
        return new Card(
                approvedCardNumber,
                generateMonth(0),
                generateYear(0),
                generateHolder(),
                faker.numerify("##")
        );
    }

    public static Card getCvvOfZerosCard() {
        return new Card(
                approvedCardNumber,
                generateMonth(0),
                generateYear(0),
                generateHolder(),
                "000"
        );
    }

    public static Card getCapitalOwnerCard() {
        return new Card(
                approvedCardNumber,
                generateMonth(0),
                generateYear(0),
                generateHolder().toUpperCase(),
                generateCVC()
        );
    }

    public static Card getOwnerWithDashCard() {
        return new Card(
                approvedCardNumber,
                generateMonth(0),
                generateYear(0),
                "TREGULOV-SAMOILOV BOGDAN",
                generateCVC()
        );
    }

    public static Card getEmptyOwnerCard() {
        return new Card(
                approvedCardNumber,
                generateMonth(0),
                generateYear(0),
                "",
                generateCVC()
        );
    }

    public static Card getOneWordOwnerCard() {
        return new Card(
                approvedCardNumber,
                generateMonth(0),
                generateYear(0),
                "KUZMITCH",
                generateCVC()
        );
    }

    public static Card getCyrillicOwnerCard() {
        return new Card(
                approvedCardNumber,
                generateMonth(0),
                generateYear(0),
                "ДАРМОЕДОВ БОРИСЛАВ",
                generateCVC()
        );
    }

    public static Card getSymbolsOwnerCard() {
        return new Card(
                approvedCardNumber,
                generateMonth(0),
                generateYear(0),
                "][@|<~$^ **#!",
                generateCVC()
        );
    }
}