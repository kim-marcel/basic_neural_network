package de.hatoka.basicneuralnetwork.activationfunctions;

import java.util.function.Function;

import org.ejml.simple.SimpleMatrix;

/**
 * ActivationFunction is a manipulation of input of the neural network. So only "column=0" is manipulated in the matrix.
 * @author KimFeichtinger
 * @author tbergmann (central matrix function) 
 */
public interface ActivationFunction
{
    static final int COLUMN_NULL = 0;

    /**
     * Applies the activation function on one single value
     * 
     * @param input value in the matrix
     * @return value after applying the function
     */
    double activate(double value);

    /**
     * Applies the derivative of the activation function to the given matrix. (not real derivative because activation
     * function has already been applied to // the input and information could got lost)
     * 
     * @param input original value
     * @return value applying the derivative activation function
     */
    double invert(double value);

    /**
     * Applies the activation function to the given matrix.
     * 
     * @param input original matrix
     * @return matrix after applying the activation function
     */
    default SimpleMatrix activate(SimpleMatrix input)
    {
        return apply(input, this::activate);
    }

    /**
     * Applies the derivative of the activation function to the given matrix. (not real derivative because activation
     * function has already been applied to // the input and information could got lost)
     * 
     * @param input original matrix
     * @return matrix after applying the derivative activation function
     */
    default SimpleMatrix invert(SimpleMatrix input)
    {
        return apply(input, this::invert);
    }

    /**
     * Applies a function to the values of the given matrix.
     * 
     * @param input original matrix
     * @return matrix after applying the activation function
     */
    private static SimpleMatrix apply(SimpleMatrix input, Function<Double, Double> f)
    {
        SimpleMatrix output = new SimpleMatrix(input.numRows(), input.numCols());
        for (int i = 0; i < input.numRows(); i++)
        {
            output.set(i, COLUMN_NULL, f.apply(input.get(i, COLUMN_NULL)));
        }
        return output;
    }
}
