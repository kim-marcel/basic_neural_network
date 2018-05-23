package basicneuralnetwork.utilities;

import basicneuralnetwork.WrongDimensionException;
import org.ejml.simple.SimpleMatrix;

import java.util.Random;

/**
 * Created by KimFeichtinger on 07.03.18.
 */
public class MatrixUtilities {

    // Converts a 2D array into a SimpleMatrix
    public static SimpleMatrix arrayToMatrix(double[] i) {
        double[][] input = {i};
        return new SimpleMatrix(input).transpose();
    }

    // Converts a SimpleMatrix into a 2D array
    public static double[][] matrixTo2DArray(SimpleMatrix i) {
        double[][] result = new double[i.numRows()][i.numCols()];

        for (int j = 0; j < result.length; j++) {
            for (int k = 0; k < result[0].length; k++) {
                result[j][k] = i.get(j, k);
            }
        }
        return result;
    }

    // Returns one specific column of a matrix as a 1D array
    public static double[] getColumnFromMatrixAsArray(SimpleMatrix data, int column) {
        double[] result = new double[data.numRows()];

        for (int i = 0; i < result.length; i++) {
            result[i] = data.get(i, column);
        }

        return result;
    }

    // Merge two matrices and return a new one
    public static SimpleMatrix mergeMatrices(SimpleMatrix matrixA, SimpleMatrix matrixB, double probability) {
        if (matrixA.numCols() != matrixB.numCols() || matrixA.numRows() != matrixB.numRows()) {
            throw new WrongDimensionException();
        } else {
            Random random = new Random();
            SimpleMatrix result = new SimpleMatrix(matrixA.numRows(), matrixA.numCols());

            for (int i = 0; i < matrixA.getNumElements(); i++) {
                // %-chance of replacing this value with the one from the input nn
                if (random.nextDouble() > probability) {
                    result.set(i, matrixA.get(i));
                } else {
                    result.set(i, matrixB.get(i));
                }
            }

            return result;
        }
    }

}
