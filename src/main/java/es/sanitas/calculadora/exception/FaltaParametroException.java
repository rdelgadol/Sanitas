package es.sanitas.calculadora.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FaltaParametroException extends CalculadoraException {

	private static final long serialVersionUID = 43876691117560211L;
	
	public FaltaParametroException(String operacion, String parametro) {
		super(operacion+"Falta introducir "+parametro);
	}
}
