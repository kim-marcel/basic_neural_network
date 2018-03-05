import java.util.Random;

/**
 * Created by KimFeichtinger on 04.03.18.
 */
public class Main {

    // Example program to test the basic neural network by letting it solve XOR
    public static void main(String args[]){

        Random r = new Random();

        // Training Data
        double[][] trainingData = {
                {0, 0},
                {0, 1},
                {1, 0},
                {1, 1},
        };

        double[][] trainingDataTargets = {
                {0},
                {1},
                {1},
                {0},
        };

        // Testing Data
        double[][] testingData = {
                {0, 0},
                {0, 1},
                {1, 0},
                {1, 1},
        };

        NeuralNetwork nn = new NeuralNetwork(2,2, 1);

        // training
        for (int i = 0; i < 50000; i++) {
            // training in random order
            int random = r.nextInt(4);
            nn.train(trainingData[random], trainingDataTargets[random]);
        }

        // testing the nn
        for (int i = 0; i < testingData.length; i++) {
            System.out.println("Guess for " + testingData[i][0] + ", " + testingData[i][1] + ": \n" + nn.feedForward(testingData[i]));
        }
    }


}
