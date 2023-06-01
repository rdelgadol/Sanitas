# Sanitas
REST API que implementa una calculadora. 

Sólo se incluyen las operaciones de suma y de resta, aunque es facilmente ampliable a otras.

## Estructura del Proyecto
El paquete base del proyecto es **es.sanitas.calculadora**.
Los elementos que lo componen son, respecto la ruta anterior:   
* Un controlador, situado en **controller/CalculadoraController**.
* Configuración para inyectar Beans: **config/CalculadoraConfig**.
* Clases para gestionar excepciones: **error/GlobalControllerAdvice, error/FueraLimites** y **error/ScaleInvalido**
* Un interfaz y una clase para contener valores límite a validar en cada operando: **error/EntornoOperacion** y **error/EntornoOperacionImpl**.
* Respuestas JSON, sin error **model/Resultado** y si hay errores **error/ApiError**.
* Soporte para Swagger: **config/SwaggerConfig** y **annotation/SwaggerResponses**.
* Tests: **CalculadoraApplicationTests**.

También cuenta con estos elementos:
* Al utilizar maven, un fichero **pom.xml**.
* Fichero **calculadora/src/main/resources/application.properties** , donde se ha cambiado el puerto por defecto al 9001.

## Dependencias
La aplicación está hecha con JDK11, Spring Boot 2.1.8.RELEASE y utiliza Maven como gestor de dependencias. Estas son:
* **Lombock**, para la generación de constructores y getters.
* **Swagger**. para generar documentación.
* **io.corp.calculator**. Librería suministrada (no está en maven central), para tracear operaciones. Aquí hay un pequeño error y es que la clase TracerImpl no implementa la interface TracerAPI, con lo cual he tenido que referenciar siempre la primera.
* **JUnit5**, para pruebas unitarias.
* **junit-jupiter-params**, para pruebas parametrizadas.
* **maven-dependency-plugin**, para copiar el jar de io.corp.calculator al proyecto cuando se ejecuta `mvn package`. 

## Compilación y ejecución

Primero se instalará la dependencia io.corp.calculator en el repositorio local con el comando:

`mvn install:install-file -Dfile=tracer-1.0.0.jar -DgroupId=io.corp.calculator -DartifactId=tracer -Dversion=1.0.0 -Dpackaging=jar`

Después, el jar de la dependencia anterior se incluira en el proyecto, sólo con ejecutar `mvn package`.
 
Se ejecutará la aplicación desde el jar con el comando:
 
`java -jar calculadora-0.0.1-SNAPSHOT.jar`

## Uso y ejemplos
Para realizar sumas, se accederá al siguiente endpoint: http://localhost/{op1}/+/{op2}, donde op1 y op2 son los operandos.
Algunos ejemplos de sumas son:
* http://localhost:9001/20.38/+/10.25 
* http://localhost:9001/20.38/+/-10.25
* http://localhost:9001/-20.38/+/10.25
* http://localhost:9001/-20.38/+/-10.25

Para realizar restas, se accederá al siguiente endpoint: https://localhost/{op1}/-/{op2}, donde op1 y op2 son los operandos.
Algunos ejemplos de restas son:
* http://localhost:9001/20.38/-/10.25 
* http://localhost:9001/20.38/-/-10.25
* http://localhost:9001/-20.38/-/10.25
* http://localhost:9001/-20.38/-/-10.25 

Para visualizar la documentación generada por swagger, se accederá al enlace http://localhost:9001/swagger-ui.html#/
