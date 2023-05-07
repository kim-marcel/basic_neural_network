package de.hatoka.basicneuralnetwork.activationfunctions;

/**
 * Created by KimFeichtinger on 04.05.18.
 */
public enum ActivationFunctions
{
    SIGMOID(new SigmoidActivationFunction()), TANH(new TanhActivationFunction()), RELU(new ReLuActivationFunction());

    private final ActivationFunction function;

    private ActivationFunctions(ActivationFunction f)
    {
        this.function = f;
    }

    public ActivationFunction getFunction()
    {
        return function;
    }
}
