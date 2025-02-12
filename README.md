# Kafka Projects Repository

## Purpose

This repository serves as a collection of projects demonstrating expertise in Apache Kafka, a distributed event-streaming platform. The projects aim to highlight real-time data integration and stream processing solutions.

## Intention

- To build scalable and fault-tolerant messaging systems.
- To showcase real-world applications of Kafka in data streaming, microservices, and event-driven architectures.

## Projects

-

## Setup

### Kafka in KRaft Mode

Kafka runs in **KRaft (Kafka Raft) mode**, which eliminates the need for Apache ZooKeeper and uses its own Raft-based metadata quorum.

### Generate a Random UUID for Storage

Run the following command to generate a **random UUID** for Kafka storage:

```sh
kafka-storage.sh random-uuid
```

### Format the Kafka Storage

Format the Kafka storage with the generated UUID by running:

```sh
~/kafka_2.13-3.9.0/bin/kafka-storage.sh format -t <uuid> -c ~/kafka_2.13-3.9.0/config/kraft/server.properties
```

Example:

```sh
~/kafka_2.13-3.9.0/bin/kafka-storage.sh format -t 3pGmqt0hQ-CV2vJzvcURUg -c ~/kafka_2.13-3.9.0/config/kraft/server.properties
```

### Start the Kafka Server

To start the Kafka server in **KRaft mode**, execute:

```sh
~/kafka_2.13-3.9.0/bin/kafka-server-start.sh ~/kafka_2.13-3.9.0/config/kraft/server.properties
```