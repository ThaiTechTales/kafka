# Setup Guide

## 1. Kafka in KRaft Mode (Zookeeper-less)

Kafka runs in **KRaft (Kafka Raft) mode**, which eliminates the need for Apache ZooKeeper and uses its own Raft-based metadata quorum.

## 2. Generate a Random UUID for Storage

Run the following command to generate a **random UUID** for Kafka storage:

```sh
kafka-storage.sh random-uuid
```

## 3. Format the Kafka Storage

Format the Kafka storage with the generated UUID by running:

```sh
~/kafka_2.13-3.9.0/bin/kafka-storage.sh format -t <uuid> -c ~/kafka_2.13-3.9.0/config/kraft/server.properties
```

Example:

```sh
~/kafka_2.13-3.9.0/bin/kafka-storage.sh format -t 3pGmqt0hQ-CV2vJzvcURUg -c ~/kafka_2.13-3.9.0/config/kraft/server.properties
```

## 4. Start the Kafka Server

To start the Kafka server in **KRaft mode**, execute:

```sh
~/kafka_2.13-3.9.0/bin/kafka-server-start.sh ~/kafka_2.13-3.9.0/config/kraft/server.properties
```