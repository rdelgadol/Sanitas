package es.sanitas.calculadora.error;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Clase que define los valores mínimo, máximo y número máximo de decimales
 * para una operación en concreto.
 */
@RequiredArgsConstructor
@Getter
public class EntornoOperacionImpl implements EntornoOperacion{
	public final BigDecimal min;
	public final BigDecimal max;
	public final int scale;
	
	public EntornoOperacionImpl(double min,double max,int scale)
	{
		this(BigDecimal.valueOf(min),BigDecimal.valueOf(max),scale);
	}
}
