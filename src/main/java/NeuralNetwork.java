import org.ejml.simple.SimpleMatrix;

import java.util.Random;

/**
 * Created by KimFeichtinger on 04.03.18.
 */
public class NeuralNetwork {

    private static final double LEARNING_RATE = 0.1;

    Random r = new Random();

    // variables which store the "size" of the neural network
    private int inputNodes;
    private int hiddenNodes;
    private int outputNodes;

    private SimpleMatrix weightsIH; // matrix with weights between input and hidden layer
    private SimpleMatrix weightsHO; // matrix with weights between hidden and output layer

    private SimpleMatrix biasH;
    private SimpleMatrix biasO;

    // Constructor
    // generate a new neural network with 1 hidden layer with the given amount of nodes in the layers
    public NeuralNetwork(int inputNodes, int hiddenNodes, int outputNodes){
        this.inputNodes = inputNodes;
        this.hiddenNodes = hiddenNodes;
        this.outputNodes = outputNodes;

        // initialize the weights between the layers and fill them with random values
        weightsIH = SimpleMatrix.random64(hiddenNodes, inputNodes, -1, 1, r);
        weightsHO = SimpleMatrix.random64(outputNodes, hiddenNodes, -1, 1, r);

        // initialize the biases and fill them with random values
        biasH = SimpleMatrix.random64(hiddenNodes, 1, -1, 1, r);
        biasO = SimpleMatrix.random64(outputNodes, 1, -1, 1, r);
    }

    // feedForward method, input is a one column matrix with the input values
    public SimpleMatrix feedForward(SimpleMatrix inputs){
        SimpleMatrix hidden = calculateLayer(weightsIH, biasH, inputs);
        SimpleMatrix output = calculateLayer(weightsHO, biasO, hidden);
        return output;
    }

    public void train(SimpleMatrix inputs, SimpleMatrix targets){
        // calculate outputs of hidden and output layer for the given inputs
        SimpleMatrix hidden = calculateLayer(weightsIH, biasH, inputs);
        SimpleMatrix outputs = calculateLayer(weightsHO, biasO, hidden);

        // calculate the errors
        SimpleMatrix outputErrors = targets.minus(outputs); // errors that happen in the output layer
        SimpleMatrix hiddenErrors = weightsHO.transpose().mult(outputErrors); // errors that happen in the hidden layer

        // calculate gradients and delta weights
        SimpleMatrix outputGradient = calculateGradient(outputs, outputErrors);
        SimpleMatrix outputDeltaWeights = calculateDeltas(outputGradient, hidden);

        SimpleMatrix hiddenGradient = calculateGradient(hidden, hiddenErrors);
        SimpleMatrix hiddenDeltaWeights = calculateDeltas(hiddenGradient, inputs);

        // adjust the weights by deltas (add deltas to the current weights)
        weightsHO = weightsHO.plus(outputDeltaWeights);
        weightsIH = weightsIH.plus(hiddenDeltaWeights);

        // adjust the bias by its deltas (gradients)
        biasO = biasO.plus(outputGradient);
        biasH = biasH.plus(hiddenGradient);
    }

    // ***** Helping methods: *****

    // generic function to calculate one layer
    private SimpleMatrix calculateLayer(SimpleMatrix weights, SimpleMatrix bias, SimpleMatrix input){
        // calculate outputs of layer
        SimpleMatrix result = weights.mult(input);
        // add bias to outputs
        result = result.plus(bias);
        // apply activation function and return result
        result = applySigmoid(result, false);
        return result;
    }

    private SimpleMatrix calculateGradient(SimpleMatrix layer, SimpleMatrix error){
        SimpleMatrix gradient = applySigmoid(layer, true);
        gradient = gradient.elementMult(error);
        return gradient.scale(LEARNING_RATE);
    }

    private SimpleMatrix calculateDeltas(SimpleMatrix gradient, SimpleMatrix layer){
        return gradient.mult(layer.transpose());
    }

    private SimpleMatrix applySigmoid(SimpleMatrix input, boolean derivative){
        SimpleMatrix output = new SimpleMatrix(input.numRows(), input.numCols());
        for (int i = 0; i < input.numRows(); i++) {
            for (int j = 0; j < input.numCols(); j++) {
                double value = input.get(i, j);
                // apply dsigmoid if derivative = true, otherwise usual sigmoid
                if(derivative){
                    output.set(i, j, dsigmoid(value));
                }else {
                    output.set(i, j, sigmoid(value));
                }
            }
        }

        return output;
    }

    private double sigmoid(double input){
        return 1 / (1 + Math.exp(-input));
    }

    // derivative of sigmoid (not real derivative because sigmoid function has already been applied to the input)
    private double dsigmoid(double input){
        return input * (1 - input);
    }
}
