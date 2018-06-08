package basicneuralnetwork.activationfunctions;

import org.ejml.simple.SimpleMatrix;

/**
 * Created by KimFeichtinger on 20.04.18.
 */
// This interface and it's methods have to be implemented in all ActivationFunction-classes
public interface ActivationFunction {

    String SIGMOID = SigmoidActivationFunction.NAME;
    String TANH = TanhActivationFunction.NAME;
    String RELU = ReLuActivationFunction.NAME;

    // Activation function
    SimpleMatrix applyActivationFunctionToMatrix(SimpleMatrix input);

    // Derivative of activation function (not real derivative because Activation function has already been applied to the input)
    SimpleMatrix applyDerivativeOfActivationFunctionToMatrix(SimpleMatrix input);

    String getName();
}
