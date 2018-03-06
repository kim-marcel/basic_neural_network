# Basic Neural Network Library

This is a very basic Java Neural Network library based on the one built by Daniel Shiffman in [this](https://www.youtube.com/watch?v=XJ7HLz9VYz0&list=PLRqwX-V7Uu6aCibgK1PTWWu9by6XFdCfh) playlist using the [Efficient Java Matrix Library](https://www.ejml.org) (EJML).

If you want to learn more about Neural Networks check out these YouTube-playlists:
- [Neural Networks - The Nature of Code](https://www.youtube.com/watch?v=XJ7HLz9VYz0&list=PLRqwX-V7Uu6aCibgK1PTWWu9by6XFdCfh) by The Coding Train (Daniel Shiffman)
- [Neural Networks](https://www.youtube.com/watch?v=aircAruvnKk&list=PLZHQObOWTQDNU6R1_67000Dx_ZCJB-3pi) by 3Blue1Brown
 
## Features

- Neural Network with variable amounts of inputs, hidden nodes and outputs
- Two layered (hidden + output)
- [Maven](https://maven.apache.org) Build-Management
- [EJML](https://www.ejml.org) used for Matrix math

## Examples (Usages)

- [XOR solved with Basic Neural Network Library](https://github.com/kim-marcel/xor_with_nn)

## Download

If you want to use this library you can [download](https://github.com/kim-marcel/basic_neural_network/releases/download/v0.1-alpha/basic_neural_network-v0.1-alpha.jar) v0.1-alpha here or check the release tab of this repository. There might be a newer version available.

## Documentation

### Code example:

```java
// Neural Network with 2 inputs, 4 hidden nodes and 1 output
NeuralNetwork nn = new Neuralnetwork(2, 4, 1);

// Train the Neural Network with a training dataset
nn.train(trainingDataInputs, trainingDataTargets);

// Guess for the given data is returned as a 2D array (double[][])
nn.guess(testingData);
```

## Upcoming features

- Support for multiple layers
- Save and load the neural network to a file
