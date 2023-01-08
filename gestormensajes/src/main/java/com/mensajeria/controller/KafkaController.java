package com.mensajeria.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
// import java.util.logging.Logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Controller;

import com.google.gson.Gson;
import com.mensajeria.domain.Mensaje;
import com.mensajeria.domain.MensajeImpreso;
import com.mensajeria.infraestructura.excepcion.MensajeLogueadoExcepcion;
import com.mensajeria.infraestructura.excepcion.MensajeProcesadoExcepcion;
import com.mensajeria.service.IMensajeImpresoService;
import com.mensajeria.service.IMensajeService;
import com.mensajeria.service.dto.MensajeDTO;

@Controller
public class KafkaController {
	@Autowired
	IMensajeService mensajeService;
	@Autowired
	IMensajeImpresoService mensajeImpresoService;
	
	Logger logger = LoggerFactory.getLogger(KafkaController.class);

	@KafkaListener(id = "escuchaKafka1", topics = "topico_mensaje", clientIdPrefix = "topicoMensaje",
    		containerFactory = "factoriaContenedorEscuchaKafka")
    public void escuchaMensajes(String data) {
		try {
	        // logger.info("El mensaje recibido en el listener es: {}", data);
	        
	        Gson gson = new Gson();
	        MensajeDTO mensajeDTO = gson.fromJson(data, MensajeDTO.class);
	        
	        Optional<Mensaje> mensajeDBOpt = mensajeService.encontrarPorFechaActualEEmailYMensaje(mensajeDTO.getFechaActual(), mensajeDTO.getEmail(),
	        		mensajeDTO.getMensaje());
	        if(!mensajeDBOpt.isPresent()) {
	        	Mensaje mensaje = gson.fromJson(data, Mensaje.class);
	        	mensajeService.guardar(mensaje);
	        	// throw new MensajeProcesadoExcepcion(String.format("El mensaje %s ya existe y por tanto no se puede procesar", data));
	        	SimpleDateFormat formateador = new SimpleDateFormat("dd-MM-yyyy");
		        mensajeDTO.setFechaActual(formateador.format(new Date()));
		        Optional<MensajeImpreso> mensajeImpresoDBOpt = mensajeImpresoService.encontrarPorFechaActualEEmailYMensaje(mensajeDTO.getFechaActual(), 
		        		mensajeDTO.getEmail(), mensajeDTO.getMensaje());
		        if(!mensajeImpresoDBOpt.isPresent()) {
		        	MensajeImpreso mensajeImpreso = new MensajeImpreso();
		        	mensajeImpreso.setFechaActual(mensajeDTO.getFechaActual());
		        	mensajeImpreso.setEmail(mensajeDTO.getEmail());
		        	mensajeImpreso.setMensaje(mensajeDTO.getMensaje());
		        	mensajeImpresoService.guardar(mensajeImpreso);
		        	logger.info("Mensaje consumido: Fecha actual: {} Consumidor: {} Email: {} Mensaje: {}", mensajeDTO.getFechaActual(),
			        		Thread.currentThread().getName(), mensajeDTO.getEmail(), mensajeDTO.getMensaje());
		        } else {
		        	String mensajeJson = gson.toJson(mensajeDTO);
		        	throw new MensajeLogueadoExcepcion(String.format("El mensaje %s ya fue puesto en los mensajes de log", mensajeJson));
		        }
	        } else
	        	throw new MensajeProcesadoExcepcion(String.format("El mensaje %s ya existe y por tanto no se puede procesar", data));
		} catch(Exception ex) {
			ex.printStackTrace();
			logger.error("Ocurrio un error al tratar de procesar el mensaje: {} El mensaje de error es: {} La traza del error es: {}",
					data, ex.getMessage(), ex.getStackTrace());
		}
    }
}
