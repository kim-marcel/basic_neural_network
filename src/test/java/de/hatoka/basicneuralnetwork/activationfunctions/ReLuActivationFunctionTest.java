package de.hatoka.basicneuralnetwork.activationfunctions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ReLuActivationFunctionTest
{
    private final ReLuActivationFunction underTest = new ReLuActivationFunction();

    @Test
    void testActivate()
    {
        assertEquals(0.2d, underTest.activate(0.2));
        assertEquals(0, underTest.activate(-0.2d));
    }

    @Test
    void testInvert()
    {
        assertEquals(1, underTest.invert(0.2));
        assertEquals(0, underTest.invert(-0.2));
    }
}
