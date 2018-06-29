package basicneuralnetwork.utilities;

import basicneuralnetwork.WrongDimensionException;
import org.ejml.simple.SimpleMatrix;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MatrixUtilitiesTest {

    Random random = new Random();

    @Test
    public void arrayToMatrixTest() {
        double[] input = {3, 5};
        SimpleMatrix result = MatrixUtilities.arrayToMatrix(input);

        assertEquals(2, result.numRows());
        assertEquals(1, result.numCols());
        assertEquals(3, result.get(0, 0));
        assertEquals(5, result.get(1, 0));
        assertEquals(2, result.getNumElements());
    }

    @Test
    public void matrixTo2DArrayTest() {
        SimpleMatrix input = SimpleMatrix.random64(2,3, 0, 1, random);
        input.set(0, 0, 5);
        double[][] result = MatrixUtilities.matrixTo2DArray(input);

        assertEquals(5, result[0][0]);
        assertEquals(input.numRows(), result.length);
        assertEquals(input.numCols(), result[0].length);
        assertEquals(input.getNumElements(), result[0].length * result.length);
    }

    @Test
    public void getColumnFromMatrixAsArrayTest() {
        SimpleMatrix input = SimpleMatrix.random64(2, 2, 0, 1, random);
        input.set(0, 0, 5);
        double[] result = MatrixUtilities.getColumnFromMatrixAsArray(input, 0);

        assertEquals(result[0], 5);
        assertEquals(2, result.length);
    }

    @Test
    public void mergeMatricesTestWrongDimensions() {
        SimpleMatrix matrixA = SimpleMatrix.random64(2,3,0,1, random);
        SimpleMatrix matrixB = SimpleMatrix.random64(1,2,0,1, random);
        Throwable exception = assertThrows(WrongDimensionException.class, () -> MatrixUtilities.mergeMatrices(matrixA, matrixB, 0.5));
        assertEquals("Dimensions don't match.", exception.getMessage());
    }

    @Test
    public void mergeMatricesTest() {
        SimpleMatrix matrixA = SimpleMatrix.random64(2,2,0,1, random);
        matrixA.set(0, 0, 5);
        SimpleMatrix matrixB = SimpleMatrix.random64(2,2,0,1, random);
        matrixB.set(0, 0, 3);

        SimpleMatrix result = MatrixUtilities.mergeMatrices(matrixA, matrixB, 0.5);
        double value = result.get(0, 0);
        assertTrue(value == 5 || value == 3);
    }
}