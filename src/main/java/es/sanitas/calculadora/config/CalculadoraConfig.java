package es.sanitas.calculadora.config;

import java.lang.Double;
import java.math.MathContext;
import java.math.RoundingMode;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.sanitas.calculadora.error.EntornoOperacion;
import es.sanitas.calculadora.error.EntornoOperacionImpl;

@Configuration
public class CalculadoraConfig {

	@Bean("entornoSuma")
	public EntornoOperacion getEntornoSuma()
	{
//		return new EntornoOperacionImpl(Double.MIN_VALUE/2,Double.MAX_VALUE/2,10,5);
		return new EntornoOperacionImpl(-100,100,4);
	}

	@Bean("entornoResta")
	public EntornoOperacion getEntornoResta()
	{
//		return new EntornoOperacionImpl(Double.MIN_VALUE/2,Double.MAX_VALUE/2,10,5);
		return new EntornoOperacionImpl(-4,4,3);
	}
}
