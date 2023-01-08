package com.mensajeria.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.mensajeria.domain.Mensaje;

public interface MensajeServiceRepository extends CrudRepository<Mensaje, String>{
	// Optional<Mensaje> findByCorrelationId(String correlationId);
	
	Optional<Mensaje> findByFechaActualAndEmailAndMensaje(String fechaActual, String email, String mensaje);
}
