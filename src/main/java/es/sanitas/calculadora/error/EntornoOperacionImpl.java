package es.sanitas.calculadora.error;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class EntornoOperacionImpl implements EntornoOperacion{
	public final BigDecimal min;
	public final BigDecimal max;
	public final int scale;
	
	public EntornoOperacionImpl(double min,double max,int scale)
	{
		this(new BigDecimal(min),new BigDecimal(max),scale);
	}
}
