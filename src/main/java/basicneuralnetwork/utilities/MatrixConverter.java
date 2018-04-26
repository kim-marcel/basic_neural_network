package basicneuralnetwork.utilities;

import org.ejml.simple.SimpleMatrix;
import org.json.simple.JSONObject;

/**
 * Created by KimFeichtinger on 07.03.18.
 */
public class MatrixConverter {

    public static SimpleMatrix arrayToMatrix(double[] i){
        double[][] input = {i};
        return new SimpleMatrix(input).transpose();
    }

    public static double[][] matrixTo2dArray(SimpleMatrix i){
        double[][] result = new double[i.numRows()][i.numCols()];
        for (int j = 0; j < result.length; j++) {
            for (int k = 0; k < result[0].length; k++) {
                result[j][k] = i.get(j, k);
            }
        }
        return result;
    }

    public static SimpleMatrix jsonToMatrix(JSONObject i){
        int rows = i.size();
        int cols = ((JSONObject) i.get(Integer.toString(0))).size();

        SimpleMatrix result = new SimpleMatrix(rows, cols);

        for (int j = 0; j < i.size(); j++) {
            JSONObject js = (JSONObject) i.get(Integer.toString(j));
            for (int k = 0; k < js.size(); k++) {
                double d = (double) js.get(Integer.toString(k));
                result.set(j, k, d);
            }
        }

        return result;
    }

    public static JSONObject matrixToJSON(SimpleMatrix i){
        JSONObject result = new JSONObject();
        JSONObject a = new JSONObject();

        for (int j = 0; j < i.numRows(); j++) {
            a.clear();
            for (int k = 0; k < i.numCols(); k++) {
                a.put(k, i.get(j, k));
            }
            result.put(j, new JSONObject(a));
        }

        return result;
    }

    // Returns only the first "row" of a matrix
    public static double[] matrixToArray(SimpleMatrix data){
        double[][] resultAs2dArray = MatrixConverter.matrixTo2dArray(data);

        double[] result = new double[resultAs2dArray.length];

        for (int i = 0; i < result.length; i++) {
            result[i] = resultAs2dArray[i][0];
        }

        return result;
    }
}
