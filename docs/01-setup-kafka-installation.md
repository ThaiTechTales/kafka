# Setup Guide

## 1. Kafka in KRaft Mode (Zookeeper-less)

Kafka runs in **KRaft (Kafka Raft) mode**, which removes the need for Apache ZooKeeper and uses its own Raft-based system for managing metadata. Zookeeper will be deprecated and expected to me removed with Kafka 4.0.

## 2. Generate a Random UUID for Storage

A UUID (Universally Unique Identifier) is a 128-bit number used to uniquely identify objects. Run the following command to generate a **random UUID** for Kafka storage:

```sh
kafka-storage.sh random-uuid
```

## 3. Format the Kafka Storage

Format the Kafka storage with the generated UUID. By formatting the storage with a unique UUID, it ensures that the Kafka server can identify its own storage and metadata.

```sh
~/kafka_2.13-3.9.0/bin/kafka-storage.sh format -t <uuid> -c ~/kafka_2.13-3.9.0/config/kraft/server.properties
```

Example:

```sh
~/kafka_2.13-3.9.0/bin/kafka-storage.sh format -t 3pGmqt0hQ-CV2vJzvcURUg -c ~/kafka_2.13-3.9.0/config/kraft/server.properties
```

## 4. Start the Kafka Server

To start the Kafka server in **KRaft mode**, execute the below command. The Kafka server must be running in the background order to create topics, produce messages, and consume messages. This can be done by running the Kafka server in a separate terminal window or as a background process and in another terminal window run the Kafka CLI commands. If a Kafka server is not running, the Kafka CLI commands will not work.

```sh
~/kafka_2.13-3.9.0/bin/kafka-server-start.sh ~/kafka_2.13-3.9.0/config/kraft/server.properties
```
