package es.sanitas.calculadora.model;

import java.util.Set;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OperacionesJson {
	@Getter
	private final Set<String> operacionesValidas;
}
