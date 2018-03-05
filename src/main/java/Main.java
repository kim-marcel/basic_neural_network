import org.ejml.simple.SimpleMatrix;

import java.util.Random;

/**
 * Created by KimFeichtinger on 04.03.18.
 */
public class Main {

    public static void main(String args[]){

        SimpleMatrix[] trainingData = new SimpleMatrix[4];
        for (int i = 0; i < trainingData.length; i++) {
            trainingData[i] = new SimpleMatrix(2,1);
        }

        trainingData[0].set(0,0,0);
        trainingData[0].set(1,0,0);

        trainingData[1].set(0,0,1);
        trainingData[1].set(1,0,1);

        trainingData[2].set(0,0,1);
        trainingData[2].set(1,0,0);

        trainingData[3].set(0,0,0);
        trainingData[3].set(1,0,1);

        SimpleMatrix[] trainingDataTargets = new SimpleMatrix[4];
        for (int i = 0; i < trainingDataTargets.length; i++) {
            trainingDataTargets[i] = new SimpleMatrix(1,1);
        }

        trainingDataTargets[0].set(0,0,0);

        trainingDataTargets[1].set(0,0,0);

        trainingDataTargets[2].set(0,0,1);

        trainingDataTargets[3].set(0,0,1);

        SimpleMatrix testData1 = new SimpleMatrix(2,1);
        testData1.set(0,0,0);
        testData1.set(1,0,0);

        SimpleMatrix testData2 = new SimpleMatrix(2,1);
        testData2.set(0,0,1);
        testData2.set(1,0,1);

        SimpleMatrix testData3 = new SimpleMatrix(2,1);
        testData3.set(0,0,0);
        testData3.set(1,0,1);

        SimpleMatrix testData4 = new SimpleMatrix(2,1);
        testData4.set(0,0,1);
        testData4.set(1,0,0);

//        // IMPORTANT: inputs has to be a one column matrix!
//        SimpleMatrix inputs = new SimpleMatrix(2,1);
//        inputs.set(0.24);
//
//        // correct answers for the given input
//        SimpleMatrix targets = new SimpleMatrix(1,1);
//        targets.set(0.1);

        NeuralNetwork nn = new NeuralNetwork(2,2, 1);

        //System.out.println(nn.feedForward(inputs));

        for (int i = 0; i < 100000; i++) {
            for (int j = 0; j < trainingData.length; j++) {
                nn.train(trainingData[j], trainingDataTargets[j]);
            }
        }

        System.out.println("0,0: " + nn.feedForward(testData1));
        System.out.println("1,1: " + nn.feedForward(testData2));
        System.out.println("0,1: " + nn.feedForward(testData3));
        System.out.println("1,0: " + nn.feedForward(testData4));
//        nn.train(inputs, targets);
    }


}
