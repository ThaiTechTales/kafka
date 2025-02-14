# Kafka Topics CLI

Kafka topics are the primary way to organise and manage data in Kafka. Topics are logical channels that allow producers to write messages and consumers to read messages. Topics are divided into partitions, which are distributed across brokers in a Kafka cluster.


```sh
# kafka-topics.sh is a command-line tool for managing Kafka topics.
# Used to create, list, delete, and describe topics.
kafka-topics.sh

# List all existing Kafka topics
kafka-topics.sh --bootstrap-server localhost:9092 --list

# Create a new topic named 'first_topic' with default settings (1 partition, replication factor 1)
kafka-topics.sh --bootstrap-server localhost:9092 --topic first_topic --create

# List all existing Kafka topics
kafka-topics.sh --bootstrap-server localhost:9092 --list

# Create a topic named 'second_topic' with 3 partitions (better for scalability and parallelism)
kafka-topics.sh --bootstrap-server localhost:9092 --topic second_topic --create --partitions 3

# List all existing Kafka topics
kafka-topics.sh --bootstrap-server localhost:9092 --list

# Attempt to create a topic named 'third_topic' with 3 partitions and a replication factor of 2.
# This will fail because there is only 1 broker running, and replication factor must not exceed the number of brokers.
kafka-topics.sh --bootstrap-server localhost:9092 --topic third_topic --create --partitions 3 --replication-factor 2

# Create a topic named 'third_topic' with 3 partitions and a replication factor of 1 (valid with 1 broker)
kafka-topics.sh --bootstrap-server localhost:9092 --topic third_topic --create --partitions 3 --replication-factor 1

# List all topics again to verify creation
kafka-topics.sh --bootstrap-server localhost:9092 --list

# Describe a specific topic to view details such as partition count and replication factor
kafka-topics.sh --bootstrap-server localhost:9092 --topic first_topic --describe
kafka-topics.sh --bootstrap-server localhost:9092 --topic second_topic --describe
kafka-topics.sh --bootstrap-server localhost:9092 --topic third_topic --describe

# Delete specific topics (this is irreversible unless topic deletion is disabled in Kafka config)
kafka-topics.sh --bootstrap-server localhost:9092 --topic first_topic --delete
kafka-topics.sh --bootstrap-server localhost:9092 --topic second_topic --delete
kafka-topics.sh --bootstrap-server localhost:9092 --topic third_topic --delete

# List all existing Kafka topics
kafka-topics.sh --bootstrap-server localhost:9092 --list
```
