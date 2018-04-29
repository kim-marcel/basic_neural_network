package basicneuralnetwork.utilities;

import basicneuralnetwork.NeuralNetwork;
import org.ejml.simple.SimpleMatrix;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by KimFeichtinger on 26.04.18.
 */
public class FileReaderAndWriter {

    public static void writeToFile(NeuralNetwork nn){
        JSONObject nnData = new JSONObject();

        nnData.put("dimensions", getDimensionsFromNN(nn));
        nnData.put("weights", getWeightsFromNN(nn));
        nnData.put("biases", getBiasesFromNN(nn));

        nnData.put("learningRate", nn.getLearningRate());
        nnData.put("activationFunction", nn.getActivationFunctionName());

        try (FileWriter file = new FileWriter("nn_data.json")) {

            file.write(nnData.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static NeuralNetwork readFromFile() {
        JSONParser parser = new JSONParser();

        NeuralNetwork nn = null;

        try {
            // Read file and parse it into a JSONObject
            JSONObject nnData = (JSONObject) parser.parse(new FileReader("nn_data.json"));

            // Get the dimensions of the nn from the nnData
            JSONObject dimensions = getDimensionsFromJSON(nnData);

            // Write the dimensions into the variables
            int inputNodes = Integer.parseInt(dimensions.get("inputNodes").toString());
            int hiddenLayers = Integer.parseInt(dimensions.get("hiddenLayers").toString());
            int hiddenNodes = Integer.parseInt(dimensions.get("hiddenNodes").toString());
            int outputNodes = Integer.parseInt(dimensions.get("outputNodes").toString());

            // Make new NN with dimensions of the one from the file and fill its weights and biases
            nn = new NeuralNetwork(inputNodes, hiddenLayers, hiddenNodes, outputNodes);

            SimpleMatrix weights[] = getWeightsFromJSON((JSONObject) nnData.get("weights"), hiddenLayers);
            SimpleMatrix biases[] = getBiasesFromJSON((JSONObject) nnData.get("biases"), hiddenLayers);

            nn.setWeights(weights);
            nn.setBiases(biases);

            nn.setLearningRate((Double) nnData.get("learningRate"));
            nn.setActivationFunction(nnData.get("activationFunction").toString());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return nn;
    }

    // *** Methods used to help to write to file ***
    private static JSONObject getDimensionsFromNN(NeuralNetwork nn){
        JSONObject dimensions = new JSONObject();

        dimensions.put("inputNodes", nn.getInputNodes());
        dimensions.put("hiddenLayers", nn.getHiddenLayers());
        dimensions.put("hiddenNodes", nn.getHiddenNodes());
        dimensions.put("outputNodes", nn.getOutputNodes());

        return dimensions;
    }

    private static JSONObject getWeightsFromNN(NeuralNetwork nn){
        JSONObject weights = new JSONObject();

        SimpleMatrix[] weightsData = nn.getWeights();

        for (int i = 0; i < weightsData.length; i++) {
            weights.put("weights" + i, MatrixConverter.matrixToJson(weightsData[i]));
        }

        return weights;
    }

    private static JSONObject getBiasesFromNN(NeuralNetwork nn){
        JSONObject biases = new JSONObject();

        SimpleMatrix[] biasesData = nn.getBiases();

        for (int i = 0; i < biasesData.length; i++) {
            biases.put("biases" + i, MatrixConverter.matrixToJson(biasesData[i]));
        }

        return biases;
    }

    // *** Methods used to help to read from file ***
    private static JSONObject getDimensionsFromJSON(JSONObject nnData){
        return (JSONObject) nnData.get("dimensions");
    }

    private static SimpleMatrix[] getWeightsFromJSON(JSONObject nnData, int layers){
        // Layers + 1 because the output layer is not included in the hiddenLayers
        SimpleMatrix weights[] = new SimpleMatrix[layers + 1];

        for (int i = 0; i < weights.length; i++) {
            JSONObject weightsJSON = (JSONObject) nnData.get("weights" + i);
            weights[i] = MatrixConverter.jsonToMatrix(weightsJSON);
        }

        return weights;
    }

    private static SimpleMatrix[] getBiasesFromJSON(JSONObject nnData, int layers){
        // Layers + 1 because the output layer is not included in the hiddenLayers
        SimpleMatrix biases[] = new SimpleMatrix[layers + 1];

        for (int i = 0; i < biases.length; i++) {
            JSONObject biasesJSON = (JSONObject) nnData.get("biases" + i);
            biases[i] = MatrixConverter.jsonToMatrix(biasesJSON);
        }

        return biases;
    }
}
