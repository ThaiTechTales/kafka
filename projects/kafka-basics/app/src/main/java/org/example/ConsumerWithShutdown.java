/*
The main reason to shut down a consumer gracefully is to commit the offsets of read messages before the consumer is shut down. 
If offsets are not committed, messages may be re-read, leading to duplicate processing. 
Additionally, if the consumer does not leave the consumer group gracefully, the group coordinator will take time to detect the dead consumer instance (based on session timeout, heartbeat interval, etc.).

When the shutdown hook is triggered, consumer.wakeup() will be called, causing a WakeupException in the consumer.poll() method. 
Then, mainThread.join() will be called to wait for the main thread to finish, allowing the execution of the code in the main thread. 
Finally, consumer.close() will be called to close the consumer and commit the offsets.
 */

 /*
Add the following to the `build.gradle.kts` file in the dependencies section to run `pkill -SIGINT -f ConsumerDemoWithShutdown` from another terminal to gracefully shutdown the consumer (which is running in the other terminal):
tasks.withType<JavaExec>().configureEach {
    // Ignore non-zero exit values (like SIGINT = 130)
    isIgnoreExitValue = true
}
 */
package org.example;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsumerWithShutdown {

    // Logger instance for logging information and debugging
    private static final Logger log = LoggerFactory.getLogger(ConsumerWithShutdown.class.getSimpleName());

    public static void main(String[] args) {
        log.info("I am a Kafka Consumer - with shutdown");
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

        // Get a reference to the main thread
        final Thread mainThread = Thread.currentThread();

        // Add a shutdown hook
        // This will be executed when the application is terminated
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                log.info("Caught shutdown hook, shutting down gracefully by calling consumer.wakeup()");

                // So next time when a poll is called, it will throw WakeupException
                consumer.wakeup();

                // Join the main thread with the current thread (shutdown hook) to 
                //      wait for the main thread to finish to allow for the execution of the code in the main thread
                try {
                    mainThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        try {
            // Subscribe to a topic
            consumer.subscribe(Arrays.asList(topic));

            // Poll for new data
            while (true) {
                log.info("Polling for new data");

                // How long we're willing to wait to receive data
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));

                // How long we're willing to wait to receive data
                // At some point, the poll method will throw a WakeupException when the consumer.wakeup() method is called
                for (ConsumerRecord<String, String> record : records) {
                    log.info("Key: " + record.key() + ", Value: " + record.value());
                    log.info("Partition: " + record.partition() + ", Offset: " + record.offset());
                }
            }
        } catch (WakeupException e) {
            log.info("Received shutdown signal! Consumer shutting down...");
        } catch (Exception e) {
            log.error("Unexpected exception in the consumer: " + e.getMessage());
        } finally {
            consumer.close(); // This will also commit the offsets
            log.info("Consumer has gracefully shutdown");
        }

    }
}
