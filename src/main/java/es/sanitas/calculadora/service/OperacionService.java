package es.sanitas.calculadora.service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.function.BinaryOperator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.corp.calculator.TracerImpl;

import es.sanitas.calculadora.error.EntornoOperacion;
import es.sanitas.calculadora.error.FueraLimites; 
import es.sanitas.calculadora.error.ScaleInvalido;

@Service
public class OperacionService {
	/*Elemento del tipo EntornoOperacion, que define los valores mínimo, máximo y número máximo de decimales
	 para todas las operaciones del API REST.*/
	@Autowired	
	public EntornoOperacion entorno;
	
	/*Map donde las claves son objetos de tipo String que identifican operaciones matemáticas, y los valores son objetos de tipo
	 BinaryOperator que describen cómo son realmente esas operaciones.
	 Sólo se pueden realizar operaciones matemáticas que estén registradas en este Map*/
	@Autowired	
	Map<String,BinaryOperator<BigDecimal>> operaciones;
	
	@Autowired
	public TracerImpl tracerImpl;
	
	private void tracertLimites(BigDecimal op,EntornoOperacion entorno) {
		tracerImpl.trace("Se ha rebasado el valor mínimo o máximo para el operando "+op+
				"\nEl valor mínimo es "+entorno.getMin()+", y el máximo es "+entorno.getMax());
	}

	private void tracertDecimales(BigDecimal op,EntornoOperacion entorno) {
		tracerImpl.trace("El número de decimales es inválido para el operando "+op+
				"\nEl valor máximo es "+entorno.getScale());
	}
	
	/**
	 * Plantilla para una operación. Incorpora todas las validaciones de mínimo, máximos y número máximo de decimales.
	 * @param op1 Primer operando.
	 * @param op2 Segundo operando.
	 * @param operacion String que representa qué operación se realiza. Sirve como clave para obtener un BinaryOperator.
	 * @param entorno Elemento del tipo EntornoOperacion, que define los valores mínimo, máximo y número máximo de decimales
	 * para una operación en concreto.
	 * @return El siguiente JSON {"resultado":Resultado de la operación}.
	 */
	public BigDecimal operar(String operacion, BigDecimal op1, BigDecimal op2) {
		if(op1.compareTo(entorno.getMin())<0 || op1.compareTo(entorno.getMax())>0) {
			tracertLimites(op1,entorno);
			throw new FueraLimites(op1);
		}
		else if(op2.compareTo(entorno.getMin())<0 || op2.compareTo(entorno.getMax())>0) {
			tracertLimites(op2,entorno);
			throw new FueraLimites(op2);
		}
		else if(op1.scale()>entorno.getScale()) {
			tracertDecimales(op1,entorno);
			throw new ScaleInvalido(op1);
		}
		else if(op2.scale()>entorno.getScale()) {
			tracertDecimales(op2,entorno);
			throw new ScaleInvalido(op2);
		}
		else {
			BigDecimal resultado=operaciones.get(operacion).apply(op1,op2);
			tracerImpl.trace(resultado);
			return resultado;
		}
	}
}
