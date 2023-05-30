package es.sanitas.calculadora.error;

import java.math.BigDecimal;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FueraLimites extends RuntimeException {

	private static final long serialVersionUID = 43876691117560211L;
	
	public FueraLimites(BigDecimal op) {
		super("Se ha rebasado el valor mínimo o máximo para el operando "+op);
	}
}
