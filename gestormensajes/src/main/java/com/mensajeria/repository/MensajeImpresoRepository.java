package com.mensajeria.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.mensajeria.domain.MensajeImpreso;

public interface MensajeImpresoRepository extends CrudRepository<MensajeImpreso, String> {
	public Optional<MensajeImpreso> findByFechaActualAndEmailAndMensaje(String fechaActual, String email,
			String mensaje);
}
