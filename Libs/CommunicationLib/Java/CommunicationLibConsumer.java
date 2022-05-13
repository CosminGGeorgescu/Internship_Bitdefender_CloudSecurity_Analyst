package CommunicationLib.Java;

import org.json.JSONObject;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.stream.Stream;


/**
 * A CommunicationLibConsumer mockup client that consumes records from a CommunicationLibConsumer cluster.
 */
public class CommunicationLibConsumer {

    final private String parentDir;
    private String cwd;
    private Boolean topicSet = false;

    /**
     * Constructor
     * Creates a new CommunicationLibConsumer object and sets its bootstrap server for the current client.
     *
     * @param properties Requires a "bootstrap.servers" key with value equal to the bootstrap server to be read.
     * @throws Exception If the bootstrap server does not exist (a producer previously connected to the topic)
     */
    public CommunicationLibConsumer(Properties properties) throws Exception {
        String classPath = System.getProperty("user.dir");
        parentDir = Paths.get(classPath, "Libs/CommData/", properties.getProperty("bootstrap.servers")).toString();

        if (!new File(parentDir).exists()) {
            throw new Exception("Could not connect to " + properties.getProperty("bootstrap.servers") + " bootstrap server");
        }


    }

    /**
     * Subscribes to a certain topic.
     *
     * @param topicName Name of the topic to be subscribed
     * @throws Exception Thrown if the topic wasn't created previously (a producer sent data to the topic)
     */
    public void subscribe(String topicName) throws Exception {
        cwd = Paths.get(parentDir, topicName).toString();
        if (!new File(cwd).exists()) {
            throw new Exception("Topic " + topicName + " does not exist");
        }
        topicSet = true;
    }

    /**
     * Reads data from the topic, maps it to JSONObject and returns it as a stream.
     *
     * @param pollingTime Not used, for compatibility only.
     * @return A stream of JSONObjects representing the data on the subscribed topic.
     * @throws Exception If no topic was previously subscribed
     */
    public Stream<JSONObject> poll(Integer pollingTime) throws Exception {
        if (!topicSet)
            throw new Exception("Consumer topic was not set!");
        return Files.lines(Paths.get(cwd)).map(JSONObject::new);
    }
}
