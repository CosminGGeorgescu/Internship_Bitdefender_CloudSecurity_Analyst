package CommunicationLib.Java;

import com.google.gson.Gson;
import org.json.JSONObject;

import java.io.File;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Properties;
import java.util.stream.Stream;

/**
 * A Kafka client mockup that publishes records to the Kafka cluster.
 */
public class CommunicationLibProducer {

    final private String parentDir;
    private String cwd;
    private Boolean topicSet = false;


    /**
     * Constructor
     * Creates a new CommunicationLibProducer object and sets its bootstrap server for the current client.
     *
     * @param properties Requires a "bootstrap.servers" key with value equal to the bootstrap server to be read.
     * @throws Exception Thrown if the bootstrap server could not be created
     */
    public CommunicationLibProducer(Properties properties) throws Exception {
        String classPath = System.getProperty("user.dir");
        parentDir = Paths.get(classPath, "Libs/CommData/", properties.getProperty("bootstrap.servers")).toString();

        if (!new File(parentDir).exists()) {
            if (!new File(parentDir).mkdirs()) {
                throw new Exception("Could not create bootstrap server!");
            }
        }
    }

    /**
     * Subscribes to a certain topic.
     *
     * @param topicName Name of the topic to subscribe to.
     * @throws Exception Thrown if the topic could not be created.
     */
    public void subscribe(String topicName) throws Exception {
        cwd = Paths.get(parentDir, topicName).toString();
        if (!new File(cwd).exists()) {
            if (!new File(cwd).createNewFile()) {
                throw new Exception("Could not create topic!");
            }
        }
        topicSet = true;
    }

    /**
     * Sends data to the subscribed topic.
     *
     * @param dataStream The data stream to be written to the subscribed topic
     * @param <T>        Data type
     * @throws Exception Thrown if the sink was not set or the object being sent to the topic is not JSON serializable
     */
    public <T> void sink(Stream<T> dataStream) throws Exception {
        if (!topicSet)
            throw new Exception("Sink topic was not set!");
        if (!new File(cwd).exists())
            throw new Exception("Kafka topic was forcibly closed");
        try (PrintWriter pw = new PrintWriter(Files.newBufferedWriter(Paths.get(cwd), StandardOpenOption.APPEND))) {
            dataStream.map(el -> {
                if (el instanceof JSONObject) {
                    return el;
                } else {
                    return new Gson().toJson(el);
                }
            }).map(Object::toString).forEach(pw::println);
        }
    }
}
