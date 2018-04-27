package basicneuralnetwork.activationfunctions;

import org.ejml.simple.SimpleMatrix;

/**
 * Created by KimFeichtinger on 20.04.18.
 */
public class SigmoidActivationFunction implements ActivationFunction {

    private static final String NAME = "SIGMOID";

    // Sigmoid
    public SimpleMatrix applyActivationFunctionToMatrix(SimpleMatrix input) {
        SimpleMatrix output = new SimpleMatrix(input.numRows(), input.numCols());

        for (int i = 0; i < input.numRows(); i++) {
            // Column is always 0 because input has only one column
            double value = input.get(i, 0);
            double result = 1 / (1 + Math.exp(-value));

            output.set(i, 0, result);
        }

        // Formula:
        // 1 / (1 + Math.exp(-input));
        return output;
    }

    // Derivative of Sigmoid (not real derivative because Activation function has already been applied to the input)
    public SimpleMatrix applyDerivativeOfActivationFunctionToMatrix(SimpleMatrix input) {
        SimpleMatrix output = new SimpleMatrix(input.numRows(), input.numCols());

        for (int i = 0; i < input.numRows(); i++) {
            // Column is always 0 because input has only one column
            double value = input.get(i, 0);
            double result = value * (1 - value);

            output.set(i, 0, result);
        }

        // Formula:
        // input * (1 - input);
        return output;
    }

    public String getName() {
        return NAME;
    }
}
