# Basic Neural Network Library

This is a very basic Java Neural Network library based on the one built by Daniel Shiffman in [this](https://www.youtube.com/watch?v=XJ7HLz9VYz0&list=PLRqwX-V7Uu6aCibgK1PTWWu9by6XFdCfh) playlist using the [Efficient Java Matrix Library](https://www.ejml.org) (EJML).

The library can also be used with [Processing](https://processing.org). Just download the jar-file and drag it into your sketch.

If you want to learn more about Neural Networks check out these YouTube-playlists:
- [Neural Networks - The Nature of Code](https://www.youtube.com/watch?v=XJ7HLz9VYz0&list=PLRqwX-V7Uu6aCibgK1PTWWu9by6XFdCfh) by The Coding Train (Daniel Shiffman)
- [Neural Networks](https://www.youtube.com/watch?v=aircAruvnKk&list=PLZHQObOWTQDNU6R1_67000Dx_ZCJB-3pi) by 3Blue1Brown
 
## Features

- Neural Network with variable amounts of inputs, hidden nodes and outputs
- Two layered (hidden + output)
- Save the weights and biases of a NN to a JSON-file
- Read a JSON-file with NN-data
- [EJML](https://www.ejml.org) used for Matrix math
- [Maven](https://maven.apache.org) Build-Management

## Code example

```java
// Neural Network with 2 inputs, 4 hidden nodes and 1 output
NeuralNetwork nn = new NeuralNetwork(2, 4, 1);

// Reads from a (previously generated) JSON-file the weights and biases of the NN
nn.readFromFile();

// Train the Neural Network with a training dataset
nn.train(trainingDataInputs, trainingDataTargets);

// Guess for the given testing data is returned as a 2D array (double[][])
nn.guess(testingData);

// Writes a JSON-file with the current "state" (weights and biases) of the NN
nn.writeToFile();
```
A more detailed example cam be found below.

## Download

If you want to use this library you can download [v0.1-alpha](https://github.com/kim-marcel/basic_neural_network/releases/download/v0.1-alpha/basic_neural_network-v0.1-alpha.jar) here or check the release tab of this repository.

## Examples

- [XOR solved with Basic Neural Network Library](https://github.com/kim-marcel/xor_with_nn)

## Upcoming features

- Support for multiple layers
