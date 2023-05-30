package es.sanitas.calculadora.error;

import java.math.BigDecimal;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ScaleInvalido extends RuntimeException {

	private static final long serialVersionUID = 43876691117560211L;
	
	public ScaleInvalido(BigDecimal op) {
		super("El valor de scale es invalido para el operando "+op);
	}
}
