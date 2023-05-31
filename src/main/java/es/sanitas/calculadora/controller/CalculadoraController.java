package es.sanitas.calculadora.controller;

import java.math.BigDecimal;
import java.util.function.BinaryOperator;
import java.math.MathContext;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;

import io.corp.calculator.TracerAPI;

import es.sanitas.calculadora.model.Resultado;
import es.sanitas.calculadora.annotation.SwaggerResponses;
import es.sanitas.calculadora.error.EntornoOperacion;
import es.sanitas.calculadora.error.FueraLimites; 
import es.sanitas.calculadora.error.ScaleInvalido;

@RestController
@RequiredArgsConstructor
public class CalculadoraController {
	/*Elemento del tipo EntornoOperacion, que define los valoresvalores mínimo, máximos y número máximo de decimale
	 * para todas las operaciones del API REST*/
	public final EntornoOperacion entornoSuma;
	public final EntornoOperacion entornoResta;
	
	/**
	 * Plantialla para una operación. Incorpora todaslas validaciones de mínimo, máximos y número máximo de decimales.
	 * Las nuevas operaciones que se añadan al API REST tienen que invocar a este método.
	 * @param op1 Primer operando.
	 * @param op2 Segundo operando.
	 * @param operacion BinaryOperator que representa qué operación se realiza.
	 * @param entorno Elemento del tipo EntornoOperacion, que define los valoresvalores mínimo, máximos y número máximo de decimale
	 * para una operación en concreto.
	 * @return El siguiente JSON {"resultado":Resultado de la operación}.
	 */
	public ResponseEntity<Resultado> operacion(BigDecimal op1,BigDecimal op2,BinaryOperator<BigDecimal> operacion,
			EntornoOperacion entorno) {
		if(op1.compareTo(entorno.getMin())<0 || op1.compareTo(entorno.getMax())>0)
			throw new FueraLimites(op1);
		else if(op2.compareTo(entorno.getMin())<0 || op2.compareTo(entorno.getMax())>0)
			throw new FueraLimites(op2);
		else if(op1.scale()>entorno.getScale()) throw new ScaleInvalido(op1);
		else if(op2.scale()>entorno.getScale()) throw new ScaleInvalido(op2);
		else return ResponseEntity.ok(new Resultado(operacion.apply(op1,op2)));
	}
		
	@ApiOperation(value="Sumar", notes="Realiza la suma de dos operando")
	@SwaggerResponses
	@GetMapping("/{op1}/+/{op2}")
	public ResponseEntity<Resultado> suma(@ApiParam(value="Primer operando", required=true, type = "BigDecimal") @PathVariable("op1") BigDecimal op1, 
			@ApiParam(value="Segundo operando", required=true, type = "BigDecimal") @PathVariable("op2") BigDecimal op2) {
		return operacion(op1,op2,(val1,val2) -> val1.add(val2),entornoSuma);
	}
	
	@ApiOperation(value="Resta", notes="Realiza la resta de dos operando")
	@SwaggerResponses
	@GetMapping("/{op1}/-/{op2}")
	public ResponseEntity<Resultado> resta(@ApiParam(value="Primer operando", required=true, type = "BigDecimal") @PathVariable("op1") BigDecimal op1, 
			@ApiParam(value="Segundo operando", required=true, type = "BigDecimal") @PathVariable("op2") BigDecimal op2) {
		return operacion(op1,op2,(val1,val2) -> val1.subtract(val2),entornoResta);
	}
}
