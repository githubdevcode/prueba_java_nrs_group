package com.mensajeria.configuracion;

import java.util.Iterator;
import java.util.concurrent.CompletableFuture;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.gson.Gson;
import com.mensajeria.controller.KafkaController;
import com.mensajeria.domain.Mensaje;
import com.mensajeria.domain.MensajeImpreso;
import com.mensajeria.service.IMensajeImpresoService;
import com.mensajeria.service.IMensajeService;
import com.mensajeria.service.dto.MensajeDTO;

@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:application_test.properties")
@DirtiesContext
@EmbeddedKafka(brokerProperties = { "listeners=PLAINTEXT://localhost:9096", "port=9096"})
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class EnviarMensajeTest {
	@Autowired
	KafkaTemplate<Integer, String> plantillaKafka;
	@Autowired
	IMensajeService mensajeService;
	@Autowired
	IMensajeImpresoService mensajeImpresoService;
	@Autowired
	KafkaController kafkaController;
	
	Logger logger = LoggerFactory.getLogger(EnviarMensajeTest.class);
	
	// @Disabled
	@Test
	public void testEnviarMensaje() {
		// System.out.println("Este test!!");
		CompletableFuture<SendResult<Integer, String>> resultadoEnvioFuturo = plantillaKafka.send(obtenerProducerRecord("topico_mensaje", 0, 41, "{\n"
				+ "\"fechaActual\": \"02-01-2023\",\n"
				+ "\"email\": \"jjfk8927@test.com\",\n"
				+ "\"mensaje\": \"Hola, tu codigo de acceso es 2232\"\n"
				+ "}"));
		resultadoEnvioFuturo.whenComplete((resultado, error) -> {
			if(resultado != null) {
				Gson gson = new Gson();
		        MensajeDTO mensajeDTO = gson.fromJson(resultado.getProducerRecord().value(), MensajeDTO.class);
				logger.info("El resultado es: Llave -> {} Valor -> {} Particion -> {} Offset -> {}", resultado.getProducerRecord().key(),
					resultado.getProducerRecord().value(), resultado.getProducerRecord().partition(), resultado.getRecordMetadata().offset());
				Assert.assertEquals("La fecha actual no es igual a 02-01-2023", "02-01-2023", mensajeDTO.getFechaActual());
				Assert.assertEquals("El email no es el esperado jjfk8927@test.com", "jjfk8927@test.com", mensajeDTO.getEmail());
				Assert.assertEquals("El mensaje no es igual al esperado: Hola, tu codigo de acceso es 2232", mensajeDTO.getMensaje(), 
						"Hola, tu codigo de acceso es 2232");
				Assert.assertEquals("El topico no es igual a topico_mensaje", "topico_mensaje", resultado.getProducerRecord().topic());
				Assert.assertEquals("La llave del mensaje no es igual a 41", 41, resultado.getProducerRecord().key().intValue());
				Assert.assertEquals("La particion no es igual a 1", 1, resultado.getRecordMetadata().partition());
			}
			if(error != null) {
				logger.error("Ha ocurrido un error al tratar de enviar el mensaje. El mensaje de error es {} La traza del error es: {}", 
						error.getMessage(), error.getStackTrace());
			}
			Assert.assertNotNull("El resultado es igual a null", resultado);
			Assert.assertNull("La excepcion no es igual a null", error);
			// Assert.assertTrue("No se encuentran mensajes almacenados", mensajeService.encontrarTodos().iterator().hasNext());
			// Assert.assertTrue("No se encontraron mensajes impresos almacenados", mensajeImpresoService.encontrarTodos().iterator().hasNext());
		});
	}

	private ProducerRecord<Integer, String> obtenerProducerRecord(String topico, Integer particion, Integer llave, String valor) {
		// TODO Auto-generated method stub
		return new ProducerRecord<Integer, String>(topico, particion, llave, valor);
	}
	
	@Disabled
	@Test
	public void testEnviarPayloadNull() {
		CompletableFuture<SendResult<Integer, String>> resultadoEnvioFuturo = plantillaKafka.send(obtenerProducerRecord("topico_mensaje", 1, 4382, ""));
		resultadoEnvioFuturo.whenComplete((resultado, error) -> {
			if(resultado != null) {
		        logger.info("El resultado es: El topico es -> {} Particion -> {} Offset -> {}", resultado.getProducerRecord().topic(),
		        		resultado.getProducerRecord().partition(), resultado.getRecordMetadata().offset());
		        Assert.assertEquals("El topico no es igual a topico_mensaje", "topico_mensaje", resultado.getProducerRecord().topic());
		        Assert.assertEquals("La particion no es igual a 1", 1, resultado.getProducerRecord().partition().intValue());
		        Assert.assertEquals("La llave del mensaje no es igual a 4382", 4382, resultado.getProducerRecord().key().intValue());
			}
			
			if(error != null) {
				logger.error("Ha ocurrido un error al tratar de enviar el mensaje. El mensaje de error es {} La traza del error es: {}", 
						error.getMessage(), error.getStackTrace());
			}
			
			Assert.assertNotNull("El resultado es igual a null", resultado);
			Assert.assertNull("La excepcion no es igual a null", error);
		});
	}
	
	@BeforeEach
	public void setUp() {
		mensajeService.borrarTodos();
		mensajeImpresoService.borrarTodos();
	}
	
	// @Disabled
	@Test
	public void testMensajeNoExisteNoFueImpreso() {
		Mensaje mensaje = new Mensaje();
		mensaje.setFechaActual("06-01-2023");
		mensaje.setEmail("jjfk8927@test.com");
		mensaje.setMensaje("Hola, tu codigo de acceso es 2232");
		// mensajeService.guardar(mensaje);
		Gson gson = new Gson();
		String mensajeJson = gson.toJson(mensaje);
		kafkaController.escuchaMensajes(mensajeJson);
		Assert.assertTrue("El mensaje no fue impreso", mensajeImpresoService.encontrarTodos().iterator().hasNext());
	}
	
	// @Disabled
	@Test
	public void testMensajeNoExisteMensajeImpresoExiste() {
		MensajeImpreso mensajeImpreso = new MensajeImpreso();
		mensajeImpreso.setFechaActual("06-01-2023");
		mensajeImpreso.setEmail("jjfk8927@test.com");
		mensajeImpreso.setMensaje("Hola, tu codigo de acceso es 2232");
		mensajeImpresoService.guardar(mensajeImpreso);
		Mensaje mensaje = new Mensaje();
		mensaje.setFechaActual("05-01-2023");
		mensaje.setEmail("jjfk8927@test.com");
		mensaje.setMensaje("Hola, tu codigo de acceso es 2232");
		Gson gson = new Gson();
		String mensajeJson = gson.toJson(mensaje);
		kafkaController.escuchaMensajes(mensajeJson);
		Assert.assertTrue("El mensaje es igual a null", mensajeService.encontrarTodos().iterator().hasNext());
	}
}
