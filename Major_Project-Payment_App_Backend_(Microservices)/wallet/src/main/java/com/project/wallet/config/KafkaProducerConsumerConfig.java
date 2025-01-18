package com.project.wallet.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
public class KafkaProducerConsumerConfig {

    @Value("${kafka-host}")
    private String KafkaHost;

    @Value("${kafka-port}")
    private int KafkaPort;

    // creating the consumer factory : start a consumer line to the kafka server for consuming messages
    @Bean
    ConsumerFactory getConsumerFactory() {
        Map<String, Object> properties = new HashMap<>();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaHost+":"+KafkaPort);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        return new DefaultKafkaConsumerFactory(properties);
    }



    // creating the producer factory : start a producer line to the kafka server for producing messages
    @Bean
    ProducerFactory getProducerFactory(){
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        return new DefaultKafkaProducerFactory(properties);
    }

    @Bean
    KafkaTemplate<String, String> getKafkaTemplate(){
        return new KafkaTemplate<>(getProducerFactory());
    }

}