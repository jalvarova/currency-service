Tenemos un retor para tí, hay que ralizar cambios para que la aplicación cumpla con los test en el projecto de java.

Funcionalidades Requeridas:

Se debe crear una API para aplicar un tipo de cambio a un monto. La API debe recibir el valor “monto“, “moneda origen”,
“moneda destino“ y devolver el “monto”, “monto con tipo de cambio”, “moneda origen”, “moneda destino“ y “tipo de
cambio”.

Consideraciones:

Cumplir con los enunciados en la capa service y usar programación reactiva en la misma. Colocar las anotaciones
correctas para el funcionamiento de las clases. Crear los endpioint correctos en el Controller. Denifir las anotaciones
para la entidad. Denifir los @Query en el repository.

Annotations:

```java

@Repository
@Service
@RestController
@Entity
@Table
class Annotation {
    
}
```

### Packages donde se tiene que modificar y revisar.

File Paths:

:file_folder: /src/main/java/org/bcp/challenge/controller

:file_folder: /src/main/java(org/bcp/challenge/repository

:file_folder: /src/main/java/org/bcp/challenge/service

:file_folder: /src/main/java/org/bcp/challenge/mapper

