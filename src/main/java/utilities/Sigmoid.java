package utilities;

import org.ejml.simple.SimpleMatrix;

/**
 * Created by KimFeichtinger on 05.03.18.
 */
public class Sigmoid {

    public static SimpleMatrix applySigmoid(SimpleMatrix input, boolean derivative){
        SimpleMatrix output = new SimpleMatrix(input.numRows(), input.numCols());
        for (int i = 0; i < input.numRows(); i++) {
            for (int j = 0; j < input.numCols(); j++) {
                double value = input.get(i, j);
                // apply dsigmoid if derivative = true, otherwise usual Sigmoid
                if(derivative){
                    output.set(i, j, dsigmoid(value));
                }else {
                    output.set(i, j, sigmoid(value));
                }
            }
        }

        return output;
    }

    private static double sigmoid(double input){
        return 1 / (1 + Math.exp(-input));
    }

    // derivative of Sigmoid (not real derivative because Sigmoid function has already been applied to the input)
    private static double dsigmoid(double input){
        return input * (1 - input);
    }

}
