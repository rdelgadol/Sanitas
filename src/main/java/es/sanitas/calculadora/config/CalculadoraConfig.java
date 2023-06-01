package es.sanitas.calculadora.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.sanitas.calculadora.error.EntornoOperacion;
import es.sanitas.calculadora.error.EntornoOperacionImpl;

import io.corp.calculator.TracerAPI;
import io.corp.calculator.TracerImpl;

/**
 * Se establecen valores mínimo, máximo y máximo número de decimales para cada operación del REST API
 * Son unos valores aleatorios, simplemente para probar que la aplicacion valida bien
 */
@Configuration
public class CalculadoraConfig {

	@Bean("entornoSuma")
	public EntornoOperacion getEntornoSuma()
	{
		return new EntornoOperacionImpl(-1_000_000,1_000_000,5);
	}

	@Bean("entornoResta")
	public EntornoOperacion getEntornoResta()
	{
		return new EntornoOperacionImpl(-1_000_000,1_000_000,5);
	}

	@Bean
	public TracerImpl getTracerAPI()
	{
		return new TracerImpl();
	}
}
