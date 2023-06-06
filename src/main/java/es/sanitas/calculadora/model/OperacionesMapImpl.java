package es.sanitas.calculadora.model;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BinaryOperator;

import lombok.Getter;

public class OperacionesMapImpl implements OperacionesMap {
	@Getter
	Map<String,BinaryOperator<BigDecimal>> operaciones;
	/*Al hacer todas estas operaciones en un constructor por defecto, este objeto podrá ser 
	 "espiado" por Mockito, y cuando hagamos tests de OperacionService sabremos cuales son 
	 las operaciones válidas*/
	public OperacionesMapImpl() {
		//Creo el Map con una capacidad de 4 por si se dan de alta las operaciones de multiplicación y división.
		operaciones=new HashMap<>(4);
		operaciones.put("suma", (op1, op2) -> op1.add(op2));
		operaciones.put("resta", (op1, op2) -> op1.subtract(op2));
	}
	public BinaryOperator<BigDecimal> get(String operacion) {
		return (BinaryOperator<BigDecimal>)operaciones.get(operacion);
	}

	@Override
	public String toString() {
		String resultado = "Las operaciones válidas son: ";
		int i = 0;
		int operacionesSize = getOperaciones().size();
		for (String key : getOperaciones().keySet()) {
			if (i == operacionesSize - 1)
				resultado += " y ";
			else if (i != 0)
				resultado += " , ";
			resultado += key;
			i++;
		}
		resultado+=".";
		return resultado;
	}
}
