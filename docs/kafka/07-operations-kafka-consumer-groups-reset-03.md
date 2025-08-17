# Kafka Consumer Groups

Kafka consumer groups allow multiple consumers to share the load of reading messages from topics. Each consumer in a group is assigned a subset of the partitions in the topic, ensuring parallel processing and fault tolerance.

## 1. Describe a Consumer Group

Use the `--describe` flag to check the status of a consumer group. This command displays information such as partition assignments, lag, and the consumer instance details.

```sh
# Describe the consumer group
kafka-consumer-groups.sh \
    --bootstrap-server localhost:9092 \
    --describe \
    --group my-first-application
```

## 2. Dry Run: Reset Offsets to the Beginning

A dry run previews the offset reset process without actually modifying anything. This helps verify changes before execution.

```sh
# Dry run: reset the offsets to the beginning of each partition
kafka-consumer-groups.sh \
    --bootstrap-server localhost:9092 \
    --group my-first-application \
    --reset-offsets \
    --to-earliest \
    --topic third_topic \
    --dry-run
```

## 3. Execute Offset Reset

To apply the offset reset, use the `--execute` flag. This will reset the offset to the earliest message in the topic. Note that this action cannot be performed while the consumer group is active. Stop all consumers before proceeding.

```sh
# Reset offsets to the beginning of the topic
kafka-consumer-groups.sh \
    --bootstrap-server localhost:9092 \
    --group my-first-application \
    --reset-offsets \
    --to-earliest \
    --topic third_topic \
    --execute
```

Other options for resetting offsets include:

- `--to-earliest` to reset to the earliest message
- `--to-latest` to reset to the latest message
- `--to-offset` to reset to a specific offset.
- `--shift-by` to move the offset by a specific number
- `--to-datetime` to reset to a specific date and time

## 4. Verify the Consumer Group Status

After resetting offsets, describe the consumer group again to verify the changes. There will be a lag between the current offset and the end offset, indicating that the consumer group is behind. This lag increased because the offset moved backward, meaning the consumer has more messages to reprocess.

```sh
# Describe the consumer group after resetting offsets
kafka-consumer-groups.sh \
    --bootstrap-server localhost:9092 \
    --describe \
    --group my-first-application
```

## 5. Consume Messages from the Reset Offset

Start consuming messages from the topic using the consumer group. Since the offsets have been reset, the consumer will start reading from the beginning.

```sh
# Consume messages from the reset offset
kafka-console-consumer.sh \
    --bootstrap-server localhost:9092 \
    --topic third_topic \
    --group my-first-application
```

## 6. Verify Consumer Lag

Once the consumer has processed all available messages, the lag should be zero. Describe the consumer group again to confirm.

```sh
# Check the consumer group again to confirm lag is 0
kafka-consumer-groups.sh \
    --bootstrap-server localhost:9092 \
    --describe \
    --group my-first-application
