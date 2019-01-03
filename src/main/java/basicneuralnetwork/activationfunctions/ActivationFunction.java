package basicneuralnetwork.activationfunctions;

import org.ejml.simple.SimpleMatrix;

/**
 * Created by KimFeichtinger on 20.04.18.
 */
public abstract class ActivationFunction {

    // Activation function
    public SimpleMatrix applyActivationFunctionToMatrix(SimpleMatrix input) {
        SimpleMatrix output = new SimpleMatrix(input.numRows(), input.numCols());
        for (int i = 0; i < input.numRows(); i++) {
            for (int j = 0; j < input.numCols(); j ++) {
                double value = input.get(i, j);
                output.set(i, j, apply(value));
            }
        }
        return output;
    }

    // Derivative of activation function (not real derivative because Activation function has already been applied to the input)
    public SimpleMatrix applyDerivativeOfActivationFunctionToMatrix(SimpleMatrix input) {
        SimpleMatrix output = new SimpleMatrix(input.numRows(), input.numCols());
        for (int i = 0; i < input.numRows(); i++) {
            for (int j = 0; j < input.numCols(); j ++) {
                double value = input.get(i, j);
                output.set(i, j, applyDerivative(value));
            }
        }
        return output;
    }
    
    /** Applies the function to a single value */
    protected abstract double apply(double value);
    
    /** Applies the pseudo-derivative of the function to a single value */
    protected abstract double applyDerivative(double value);
    
    /** Returns the name of the function */
    public abstract String getName();
}
