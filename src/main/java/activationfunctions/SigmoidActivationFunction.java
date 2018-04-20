package activationfunctions;

/**
 * Created by KimFeichtinger on 20.04.18.
 */
public class SigmoidActivationFunction implements ActivationFunction {

    private static final String NAME = "SIGMOID";

    // Sigmoid
    public double function(double input){
        return 1 / (1 + Math.exp(-input));
    }

    // Derivative of Sigmoid (not real derivative because Activation function has already been applied to the input)
    public double dfunction(double input){
        return input * (1 - input);
    }

    public String getName() {
        return NAME;
    }
}
