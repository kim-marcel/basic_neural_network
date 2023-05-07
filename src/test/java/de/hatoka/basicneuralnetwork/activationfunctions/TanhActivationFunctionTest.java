package de.hatoka.basicneuralnetwork.activationfunctions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class TanhActivationFunctionTest
{
    private final TanhActivationFunction underTest = new TanhActivationFunction();

    @Test
    void testActivate()
    {
        assertEquals(0.197375320224904, underTest.activate(0.2));
        assertEquals(-0.197375320224904, underTest.activate(-0.2d));
        assertEquals(0, underTest.activate(0));
    }

    @Test
    void testInvert()
    {
        assertEquals(0.96, underTest.invert(0.2));
        assertEquals(0.96, underTest.invert(-0.2));
        assertEquals(1, underTest.invert(0));
    }
}
