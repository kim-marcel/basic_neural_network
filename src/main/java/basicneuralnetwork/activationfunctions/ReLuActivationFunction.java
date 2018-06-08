package basicneuralnetwork.activationfunctions;

/**
 * Created by KimFeichtinger on 26.04.18.
 */
public class ReLuActivationFunction extends ActivationFunction {

    public static final String NAME = "relu";
    
    static {
        ActivationFunctionFactory.register(NAME, ReLuActivationFunction::new);
    }
    
    @Override
    protected double apply(double value) {
        return value > 0 ? value : 0;
    }
    
    @Override
    protected double applyDerivative(double value) {
        return value > 0 ? 1 : 0;
    }
    
    public String getName() {
        return NAME;
    }
}
