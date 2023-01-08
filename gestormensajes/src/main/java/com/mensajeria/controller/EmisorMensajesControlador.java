package com.mensajeria.controller;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import com.google.gson.Gson;
import com.mensajeria.service.dto.MensajeDTO;

@Controller
public class EmisorMensajesControlador {
	@Autowired
	KafkaTemplate<Integer, String> plantillaKafka;
	
	Logger logger = LoggerFactory.getLogger(EmisorMensajesControlador.class);
	
	private int numeroParticion;
	
	@Scheduled(fixedDelay = 5000, initialDelay = 30000)
	public void enviarMensaje() {
		MensajeDTO mensajeDTO = new MensajeDTO();
		SimpleDateFormat formateador = new SimpleDateFormat("dd-MM-yyyy");
        mensajeDTO.setFechaActual(formateador.format(new Date()));
        char[] CARACTERES_AZ_09 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toLowerCase().toCharArray();
        mensajeDTO.setEmail(String.format("%s@gmail.com", textoRandomico(CARACTERES_AZ_09, 4)));
        int codigoAcceso = new Random().nextInt(1000, 10000);
        mensajeDTO.setMensaje(String.format("Hola, tu codigo de acceso es %d", new Random().nextLong(1000, 10000)));
        Gson gson = new Gson();
        String mensajeJSON = gson.toJson(mensajeDTO, MensajeDTO.class);
        plantillaKafka.send(getProducerRecord("topico_mensaje", numeroParticion, codigoAcceso, mensajeJSON));
        numeroParticion += 1;
        if(numeroParticion == 10)
        	numeroParticion = 0;
        logger.info("El mensaje enviado es: {}", mensajeJSON);
	}
	
	private ProducerRecord<Integer, String> getProducerRecord(String topico, Integer particion, Integer llave, String valor) {
		// TODO Auto-generated method stub
		return new ProducerRecord<Integer, String>(topico, particion, llave, valor);
	}
	
	public String textoRandomico(char[] caracteres, int tamano) {
	    Random randomico = new SecureRandom();
	    char[] resultado = new char[tamano];
	    for (int i = 0; i < resultado.length; i++) {
	        // picks a random index out of character set > random character
	        int indiceCaracterRandomico = randomico.nextInt(caracteres.length);
	        resultado[i] = caracteres[indiceCaracterRandomico];
	    }
	    return new String(resultado);
	}
}
