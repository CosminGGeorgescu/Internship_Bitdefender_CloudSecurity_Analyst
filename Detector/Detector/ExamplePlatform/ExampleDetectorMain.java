package Detector.ExamplePlatform;

import Detector.Alert;
import Detector.ExamplePlatform.Pojos.ServerCertificate;
import Detector.ExamplePlatform.Processors.*;
import CommunicationLib.Java.CommunicationLibConsumer;
import CommunicationLib.Java.CommunicationLibProducer;
import com.google.gson.Gson;
import org.json.JSONObject;

import java.util.Properties;
import java.util.stream.Stream;

public class ExampleDetectorMain {

//     This is an example for rule 1.19 in the CIS document
//     We will focus on the actual detection:
//
//     Verify the ServerCertificateName and Expiration parameter value (expiration date) for
//     each SSL/TLS certificate returned by the list-server-certificates command and determine if
//     there are any expired server certificates currently stored in AWS IAM
//
//     Before implementing the detection a few prep steps are required:
//     Step 1: Instantiate and subscribe the KafkaConsumer and CommunicationLibProducer objects
//     Step 2: Read the data
//     Step 3: De-encapsulate the data
//     Step 4: Convert JSONs to POJOs (optional but nice to have)
//     Step 5: Apply the cis rule and filter out non-expired certificates
//     Step 6: The remaining objects are converted to the Alert class
//     Step 7: Send the Alerts to the sink
//
//    It will be helpful to have a view of the data that we are processing
//    As seen in SensorExample, the data sent by the Sensor is structured as such:
//
//         {
//            "ServerCertificateMetadataList": [
//                {
//                    "Path": "/",
//                    "ServerCertificateName": "https_server_certificate",
//                    "ServerCertificateId": "AAAAJ1AAA2AAAAAAA1A11",
//                    "Arn": "arn:aws:iam::555555555555:server-certificate/https_server_certificate",
//                    "UploadDate": "2013-06-14 12:56:41+00:00",
//                    "Expiration": "2013-09-11 23:59:59+00:00"
//                }
//            ],
//            "IsTruncated": false,
//            "ResponseMetadata": {
//                "RequestId": "530d9bf9-3439-42e6-bd26-0966a6a8697e",
//                "HTTPStatusCode": 200,
//                "HTTPHeaders": {
//                    "x-amzn-requestid": "530d9bf9-3439-42e6-bd26-0966a6a8697e",
//                    "content-type": "text/xml",
//                    "content-length": "799",
//                    "date": "Sun, 20 Mar 2022 22:13:06 GMT"
//                },
//                "RetryAttempts": 0
//            }
//        }

    public static void main(String[] args) throws Exception {


        /** Step 1 : **/
        //// Properties.
        Properties properties = new Properties();
        // Only the bootstrap.servers is required; this should be a single string.
        properties.put("bootstrap.servers", "internship_bootstrap_server");

        //// Kafka Consumer.
        // Use the properties to set the bootstrap server.
        CommunicationLibConsumer kc = new CommunicationLibConsumer(properties);

        ////Kafka Producer
        // Use the properties to set the bootstrap server.
        CommunicationLibProducer kp = new CommunicationLibProducer(properties);
        // Set the topic to be written.
        kp.subscribe("alert_output_topic");



        /** Step 2 : **/
        // Subscribe to the server certificates topic created by the Sensor and read the data
        kc.subscribe("server_certificates_topic");
        Stream<JSONObject> serverCertificate = kc.poll(0);

        /** Step 3 : **/
        // It is easily seen that ServerCertificateMetadataList is a list of ServerCertificateObject. We would like to flatten this list
        // in a single ServerCertificateObject stream. As such we use the .flatMap() method on the stream
        Stream<JSONObject> flattenedServerCertificates = serverCertificate.flatMap(new ProcessorFlatMapRule1_19());

        /** Step 4 : **/
        // Now we can convert the JSON to a POJO, this step is optional, but we strongly recommend it
        Stream<ServerCertificate> serverCertificateAsPojoStream = flattenedServerCertificates.map(el -> new Gson().fromJson(el.toString(), ServerCertificate.class));

        /** Step 5 : **/
        // Filter out the non-expired certificates from the stream
        // Here we apply the actual logic from the CIS document and
        Stream<ServerCertificate> expiredCertificates = serverCertificateAsPojoStream.filter(new ProcessorFilterExpiredCertificatesRule1_19());

        /** Step 6 : **/
        // For each expired certificate generate an Alert.
        // The extra info field in the Alert field can contain any information you think will uniquely identify
        // the certificate (or other resource) [HINT look for ARN or ID keys in the data]
        Stream<Alert> alertStream = expiredCertificates.map(certificate ->
                new Alert(
                        "1.19",
                        "Ensure that all the expired SSL/TLS certificates stored in AWS IAM are removed (Automated)",
                        new JSONObject().put("ServerCertificateId", certificate.getServerCertificateId())
                )
        );

        /** Step 7 : **/
        // Send the alerts to the sink for collection
        kp.sink(alertStream);


    }

}
