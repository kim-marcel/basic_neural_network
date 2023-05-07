package de.hatoka.basicneuralnetwork.activationfunctions;

/**
 * TanhActivationFunction 
 * <ul>
 * <li>activate: Math.tanh(input)</li>
 * <li>invert:   1 - (input * input)</li>
 * </ul>
 * @author KimFeichtinger
 * @author tbergmann (reduced to function) 
 */
public class TanhActivationFunction implements ActivationFunction
{
    @Override
    public double activate(double input)
    {
        return Math.tanh(input);
    }

    @Override
    public double invert(double input)
    {
        return 1 - (input * input);
    }
}
