package org.example;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.protocol.types.Field;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
import java.util.Random;


public class ConsumerDemo {

    private static final Logger log = LoggerFactory.getLogger(ConsumerDemo.class.getSimpleName());
    private static final String path = "/mnt/consumer.properties";
    public static void main(String[] args) {
        log.info("File Path" + path);
        log.info("File Content:" + readContentFromFile(path));
        //create Producer properties
        Properties properties = new Properties();
        String topic = "role";
//        String path = "/mnt/client.properties";

        //load the client.properties file
        loadProperties(properties, path);
        log.info("Properties file is getting loaded");
        //Producer configuration
//        producerConfig(properties);

        //create the Producer
        Consumer<String, String> consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Arrays.asList(topic));


        while (true) {
            log.info("Polling");
            ConsumerRecords<String, String> consumerRecords = consumer.poll(Duration.ofMillis(5000));

            for (ConsumerRecord<String, String> consumerRecord : consumerRecords) {
                log.info("Key: " + consumerRecord.key() + "Value: "+ consumerRecord.value());
                log.info("Partition: " + consumerRecord.partition() + "offset: " + consumerRecord.offset());
            }
        }
    }
    private static void loadProperties(Properties properties, String path) {
        try (FileInputStream fileInputStream = new FileInputStream(path)){
            properties.load(fileInputStream);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    private static String readContentFromFile(String Path) {
        try {
            return String.join("\n", Files.readAllLines(Paths.get(Path)));
        } catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }
//    private static void producerConfig(Properties properties){
//        properties.setProperty("key.serializer",StringSerializer.class.getName());
//        properties.setProperty("value.serializer",StringSerializer.class.getName());
//    }
}
