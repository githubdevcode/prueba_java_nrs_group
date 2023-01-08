package com.mensajeria.configuracion;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
public class KafkaConfiguracion {
	@Autowired
	private Environment ambiente;
	
	Logger logger = LoggerFactory.getLogger(KafkaConfiguracion.class);
	
	@Bean
	public KafkaAdmin admin() {
	    Map<String, Object> configs = new HashMap<>();
	    // configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9093");
	    // configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka:9092");
	    configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, ambiente.getProperty("url.servidor.kafka"));
	    // System.out.println("Url servidor kafka: "+ambiente.getProperty("url.servidor.kafka"));
	    KafkaAdmin kafkaAdmin = new KafkaAdmin(configs);
	    return kafkaAdmin;
	}

	@Bean
	public NewTopic topicoMensaje1() {
	    return TopicBuilder.name("topico_mensaje")
	            .partitions(10)
	            .replicas(1)
	            .compact()
	            .build();
	}
	
	@Bean
	public ProducerFactory<Integer, String> factoriaProductor() {
	    return new DefaultKafkaProducerFactory<>(configuracionProductor());
	}

	@Bean
	public Map<String, Object> configuracionProductor() {
	    Map<String, Object> props = new HashMap<>();
	    // props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka:9092");
	    // props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9093");
	    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, ambiente.getProperty("url.servidor.kafka"));
	    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class);
	    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
	    // See https://kafka.apache.org/documentation/#producerconfigs for more properties
	    return props;
	}

	@Bean
	public KafkaTemplate<Integer, String> plantillaKafka() {
		KafkaTemplate<Integer, String> plantillaKafka = new KafkaTemplate<Integer, String>(factoriaProductor());
		// plantillaKafka.setConsumerFactory(factoriaConsumidor());
	    return plantillaKafka;
	}
	
	/*
	@Bean
	public KafkaMessageListenerContainer<Integer, String> kafkaMessageListenerContainer() {
		ContainerProperties containerProps = new ContainerProperties("thing2");
		containerProps.setMessageListener(new MessageListener<String, String>() {

			@Override
			public void onMessage(ConsumerRecord<String, String> data) {
				System.out.println("Mensaje recibido: "+data.value());
				//acknowledgment.acknowledge();
			}
		});
		DefaultKafkaConsumerFactory<Integer, String> cf =
		                        new DefaultKafkaConsumerFactory<>(consumerConfigs());
		KafkaMessageListenerContainer<Integer, String> container =
		                        new KafkaMessageListenerContainer<>(cf, containerProps);
		return container;
	}*/

	/*
	@Bean
	public Map<String, Object> configuracionConsumidor() {
		// TODO Auto-generated method stub
		Map<String, Object> props = new HashMap<>();
	    // props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka:9092");
	    // props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9093");
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, ambiente.getProperty("url.servidor.kafka"));
	    // props.put(ConsumerConfig.GROUP_ID_CONFIG, "test-consumer-group");
		props.put(ConsumerConfig.GROUP_ID_CONFIG, ambiente.getProperty("consumidor.grupo.id"));
	    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
	    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
	    // props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
		return props;
	}*/
	
	/*
	@Bean
	public ConsumerFactory<Integer, String> factoriaConsumidor() {
		DefaultKafkaConsumerFactory<Integer, String> cf =
                new DefaultKafkaConsumerFactory<>(configuracionConsumidor());
		return cf;
	}*/
}
