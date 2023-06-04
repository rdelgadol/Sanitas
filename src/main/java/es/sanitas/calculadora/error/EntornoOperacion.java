package es.sanitas.calculadora.error;

import java.math.BigDecimal;

public interface EntornoOperacion {
	BigDecimal getMin();
	BigDecimal getMax();
	int getScale();
}
