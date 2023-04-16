package de.hatoka.basicneuralnetwork.utilities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import de.hatoka.basicneuralnetwork.NeuralNetwork;

class FileReaderAndWriterTest
{
    private final FileReaderAndWriter underTest = new FileReaderAndWriter();
    private final List<Path> createdFiles = new ArrayList<>();

    @AfterEach
    public void removeCreateFiles()
    {
        createdFiles.forEach(p -> p.toFile().delete());
        createdFiles.clear();
    }

    @Test
    void writeAndReadFileTest() throws IOException
    {
        NeuralNetwork nn = new NeuralNetwork(4, 12, 3);
        nn.train(new double[] { 0.1, 0.2, 0.3, 0.4 }, new double[] { 0.1, 0.2, 0.3 });
        Path file = Files.createTempFile("neuro1_", ".json");
        underTest.write(nn, file);
        createdFiles.add(file);
        NeuralNetwork stored = underTest.read(file);
        Path secondFile = Files.createTempFile("neuro2_", ".json");
        underTest.write(stored, secondFile);
        createdFiles.add(secondFile);
        assertEquals(nn.hashCode(), stored.hashCode());
    }

    @Test
    void readFromResourceTest() throws IOException
    {
        InputStream input = FileReaderAndWriter.class.getResourceAsStream("neuro1.json");
        NeuralNetwork stored = underTest.read(input);
        // the hashCode came from stored network, if hashCode will must be adapted, this test will fail
        assertEquals(757017806, stored.hashCode());
    }
}
