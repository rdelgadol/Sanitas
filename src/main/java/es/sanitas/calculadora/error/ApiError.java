package es.sanitas.calculadora.error;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Clase que devuelve una excepción de java con un formato JSON.
 */
@Getter
@RequiredArgsConstructor
public class ApiError {

	@NonNull
	private HttpStatus estado;
	@NonNull
	private String resultado;
	@JsonFormat(shape = Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss")
	private LocalDateTime fecha = LocalDateTime.now();
	@NonNull
	private String mensaje;
}
