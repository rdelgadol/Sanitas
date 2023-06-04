package es.sanitas.calculadora;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = CalculadoraApplication.class)
class CalculadoraApplicationTests {
	
	@Autowired
    private  MockMvc mockMvc;

	/**
	 * Test de cualquier tipo para las operacione del API REST, introduciendo los valores apropiados en cada caso.
	 * @param operacion String que representa la operación a realizar, según están difinidos los @GetMapping en CalculadoraController.
	 * @param status Respuesta Http
	 * @param op1 Primer operando.
	 * @param op2 Segundo operando.
	 * @param resultado Resultado esperado. Si se trata de una operación válida, es el resultado de la operación, en el caso de que sea
	 * inválida, es el nombre de la excepción lanzada por java.
	 */
    @ParameterizedTest
    @CsvSource({
    		//SUMAS-------------------------------
    		//Sumas válidas
            "'suma', 200, 2.12345, 1, 3.12345",
            "'suma', 200, 2, 1.12345, 3.12345",
            "'suma', 200, -1000000, 10, -999990",
            "'suma', 200, 1000000, 10, 1000010",
            "'suma', 200, 10, -1000000, -999990",
            "'suma', 200, 10, 1000000, 1000010",
    		//Sumas inválidas
            "'suma', 400, 2.123456, 1, 'ScaleInvalido'",
            "'suma', 400, 2, 1.123456, 'ScaleInvalido'",
            "'suma', 400, -1000001, 10, 'FueraLimites'",
            "'suma', 400, 1000001, 10, 'FueraLimites'",
            "'suma', 400, 10, -1000001, 'FueraLimites'",
            "'suma', 400, 10, 1000001, 'FueraLimites'",
            "'suma', 400, 10, 'dd', 'MethodArgumentTypeMismatchException'",
            "'suma', 400, 'dd', 10, 'MethodArgumentTypeMismatchException'",
    		//RESTAS-------------------------------
    		//Restas válidas
            "'resta', 200, 2.12345, 1, 1.12345",
            "'resta', 200, 2, 1.12345, 0.87655",
            "'resta', 200, -1000000, 10, -1000010",
            "'resta', 200, 1000000, 10, 999990",
            "'resta', 200, 10, -1000000, 1000010",
            "'resta', 200, 10, 1000000, -999990",
    		//Restas inválidas
            "'resta', 400, 2.123456, 1, 'ScaleInvalido'",
            "'resta', 400, 2, 1.123456, 'ScaleInvalido'",
            "'resta', 400, -1000001, 10, 'FueraLimites'",
            "'resta', 400, 1000001, 10, 'FueraLimites'",
            "'resta', 400, 10, -1000001, 'FueraLimites'",
            "'resta', 400, 10, 1000001, 'FueraLimites'",
            "'resta', 400, 10, 'dd', 'MethodArgumentTypeMismatchException'",
            "'resta', 400, 'dd', 10, 'MethodArgumentTypeMismatchException'"
    })
    @DisplayName("Operaciones")
    public void testSuma(String operacion,int status, String op1,String op2,String resultado) throws Exception {
        /*Cuando se ha realizado una operación inválida, el resultado esperado es el nombre de una excepción de java.
         Si en cambio lo podemos convertir a un BigDecimal, es el resultado de una operación válida.*/
        Object resultadoEsperado=resultado; 
        try {
			resultadoEsperado=new BigDecimal(resultado);
		/*Si la operación de convertir a BigDecimal es errónea, la causa es que resultado es un String y no un número. 
		 Dicho String representa el nombre de una excepción de Java, como por ejemplo MethodArgumentTypeMismatchException.
		 En estos casos no realizamos ninguna operación adicional, así resultadoEsperado sera igual a resultado y por tanto
		 a un String que representa el nombre de una de una excepción de Java.
		 Este es el caso en el que se está probando una operación invalida y esperamos como resultado una excepcion en lugar de
		 un valor numérico*/
		} catch (Exception e) {}

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/?operacion="+operacion+"&op1="+op1+"&op2="+op2)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(status))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.resultado").value(resultadoEsperado));
    }
}
