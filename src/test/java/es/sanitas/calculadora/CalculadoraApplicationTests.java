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
	 * @param val1 Primer operando.
	 * @param val2 Segundo operando.
	 * @param resultado Resultado esperado.Si se trata de una operación válida es el resultado de la operación, en el caso de que sea
	 * inválida, es el nombre de la excepción lanzada por java.
	 */
    @ParameterizedTest
    @CsvSource({
    		//SUMAS-------------------------------
    		//Sumas válidas
            "'+', 200, 2.12345, 1, '3.12345'",
            "'+', 200, 2, 1.12345, '3.12345'",
            "'+', 200, -1000000, 10, '-999990.0'",
            "'+', 200, 1000000, 10, '1000010.0'",
            "'+', 200, 10, -1000000, '-999990.0'",
            "'+', 200, 10, 1000000, '1000010.0'",
    		//Sumas inválidas
            "'+', 400, 2.123456, 1, 'ScaleInvalido'",
            "'+', 400, 2, 1.123456, 'ScaleInvalido'",
            "'+', 400, -1000001, 10, 'FueraLimites'",
            "'+', 400, 1000001, 10, 'FueraLimites'",
            "'+', 400, 10, -1000001, 'FueraLimites'",
            "'+', 400, 10, 1000001, 'FueraLimites'",
    		//RESTAS-------------------------------
    		//Restas válidas
            "'-', 200, 2.12345, 1, '1.12345'",
            "'-', 200, 2, 1.12345, '0.87655'",
            "'-', 200, -1000000, 10, '-1000010.0'",
            "'-', 200, 1000000, 10, '999990.0'",
            "'-', 200, 10, -1000000, '1000010.0'",
            "'-', 200, 10, 1000000, '-999990.0'",
    		//Restas inválidas
            "'-', 400, 2.123456, 1, 'ScaleInvalido'",
            "'-', 400, 2, 1.123456, 'ScaleInvalido'",
            "'-', 400, -1000001, 10, 'FueraLimites'",
            "'-', 400, 1000001, 10, 'FueraLimites'",
            "'-', 400, 10, -1000001, 'FueraLimites'",
            "'-', 400, 10, 1000001, 'FueraLimites'"
    })
    @DisplayName("Operaciones")
    public void testSuma(String operacion,int status, double val1,double val2,String resultado) throws Exception {
        BigDecimal op1 = BigDecimal.valueOf(val1);
        BigDecimal op2 = BigDecimal.valueOf(val2);
        /*Cuando se ha realizado una operación inválida el resultado esperado es el nombre de una excepción de java.
         * Si en cambio lo podemos convertir a un BigDecimal es el resultado de una operación válida*/
        Object resultadoEsperado=resultado; 
        try {
			resultadoEsperado=new BigDecimal(resultado);
		} catch (Exception e) {}

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/" + op1 + "/"+operacion+"/" + op2)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(status))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.resultado").value(resultadoEsperado));
    }
}
