# Kafka Producers CLI

Kafka producers are applications responsible for writing data to Kafka topics. They send messages to Kafka brokers, which store them within the designated topics. Producers can send messages with or without keys and configure various properties, such as acknowledgments and message compression, to optimise performance and reliability.

## 1️. Create a Topic with Partitions

Kafka topics are where messages are stored. By default, if a producer sends messages to a **non-existent topic**, Kafka automatically creates it **with 1 partition** and **a replication factor of 1**.  

However, it's **recommended** to create topics **explicitly** with the appropriate number of partitions to avoid **scalability and performance issues**.

**Note:**

- Small applications → 1-3 partitions.
- Larger applications → 6+ partitions.
- Increasing partitions after creation can lead to ordering issues and require data redistribution.

```sh
# Terminal 1 (Topic) - Create a topic with 1 partition
kafka-topics.sh \
    --bootstrap-server localhost:9092 \
    --topic first_topic \
    --create \
    --partitions 1
```

## 2️. Produce Messages to a Topic

Kafka producers write messages to a topic.
Use `kafka-console-producer.sh` to send messages to `first_topic`.

**Note:**

- Without a key, messages are distributed to partitions round-robin.
- With a key, messages with the same key are sent to the same partition.

```sh
# Terminal 2 (Producer) - Producing messages to 'first_topic'
kafka-console-producer.sh \
    --bootstrap-server localhost:9092 \
    --topic first_topic
> Hello World
> My name is Thai
> ^C  # Press Ctrl+C to exit
```

## 3. Additional Producer Commands (Enable Message Acknowledgment (acks))

By default, Kafka only acknowledges messages **once they are received by the leader.**
For better **reliability**, set `acks=all`, which ensures messages are **fully committed** by all in-sync replicas before being acknowledged.

**Note:**

- `acks=0` → No acknowledgment (fastest, but risky).
- `acks=1` → Acknowledgment from the leader only.
- `acks=all` → Acknowledgment from all in-sync replicas (most reliable).

```sh
# Terminal 2 (Producer) - Producing messages with acks=all
kafka-console-producer.sh \
    --bootstrap-server localhost:9092 \
    --topic first_topic \
    --producer-property acks=all
> Bye World
> Until next time!
> ^C  # Press Ctrl+C to exit
```

## 4️. List All Existing Kafka Topics

To check available topics in the Kafka cluster:

```sh
# Terminal 1 (Topic) - List all topics
kafka-topics.sh \
    --bootstrap-server localhost:9092 \
    --list
```

## 5. Producing Messages to a Non-Existing Topic

If a topic does **not exist**, Kafka **creates it automatically** with default settings (1 partition, replication factor 1).

However, this is not recommended for production systems.

**Note:**

- Auto-topic creation can be disabled (auto.create.topics.enable=false in server.properties).
- Topics should be explicitly created to prevent uncontrolled topic growth.

```sh
# Terminal 2 (Producer) - Producing to a non-existent topic 'new_topic'
kafka-console-producer.sh \
    --bootstrap-server localhost:9092 \
    --topic new_topic
> Hello world!
> # ⚠️ A warning log will appear in Kafka server logs due to automatic topic creation
> ^C  # Press Ctrl+C to exit

```

## 6. Check Topic Details

```sh
# New topic only has 1 partition
kafka-topics.sh \
    --bootstrap-server localhost:9092 \
    --list

kafka-topics.sh \
    --bootstrap-server localhost:9092 \
    --topic new_topic --describe \
```

To change the **default partition count**, update the Kafka broker configuration:

```sh
# Edit server.properties or kraft/server.properties
num.partitions=3
```

## 7. Produce Messages with Key-Value Pair

By default, Kafka producers send messages **without keys**, meaning messages are **distributed randomly** across partitions.

Adding a key ensures that all messages with the same key **go to the same partition**, preserving order **within that partition**.

**Key Usage in Kafka:**

- No key → Messages are round-robin across partitions (unordered).
- With key → Messages with the same key go to the same partition (ordered per partition).
- Use cases → User sessions, transactions, customer orders.

```sh
# Terminal 2 (Producer) - Producing key-value pairs
kafka-console-producer.sh \
    --bootstrap-server localhost:9092 \
    --topic first_topic \
    --property parse.key=true \
    --property key.separator=:
> name:thai   # Key = 'name', Value = 'thai'
```
