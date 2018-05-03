package basicneuralnetwork.utilities;

import org.ejml.simple.SimpleMatrix;

/**
 * Created by KimFeichtinger on 07.03.18.
 */
public class MatrixConverter {

    public static SimpleMatrix arrayToMatrix(double[] i){
        double[][] input = {i};
        return new SimpleMatrix(input).transpose();
    }

    // Returns only the first "row" of a matrix
    public static double[] matrixToArray(SimpleMatrix data){
        double[][] resultAs2dArray = MatrixConverter.matrixTo2DArray(data);

        double[] result = new double[resultAs2dArray.length];

        for (int i = 0; i < result.length; i++) {
            result[i] = resultAs2dArray[i][0];
        }

        return result;
    }

    public static double[][] matrixTo2DArray(SimpleMatrix i) {
        double[][] result = new double[i.numRows()][i.numCols()];
        for (int j = 0; j < result.length; j++) {
            for (int k = 0; k < result[0].length; k++) {
                result[j][k] = i.get(j, k);
            }
        }
        return result;
    }

}
