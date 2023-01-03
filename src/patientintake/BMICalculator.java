package patientintake;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BMICalculator {
    public static double calculateBMI(int inches, int pounds) {
       double BMI = (double) (pounds * 703) / (inches * inches);
        return new BigDecimal(BMI)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }
}
