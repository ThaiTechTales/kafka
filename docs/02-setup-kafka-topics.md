# Kafka Topics CLI

Kafka topics serve as the primary mechanism for organising and managing data within Kafka. They act as logical channels where producers send messages and consumers retrieve them. Each topic is divided into partitions, which are distributed across multiple brokers in a Kafka cluster to ensure scalability and fault tolerance.

## 1️. List All Kafka Topics

To view all existing topics in the Kafka cluster:

```sh
kafka-topics.sh --bootstrap-server localhost:9092 --list
```

```sh
# kafka-topics.sh is a command-line tool for managing Kafka topics.
# Used to create, list, delete, and describe topics.
kafka-topics.sh
```

```sh
# List all existing Kafka topics
kafka-topics.sh --bootstrap-server localhost:9092 --list
```

## 2. Create Topics

Kafka topics can be created with **default settings** or with a specific number of partitions and replication factors.


### Create a Topic with Default Settings
By default, topics are created with **1 partition** and **a replication factor of 1**.

```sh
# Create a topic named 'first_topic' with default settings
kafka-topics.sh \
    --bootstrap-server localhost:9092 \
    --topic first_topic \
    --create
```

```sh
# List all existing Kafka topics
kafka-topics.sh \
    --bootstrap-server localhost:9092 \
    --list
```

### Create a Topic with Multiple Partitions
Using multiple partitions enables **parallelism and scalability**.

```sh
# Create a topic named 'second_topic' with 3 partitions
kafka-topics.sh \
    --bootstrap-server localhost:9092 \
    --topic second_topic \
    --create \
    --partitions 3
```

```sh
# List all existing Kafka topics
kafka-topics.sh \
    --bootstrap-server localhost:9092 \
    --list
```

### Create a Topic with Partitions and Replication Factor

Replication ensures **fault tolerance** by distributing copies of partitions across multiple brokers.

```sh
# Attempt to create a topic with a replication factor of 2
# This will fail if there is only 1 broker running
kafka-topics.sh \
    --bootstrap-server localhost:9092 \
    --topic third_topic \
    --create \
    --partitions 3 \
    --replication-factor 2
```

To avoid failure, ensure the **replication factor does not exceed the number of available brokers**. If only one broker is running, use:

```sh
# Create a topic with 3 partitions and a replication factor of 1
kafka-topics.sh \
    --bootstrap-server localhost:9092 \
    --topic third_topic \
    --create \
    --partitions 3 \
    --replication-factor 1
```

## 3️. Verify Topic Creation
After creating topics, list them to confirm they exist:

```sh
# List all existing topics
kafka-topics.sh \
    --bootstrap-server localhost:9092 \
    --list
```

To get detailed information about a topic, including **partitions and replication factor**, use:

```sh
# Describe 'first_topic'
kafka-topics.sh \
    --bootstrap-server localhost:9092 \
    --topic first_topic \
    --describe

# Describe 'second_topic'
kafka-topics.sh \
    --bootstrap-server localhost:9092 \
    --topic second_topic \
    --describe

# Describe 'third_topic'
kafka-topics.sh \
    --bootstrap-server localhost:9092 \
    --topic third_topic \
    --describe
```

## 4️. Delete Kafka Topics (Irreversible!)
Deleting a topic **removes all data and metadata** associated with it. Be **cautious**, as this action is **irreversible** unless topic deletion is disabled in Kafka configurations (`delete.topic.enable=false`).

```sh
# Delete 'first_topic'
kafka-topics.sh \
    --bootstrap-server localhost:9092 \
    --topic first_topic \
    --delete

# Delete 'second_topic'
kafka-topics.sh \
    --bootstrap-server localhost:9092 \
    --topic second_topic \
    --delete

# Delete 'third_topic'
kafka-topics.sh \
    --bootstrap-server localhost:9092 \
    --topic third_topic \
    --delete
```

Verify that the topics have been deleted:

```sh
# List all remaining topics
kafka-topics.sh --bootstrap-server localhost:9092 --list
```
