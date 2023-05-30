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

import io.corp.calculator.tracer.TracerAPI;

import es.sanitas.calculadora.model.Resultado;
import es.sanitas.calculadora.annotation.SwaggerResponses;
import es.sanitas.calculadora.error.EntornoOperacion;
import es.sanitas.calculadora.error.FueraLimites; 
import es.sanitas.calculadora.error.ScaleInvalido;

@RestController
@RequiredArgsConstructor
public class CalculadoraController {
	public final EntornoOperacion entornoSuma;
	public final EntornoOperacion entornoResta;
	
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
	@GetMapping("/{op1}+{op2}")
	public ResponseEntity<Resultado> suma(@ApiParam(value="Primer operando", required=true, type = "BigDecimal") @PathVariable("op1") BigDecimal op1, 
			@ApiParam(value="Segundo operando", required=true, type = "BigDecimal") @PathVariable("op2") BigDecimal op2) {
		return operacion(op1,op2,(val1,val2) -> val1.add(val2),entornoSuma);
	}
}
