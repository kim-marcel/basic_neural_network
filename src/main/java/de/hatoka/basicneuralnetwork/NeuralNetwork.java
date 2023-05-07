package de.hatoka.basicneuralnetwork;

import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

import org.ejml.simple.SimpleMatrix;

import com.google.gson.annotations.Expose;

import de.hatoka.basicneuralnetwork.activationfunctions.ActivationFunction;
import de.hatoka.basicneuralnetwork.activationfunctions.ActivationFunctions;
import de.hatoka.basicneuralnetwork.utilities.MatrixUtilities;

/**
 * Created by KimFeichtinger on 04.03.18.
 */
public class NeuralNetwork
{
    private Random random = new Random();

    // Dimensions of the neural network
    @Expose(serialize = true, deserialize = true)
    private int inputNodes;
    @Expose(serialize = true, deserialize = true)
    private int hiddenLayers;
    @Expose(serialize = true, deserialize = true)
    private int hiddenNodes;
    @Expose(serialize = true, deserialize = true)
    private int outputNodes;

    @Expose(serialize = true, deserialize = true)
    private SimpleMatrix[] weights;
    @Expose(serialize = true, deserialize = true)
    private SimpleMatrix[] biases;

    @Expose(serialize = true, deserialize = true)
    private double learningRate;

    @Expose(serialize = true, deserialize = true)
    private ActivationFunctions activationFunctionKey;

    // Constructor
    // Generate a new neural network with 1 hidden layer with the given amount of
    // nodes in the individual layers
    public NeuralNetwork(int inputNodes, int hiddenNodes, int outputNodes)
    {
        this(inputNodes, 1, hiddenNodes, outputNodes);
    }

    // Constructor
    // Generate a new neural network with a given amount of hidden layers with the
    // given amount of nodes in the individual layers
    // Every hidden layer will have the same amount of nodes
    public NeuralNetwork(int inputNodes, int hiddenLayers, int hiddenNodes, int outputNodes)
    {
        this.inputNodes = inputNodes;
        this.hiddenLayers = hiddenLayers;
        this.hiddenNodes = hiddenNodes;
        this.outputNodes = outputNodes;

        initializeDefaultValues();
        initializeWeights();
        initializeBiases();
    }

    // Copy constructor
    public NeuralNetwork(NeuralNetwork nn)
    {
        this.inputNodes = nn.inputNodes;
        this.hiddenLayers = nn.hiddenLayers;
        this.hiddenNodes = nn.hiddenNodes;
        this.outputNodes = nn.outputNodes;

        this.weights = new SimpleMatrix[hiddenLayers + 1];
        this.biases = new SimpleMatrix[hiddenLayers + 1];

        for (int i = 0; i < nn.weights.length; i++)
        {
            this.weights[i] = nn.weights[i].copy();
        }

        for (int i = 0; i < nn.biases.length; i++)
        {
            this.biases[i] = nn.biases[i].copy();
        }

        this.learningRate = nn.learningRate;

        this.activationFunctionKey = nn.activationFunctionKey;
    }

    private void initializeDefaultValues()
    {
        this.setLearningRate(0.1);

        // Sigmoid is the default ActivationFunction
        this.setActivationFunction(ActivationFunctions.SIGMOID);
    }

    private void initializeWeights()
    {
        weights = new SimpleMatrix[hiddenLayers + 1];

        // Initialize the weights between the layers and fill them with random values
        for (int i = 0; i < weights.length; i++)
        {
            if (i == 0)
            { // 1st weights that connects inputs to first hidden layer
                weights[i] = SimpleMatrix.random64(hiddenNodes, inputNodes, -1, 1, random);
            }
            else if (i == weights.length - 1)
            { // last weights that connect last hidden layer to output
                weights[i] = SimpleMatrix.random64(outputNodes, hiddenNodes, -1, 1, random);
            }
            else
            { // everything else
                weights[i] = SimpleMatrix.random64(hiddenNodes, hiddenNodes, -1, 1, random);
            }
        }
    }

    private void initializeBiases()
    {
        biases = new SimpleMatrix[hiddenLayers + 1];

        // Initialize the biases and fill them with random values
        for (int i = 0; i < biases.length; i++)
        {
            if (i == biases.length - 1)
            { // bias for last layer (output layer)
                biases[i] = SimpleMatrix.random64(outputNodes, 1, -1, 1, random);
            }
            else
            {
                biases[i] = SimpleMatrix.random64(hiddenNodes, 1, -1, 1, random);
            }
        }
    }

