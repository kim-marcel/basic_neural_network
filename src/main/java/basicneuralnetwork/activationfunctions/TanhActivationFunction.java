package basicneuralnetwork.activationfunctions;

import org.ejml.simple.SimpleMatrix;

/**
 * Created by KimFeichtinger on 20.04.18.
 */
public class TanhActivationFunction implements ActivationFunction {

    private static final String NAME = "TANH";

    public SimpleMatrix applyActivationFunctionToMatrix(SimpleMatrix input) {
        SimpleMatrix output = new SimpleMatrix(input.numRows(), input.numCols());

        for (int i = 0; i < input.numRows(); i++) {
            // Column is always 0 because input has only one column
            double value = input.get(i, 0);
            double result = Math.tanh(value);

            output.set(i, 0, result);
        }

        // Formula:
        // 2 * (1 / (1 + Math.exp(2 * -input))) - 1;
        // Math.tanh(input);
        return output;
    }

    public SimpleMatrix applyDerivativeOfActivationFunctionToMatrix(SimpleMatrix input) {
        SimpleMatrix output = new SimpleMatrix(input.numRows(), input.numCols());

        for (int i = 0; i < input.numRows(); i++) {
            // Column is always 0 because input has only one column
            double value = input.get(i, 0);
            double result = 1 - (value * value);

            output.set(i, 0, result);
        }

        // Formula:
        // 1 - (input * input);
        return output;
    }

    public String getName() {
        return NAME;
    }
}
