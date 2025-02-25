/*
 * This is a basic Kafka producer that sends a simple message 
 * to a Kafka topic using the KafkaProducer API.
 */
package org.example;

import java.util.Properties;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProducerWithKeys {

    // Logger instance for logging information and debugging
    private static final Logger log = LoggerFactory.getLogger(ProducerWithKeys.class.getSimpleName());

    public static void main(String[] args) {
        log.info("I am a Kafka Producer with a Callback");
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

        for (int j = 0; j < 2; j++) {

            // Send 10 messages to the Kafka topic note that it will use sticky partitioning because the key is null
            // Sticky partitioning is when all messages with the same key go to the same partition (if the key is null, it will be sticky to a partition)
            for (int i = 0; i < 10; i++) {

                String topic = "first_topic";
                String key = "id_" + i;
                String value = "Hello, Kafka!! " + i;

                // Create a Producer Record (message to be sent)
                // A ProducerRecord consists of:
                //    - Topic: The Kafka topic to which the message is sent
                //    - Key (optional): Used for partitioning. If the key is null, it will be sticky to a partition however if the key is not null, it will be sent to a partition based on the hash of the key
                //    - Value: The actual message content
                ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, value);

                // Send the message
                producer.send(record, new Callback() {
                    @Override
                    public void onCompletion(RecordMetadata metadata, Exception exception) {
                        // Executes every time a record is successfully sent or an exception is thrown

                        if (exception == null) {
                            // if the record is successfully sent
                            log.info("Key: " + key + " | Partition: " + metadata.partition());
                        } else {
                            log.error("Error while producing", exception);
                        }
                    }
                });
            }

            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
            }
        }

        // Flush and close the producer
        // Ensures all pending messages are sent before closing the producer
        producer.close();

        log.info("Kafka Producer Application Finished");
    }
}
