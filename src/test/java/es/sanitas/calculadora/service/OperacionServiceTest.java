package es.sanitas.calculadora.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;
import static org.junit.jupiter.api.Assertions.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.lenient;

import io.corp.calculator.TracerImpl;

import es.sanitas.calculadora.error.EntornoOperacion;
import es.sanitas.calculadora.model.OperacionesMapImpl;
import es.sanitas.calculadora.exception.CalculadoraException;
import es.sanitas.calculadora.exception.FaltaParametroException;
import es.sanitas.calculadora.exception.FueraLimitesException;
import es.sanitas.calculadora.exception.OperacionErroneaException;
import es.sanitas.calculadora.exception.ScaleInvalidoException;

@ExtendWith(MockitoExtension.class)
public class OperacionServiceTest {
	@InjectMocks
	OperacionService operacionService;
	
	@Mock
	EntornoOperacion entorno;
	
	@Spy
	OperacionesMapImpl operacionesMapImpl;
	
	@Spy
	TracerImpl tracerImpl;

	@BeforeEach
    void beforeEach() {
		double min=-1_000_000;
    	lenient().when(entorno.getMin()).thenReturn(BigDecimal.valueOf(min));
    	double max=1_000_000;
    	lenient().when(entorno.getMax()).thenReturn(BigDecimal.valueOf(max));
		int scale=5;
    	lenient().when(entorno.getScale()).thenReturn(scale);
    	lenient().when(entorno.toString()).thenReturn("Los valores límite permitidos son: mímimo= "+
    			min+", máximo= "+max+" y máximo número de decimales= "+scale+".");
    }
    
    @ParameterizedTest
    @CsvSource({
    		//Sumas válidas----------------------
            "'suma', 2.12345, 1, 3.12345",
            "'suma', 2, 1.12345, 3.12345",
            "'suma', -1000000, 10, -999990",
            "'suma', 1000000, 10, 1000010",
            "'suma', 10, -1000000, -999990",
            "'suma', 10, 1000000, 1000010",
    		//Restas válidas----------------------
            "'resta', 2.12345, 1, 1.12345",
            "'resta', 2, 1.12345, 0.87655",
            "'resta', -1000000, 10, -1000010",
            "'resta', 1000000, 10, 999990",
            "'resta', 10, -1000000, 1000010",
            "'resta', 10, 1000000, -999990",
    })    
    @DisplayName("Operaciones correctas.")
    void testValido(String operacion, BigDecimal op1, BigDecimal op2, BigDecimal resultado) throws Exception {
    	assertEquals(operacionService.operar(operacion, op1, op2), resultado);
    }
    
    void testsErroneo(Class<? extends CalculadoraException> expectedType, String operacion, 
    		BigDecimal op1, BigDecimal op2) {
    	assertThrows(expectedType 
    			,() -> {
    				operacionService.operar(operacion, op1, op2);
    			});
    }
    void testsErroneo(Class<? extends CalculadoraException> expectedType, String operacion,
    		double op1, double op2) {
    	testsErroneo(expectedType, operacion, BigDecimal.valueOf(op1), BigDecimal.valueOf(op2));
    }
    
    @TestFactory
    @DisplayName("Operaciones erróneas globales.") 
    /**
     * Tests que devuelven una excepción en lugar de un resultado matemático y que se ejecutan sólo una vez, 
     * independientemente de las operaciones matemáticas registradas en la aplicación.
     * @return Colección de objetos DynamicTest con las pruebas pertinentes.
     */
    Collection<DynamicTest> testErroneosGlobales() {
        return Arrays.asList(
	    	dynamicTest("Sin operación definida.", 
	    			() -> testsErroneo(FaltaParametroException.class,null,3,3)),
	    	dynamicTest("Con una operación que no existe.", 
	    			() -> testsErroneo(OperacionErroneaException.class,"operacionNoExiste",3,3))
    	);
    }

    /**
     * Tests que devuelven una excepción en lugar de un resultado matemático, y que se ejecutan para cada operación 
     * registrada en la aplicación.
     * El resultado para cada uno de ellos siempre es el mismo, independientemente de la operación que se invoque, ya
     * que esta no se llega a realizar por haber llamado a OperacionService.operar con algún parámetro incorrecto.
     * @param operacion Es la operación matemática que se pretende realizar.
     * @return Una colección de objetos DynamicTest, para ver el resultado de cada uno de esos tests de
     * forma independiente.
     */
    Collection<DynamicTest> testsErroneos(String operacion) {
        return Arrays.asList(
        	dynamicTest(operacion+": primer operando nulo.", 
        			() -> testsErroneo(FaltaParametroException.class, operacion, null, BigDecimal.valueOf(1))),
        	dynamicTest(operacion+": segundo operando nulo.", 
        			() -> testsErroneo(FaltaParametroException.class, operacion, BigDecimal.valueOf(1), null)),
        	dynamicTest(operacion+": primer operando supera número de decimales permitido.", 
        			() -> testsErroneo(ScaleInvalidoException.class, operacion, 2.123456, 1)),
        	dynamicTest(operacion+": segundo operando supera número de decimales permitido.", 
        			() -> testsErroneo(ScaleInvalidoException.class, operacion, 2, 1.123456)),
        	dynamicTest(operacion+": primer operando es inferior al valor mínimo.", 
        			() -> testsErroneo(FueraLimitesException.class, operacion, -1000001, 10)),
        	dynamicTest(operacion+": segundo operando es inferior al valor mínimo.", 
        			() -> testsErroneo(FueraLimitesException.class, operacion, 10, -1000001)),
        	dynamicTest(operacion+": primer operando es superior al valor máximo.", 
        			() -> testsErroneo(FueraLimitesException.class, operacion, 1000001, 10)),
        	dynamicTest(operacion+": segundo operando es superior al valor máximo.", 
        			() -> testsErroneo(FueraLimitesException.class, operacion, 10, 1000001))
    	);
    }
    
    @TestFactory 
    @DisplayName("Operaciones erróneas") 
    /**
     * Método que ejecuta testsErroneos(String operacion) para cada una de las operaciones permitidas 
     * en la aplicación.
     * @return Una colección con todos los tests dinámicos para todas las operaciones.
     */
    Collection<DynamicTest> testsErroneos() {
    	Collection<DynamicTest> tests=new ArrayList<>();
		for (String key : operacionesMapImpl.getOperaciones().keySet()) {
			tests.addAll(testsErroneos(key));
		}
    	return tests;
    }
}
