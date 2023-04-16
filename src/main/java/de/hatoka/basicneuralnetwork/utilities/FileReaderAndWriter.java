package de.hatoka.basicneuralnetwork.utilities;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;

import org.ejml.simple.SimpleMatrix;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import de.hatoka.basicneuralnetwork.NeuralNetwork;

/**
 * Created by KimFeichtinger on 26.04.18.
 * FileReaderAndWriter is responsible for writing and reading neural networks.
 * {@link NeuralNetwork} is annotated with gson to support serialization.
 */
public class FileReaderAndWriter
{
    /**
     * Writes a neural network to file
     * @param nn network
     * @param file file location
     * @throws IOException
     */
    public void write(NeuralNetwork nn, Path file) throws IOException
    {
        String nnData = getGson().toJson(nn);
        try (FileWriter fw = new FileWriter(file.toFile()))
        {
            fw.write(nnData);
            fw.flush();
        }
    }

    /**
     * Read neural network from file
     * @param file file location
     * @return neural network
     * @throws IOException
     */
    public NeuralNetwork read(Path file) throws IOException
    {
        try (JsonReader jsonReader = new JsonReader(new FileReader(file.toFile())))
        {
            return getGson().fromJson(jsonReader, NeuralNetwork.class);
        }
    }

    /**
     * Read neural network from resource (application can provide trained networks
     * @param input input stream from resource
     * @return neural network
     * @throws IOException
     */
    public NeuralNetwork read(InputStream input) throws IOException
    {
        try (JsonReader jsonReader = new JsonReader(new InputStreamReader(input)))
        {
            return getGson().fromJson(jsonReader, NeuralNetwork.class);
        }
    }
    
    /**
     * @return Gson via GsonBuilder with all the needed adapters added
     */
    private Gson getGson()
    {
        return new GsonBuilder().registerTypeAdapter(SimpleMatrix.class, new SimpleMatrixAdapter())
                                .setPrettyPrinting()
                                .excludeFieldsWithoutExposeAnnotation().create();
    }

}
