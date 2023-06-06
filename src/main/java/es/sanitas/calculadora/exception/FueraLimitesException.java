package es.sanitas.calculadora.exception;

import java.math.BigDecimal;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FueraLimitesException extends CalculadoraException {

	private static final long serialVersionUID = 43876691117560211L;
	
	public FueraLimitesException(String operacion, BigDecimal op, String valoresLimite) {
		super(operacion+"Se ha rebasado el valor mínimo o máximo para el operando "+op+". "+valoresLimite);
	}
}
