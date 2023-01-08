package com.mensajeria.configuracion;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

@Configuration
@EnableKafka
public class KafkaConfiguration2 {
	@Autowired
	private org.springframework.core.env.Environment ambiente;
	
	@Bean
    KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<Integer, String>>
			factoriaContenedorEscuchaKafka() {
        ConcurrentKafkaListenerContainerFactory<Integer, String> factoria =
                                new ConcurrentKafkaListenerContainerFactory<>();
        factoria.setConsumerFactory(factoriaConsumidor2());
        factoria.setConcurrency(3);
        factoria.getContainerProperties().setPollTimeout(3000);
        //factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
        return factoria;
    }

    @Bean
    public ConsumerFactory<Integer, String> factoriaConsumidor2() {
        return new DefaultKafkaConsumerFactory<>(configuracionConsumidor2());
    }

    @Bean
    public Map<String, Object> configuracionConsumidor2() {
        Map<String, Object> props = new HashMap<>();
        // props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka:9093");
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, ambiente.getProperty("url.servidor.kafka"));
        // System.out.println("Url servidor kafka: "+ambiente.getProperty("url.servidor.kafka"));
        // props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9096");
        //props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, urlServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, ambiente.getProperty("consumidor.grupo.id"));
	    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
	    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return props;
    }
}
