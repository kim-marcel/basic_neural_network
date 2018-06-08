package basicneuralnetwork.activationfunctions;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * Created by KimFeichtinger on 04.05.18.
 */
public class ActivationFunctionFactory {

    private static Map<String, Supplier<ActivationFunction>> factories = new HashMap<>();
    
    public static ActivationFunction createByName(String name) {
        return Optional.ofNullable(factories.get(name)).map(Supplier::get).orElse(null);
    }

    public static void register(String key, Supplier<ActivationFunction> factory) {
        factories.put(key, factory);
    }
}
