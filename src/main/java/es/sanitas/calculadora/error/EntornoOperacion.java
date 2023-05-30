package es.sanitas.calculadora.error;

import java.math.BigDecimal;

public interface EntornoOperacion {
	public BigDecimal getMin();
	public BigDecimal getMax();
	public int getScale();
}
