package basicneuralnetwork;

import basicneuralnetwork.activationfunctions.ActivationFunction;
import basicneuralnetwork.activationfunctions.SigmoidActivationFunction;
import org.ejml.simple.SimpleMatrix;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NeuralNetworkTest {

    int inputNodes = 1;
    int hiddenLayers = 2;
    int hiddenNodes = 3;
    int outputNodes = 4;

    NeuralNetwork nn;

    @BeforeEach
    public void setup() {
        nn = new NeuralNetwork(inputNodes, hiddenLayers, hiddenNodes, outputNodes);
    }

    @Test
    public void initializeDefaultValuesTest() {
        assertEquals(0.1, nn.getLearningRate());
        assertEquals(ActivationFunction.SIGMOID, nn.getActivationFunctionName());
    }

    @Test
    public void initializeWeightsTest() {
        SimpleMatrix[] weights = nn.getWeights();
        assertEquals(hiddenLayers + 1, weights.length);
        assertEquals(inputNodes, weights[0].numCols());
        assertEquals(hiddenNodes, weights[0].numRows());
        assertEquals(hiddenNodes, weights[weights.length - 1].numCols());
        assertEquals(outputNodes, weights[weights.length - 1].numRows());

        for (SimpleMatrix weight : weights) {
            for (int i = 0; i < weight.getNumElements(); i++) {
                double value = weight.get(i);
                assertTrue(value >= -1 && value <= 1);
            }
        }
    }

    @Test
    public void initializeBiasesTest() {
        SimpleMatrix[] biases = nn.getBiases();
        assertEquals(hiddenLayers + 1, biases.length);
        assertEquals(1, biases[0].numCols());
        assertEquals(hiddenNodes, biases[0].numRows());
        assertEquals(1, biases[biases.length - 1].numCols());
        assertEquals(outputNodes, biases[biases.length - 1].numRows());

        for (SimpleMatrix bias : biases) {
            for (int i = 0; i < bias.getNumElements(); i++) {
                double value = bias.get(i);
                assertTrue(value >= -1 && value <= 1);
            }
        }
    }

    @Test
    public void guessTestWrongDimension() {
        Throwable exception = assertThrows(WrongDimensionException.class, () -> nn.guess(new double[] {0, 1}));
        assertEquals("Expected 1 value(s) for Input-layer but got 2.", exception.getMessage());
    }

    @Test
    public void guessTest() {
        double[] result = nn.guess(new double[] {1});
        assertEquals(outputNodes, result.length);
    }

    @Test
    public void trainTestWrongDimensionInput() {
        Throwable exception = assertThrows(WrongDimensionException.class, () -> nn.train(new double[] {0, 1}, new double[] {0, 1}));
        assertEquals("Expected 1 value(s) for Input-layer but got 2.", exception.getMessage());
    }

    @Test
    public void trainTestWrongDimensionOutput() {
        Throwable exception = assertThrows(WrongDimensionException.class, () -> nn.train(new double[] {0}, new double[] {0, 1}));
        assertEquals("Expected 4 value(s) for Output-layer but got 2.", exception.getMessage());
    }

    @Test
    public void trainTest() {
        assertAll(() -> nn.train(new double[] {0}, new double[] {0, 1, 2, 3}));
    }

    @Test
    public void copyTest() {
        NeuralNetwork nnB = nn.copy();

        assertNotEquals(nn, nnB);

        assertEquals(nn.getInputNodes(), nnB.getInputNodes());
        assertEquals(nn.getHiddenNodes(), nnB.getHiddenNodes());
        assertEquals(nn.getHiddenLayers(), nnB.getHiddenLayers());
        assertEquals(nn.getOutputNodes(), nnB.getOutputNodes());

        assertNotEquals(nn.getWeights(), nnB.getWeights());
        assertNotEquals(nn.getBiases(), nnB.getBiases());

        assertEquals(nn.getLearningRate(), nnB.getLearningRate());
        assertEquals(nn.getActivationFunctionName(), nnB.getActivationFunctionName());
    }

    @Test
    public void mergeTestWrongDimension() {
        Throwable exception = assertThrows(WrongDimensionException.class, () -> nn.merge(new NeuralNetwork(2,3,4,5)));
        assertEquals("The dimensions of these two neural networks don't match: [1, 2, 3, 4], [2, 3, 4, 5]", exception.getMessage());
    }

    @Test
    public void mergeTest() {
        NeuralNetwork nnB = new NeuralNetwork(1,2,3,4);
        NeuralNetwork result = nn.merge(nnB);

        assertNotEquals(nn, result);
        assertNotEquals(nnB, result);

        assertEquals(nn.getLearningRate(), result.getLearningRate());
        assertEquals(nn.getActivationFunctionName(), result.getActivationFunctionName());

        for (int i = 0; i < result.getWeights().length; i++) {
            SimpleMatrix resultWeights = result.getWeights()[i];
            SimpleMatrix nnWeights = nn.getWeights()[i];
            SimpleMatrix nnBWeights = nnB.getWeights()[i];
            for (int j = 0; j < resultWeights.getNumElements(); j++) {
                double value = resultWeights.get(j);
                assertTrue(value == nnWeights.get(j) || value == nnBWeights.get(j));
            }
        }

        for (int i = 0; i < result.getBiases().length; i++) {
            SimpleMatrix resultBiases = result.getBiases()[i];
            SimpleMatrix nnBiases = nn.getBiases()[i];
            SimpleMatrix nnBBiases = nnB.getBiases()[i];
            for (int j = 0; j < resultBiases.getNumElements(); j++) {
                double value = resultBiases.get(j);
                assertTrue(value == nnBiases.get(j) || value == nnBBiases.get(j));
            }
        }
    }

    @Test
    public void mutateTest() {
        assertAll(() -> nn.mutate(0.5));
    }

    @Test
    public void writeToFileTest() {
        assertAll(() -> nn.writeToFile());
    }

    @Test
    public void readFromFileTest() {
        nn.writeToFile();
        assertAll(() -> NeuralNetwork.readFromFile());
    }

    @Test
    public void addActivationFunctionTest() {
        ActivationFunction activation = new SigmoidActivationFunction();
        nn.addActivationFunction("TestFunction", activation);
        nn.setActivationFunction("TestFunction");
        assertEquals("TestFunction", nn.getActivationFunctionName());
        assertAll(() -> nn.guess(new double[] {1}));
    }

}