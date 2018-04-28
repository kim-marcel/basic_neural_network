package basicneuralnetwork;

/**
 * Created by KimFeichtinger on 28.04.18.
 */
public class WrongDimensionException extends RuntimeException {

    public WrongDimensionException(int actual, int expected, String layer){
        super("Expected " + expected + " value(s) for " + layer + "-layer but got " + actual + ".");
    }

}
