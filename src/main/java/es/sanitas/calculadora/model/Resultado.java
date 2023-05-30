package es.sanitas.calculadora.model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Resultado {
	@Getter
	private final BigDecimal resultado;
}
