package basicneuralnetwork;

import basicneuralnetwork.activationfunctions.SigmoidActivationFunction;
import org.junit.Test;

import static org.junit.Assert.*;

public class NeuralNetworkBuilderTest {
	
	int inputNodes = 2;
	int hiddenLayers = 3;
	int hiddenNodes = 4;
	int outputNodes = 5;
	String activationFunction = SigmoidActivationFunction.NAME;
	double learningRate = 0.5;
	
	@Test
	public void builderTest() {
		NeuralNetwork nn = new NeuralNetworkBuilder()
				.setInputNodes(inputNodes)
				.setHiddenLayers(hiddenLayers)
				.setHiddenNodes(hiddenNodes)
				.setOutputNodes(outputNodes)
				.setActivationFunction(activationFunction)
				.setLearningRate(learningRate)
				.create();
		
		assertEquals(inputNodes, nn.getInputNodes());
		assertEquals(hiddenLayers, nn.getHiddenLayers());
		assertEquals(hiddenNodes, nn.getHiddenNodes());
		assertEquals(outputNodes, nn.getOutputNodes());
		assertEquals(nn.getActivationFunctionName(), activationFunction);
		assertEquals(nn.getLearningRate(), learningRate, 0.0);
	}
	
}