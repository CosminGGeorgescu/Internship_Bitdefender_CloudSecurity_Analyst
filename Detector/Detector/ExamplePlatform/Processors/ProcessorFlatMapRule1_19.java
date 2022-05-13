package Detector.ExamplePlatform.Processors;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public class ProcessorFlatMapRule1_19 implements Function<JSONObject, Stream<JSONObject>> {
    @Override
    public Stream<JSONObject> apply(JSONObject jsonObject) {

/*
            Let's look again at the data. We'll have to extract iterate the ServerCertificateMetadataList objects and
            append them to a java list that will be returned as a stream. The result will be our new stream.

            {
                "ServerCertificateMetadataList": [
                {
                    "Path": "/",
                        "ServerCertificateName": "https_server_certificate",
                        "ServerCertificateId": "AAAAJ1AAA2AAAAAAA1A11",
                        "Arn": "arn:aws:iam::555555555555:server-certificate/https_server_certificate",
                        "UploadDate": "2013-06-14 12:56:41+00:00",
                        "Expiration": "2013-09-11 23:59:59+00:00"
                }
                ],
                "IsTruncated": false,
                    "ResponseMetadata": {
                "RequestId": "530d9bf9-3439-42e6-bd26-0966a6a8697e",
                        "HTTPStatusCode": 200,
                        "HTTPHeaders": {
                    "x-amzn-requestid": "530d9bf9-3439-42e6-bd26-0966a6a8697e",
                            "content-type": "text/xml",
                            "content-length": "799",
                            "date": "Sun, 20 Mar 2022 22:13:06 GMT"
                },
                "RetryAttempts": 0
            }
        }
*/

        JSONArray certificates = jsonObject.getJSONArray("ServerCertificateMetadataList");
        List<JSONObject> certificateJavaList = new ArrayList<>();
        for(Object certificate : certificates)
        {
            certificateJavaList.add((JSONObject) certificate);
        }
        return certificateJavaList.stream();
    }
}
