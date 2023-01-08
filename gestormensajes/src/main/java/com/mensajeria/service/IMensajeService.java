package com.mensajeria.service;

import java.util.Optional;

import com.mensajeria.domain.Mensaje;

public interface IMensajeService {

	Optional<Mensaje> encontrarPorFechaActualEEmailYMensaje(String fechaActual, String email, String mensaje);

	void guardar(Mensaje mensaje);

	void borrarTodos();

	Iterable<Mensaje> encontrarTodos();

}
