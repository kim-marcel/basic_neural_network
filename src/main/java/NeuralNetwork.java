import org.ejml.simple.SimpleMatrix;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import utilities.MatrixConverter;
import utilities.Sigmoid;

import java.io.*;
import java.util.Random;

/**
 * Created by KimFeichtinger on 04.03.18.
 */
public class NeuralNetwork {

    Random r = new Random();

    private double learningRate = 0.1;

    // Dimensions of the neural network
    private int inputNodes;
    private int hiddenLayers; // only until multi layered networks are supported and parameter is added to constructor
    private int hiddenNodes;
    private int outputNodes;

    private SimpleMatrix[] weights;
    private SimpleMatrix[] biases;

    // Constructor
    // Generate a new neural network with 1 hidden layer with the given amount of nodes in the individual layers
    // Every hidden layer will have the same amount of nodes
    public NeuralNetwork(int inputNodes, int hiddenNodes, int outputNodes){
        this.inputNodes = inputNodes;
        this.hiddenLayers = 1;
        this.hiddenNodes = hiddenNodes;
        this.outputNodes = outputNodes;

        initializeWeights();
        initializeBiases();
    }

    // Constructor
    // Generate a new neural network with a given amount of hidden layers with the given amount of nodes in the individual layers
    public NeuralNetwork(int inputNodes, int hiddenLayers, int hiddenNodes, int outputNodes){
        this.inputNodes = inputNodes;
        this.hiddenLayers = hiddenLayers;
        this.hiddenNodes = hiddenNodes;
        this.outputNodes = outputNodes;

        initializeWeights();
        initializeBiases();
    }

    private void initializeWeights(){
        weights = new SimpleMatrix[hiddenLayers + 1];

        // Initialize the weights between the layers and fill them with random values
        for (int i = 0; i < weights.length; i++) {
            if(i == 0){ // 1st weights that connects inputs to first hidden layer
                weights[i] = SimpleMatrix.random64(hiddenNodes, inputNodes, -1, 1, r);
            }else if(i == weights.length - 1){ // last weights that connect last hidden layer to output
                weights[i] = SimpleMatrix.random64(outputNodes, hiddenNodes, -1, 1, r);
            }else{ // everything else
                weights[i] = SimpleMatrix.random64(hiddenNodes, hiddenNodes, -1, 1, r);
            }
        }
    }

    private void initializeBiases(){
        biases = new SimpleMatrix[hiddenLayers + 1];

        // Initialize the biases and fill them with random values
        for (int i = 0; i < biases.length; i++) {
            if(i == biases.length - 1){ // bias for last layer (output layer)
                biases[i] = SimpleMatrix.random64(outputNodes, 1, -1, 1, r);
            }else{
                biases[i] = SimpleMatrix.random64(hiddenNodes, 1, -1, 1, r);
            }
        }
    }

    // Guess method, input is a one column matrix with the input values
    public double[][] guess(double[] input){
        // Transform array to matrix
        SimpleMatrix output = MatrixConverter.arrayToMatrix(input);

        for (int i = 0; i < hiddenLayers + 1; i++) {
            output = calculateLayer(weights[i], biases[i], output);
        }

        return MatrixConverter.matrixToArray(output);
    }

    public void train(double[] i, double[] t){
        // Transform 2D array to matrix
        SimpleMatrix inputs = MatrixConverter.arrayToMatrix(i);
        SimpleMatrix targets = MatrixConverter.arrayToMatrix(t);

        // Calculate the values of every single layer
        SimpleMatrix layers[] = new SimpleMatrix[hiddenLayers + 2];
        layers[0] = inputs;
        for (int j = 1; j < hiddenLayers + 2; j++) {
            layers[j] = calculateLayer(weights[j - 1], biases[j - 1], inputs);
            inputs = layers[j];
        }

        for (int n = hiddenLayers + 1; n > 0; n--) {
            // Calculate error
            SimpleMatrix errors = targets.minus(layers[n]);

            // Calculate gradient
            SimpleMatrix gradients = calculateGradient(layers[n], errors);

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
    private SimpleMatrix calculateLayer(SimpleMatrix weights, SimpleMatrix bias, SimpleMatrix input){
        // Calculate outputs of layer
        SimpleMatrix result = weights.mult(input);
        // Add bias to outputs
        result = result.plus(bias);
        // Apply activation function and return result
        return Sigmoid.applySigmoid(result, false);
    }

    private SimpleMatrix calculateGradient(SimpleMatrix layer, SimpleMatrix error){
        SimpleMatrix gradient = Sigmoid.applySigmoid(layer, true);
        gradient = gradient.elementMult(error);
        return gradient.scale(learningRate);
    }

    private SimpleMatrix calculateDeltas(SimpleMatrix gradient, SimpleMatrix layer){
        return gradient.mult(layer.transpose());
    }

    public void writeToFile(){
        JSONObject nnData = new JSONObject();

        for (int i = 0; i < weights.length; i++) {
            nnData.put("weights" + i, MatrixConverter.matrixToJSON(weights[i]));
        }

        for (int i = 0; i < biases.length; i++) {
            nnData.put("bias" + i, MatrixConverter.matrixToJSON(biases[i]));
        }

        try (FileWriter file = new FileWriter("nn_data.json")) {

            file.write(nnData.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static NeuralNetwork readFromFile(){
        JSONParser parser = new JSONParser();

        NeuralNetwork nn = null;

        try {
            JSONObject nnData = (JSONObject) parser.parse(new FileReader("nn_data.json"));

            int inputNodes;
            int hiddenLayers = (nnData.size() / 2) - 1;
            int hiddenNodes;
            int outputNodes;

            SimpleMatrix weights[] = new SimpleMatrix[hiddenLayers + 1];
            SimpleMatrix biases[] = new SimpleMatrix[hiddenLayers + 1];

            for (int i = 0; i < weights.length; i++) {
                JSONObject weightsJSON = (JSONObject) nnData.get("weights" + i);
                weights[i] = MatrixConverter.jsonToMatrix(weightsJSON);
            }

            for (int i = 0; i < biases.length; i++) {
                JSONObject biasesJSON = (JSONObject) nnData.get("bias" + i);
                biases[i] = MatrixConverter.jsonToMatrix(biasesJSON);
            }

            // Get dimensions of the neural network from the file
            inputNodes = weights[0].numCols();
            hiddenNodes = biases[biases.length - 2].numRows();
            outputNodes = biases[biases.length - 1].numRows();

            // Make new NN with dimensions of the one from the file and fill its weights and biases
            nn = new NeuralNetwork(inputNodes, hiddenLayers, hiddenNodes, outputNodes);

            nn.weights = weights;
            nn.biases = biases;

            return nn;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return nn;
    }

    public double getLearningRate() {
        return learningRate;
    }

    public void setLearningRate(double learningRate) {
        this.learningRate = learningRate;
    }

}
