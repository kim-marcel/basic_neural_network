package basicneuralnetwork;

/**
 * Created by MichalWa on 08.06.18
 */
public class NeuralNetworkBuilder {

	private int inputNodes = 0;
	private int hiddenLayers = 0;
	private int hiddenNodes = 0;
	private int outputNodes = 0;
	private String activationFunction = null;
	private double learningRate = -1.0;
	
	public NeuralNetworkBuilder setInputNodes(int inputNodes) {
		this.inputNodes = inputNodes;
		return this;
	}
	
	public NeuralNetworkBuilder setHiddenLayers(int hiddenLayers) {
		this.hiddenLayers = hiddenLayers;
		return this;
	}
	
	public NeuralNetworkBuilder setHiddenNodes(int hiddenNodes) {
		this.hiddenNodes = hiddenNodes;
		return this;
	}
	
	public NeuralNetworkBuilder setOutputNodes(int outputNodes) {
		this.outputNodes = outputNodes;
		return this;
	}
	
	public NeuralNetworkBuilder setActivationFunction(String activationFunction) {
		this.activationFunction = activationFunction;
		return this;
	}
	
	public NeuralNetworkBuilder setLearningRate(double learningRate) {
		this.learningRate = learningRate;
		return this;
	}
	
	public NeuralNetwork create() {
		if(inputNodes < 1) throw new IllegalStateException("There must be 1 or more input nodes.");
		if(hiddenNodes < 1) throw new IllegalStateException("There must be 1 or more hidden nodes.");
		if(outputNodes < 1) throw new IllegalStateException("There must be 1 or more output nodes");
		
		NeuralNetwork nn = new NeuralNetwork(inputNodes, hiddenLayers, hiddenNodes, outputNodes);
		
		if(activationFunction != null) nn.setActivationFunction(activationFunction);
		if(learningRate != -1.0) nn.setLearningRate(learningRate);
		
		return nn;
	}

}