    // Guess method, input is a one column matrix with the input values
    public double[] guess(double[] input)
    {
        if (input.length != inputNodes)
        {
            throw new WrongDimensionException(input.length, inputNodes, "Input");
        }
        else
        {
            ActivationFunction activationFunction = activationFunctionKey.getFunction();

            // Transform array to matrix
            SimpleMatrix output = MatrixUtilities.arrayToMatrix(input);

            for (int i = 0; i < hiddenLayers + 1; i++)
            {
                output = calculateLayer(weights[i], biases[i], output, activationFunction);
            }

            return MatrixUtilities.getColumnFromMatrixAsArray(output, 0);
        }
    }

    public void train(double[] inputArray, double[] targetArray)
    {
        if (inputArray.length != inputNodes)
        {
            throw new WrongDimensionException(inputArray.length, inputNodes, "Input");
        }
        else if (targetArray.length != outputNodes)
        {
            throw new WrongDimensionException(targetArray.length, outputNodes, "Output");
        }
        else
        {
            // Get ActivationFunction-object from the map by key
            ActivationFunction activationFunction = activationFunctionKey.getFunction();

            // Transform 2D array to matrix
            SimpleMatrix input = MatrixUtilities.arrayToMatrix(inputArray);
            SimpleMatrix target = MatrixUtilities.arrayToMatrix(targetArray);

            // Calculate the values of every single layer
            SimpleMatrix layers[] = new SimpleMatrix[hiddenLayers + 2];
            layers[0] = input;
            for (int j = 1; j < hiddenLayers + 2; j++)
            {
                layers[j] = calculateLayer(weights[j - 1], biases[j - 1], input, activationFunction);
                input = layers[j];
            }

            for (int n = hiddenLayers + 1; n > 0; n--)
            {
                // Calculate error
                SimpleMatrix errors = target.minus(layers[n]);

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
                target = previousError.plus(layers[n - 1]);
            }
        }
    }

    // Generates an exact copy of a NeuralNetwork
    public NeuralNetwork copy()
    {
        return new NeuralNetwork(this);
    }

    // Merges the weights and biases of two NeuralNetworks and returns a new object
    // Merge-ratio: 50:50 (half of the values will be from nn1 and other half from
    // nn2)
    public NeuralNetwork merge(NeuralNetwork nn)
    {
        return this.merge(nn, 0.5);
    }

    // Merges the weights and biases of two NeuralNetworks and returns a new object
    // Everything besides the weights and biases will be the same
    // of the object on which this method is called (Learning Rate, activation
    // function, etc.)
    // Merge-ratio: defined by probability
    public NeuralNetwork merge(NeuralNetwork nn, double probability)
    {
        // Check whether the nns have the same dimensions
        if (!Arrays.equals(this.getDimensions(), nn.getDimensions()))
        {
            throw new WrongDimensionException(this.getDimensions(), nn.getDimensions());
        }
        else
        {
            NeuralNetwork result = this.copy();

            for (int i = 0; i < result.weights.length; i++)
            {
                result.weights[i] = MatrixUtilities.mergeMatrices(this.weights[i], nn.weights[i], probability);
            }

            for (int i = 0; i < result.biases.length; i++)
            {
                result.biases[i] = MatrixUtilities.mergeMatrices(this.biases[i], nn.biases[i], probability);
            }
            return result;
        }
    }

    // Gaussian mutation with given probability, Slightly modifies values (weights +
    // biases) with given probability
    // Probability: number between 0 and 1
    // Depending on probability more/ less values will be mutated (e.g. prob = 1.0:
    // all the values will be mutated)
    public void mutate(double probability)
    {
        applyMutation(weights, probability);
        applyMutation(biases, probability);
    }

    // Adds a randomly generated gaussian number to each element of a Matrix in an
    // array of matrices
    // Probability: determines how many values will be modified
    private void applyMutation(SimpleMatrix[] matrices, double probability)
    {
        for (SimpleMatrix matrix : matrices)
        {
            for (int j = 0; j < matrix.getNumElements(); j++)
            {
                if (random.nextDouble() < probability)
                {
                    double offset = random.nextGaussian() / 2;
                    matrix.set(j, matrix.get(j) + offset);
                }
            }
        }
    }

