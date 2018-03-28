# Basic Neural Network Library

This is a very basic Java Neural Network library based on the one built by Daniel Shiffman in [this](https://www.youtube.com/watch?v=XJ7HLz9VYz0&list=PLRqwX-V7Uu6aCibgK1PTWWu9by6XFdCfh) playlist using the [Efficient Java Matrix Library](https://www.ejml.org) (EJML).

The library can also be used with [Processing](https://processing.org). Just download the jar-file and drag it into your sketch.

If you want to learn more about Neural Networks check out these YouTube-playlists:
- [Neural Networks - The Nature of Code](https://www.youtube.com/watch?v=XJ7HLz9VYz0&list=PLRqwX-V7Uu6aCibgK1PTWWu9by6XFdCfh) by The Coding Train (Daniel Shiffman)
- [Neural Networks](https://www.youtube.com/watch?v=aircAruvnKk&list=PLZHQObOWTQDNU6R1_67000Dx_ZCJB-3pi) by 3Blue1Brown
 
## Features

- Neural Network with variable amounts of inputs, hidden nodes and outputs
- Multiple hidden layers
- Adjustable learning rate
- Fully connected
- Save the weights and biases of a NN to a JSON-file
- Read a JSON-file with NN-data
- [EJML](https://www.ejml.org) used for Matrix math
- [Maven](https://maven.apache.org) dependency-management

## Code example

Constructors:
```java
// Neural network with 2 inputs, 4 hidden nodes and 1 output
NeuralNetwork nn0 = new NeuralNetwork(2, 4, 1);

// Neural network with 2 inputs, 2 hidden layers, 4 hidden nodes and 1 output
NeuralNetwork nn1 = new NeuralNetwork(2, 2, 4, 1);
```

Training and guessing:
```java
// Train the neural network with a training dataset (inputs and expected outputs)
nn.train(trainingDataInputs, trainingDataTargets);

// Guess for the given testing data is returned as an array (double[])
nn.guess(testingData);
```

Read and write from/to file:
```java
// Reads from a (previously generated) JSON-file the nn-Data and returns a NeuralNetwork
NeuralNetwork myNN = NeuralNetwork.readFromFile();

// Writes a JSON-file with the current "state" (weights and biases) of the NN
myNN.writeToFile();
```

Adjusting the learning rate:
```java
// Set the learning rate (Initially the learning rate is 0.1)
nn.setLearningRate(0.01);

// Get current learning rate
nn.getLearningRate();
```
A more detailed example can be found below.

## Download

If you want to use this library you can download [v0.2](https://github.com/kim-marcel/basic_neural_network/releases/download/v0.2/basic_neural_network-v0.2.jar) here or check the release tab of this repository.

## Examples

- [XOR solved with Basic Neural Network Library](https://github.com/kim-marcel/xor_with_nn)
- [Doodle Classification in Processing](https://github.com/kim-marcel/doodle_classifier)
