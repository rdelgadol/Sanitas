package es.sanitas.calculadora.model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ResultadoJson {
	@Getter
	private final BigDecimal resultado;
}
