# SOLOBAJOS

<img src="https://img.shields.io/badge/Spring--Framework-5.7-green"/> <img src="https://img.shields.io/badge/Apache--Maven-3.8.6-blue"/> <img src="https://img.shields.io/badge/Java-17.0-brightgreen"/>

<img src="https://niixer.com/wp-content/uploads/2020/11/spring-boot.png" width="500" alt="Spring Logo"/>
 
___
## **Introducción**

## API REST con **SPRING**, subida de ficheros, validación, gestión de errores, búsqueda y autenticación (spring security) - Se trata de la parte Backend sobre una APP multiplataforma sobre modelos de bajos divididos en categorías. Cada usuario podrá guardar su lista de modelos de bajos favoritos.
Se ha trabajado sobre la siguiente **documentación:**

:point_right: Véase en la carpeta UML del proyecto. O entre en **/swagger-ui-solobajosdocs.html**


También se ha prácticado el manejo de **PostMan**, **Swagger**, **Docker**, **Postgresql** y la metodología ágil **SCRUM** para el reparto de tareas a través la ramificación de estas con **GitHub**.

Se pueden realizar las siguientes funcionalidades: 
* Listado tanto de bajos favoritos como de categorías
* Paginado de tanto de bajos como de usuarios
* Login usuario
* Creación de un usuario normal, o creación de un usuario admin
* Creación tanto de un nuevo bajo como de una nueva categoría
* Búsqueda tanto de un bajo, como una categoría o como un usuario concreto
* Perfil del usuario
* Edición de imagen tanto del bajo, categoría o usuario
* Edición tanto del bajo, categoría o usuario
* Baneado del acceso de un usuario
* Borrado tanto de una categoría, bajo o de un usuario

---

## **Tecnologías utilizadas** 

Para realizar este proyecto hemos utilizado:

1. [Spring Boot 2.7.5](https://spring.io/) - Dependencias: **Spring-Web**, **JPA**, **Spring-boot-validation**, **json-web-token**, **Passay**, **Tika**, **H2 Database**, **Sprin-doc Open-api**, **Lombok**,  **Spring-Security**, **Postgresql**
2. [Apache Maven 3.8.6](https://maven.apache.org/)
3. [Postman](https://www.postman.com/)
4. [GitHub](https://github.com/)
5. [springdoc 1.6.13](https://springdoc.org/)
6. [Swagger](https://swagger.io/)
5. [Docker](https://www.docker.com/)
6. [Postgresql](https://www.postgresql.org/)



### Ejemplos del Código Usado: 

**JAVA**:
```Java
    @PostMapping("/bass/fav/{id}")
    public ResponseEntity<BassResponse> favBass(@PathVariable UUID id,
                                                @AuthenticationPrincipal User user) {
        Bass bass = bassService.findById(id);
        Bass fav = bassService.makeFav(user, bass);

        URI createdURI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(bass.getId()).toUri();

        return ResponseEntity.created(createdURI).body(BassResponse.fromBass(bass));
    }

```

**Documentación**

```Java
    @Operation(summary = "Modifica un bajo por su id")
    @Parameter(description = "El id del bajo que se quiere modificar", name = "id", required = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha modificado correctamente un bajo ",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = BassResponse.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                "brand": "Musicman",
                                                "model": "Stingray",
                                                "frets": 21,
                                                "image": "stringray1 (2)_589971.png",
                                                "origin": "EEUU",
                                                "builtYear": "2022",
                                                "price": 1900.0,
                                                "discount": 0.0,
                                                "isAvailable": true,
                                                "state": "NEW"
                                            }
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha podido modificar el bajo",
                    content = @Content),
    })
```


---
## **Arranque**



Realiza un *git clone* de la siguiente dirección: 
*https://github.com/RogeMB/soloBajos-Back*

```console
git clone https://github.com/RogeMB/Trianafy.git
```

Dirígete hasta la carpeta:

> cd ./Trianafy/


**Primero** tienes que tener instalado Apache Maven y sería recomendable tener alguna IDE, como **Intellij IDEA** o **VisualStudio Code**

Ejecuta el siguiente comando:
    
    mvn complile
    
    
Ejecuta el siguiente comando:
    
    mvn clean


Ejecuta el comando:

    mvn spring-boot:run
    
    
Si diese algún error, realiza el siguiente comando:  

    mvn dependencies:resolve
    ---> 100% 

___
## **Autor**

Este proyecto ha sido realizado por: 

* [Rogelio Mohigefer Barrera - GITHUB](https://github.com/RogeMB)

Etudiante de 2º Desarrollo de Aplicaciones Multiplataforma, grado 
superior de formación profesional en la ciudad de Sevilla, España.

### **Thump up :+1: And if it was useful for you, star it! :star: :smiley:**

___
## **TODO**

Tareas realizadas y cosas por hacer.
[ ] Fix possible future errors
___

