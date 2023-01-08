package com.mensajeria.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mensajeria.domain.MensajeImpreso;
import com.mensajeria.repository.MensajeImpresoRepository;

@Service
public class MensajeImpresoService implements IMensajeImpresoService {
	@Autowired
	private MensajeImpresoRepository mensajeImpresoRepository;
	
	@Override
	public Optional<MensajeImpreso> encontrarPorFechaActualEEmailYMensaje(String fechaActual, String email,
			String mensaje) {
		// TODO Auto-generated method stub
		return mensajeImpresoRepository.findByFechaActualAndEmailAndMensaje(fechaActual, email, mensaje);
	}

	@Override
	public void guardar(MensajeImpreso mensajeImpreso) {
		// TODO Auto-generated method stub
		mensajeImpresoRepository.save(mensajeImpreso);
	}

	@Override
	public Iterable<MensajeImpreso> encontrarTodos() {
		// TODO Auto-generated method stub
		return mensajeImpresoRepository.findAll();
	}

	@Override
	public void borrarTodos() {
		// TODO Auto-generated method stub
		mensajeImpresoRepository.deleteAll();
	}

}
