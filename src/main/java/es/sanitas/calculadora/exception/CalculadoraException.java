package es.sanitas.calculadora.exception;

import io.corp.calculator.TracerImpl;
import lombok.Setter;

public class CalculadoraException extends RuntimeException {

	@Setter	
	private static TracerImpl tracer;
	
	private static final long serialVersionUID = 43876691117560211L;
	
	public CalculadoraException(String message) {
		super(message);
		if(tracer!=null) tracer.trace(message);
	}
}
