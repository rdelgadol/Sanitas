package es.sanitas.calculadora.model;

import java.math.BigDecimal;
import java.util.Map;
import java.util.function.BinaryOperator;

public interface OperacionesMap {
	Map<String, BinaryOperator<BigDecimal>> getOperaciones();
	BinaryOperator<BigDecimal> get(String operacion);
}