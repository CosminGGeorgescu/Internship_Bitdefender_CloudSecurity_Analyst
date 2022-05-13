import json
import os


class CommunicationLibProducer:
    """
    A Kafka client mockup that publishes records to the Kafka cluster.
    """

    def __init__(self, bootstrap_server: str) -> None:
        """
        Constructor.
        Creates a new CommunicationLibProducer object bound to a bootstrap server

        :param bootstrap_server: Name of the bootstrap server
        """
        self.__bootstrap_server = bootstrap_server
        self.__dirFile = os.path.dirname(os.path.dirname(os.path.dirname(os.path.realpath(__file__))))
        self.__dirFile = os.path.join(self.__dirFile, "CommData", self.__bootstrap_server)

        if not os.path.exists(self.__dirFile) or not os.path.isdir(self.__dirFile):
            os.makedirs(self.__dirFile)

    def send(self, topic: str, value: dict) -> None:
        """
        Sends data to a topic on the current bootstrap server

        :param topic: Topic name
        :param value: Data to be sent on the topic
        :return: None
        """
        topic_path = os.path.join(self.__dirFile, topic)
        data = json.dumps(value, default=str)
        with open(topic_path, "a") as f:
            f.write(data + "\n")


class KafkaConsumer:
    """
    A Kafka mockup client that consumes records from a Kafka cluster.
    """

    def __init__(self, bootstrap_server: str) -> None:
        """
        Constructor.
        Creates a new KafkaConsumer object bound to a bootstrap server

        :param bootstrap_server: Name of the bootstrap server
        """
        self.__bootstrap_server = bootstrap_server
        self.__dirFile = os.path.dirname(os.path.dirname(os.path.dirname(os.path.realpath(__file__))))
        self.__dirFile = os.path.join(self.__dirFile, "CommData", self.__bootstrap_server)

        if not os.path.exists(self.__dirFile) or not os.path.isdir(self.__dirFile):
            raise Exception(f"Could not connect to bootstrap server {bootstrap_server}")

    def subscribe(self, topic: str) -> list:
        """
        Reads data from a topic on the current bootstrap server

        :param topic: Name of the topic to be read.
        :return: A list containing the records on the topic.
        """
        topic_path = os.path.join(self.__dirFile, topic)
        result = []

        with open(topic_path, "r") as f:
            for data in f:
                result.append(json.loads(data))

        return result
