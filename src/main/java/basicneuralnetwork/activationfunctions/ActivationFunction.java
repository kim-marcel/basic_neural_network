package basicneuralnetwork.activationfunctions;

import org.ejml.simple.SimpleMatrix;

/**
 * Created by KimFeichtinger on 20.04.18.
 */
// This interface and it's methods have to be implemented in all ActivationFunction-classes
public interface ActivationFunction {

    String SIGMOID = "SIGMOID";
    String TANH = "TANH";
    String RELU = "RELU";

    // Activation function
    SimpleMatrix applyActivationFunctionToMatrix(SimpleMatrix input);

    // Derivative of activation function (not real derivative because Activation function has already been applied to the input)
    SimpleMatrix applyDerivativeOfActivationFunctionToMatrix(SimpleMatrix input);

    String getName();

}
