package de.hatoka.basicneuralnetwork.activationfunctions;

/**
 * SigmoidActivationFunction 
 * <ul>
 * <li>activate: 1 / (1 + Math.exp(-input))</li>
 * <li>invert:   input * (1 - input)</li>
 * </ul>
 * @author KimFeichtinger
 * @author tbergmann (reduced to function) 
 */
public class SigmoidActivationFunction implements ActivationFunction
{
    @Override
    public double activate(double input)
    {
        return 1 / (1 + Math.exp(-input));
    }

    @Override
    public double invert(double input)
    {
        return input * (1 - input);
    }
}
