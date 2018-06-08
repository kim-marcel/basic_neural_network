package basicneuralnetwork.activationfunctions;

/**
 * Created by KimFeichtinger on 20.04.18.
 */
public class TanhActivationFunction extends ActivationFunction {
    
    public static final String NAME = "tanh";
    
    static {
        ActivationFunctionFactory.register(NAME, TanhActivationFunction::new);
    }
    
    @Override
    protected double apply(double value) {
        return Math.tanh(value);
    }
    
    @Override
    protected double applyDerivative(double value) {
        return 1 - (value * value);
    }
    
    public String getName() {
        return NAME;
    }
}
