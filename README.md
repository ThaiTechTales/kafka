# Kafka Projects Repository

## Purpose

This repository serves as a collection of projects demonstrating expertise in Apache Kafka, a distributed event-streaming platform. The projects aim to highlight real-time data integration and stream processing solutions.

## Intention

- To build scalable and fault-tolerant messaging systems.
- To showcase real-world applications of Kafka in data streaming, microservices, and event-driven architectures.

## Projects

-

## Setup Guide

### 1. Kafka in KRaft Mode (Zookeeper-less)

Kafka runs in **KRaft (Kafka Raft) mode**, which eliminates the need for Apache ZooKeeper and uses its own Raft-based metadata quorum.

### 2. Generate a Random UUID for Storage

Run the following command to generate a **random UUID** for Kafka storage:

```sh
kafka-storage.sh random-uuid
```

### 3. Format the Kafka Storage

Format the Kafka storage with the generated UUID by running:

```sh
~/kafka_2.13-3.9.0/bin/kafka-storage.sh format -t <uuid> -c ~/kafka_2.13-3.9.0/config/kraft/server.properties
```

Example:

```sh
~/kafka_2.13-3.9.0/bin/kafka-storage.sh format -t 3pGmqt0hQ-CV2vJzvcURUg -c ~/kafka_2.13-3.9.0/config/kraft/server.properties
```

### 4. Start the Kafka Server

To start the Kafka server in **KRaft mode**, execute:

```sh
~/kafka_2.13-3.9.0/bin/kafka-server-start.sh ~/kafka_2.13-3.9.0/config/kraft/server.properties
```

### Using Kafka Command-Line Interface (CLI)

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
