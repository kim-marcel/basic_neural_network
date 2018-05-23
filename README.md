# Basic Neural Network Library

This is a very basic Java Neural Network library based on the one built by Daniel Shiffman in [this](https://www.youtube.com/watch?v=XJ7HLz9VYz0&list=PLRqwX-V7Uu6aCibgK1PTWWu9by6XFdCfh) playlist using the [Efficient Java Matrix Library](https://www.ejml.org) (EJML).

The library can also be used with [Processing](https://processing.org). Just download the jar-file and drag it into your sketch.

If you want to learn more about Neural Networks check out these YouTube-playlists:
- [Neural Networks - The Nature of Code](https://www.youtube.com/watch?v=XJ7HLz9VYz0&list=PLRqwX-V7Uu6aCibgK1PTWWu9by6XFdCfh) by The Coding Train (Daniel Shiffman)
- [Neural Networks](https://www.youtube.com/watch?v=aircAruvnKk&list=PLZHQObOWTQDNU6R1_67000Dx_ZCJB-3pi) by 3Blue1Brown
 
## Features

- Neural Network with variable amounts of inputs, hidden nodes and outputs
- Multiple hidden layers
- Activation functions: Sigmoid, Tanh, ReLu
- Adjustable learning rate
- Fully connected
- Support for genetic algorithms: copy-, mutate-, and merge-functionality
- Save the weights and biases of a NN to a JSON-file
- Generate a NeuralNetwork-object from a JSON-file

## Getting Started

This section describes how a working copy of this project can be set up on your local machine for testing and development purposes. If you just want to use the library you can skip this part.

### Prerequisites
[Maven](https://maven.apache.org) has to be installed:
- [Download Maven](https://maven.apache.org/download.cgi)
- [Installation guide](https://maven.apache.org/install.html)

### Installing
```
mvn install
```
All the dependencies specified in pom.xml will be installed.

### Building
```
mvn package
```
In the directory ```/target``` of the project two jar-files will be generated. One with all the dependencies included and one without.

## Use the library

Constructors:
```java
// Neural network with 2 inputs, 1 hidden layer, 4 hidden nodes and 1 output
NeuralNetwork nn0 = new NeuralNetwork(2, 4, 1);

// Neural network with 2 inputs, 2 hidden layers, 4 hidden nodes and 1 output
NeuralNetwork nn1 = new NeuralNetwork(2, 2, 4, 1);
```

Train and guess:
```java
// Train the neural network with a training dataset (inputs and expected outputs)
nn.train(trainingDataInputs, trainingDataTargets);

// Guess for the given testing data is returned as an array (double[])
nn.guess(testingData);
```

Read and write from/to file:
```java
// Reads from a (previously generated) JSON-file the nn-Data and returns a NeuralNetwork-object
NeuralNetwork myNN = NeuralNetwork.readFromFile();

// Writes a JSON-file with the current "state" (weights and biases) of the NN
myNN.writeToFile();
```

Adjust the learning rate:
```java
// Set the learning rate (Initially the learning rate is 0.1)
nn.setLearningRate(0.01);

// Get current learning rate
nn.getLearningRate();
```

Use different activation functions:
```java
// Set the activation function (By default Sigmoid will be used)
nn.setLearningRate(ActivationFunction.TANH);

// Get name of currently used activation function
nn.getActivationFunctionName();
```

Use this library with genetic algorithms:
```java
// Make an exact and "independent" copy of a Neural Network
NeuralNetwork nn2 = nn1.copy();

// Merge the weights and biases of two Neural Networks with a ratio of 50:50
NeuralNetwork merged = nnA.merge(nnB);

// Merge the weights and biases of two Neural Networks with a custom ratio (here: 20:80)
NeuralNetwork merged = nnA.merge(nnB, 0.2);

// Mutate the weights and biases of a Neural Network with custom probability
nn.mutate(0.1);
```
More detailed examples can be found below.

## Download

If you want to use this library you can download [v0.4](https://github.com/kim-marcel/basic_neural_network/releases/download/v0.4/basic_neural_network-v0.4.jar) here or check the release tab of this repository.

## Examples

- [XOR solved with Basic Neural Network Library](https://github.com/kim-marcel/xor_with_nn)
- [Doodle Classification in Processing](https://github.com/kim-marcel/doodle_classifier)

If you want you can add your own projects, that were build with this library, to this list. Please send me a pull request.

## TODO

- implement softmax
- add more functionality for genetic algorithms (e.g. different merge functions,...)
- JUnit-tests
- Javadoc documentation
- weights and biases should get normalized
- more examples

If you have any other suggestions on what should be done, feel free to open an issue or add it to this list.

If you want to contribute by implementing any of these features or your own ideas please do so and send me a pull request.

## Libraries & Tools used in this project

- [EJML](https://www.ejml.org) used for Matrix math
- [Google Gson](https://github.com/google/gson) library
- [Maven](https://maven.apache.org) build- and dependency-management
