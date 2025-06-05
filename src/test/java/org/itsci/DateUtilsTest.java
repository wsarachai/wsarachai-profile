package org.itsci;

import org.itsci.utils.DateUtils;
import org.junit.jupiter.api.*;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class DateUtilsTest {
    protected static Date testDate = DateUtils.getDateFrom(2568, 2, 18);

    @BeforeAll
    public static void setup() {
        System.out.println("Date Utils test setup");
    }

    @AfterAll
    public static void tearDown() {
        System.out.println("Date Utils test teardown");
    }

    @Test
    void testNormalDate() {
        // Convert the Date object to ZonedDateTime
        ZonedDateTime actualDateTime = testDate.toInstant().atZone(DateUtils.timeZone);

        // Define the expected ZonedDateTime (adjust for Buddhist calendar if needed)
        ZonedDateTime expectedDateTime = ZonedDateTime.of(2568, 2, 18, 0, 0, 0, 0, DateUtils.timeZone);

        // Assert the date is correct
        assertEquals(expectedDateTime.toLocalDate(), actualDateTime.toLocalDate(),
                "The returned date does not match the expected date.");
    }

    @Test
    void testDateUsingDateTimeFormatter() {
        // Convert the Date object to LocalDate
        LocalDate actualDate = testDate.toInstant()
                .atZone(java.time.ZoneId.systemDefault())
                .toLocalDate();

        // Define the expected date (adjust for Buddhist calendar if needed)
        LocalDate expectedDate = LocalDate.of(2568, 2, 18); // Example for Gregorian calendar

        // Format both dates using DateTimeFormatter
        String actualDateString = actualDate.format(DateUtils.dateFormat);
        String expectedDateString = expectedDate.format(DateUtils.dateFormat);

        // Assert the formatted strings match
        assertEquals(expectedDateString, actualDateString,
                "The returned date does not match the expected date.");
    }

    @Test
    void testLeapYearDate() {
        assertNotNull(testDate);
        // Verify the date is correct if 2568 is a leap year
    }

    @Test
    void testInvalidMonth() {
        assertThrows(DateTimeException.class, () -> {
            DateUtils.getDateFrom(2568, 13, 18);
        });
    }

    @Test
    void testInvalidDay() {
        assertThrows(DateTimeException.class, () -> {
            DateUtils.getDateFrom(2568, 2, 30);
        });
    }

    @Test
    void testEdgeCaseMinimumDate() {
        Date date = DateUtils.getDateFrom(544, 1, 1);
        assertNotNull(date);
        // Additional assertions for date correctness
    }

    @Test
    void testEdgeCaseMaximumDate() {
        Date date = DateUtils.getDateFrom(9999, 12, 31);
        assertNotNull(date);
        // Additional assertions for date correctness
    }

    @Test
    void testZeroOrNegativeValues() {
        assertThrows(IllegalArgumentException.class, () -> {
            DateUtils.getDateFrom(-1, 0, 0);
        });
    }

    @BeforeEach
    public void openSession() {
        System.out.println("Date Utils open session");
    }

    @AfterEach
    public void closeSession() {
        System.out.println("Date Utils close session\n");
    }
}
