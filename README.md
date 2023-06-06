# Sanitas
REST API que implementa una calculadora. 

Sólo se incluyen las operaciones de suma y de resta, aunque es fácilmente ampliable a otras.

## Estructura del Proyecto
El paquete base del proyecto es **es.sanitas.calculadora**.
Los elementos que lo componen son, respecto la ruta anterior:   
* Un controlador, situado en **controller/CalculadoraController**.
* Un servicio **service/OperacionService**, que es el que hace las operaciones matemáticas.
* Configuración para inyectar Beans: **config/CalculadoraConfig**.
* Una excepción base **exception/CalculadoraException**, de la que heredan el resto de excepciones de la aplicación: **exception/FaltaParametroException, exception/FueraLimitesException, exception/OperacionErroneaException** y **error/ScaleInvalidoException**.
* Una clase para gestionar excepciones y generar una respuesta JSON: **error/GlobalControllerAdvice**.
* Una interfaz y una clase para contener valores límite a validar en cada operando: **error/EntornoOperacion** y **error/EntornoOperacionImpl**.
* Una interfaz y una clase para contener las operaciones matemáticas permitidas **model/OperacionesMap** y **model/OperacionesMapImpl**.
* Respuestas JSON, de las operaciones disponibles **model/OperacionesJson**, operaciones matemáticas sin error **model/ResultadoJson** y si hay errores **error/ApiError**.
* Soporte para Swagger: **config/SwaggerConfig, annotation/SwaggerOperar** y **annotation/SwaggerOperaciones**.
* Tests unitarios: **service/OperacionServiceTest**.

También cuenta con estos elementos:
* Al utilizar maven, un fichero **pom.xml**.
* Fichero **calculadora/src/main/resources/application.properties**, donde se ha cambiado el puerto por defecto al 9001.

## Dependencias
La aplicación está hecha con JDK11, Spring Boot 2.1.8.RELEASE y utiliza Maven como gestor de dependencias. Estas son:
* **Lombock**, para la generación de constructores y getters.
* **Swagger**. para generar documentación.
* **io.corp.calculator**. Librería suministrada (no está en maven central), para tracear operaciones. Aquí hay un pequeño error y es que la clase TracerImpl no implementa la interface TracerAPI, con lo cual he tenido que referenciar siempre la primera.
* **JUnit5**, para pruebas unitarias.
* **junit-jupiter-params**, para pruebas parametrizadas.
* **Mockito**, para pruebas unitarias.
* **maven-dependency-plugin**, para copiar el jar de io.corp.calculator al proyecto cuando se ejecuta `mvn package`. 

## Compilación y ejecución

Primero se instalará la dependencia io.corp.calculator en el repositorio local con el comando:

`mvn install:install-file -Dfile=tracer-1.0.0.jar -DgroupId=io.corp.calculator -DartifactId=tracer -Dversion=1.0.0 -Dpackaging=jar`

Después, el jar de la dependencia anterior se incluirá en el proyecto, sólo con ejecutar `mvn package`.
 
Se ejecutará la aplicación desde el jar con el comando:
 
`java -jar calculadora-0.0.1-SNAPSHOT.jar`

## Uso y ejemplos
Para realizar operaciones matemáticas, se accederá al siguiente endpoint: http://localhost:9001/?operacion={operacion}&op1={op1}&op2={op2}, donde:
* operacion es la operación matemática a realizar.
* op1 y op2 son los operandos.

Algunos ejemplos de sumas son:
* http://localhost:9001/?operacion=suma&op1=20.38&op2=10.25
* http://localhost:9001/?operacion=suma&op1=20.38&op2=-10.25
* http://localhost:9001/?operacion=suma&op1=-20.38&op2=10.25
* http://localhost:9001/?operacion=suma&op1=-20.38&op2=-10.25

Algunos ejemplos de restas son:
* http://localhost:9001/?operacion=resta&op1=20.38&op2=10.25
* http://localhost:9001/?operacion=resta&op1=20.38&op2=-10.25
* http://localhost:9001/?operacion=resta&op1=-20.38&op2=10.25
* http://localhost:9001/?operacion=resta&op1=-20.38&op2=-10.25

Las operaciones matemáticas disponibles se pueden consultar en el siguiente endpoint: http://localhost:9001/operaciones

Para visualizar la documentación generada por swagger, se accederá al enlace http://localhost:9001/swagger-ui.html#/