    // Generic function to calculate one layer
    private SimpleMatrix calculateLayer(SimpleMatrix weights, SimpleMatrix bias, SimpleMatrix input,
                    ActivationFunction activationFunction)
    {
        // Calculate outputs of layer
        SimpleMatrix result = weights.mult(input);
        // Add bias to outputs
        result = result.plus(bias);
        // Apply activation function and return result
        return applyActivationFunction(result, false, activationFunction);
    }

    private SimpleMatrix calculateGradient(SimpleMatrix layer, SimpleMatrix error,
                    ActivationFunction activationFunction)
    {
        SimpleMatrix gradient = applyActivationFunction(layer, true, activationFunction);
        gradient = gradient.elementMult(error);
        return gradient.scale(learningRate);
    }

    private SimpleMatrix calculateDeltas(SimpleMatrix gradient, SimpleMatrix layer)
    {
        return gradient.mult(layer.transpose());
    }

    // Applies an activation function to a matrix
    // An object of an implementation of the ActivationFunction-interface has to be
    // passed
    // The function in this class will be to the matrix
    private SimpleMatrix applyActivationFunction(SimpleMatrix input, boolean derivative,
                    ActivationFunction activationFunction)
    {
        // Applies either derivative of activation function or regular activation
        // function to a matrix and returns the result
        return derivative ? activationFunction.invert(input)
                        : activationFunction.activate(input);
    }

    public void setActivationFunction(ActivationFunctions activationFunction)
    {
        this.activationFunctionKey = activationFunction;
    }

    public String getActivationFunctionName()
    {
        return this.activationFunctionKey.name();
    }

    public double getLearningRate()
    {
        return learningRate;
    }

    public void setLearningRate(double learningRate)
    {
        this.learningRate = learningRate;
    }

    public int getInputNodes()
    {
        return inputNodes;
    }

    public int getHiddenLayers()
    {
        return hiddenLayers;
    }

    public int getHiddenNodes()
    {
        return hiddenNodes;
    }

    public int getOutputNodes()
    {
        return outputNodes;
    }

    public SimpleMatrix[] getWeights()
    {
        return weights;
    }

    public void setWeights(SimpleMatrix[] weights)
    {
        this.weights = weights;
    }

    public SimpleMatrix[] getBiases()
    {
        return biases;
    }

    public void setBiases(SimpleMatrix[] biases)
    {
        this.biases = biases;
    }

    public int[] getDimensions()
    {
        return new int[] { inputNodes, hiddenLayers, hiddenNodes, outputNodes };
    }

    private int hashCode(SimpleMatrix[] matrices)
    {
        final int prime = 31;
        int result = 1;
        for (int i = 0; i < matrices.length; i++)
        {
            result = prime * result + hashCode(matrices[i]);
        }
        return result;
    }

    private int hashCode(SimpleMatrix matrix)
    {
        final int prime = 31;
        int result = 1;
        for (int i = 0; i < matrix.getNumElements(); i++)
        {
            result = prime * result + Double.valueOf(matrix.get(i)).hashCode();
        }
        return result;
    }

    private boolean equalsMatrix(SimpleMatrix[] a, SimpleMatrix[] b)
    {
        if (a.length != b.length)
        {
            return false;
        }
        for (int i = 0; i < a.length; i++)
        {
            if (!equalsMatrix(a[i], b[i]))
            {
                return false;
            }
        }
        return true;
    }

    private boolean equalsMatrix(SimpleMatrix a, SimpleMatrix b)
    {
        return a.isIdentical(b, 0d);
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + Objects.hash(activationFunctionKey.name(), hiddenLayers, hiddenNodes, inputNodes,
                        learningRate, outputNodes);
        result = prime * result + hashCode(biases);
        result = prime * result + hashCode(weights);
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        NeuralNetwork other = (NeuralNetwork)obj;
        return Objects.equals(activationFunctionKey, other.activationFunctionKey) && equalsMatrix(biases, other.biases)
                        && hiddenLayers == other.hiddenLayers && hiddenNodes == other.hiddenNodes
                        && inputNodes == other.inputNodes
                        && Double.doubleToLongBits(learningRate) == Double.doubleToLongBits(other.learningRate)
                        && outputNodes == other.outputNodes && equalsMatrix(weights, other.weights);
    }
}
