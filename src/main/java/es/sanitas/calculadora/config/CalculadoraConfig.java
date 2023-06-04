package es.sanitas.calculadora.config;

import java.util.HashMap;
import java.util.Map;
import java.math.BigDecimal;
import java.util.function.BinaryOperator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.sanitas.calculadora.error.EntornoOperacion;
import es.sanitas.calculadora.error.EntornoOperacionImpl;

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
	 * @return Map con todas las operaciones que puede realizar la aplicación.
	 */
	@Bean
	public Map<String,BinaryOperator<BigDecimal>> geOperaciones()
	{
		//Iniciamos el HadhMap con una capacidad de 4, pensando en que se agregarán las operaciones de multiplicación y división. 
		HashMap<String,BinaryOperator<BigDecimal>> map=new HashMap<>(4);
		map.put("suma", (op1, op2) -> op1.add(op2));
		map.put("resta", (op1, op2) -> op1.subtract(op2));
		return map;
	}	

	@Bean
	public TracerImpl getTracerAPI()
	{
		return new TracerImpl();
	}
}
