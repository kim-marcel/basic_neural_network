package de.hatoka.basicneuralnetwork.activationfunctions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import org.junit.jupiter.api.Test;

class SigmoidActivationFunctionTest
{
    private final SigmoidActivationFunction underTest = new SigmoidActivationFunction();

    @Test
    void testActivate()
    {
        assertEquals(forCompare(0.5498), forCompare(underTest.activate(0.2)));
        assertEquals(forCompare(0.4502), forCompare(underTest.activate(-0.2d)));
    }

    @Test
    void testInvert()
    {
        assertEquals(forCompare(0.16), forCompare(underTest.invert(0.2)));
        assertEquals(forCompare(-0.24), forCompare(underTest.invert(-0.2)));
    }

    /**
     * Avoid numeric difference between real and possible doubles, e.g. 0.16 will double  0.160000003
     * @param value a double value
     * @return decimal number with 5 significant digits
     */
    private BigDecimal forCompare(double value)
    {
        return new BigDecimal(value).round(new MathContext(5, RoundingMode.HALF_EVEN));
    }
}
