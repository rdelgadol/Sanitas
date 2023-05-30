package es.sanitas.calculadora.error;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import es.sanitas.calculadora.error.ApiError;
import es.sanitas.calculadora.error.FueraLimites;
import es.sanitas.calculadora.error.ScaleInvalido;

@RestControllerAdvice
public class GlobalControllerAdvice extends ResponseEntityExceptionHandler {

	@ExceptionHandler(FueraLimites.class)
	public ResponseEntity<ApiError> handleMissingOperand(FueraLimites ex) {
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
	}

	@ExceptionHandler(ScaleInvalido.class)
	public ResponseEntity<ApiError> handleMissingOperand(ScaleInvalido ex) {
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
	}
	
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		String message="";
		if(ex instanceof MissingPathVariableException) message="Falta introducir alguno de los operandos\n";
		ApiError  apiError = new ApiError(status, message+ex.getMessage());
		return ResponseEntity.status(status).headers(headers).body(apiError);
	}
}
