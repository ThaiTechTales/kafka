/*
 * This is a basic Kafka producer that sends a simple message 
 * to a Kafka topic using the KafkaProducer API.
 */
package org.example;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Consumer {

    // Logger instance for logging information and debugging
    private static final Logger log = LoggerFactory.getLogger(Consumer.class.getSimpleName());

    public static void main(String[] args) {
        log.info("I am a Kafka Consumer");
        log.info("Kafka Producer Application Started");

        String groupID = "my-consumer-group";
        String topic = "first_topic";

        // Define Kafka Producer properties
        Properties properties = new Properties();

        // Specify Kafka broker address (localhost in this case)
        properties.setProperty("bootstrap.servers", "127.0.0.1:9092");

        // Create consumer configs
        properties.setProperty("key.deserializer", StringDeserializer.class.getName());
        properties.setProperty("value.deserializer", StringDeserializer.class.getName());

        properties.setProperty("group.id", groupID);

        // earliest: read from the beginning of the topic
        // latest: read only new messages
        // none: throws an error if no offsets are found. We must set the consumer group before starting the application
        properties.setProperty("auto.offset.reset", "earliest");

        // Create a consumer
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);

        // Subscribe to a topic
        consumer.subscribe(Arrays.asList(topic));

        // Poll for new data
        while (true) {
            log.info("Polling for new data");

            // How long we're willing to wait to receive data
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));

            for (ConsumerRecord<String, String> record : records) {
                log.info("Key: " + record.key() + ", Value: " + record.value());
                log.info("Partition: " + record.partition() + ", Offset: " + record.offset());
            }
        }
    }
}
