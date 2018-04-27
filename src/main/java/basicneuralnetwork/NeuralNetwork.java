package basicneuralnetwork;

import basicneuralnetwork.activationfunctions.ReLuActivationFunction;
import basicneuralnetwork.activationfunctions.SigmoidActivationFunction;
import basicneuralnetwork.activationfunctions.TanhActivationFunction;
import basicneuralnetwork.activationfunctions.ActivationFunction;
import basicneuralnetwork.utilities.FileReaderAndWriter;
import basicneuralnetwork.utilities.MatrixConverter;
import org.ejml.simple.SimpleMatrix;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by KimFeichtinger on 04.03.18.
 */
public class NeuralNetwork {

    private Map<String, ActivationFunction> activationFunctionMap = new HashMap<String, ActivationFunction>();

    private Random random = new Random();

    private String activationFunctionKey;

    private double learningRate;

    // Dimensions of the neural network
    private int inputNodes;
    private int hiddenLayers;
    private int hiddenNodes;
    private int outputNodes;

    private SimpleMatrix[] weights;
    private SimpleMatrix[] biases;

    // Constructor
    // Generate a new neural network with 1 hidden layer with the given amount of nodes in the individual layers
    // Every hidden layer will have the same amount of nodes
    public NeuralNetwork(int inputNodes, int hiddenNodes, int outputNodes) {
        this.inputNodes = inputNodes;
        this.hiddenLayers = 1;
        this.hiddenNodes = hiddenNodes;
        this.outputNodes = outputNodes;

        initializeDefaultValues();
        initializeWeights();
        initializeBiases();
    }

    // Constructor
    // Generate a new neural network with a given amount of hidden layers with the given amount of nodes in the individual layers
    public NeuralNetwork(int inputNodes, int hiddenLayers, int hiddenNodes, int outputNodes) {
        this.inputNodes = inputNodes;
        this.hiddenLayers = hiddenLayers;
        this.hiddenNodes = hiddenNodes;
        this.outputNodes = outputNodes;

        initializeDefaultValues();
        initializeWeights();
        initializeBiases();
    }

    private void initializeDefaultValues() {
        learningRate = 0.1;

        // Sigmoid is the default ActivationFunction
        activationFunctionKey = ActivationFunction.SIGMOID;

        // Fill map with all the activation functions
        ActivationFunction sigmoid = new SigmoidActivationFunction();
        activationFunctionMap.put(sigmoid.getName(), sigmoid);

        ActivationFunction tanh = new TanhActivationFunction();
        activationFunctionMap.put(tanh.getName(), tanh);

        ActivationFunction relu = new ReLuActivationFunction();
        activationFunctionMap.put(relu.getName(), relu);
    }

    private void initializeWeights() {
        weights = new SimpleMatrix[hiddenLayers + 1];

        // Initialize the weights between the layers and fill them with random values
        for (int i = 0; i < weights.length; i++) {
            if (i == 0) { // 1st weights that connects inputs to first hidden layer
                weights[i] = SimpleMatrix.random64(hiddenNodes, inputNodes, -1, 1, random);
            } else if (i == weights.length - 1) { // last weights that connect last hidden layer to output
                weights[i] = SimpleMatrix.random64(outputNodes, hiddenNodes, -1, 1, random);
            } else { // everything else
                weights[i] = SimpleMatrix.random64(hiddenNodes, hiddenNodes, -1, 1, random);
            }
        }
    }

    private void initializeBiases() {
        biases = new SimpleMatrix[hiddenLayers + 1];

        // Initialize the biases and fill them with random values
        for (int i = 0; i < biases.length; i++) {
            if (i == biases.length - 1) { // bias for last layer (output layer)
                biases[i] = SimpleMatrix.random64(outputNodes, 1, -1, 1, random);
            } else {
                biases[i] = SimpleMatrix.random64(hiddenNodes, 1, -1, 1, random);
            }
        }
    }

