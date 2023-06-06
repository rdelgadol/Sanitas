package es.sanitas.calculadora.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class OperacionErroneaException extends CalculadoraException {
	
	private static final long serialVersionUID = 43876691117560211L;
	
	public OperacionErroneaException(String operacion, String operacionesValidas) {
		super("La operación "+operacion+" es incorrecta. "+operacionesValidas);
	}
}
