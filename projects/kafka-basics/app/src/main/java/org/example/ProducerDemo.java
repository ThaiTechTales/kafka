/*
 * This is a basic Kafka producer that sends a simple message 
 * to a Kafka topic using the KafkaProducer API.
 */
package org.example;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProducerDemo {

    // Logger instance for logging information and debugging
    private static final Logger log = LoggerFactory.getLogger(ProducerDemo.class.getSimpleName());

    public static void main(String[] args) {
        log.info("I am a Kafka Producer");
        log.info("Kafka Producer Application Started");

        // Define Kafka Producer properties
        Properties properties = new Properties();

        // Specify Kafka broker address (localhost in this case)
        properties.setProperty("bootstrap.servers", "127.0.0.1:9092");

        // Configure the serializer for key and value
        // Kafka requires data to be serialized before sending messages
        properties.setProperty("key.serializer", StringSerializer.class.getName());
        properties.setProperty("value.serializer", StringSerializer.class.getName());

        // Create the Kafka Producer
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        // Create a Producer Record (message to be sent)
        // A ProducerRecord consists of:
        //    - Topic: The Kafka topic to which the message is sent
        //    - Key (optional): Used for partitioning (null in this case)
        //    - Value: The actual message content
        ProducerRecord<String, String> record = new ProducerRecord<>("first_topic", "Hello, Kafka!");

        // Send the message asynchronously
        producer.send(record);

        // Flush and close the producer
        // Ensures all pending messages are sent before closing the producer
        producer.close();

        log.info("Kafka Producer Application Finished");
    }
}
