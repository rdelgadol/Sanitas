package es.sanitas.calculadora.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import es.sanitas.calculadora.error.ApiError;
import es.sanitas.calculadora.model.ResultadoJson;
import es.sanitas.calculadora.exception.FaltaParametroException;
import es.sanitas.calculadora.exception.FueraLimitesException;
import es.sanitas.calculadora.exception.OperacionErroneaException;
import es.sanitas.calculadora.exception.ScaleInvalidoException;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@ApiOperation(value="Operar", notes="Realiza una operación matemática sobre dos operandos.")
@ApiResponses(value= {
		@ApiResponse(code=200, message="OK", response=ResultadoJson.class),
		@ApiResponse(code=400, message="Falta introducir algún parámetro.", 
			response=FaltaParametroException.class),
		@ApiResponse(code=400, message="Se ha rebasado el valor mínimo o máximo para algún operando.", 
			response=FueraLimitesException.class),
		@ApiResponse(code=400, message="Se ha rebasado el máximo número de decimales para algún operando.", 
			response=ScaleInvalidoException.class),
		@ApiResponse(code=400, message="La operación introducida es incorrecta.", 
			response=OperacionErroneaException.class),
		@ApiResponse(code=400, message="Alguno de los operandos no tiene un formato válido.", 
			response=MethodArgumentTypeMismatchException.class),
		@ApiResponse(code=500, message="Internal Server Error", response=ApiError.class)
})
/**
 * Anotaciones para realizar la documentación Swagger del endpoint CalculadoraController.operar
 */
public @interface SwaggerOperar {
}
