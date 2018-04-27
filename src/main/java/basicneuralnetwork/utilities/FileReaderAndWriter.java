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

    public static void writeToFile(SimpleMatrix[] weights, SimpleMatrix[] biases){
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

    public static NeuralNetwork readFromFile() {
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

            nn.setWeights(weights);
            nn.setBiases(biases);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return nn;
    }
}
