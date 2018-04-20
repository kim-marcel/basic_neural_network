package activationfunctions;

/**
 * Created by KimFeichtinger on 20.04.18.
 */
public class TanhActivationFunction implements ActivationFunction {

    private static final String NAME = "TANH";

    public double function(double input) {
        return 2 * (1 / (1 + Math.exp(2 * -input))) - 1;
    }

    public double dfunction(double input) {
        return input * (1 - input);
    }

    public String getName() {
        return NAME;
    }
}
