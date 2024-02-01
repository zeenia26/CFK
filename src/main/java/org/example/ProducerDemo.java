package org.example;
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
import java.util.Properties;
import java.util.Random;


public class ProducerDemo {

    private static final Logger log = LoggerFactory.getLogger(ProducerDemo.class.getSimpleName());
    private static final String path = "/mnt/client.properties";
    public static void main(String[] args) {
        log.info("File Path" + path);
        log.info("File Content:" + readContentFromFile(path));
        //create Producer properties
        Properties properties = new Properties();
//        String path = "/mnt/client.properties";

        //load the client.properties file
        loadProperties(properties, path);
        log.info("Properties file is getting loaded");
        //Producer configuration
        producerConfig(properties);

        //create the Producer
        Producer<String, String> producer = new KafkaProducer<>(properties);

        //create a producer record
        int data=1;

            while (true) {
                /*loadProperties(properties, path);*/

                try {
                    ProducerRecord<String, String> producerRecord = new ProducerRecord<>("role", Integer.toString(data), Integer.toString(data));
                    //send data
                    producer.send(producerRecord);
                    log.info("message sent");
                    //flush and close the produce

                    data++;
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    producer.flush();
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
    private static void producerConfig(Properties properties){
        properties.setProperty("key.serializer",StringSerializer.class.getName());
        properties.setProperty("value.serializer",StringSerializer.class.getName());
    }
}