    // Guess method, input is a one column matrix with the input values
    public double[] guess(double[] input) {
        // Get ActivationFunction-object from the map by key
        ActivationFunction activationFunction = activationFunctionMap.get(activationFunctionKey);


        // Transform array to matrix
        SimpleMatrix output = MatrixConverter.arrayToMatrix(input);

        for (int i = 0; i < hiddenLayers + 1; i++) {
            output = calculateLayer(weights[i], biases[i], output, activationFunction);
        }

        return MatrixConverter.matrixToArray(output);
    }

    public void train(double[] i, double[] t) {
        // Get ActivationFunction-object from the map by key
        ActivationFunction activationFunction = activationFunctionMap.get(activationFunctionKey);


        // Transform 2D array to matrix
        SimpleMatrix inputs = MatrixConverter.arrayToMatrix(i);
        SimpleMatrix targets = MatrixConverter.arrayToMatrix(t);

        // Calculate the values of every single layer
        SimpleMatrix layers[] = new SimpleMatrix[hiddenLayers + 2];
        layers[0] = inputs;
        for (int j = 1; j < hiddenLayers + 2; j++) {
            layers[j] = calculateLayer(weights[j - 1], biases[j - 1], inputs, activationFunction);
            inputs = layers[j];
        }

        for (int n = hiddenLayers + 1; n > 0; n--) {
            // Calculate error
            SimpleMatrix errors = targets.minus(layers[n]);

            // Calculate gradient
            SimpleMatrix gradients = calculateGradient(layers[n], errors, activationFunction);

            // Calculate delta
            SimpleMatrix deltas = calculateDeltas(gradients, layers[n - 1]);

            // Apply gradient to bias
            biases[n - 1] = biases[n - 1].plus(gradients);

            // Apply delta to weights
            weights[n - 1] = weights[n - 1].plus(deltas);

            // Calculate and set target for previous (next) layer
            SimpleMatrix previousError = weights[n - 1].transpose().mult(errors);
            targets = previousError.plus(layers[n - 1]);
        }
    }

    // Generic function to calculate one layer
    private SimpleMatrix calculateLayer(SimpleMatrix weights, SimpleMatrix bias, SimpleMatrix input, ActivationFunction activationFunction) {
        // Calculate outputs of layer
        SimpleMatrix result = weights.mult(input);
        // Add bias to outputs
        result = result.plus(bias);
        // Apply activation function and return result
        return applyActivationFunction(result, false, activationFunction);
    }

    private SimpleMatrix calculateGradient(SimpleMatrix layer, SimpleMatrix error, ActivationFunction activationFunction) {
        SimpleMatrix gradient = applyActivationFunction(layer, true, activationFunction);
        gradient = gradient.elementMult(error);
        return gradient.scale(learningRate);
    }

    private SimpleMatrix calculateDeltas(SimpleMatrix gradient, SimpleMatrix layer) {
        return gradient.mult(layer.transpose());
    }

    public void writeToFile() {
        FileReaderAndWriter.writeToFile(weights, biases);
    }

    public static NeuralNetwork readFromFile() {
        return FileReaderAndWriter.readFromFile();
    }

    // Applies an activation function to a matrix
    // An object of an implementation of the ActivationFunction-interface has to be passed
    // The function in this class will be  to the matrix
    private SimpleMatrix applyActivationFunction(SimpleMatrix input, boolean derivative, ActivationFunction activationFunction) {
        SimpleMatrix output;

        if (derivative){
            output = activationFunction.applyDerivativeOfActivationFunctionToMatrix(input);
        } else {
            output = activationFunction.applyActivationFunctionToMatrix(input);
        }

        return output;
    }

    public void setActivationFunction(String activationFunction) {
        this.activationFunctionKey = activationFunction;
    }

    public String getActivationFunctionName() {
        return activationFunctionKey;
    }

    public void addActivationFunction(String key, ActivationFunction activationFunction) {
        activationFunctionMap.put(key, activationFunction);
    }

    public double getLearningRate() {
        return learningRate;
    }

    public void setLearningRate(double learningRate) {
        this.learningRate = learningRate;
    }

    public void setWeights(SimpleMatrix[] weights) {
        this.weights = weights;
    }

    public void setBiases(SimpleMatrix[] biases) {
        this.biases = biases;
    }

}
