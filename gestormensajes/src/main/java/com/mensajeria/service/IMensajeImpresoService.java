package com.mensajeria.service;

import java.util.Optional;

import com.mensajeria.domain.MensajeImpreso;

public interface IMensajeImpresoService {

	Optional<MensajeImpreso> encontrarPorFechaActualEEmailYMensaje(String fechaActual, String email, String mensaje);

	void guardar(MensajeImpreso mensajeImpreso);
	
	Iterable<MensajeImpreso> encontrarTodos();

	void borrarTodos();
}
