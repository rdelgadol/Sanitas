package es.sanitas.calculadora.error;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Clase que captura excepciones de java y devuelve elementos JSON con la información de cada error.
 */
@RestControllerAdvice
public class GlobalControllerAdvice extends ResponseEntityExceptionHandler {
	/**
	 * Método para obtener el nombre simple de una excepción.
	 * @param ex Excepción que se lanza en un momento determinado.
	 * @return El nombre de la excepción únicamente, sin tener en cuenta la ruta de paquetes. 
	 */
	private String getName(Exception ex) {
		String[] path=ex.getClass().getName().split("\\.");
		return path[path.length-1];
	}
	
	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<ApiError> handleMissingOperand(NullPointerException ex) {
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, getName(ex), ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
	}

	@ExceptionHandler(FueraLimites.class)
	public ResponseEntity<ApiError> handleMissingOperand(FueraLimites ex) {
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, getName(ex), ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
	}

	@ExceptionHandler(ScaleInvalido.class)
	public ResponseEntity<ApiError> handleMissingOperand(ScaleInvalido ex) {
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, getName(ex), ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
	}
	
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		ApiError  apiError = new ApiError(status, getName(ex), ex.getMessage());
		return ResponseEntity.status(status).headers(headers).body(apiError);
	}
}
