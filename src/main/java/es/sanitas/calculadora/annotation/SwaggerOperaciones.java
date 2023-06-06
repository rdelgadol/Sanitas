package es.sanitas.calculadora.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import es.sanitas.calculadora.error.ApiError;
import es.sanitas.calculadora.model.OperacionesJson;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@ApiOperation(value="Operaciones", notes="Muestra las operaciones permitidas por la aplicación.")
@ApiResponses(value= {
		@ApiResponse(code=200, message="OK", response=OperacionesJson.class),
		@ApiResponse(code=500, message="Internal Server Error", response=ApiError.class)
})
/**
 * Anotaciones para realizar la documentación Swagger del endpoint CalculadoraController.operaciones
 */
public @interface SwaggerOperaciones {
}
