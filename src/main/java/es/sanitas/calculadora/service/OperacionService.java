package es.sanitas.calculadora.service;

import java.math.BigDecimal;
import java.util.function.BinaryOperator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.corp.calculator.TracerImpl;

import es.sanitas.calculadora.error.EntornoOperacion;
import es.sanitas.calculadora.exception.CalculadoraException;
import es.sanitas.calculadora.exception.FaltaParametroException;
import es.sanitas.calculadora.exception.FueraLimitesException;
import es.sanitas.calculadora.exception.OperacionErroneaException;
import es.sanitas.calculadora.exception.ScaleInvalidoException;
import es.sanitas.calculadora.model.OperacionesMap;

@Service
public class OperacionService {
	/*Elemento que define los valores mínimo, máximo y número máximo de decimales
	 para todas las operaciones del API REST.*/
	@Autowired	
	private EntornoOperacion entorno;
	
	/*Objeto que tiene un Map<String, BinaryOperator<BigDecimal>> embebido. Es en este Map donde las 
	 claves son objetos de tipo String que identifican operaciones matemáticas, y los valores son 
	 objetos de tipo BinaryOperator que describen cómo son realmente esas operaciones.
	 Sólo se pueden realizar operaciones matemáticas que estén registradas en este Map.
	 La clase OperacionServiceTest, que hace test unitarios de esta misma clase, recibirá una referencia
	 de OperacionesMapImpl y también sabrá que operaciones son válidas.*/
	@Autowired	
	private OperacionesMap operaciones;
	
	@Autowired
	public TracerImpl tracerImpl;
	
	private boolean validar(String operacion, BigDecimal op1, BigDecimal op2)
	{
		/*Asigno el tracer a un campo estático, ante la imposibilidad de hacer @Autowired de TracerImpl en la 
		 propia excepción para usarlo en su constructor.*/
		CalculadoraException.setTracer(tracerImpl);
		if(operacion==null || operacion.equals("")) throw new FaltaParametroException("", "la operación a realizar.");
		else if(op1==null) throw new FaltaParametroException(operacion+": ","el primer operando.");
		else if(op2==null) throw new FaltaParametroException(operacion+": ","el segundo operando.");
		else if(op1.compareTo(entorno.getMin())<0 || op1.compareTo(entorno.getMax())>0)
			throw new FueraLimitesException(operacion+": ",op1,entorno.toString());
		else if(op2.compareTo(entorno.getMin())<0 || op2.compareTo(entorno.getMax())>0) 
			throw new FueraLimitesException(operacion+": ",op2,entorno.toString());
		else if(op1.scale()>entorno.getScale()) 
			throw new ScaleInvalidoException(operacion+": ",op1,entorno.toString());
		else if(op2.scale()>entorno.getScale()) 
			throw new ScaleInvalidoException(operacion+": ",op2,entorno.toString());
		else {
			BinaryOperator<BigDecimal> binOoperator=operaciones.get(operacion);
			if(binOoperator==null) throw new OperacionErroneaException(operacion,operaciones.toString());
			else return true;
		}	
	}
	
	/**
	 * Método para realizar una operación matemática entre dos números de tipo BigDecimal. 
	 * Incorpora todas las validaciones de mínimo, máximos y número máximo de decimales.
	 * @param op1 Primer operando.
	 * @param op2 Segundo operando.
	 * @param operacion String que representa qué operación se realiza. Sirve como clave para obtener un BinaryOperator.
	 * @param entorno Elemento del tipo EntornoOperacion, que define los valores mínimo, máximo y número máximo 
	 * de decimales para una operación en concreto.
	 * @return El siguiente JSON {"resultado":Resultado de la operación}.
	 */
	public BigDecimal operar(String operacion, BigDecimal op1, BigDecimal op2) {
		if(validar(operacion, op1, op2)) {
			BigDecimal resultado=operaciones.get(operacion).apply(op1,op2);
			tracerImpl.trace(operacion +" de "+op1+" y "+op2+" = "+resultado);
			return resultado;
		}
		else return null;
	}
}
