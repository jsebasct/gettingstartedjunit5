package patientintake;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@Tag("dateTime")
@DisplayName("DateTimeConverter should")
class DateTimeConverterTest {

    @Test
    @DisplayName("convert string with 'today' keyword correctly")
    void convertTodayStringCorrectly() {
        LocalDate today = LocalDate.of(2018, 9, 1);
        LocalDateTime result = DateTimeConverter.convertToDateFromString("today 1:00 pm",
                today);
        assertEquals(result,
                LocalDateTime.of(2018, 9, 1, 13, 0),
                () -> "Failed to convert 'today' string to a expected date time, today passed was: " + today
        );
    }

    @Test
    void convertCorrectPatterToDateTime() {
        LocalDate today = LocalDate.of(2018, 9, 1);
        LocalDateTime result = DateTimeConverter.convertToDateFromString("9/2/2018 1:00 pm", today);

        assertEquals(result,
                LocalDateTime.of(2018, 9, 2, 13, 0),
                () -> "Failed to convert 'today' string to a expected date time, today passed was: " + today
        );
    }

    @Test
    void throwExceptionIfIncorrectPatternProvided() {

        Throwable error = assertThrows(RuntimeException.class, () ->
                DateTimeConverter.convertToDateFromString(
                        "9/2/2018 100 pm",
                                LocalDate.of(2018, 9, 1))
                );
        assertEquals("Unable to create date time from:" +
                " [9/2/2018 100 PM], please enter with format [M/d/yyyy h:mm a]Text '9/2/2018 100 PM'" +
                " could not be parsed at index 12", error.getMessage());
    }
}