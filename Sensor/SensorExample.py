import os
import sys
sys.path.append(os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

import json
from Libs.CloudApi import CloudApi
from Libs.CommunicationLib.Python import communication_lib_mockup

"""
This is an implementation for a Sensor for CIS rule 1.19

In the rule 1.19 > Audit > From Command Line it is clearly stated that 
you should "Run list-server-certificates command to list all the IAM-stored server certificates"
in order to gather the data required for the detection.

This code calls list_server_certificates from our Boto3Mockup library and sends the data to kafka on 
    bootstrap server: internship_bootstrap_server
    topic: server_certificates_topic

See also:
    https://boto3.amazonaws.com/v1/documentation/api/latest/reference/services/iam.html#IAM.Client.list_server_certificates
    https://docs.aws.amazon.com/IAM/latest/APIReference/API_ListServerCertificates.html
    
"""

# Kafka producer and consumer on the "internship_bootstrap_server" bootstrap server
producer = communication_lib_mockup.CommunicationLibProducer("internship_bootstrap_server")

# Boto3Mockup library client
client = CloudApi.client(service_name="iam")

# Call the Boto3Mockup's list_server_certificates method in order to obtain the certificates
server_certificates = client.list_server_certificates()

# Print the result for visualisation
print(json.dumps(server_certificates, indent=4, default=str))

# Send the result in kafka
producer.send("server_certificates_topic", server_certificates)

"""
It is very important to have an overview of the data structure as we will use it extensively in the detector.
A good practice is to print and keep a copy of the data on hand.

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
"""
