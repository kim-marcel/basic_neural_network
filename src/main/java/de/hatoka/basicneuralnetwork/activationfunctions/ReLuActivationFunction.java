package de.hatoka.basicneuralnetwork.activationfunctions;

/**
 * ReLuActivationFunction 
 * <ul>
 * <li>activate: if 0 < input then input else 0</li>
 * <li>invert:   if 0 < input then 1     else 0</li>
 * </ul>
 * @author KimFeichtinger
 * @author tbergmann (reduced to function) 
 */
public class ReLuActivationFunction implements ActivationFunction
{
    @Override
    public double activate(double input)
    {
        return 0 < input ? input : 0;
    }

    @Override
    public double invert(double input)
    {
        return 0 < input ? 1 : 0;
    }
}
