package basicneuralnetwork.activationfunctions;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by KimFeichtinger on 04.05.18.
 */
public class ActivationFunctionFactory {

    private Map<String, ActivationFunction> activationFunctionMap = new HashMap<>();

    public ActivationFunctionFactory () {
        // Fill map with all the activation functions
        ActivationFunction sigmoid = new SigmoidActivationFunction();
        activationFunctionMap.put(sigmoid.getName(), sigmoid);

        ActivationFunction tanh = new TanhActivationFunction();
        activationFunctionMap.put(tanh.getName(), tanh);

        ActivationFunction relu = new ReLuActivationFunction();
        activationFunctionMap.put(relu.getName(), relu);
    }

    public ActivationFunction getActivationFunctionByKey (String activationFunctionKey) {
        return activationFunctionMap.get(activationFunctionKey);
    }

    public void addActivationFunction(String key, ActivationFunction activationFunction) {
        activationFunctionMap.put(key, activationFunction);
    }
}
