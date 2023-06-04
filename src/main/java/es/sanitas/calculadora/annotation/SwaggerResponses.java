package es.sanitas.calculadora.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import es.sanitas.calculadora.error.ApiError;
import es.sanitas.calculadora.model.ResultadoJson;
import es.sanitas.calculadora.error.FueraLimites; 
import es.sanitas.calculadora.error.ScaleInvalido;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@ApiResponses(value= {
		@ApiResponse(code=200, message="OK", response=ResultadoJson.class),
		@ApiResponse(code=400, message="Se ha rebasado el valor mínimo o máximo para algún operando", 
			response=FueraLimites.class),
		@ApiResponse(code=400, message="El número de decimales es invalido para algún operando", 
			response=ScaleInvalido.class),
		@ApiResponse(code=400, message="Alguno de los operandos no tiene un formato válido", 
			response=MethodArgumentTypeMismatchException.class),
		@ApiResponse(code=500, message="Internal Server Error", response=ApiError.class)
})
public @interface SwaggerResponses {
}
