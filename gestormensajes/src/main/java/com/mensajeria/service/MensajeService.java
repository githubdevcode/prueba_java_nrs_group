package com.mensajeria.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mensajeria.domain.Mensaje;
import com.mensajeria.repository.MensajeServiceRepository;

@Service
public class MensajeService implements IMensajeService {
	@Autowired
	private MensajeServiceRepository mensajeRepository;
	
	@Override
	public Optional<Mensaje> encontrarPorFechaActualEEmailYMensaje(String fechaActual, String email, String mensaje) {
		// TODO Auto-generated method stub
		return mensajeRepository.findByFechaActualAndEmailAndMensaje(fechaActual, email, mensaje);
	}

	@Override
	public void guardar(Mensaje mensaje) {
		// TODO Auto-generated method stub
		mensajeRepository.save(mensaje);
	}

	@Override
	public void borrarTodos() {
		// TODO Auto-generated method stub
		mensajeRepository.deleteAll();
	}

	@Override
	public Iterable<Mensaje> encontrarTodos() {
		// TODO Auto-generated method stub
		return mensajeRepository.findAll();
	}

}
