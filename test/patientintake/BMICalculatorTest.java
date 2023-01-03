package patientintake;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("BMI Calculator should")
class BMICalculatorTest {
    @Test
    @DisplayName("calculate BMI to two places correctly via inches and pounds")
    void calculateCorrectly() {
        assertEquals(19.2, BMICalculator.calculateBMI(69, 130));
        assertEquals(21.52, BMICalculator.calculateBMI(70, 150));
    }
}