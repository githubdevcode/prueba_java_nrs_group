package com.mensajeria.infraestructura.excepcion;

public class MensajeProcesadoExcepcion extends RuntimeException {

	public MensajeProcesadoExcepcion() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MensajeProcesadoExcepcion(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public MensajeProcesadoExcepcion(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public MensajeProcesadoExcepcion(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public MensajeProcesadoExcepcion(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	
}
