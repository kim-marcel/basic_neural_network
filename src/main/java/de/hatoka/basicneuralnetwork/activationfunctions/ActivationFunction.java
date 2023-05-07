package de.hatoka.basicneuralnetwork.activationfunctions;

import org.ejml.simple.SimpleMatrix;

/**
 * Created by KimFeichtinger on 20.04.18.
 */
public interface ActivationFunction {

    // Activation function
    SimpleMatrix applyActivationFunctionToMatrix(SimpleMatrix input);

    // Derivative of activation function (not real derivative because Activation function has already been applied to the input)
    SimpleMatrix applyDerivativeOfActivationFunctionToMatrix(SimpleMatrix input);
}
