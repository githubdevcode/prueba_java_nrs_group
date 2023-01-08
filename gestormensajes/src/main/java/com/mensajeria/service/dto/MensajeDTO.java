package com.mensajeria.service.dto;

import java.time.LocalDate;

public class MensajeDTO {
	//private Integer id;
	private String fechaActual;
	// private String consumidor;
	private String email;
	private String mensaje;
	
	public MensajeDTO() {
		super();
	}

	public String getFechaActual() {
		return fechaActual;
	}

	public void setFechaActual(String fechaActual) {
		this.fechaActual = fechaActual;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

}
