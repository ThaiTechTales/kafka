# Kafka Producers CLI

Kafka producers are applications that write data to Kafka topics. Producers send messages to Kafka brokers, which then store the messages in topics. Producers can send messages with or without keys, and can also specify message properties such as acknowledgements and message compression.

```sh
# Create a topic. If the topic does not exist, it will be created with default settings (1 partition, replication factor 1) however it is recommended to create topics with the appropriate number of partitions because changing the number of partitions later can be complex and prevent prevent performance issue and future headaches.
# For small applications, 1-3 partitions is usually sufficient. For larger applications, 6+ partitions is common.
kafka-topics.sh --bootstrap-server localhost:9092 --topic first_topic --create --partitions 1

# Produce messages to the topic using the kafka-console-producer.sh script
# This will create a message to the topic 'first_topic'
kafka-console-producer.sh --bootstrap-server localhost:9092 --topic first_topic
> Hello World
>My name is Thai
>^C # to exit

# Producing message with properties (acks=all) to topic 'first_topic' and ensuring that the message is acked by all in-sync replicas
kafka-console-producer.sh --bootstrap-server localhost:9092 --topic first_topic --producer-property acks=all
> bye world
> until next time!
>^C # to exit

# List all existing Kafka topics
kafka-topics.sh --bootstrap-server localhost:9092 --list

# Producing to a non existing topic. This will create the topic with default settings (1 partition, replication factor 1)
kafka-console-producer.sh --bootstrap-server localhost:9092 --topic new_topic
> hello world!
> # Warning log will appear in the Kafka server logs due to the topic not existing and thus being created with default settings
>^C # to exit

# New topic only has 1 partition
kafka-topics.sh --bootstrap-server localhost:9092 --list
kafka-topics.sh --bootstrap-server localhost:9092 --topic new_topic --describe

# Edit config/server.properties or config/kraft/server.properties to change the default number of partitions
# num.partitions=3

# Produce message with key-value pair to topic 'first_topic'. A key is used to determine the partition to which the message is sent. Without a key, messages are sent to partitions in a round-robin fashion, which can lead to out-of-order messages. Round-robin is not recommended for applications that require message order
kafka-console-producer.sh --bootstrap-server localhost:9092 --topic first_topic --property parse.key=true --property key.separator=:
>name:thai # key:value pair, key is 'name' and value is 'thai'
```
