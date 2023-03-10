package com.mensajeria.configuracion;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@ConditionalOnProperty(
	     value = "aplicacion.agendamiento.habilitar", havingValue = "true", matchIfMissing = true
	  )
@EnableScheduling
public class SchedulingConfiguration {

}
