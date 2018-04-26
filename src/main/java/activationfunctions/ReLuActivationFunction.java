package activationfunctions;

import org.ejml.simple.SimpleMatrix;

/**
 * Created by KimFeichtinger on 26.04.18.
 */
public class ReLuActivationFunction implements ActivationFunction {

    private static final String NAME = "RELU";

    public SimpleMatrix function(SimpleMatrix input) {
        SimpleMatrix output = new SimpleMatrix(input.numRows(), input.numCols());

        for (int i = 0; i < input.numRows(); i++) {
            // Column is always 0 because input has only one column
            double value = input.get(i, 0);
            double result = 0;

            if (value > 0) {
                result = value;
            }

            output.set(i, 0, result);
        }

        // Formula:
        // for input < 0: 0, else input
        return output;
    }

    public SimpleMatrix dfunction(SimpleMatrix input) {
        SimpleMatrix output = new SimpleMatrix(input.numRows(), input.numCols());

        for (int i = 0; i < input.numRows(); i++) {
            // Column is always 0 because input has only one column
            double value = input.get(i, 0);
            double result = 0;

            if (value > 0) {
                result = 1;
            }

            output.set(i, 0, result);
        }

        // Formula:
        // for input > 0: 1, else 0
        return output;
    }

    public String getName() {
        return NAME;
    }
}
