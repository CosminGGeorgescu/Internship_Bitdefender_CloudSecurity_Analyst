package Detector;

import CommunicationLib.Java.CommunicationLibConsumer;
import CommunicationLib.Java.CommunicationLibProducer;
import org.json.JSONObject;

import java.util.Properties;
import java.util.stream.Stream;

public class Detector {

    // It is required that you use java streams
    public static void main(String[] args) throws Exception {

        Properties properties = new Properties();
        properties.put("bootstrap.servers", "internship_bootstrap_server");
        CommunicationLibConsumer cc = new CommunicationLibConsumer(properties);
//        kc.subscribe(<your input topic name here>);

        CommunicationLibProducer cp = new CommunicationLibProducer(properties);
//        kp.subscribe(<your output topic name here>);

        Stream<JSONObject> inputStream = cc.poll(0);

        // TODO: Replace this with your implementation
        Stream<JSONObject> processedStream = inputStream.map(el -> el);

        cp.sink(processedStream);
    }

}
