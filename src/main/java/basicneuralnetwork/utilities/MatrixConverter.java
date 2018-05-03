package basicneuralnetwork.utilities;

import org.ejml.simple.SimpleMatrix;

/**
 * Created by KimFeichtinger on 07.03.18.
 */
public class MatrixConverter {

    // Converts a 2D array into a SimpleMatrix
    public static SimpleMatrix arrayToMatrix(double[] i){
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
    public static double[] getColumnFromMatrixAsArray(SimpleMatrix data, int column){
        double[] result = new double[data.numRows()];

        for (int i = 0; i < result.length; i++) {
            result[i] = data.get(i, column);
        }

        return result;
    }

}
