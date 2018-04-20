package activationfunctions;

/**
 * Created by KimFeichtinger on 20.04.18.
 */
// This interface and it's methods have to be implemented in all ActivationFunction-classes
public interface ActivationFunction {

    // Activation function
    double function(double input);

    // Derivative of activation function (not real derivative because Activation function has already been applied to the input)
    double dfunction(double input);

    String getName();

}
