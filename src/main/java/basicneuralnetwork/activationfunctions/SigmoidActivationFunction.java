package basicneuralnetwork.activationfunctions;

/**
 * Created by KimFeichtinger on 20.04.18.
 */
public class SigmoidActivationFunction extends ActivationFunction {

    public static final String NAME = "sigmoid";
    
    static {
        ActivationFunctionFactory.register(NAME, SigmoidActivationFunction::new);
    }
    
    @Override
    protected double apply(double value) {
        return 1 / (1 + Math.exp(-value));
    }
    
    @Override
    protected double applyDerivative(double value) {
        return value * (1 - value);
    }
    
    public String getName() {
        return NAME;
    }
}
