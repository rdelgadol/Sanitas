package es.sanitas.calculadora.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.sanitas.calculadora.error.EntornoOperacion;
import es.sanitas.calculadora.error.EntornoOperacionImpl;
import es.sanitas.calculadora.model.OperacionesMap;
import es.sanitas.calculadora.model.OperacionesMapImpl;

import io.corp.calculator.TracerImpl;

@Configuration
public class CalculadoraConfig {
	/**
	 * @return Valores mínimo, máximo y máximo número de decimales para cada operación del REST API.
	 */
	@Bean
	public EntornoOperacion getEntornoOperacion()
	{
		//Son unos valores aleatorios, simplemente para probar que la aplicación valida bien.
		return new EntornoOperacionImpl(-1_000_000,1_000_000,5);
	}
	
	/**
	 * @return Objeto que contiene en un Map todas las operaciones que puede realizar la aplicación.
	 */
	@Bean
	public OperacionesMap getOperacioneMaps()
	{
		return new OperacionesMapImpl();
	}	

	@Bean
	public TracerImpl getTracerAPI()
	{
		return new TracerImpl();
	}
}
