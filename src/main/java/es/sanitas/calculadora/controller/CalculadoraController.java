package es.sanitas.calculadora.controller;

import java.math.BigDecimal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import io.swagger.annotations.ApiParam;

import es.sanitas.calculadora.model.ResultadoJson;
import es.sanitas.calculadora.annotation.SwaggerDocumentacion;
import es.sanitas.calculadora.service.OperacionService;

@RestController
public class CalculadoraController {
	@Autowired	
	OperacionService operacionService;
	
	@SwaggerDocumentacion
	@GetMapping("/")
	public ResponseEntity<ResultadoJson> operar(@ApiParam(value="Operación", required=true, type = "String") @RequestParam("operacion") String operacion,
			@ApiParam(value="Primer operando", required=true, type = "BigDecimal") @RequestParam("op1") BigDecimal op1, 
			@ApiParam(value="Segundo operando", required=true, type = "BigDecimal") @RequestParam("op2") BigDecimal op2) {
		return ResponseEntity.ok(new ResultadoJson(operacionService.operar(operacion, op1, op2)));
	}
}
