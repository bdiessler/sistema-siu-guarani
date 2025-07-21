# Sistema SIU Guaraní

Este proyecto implementa un sistema simplificado de inscripciones universitarias, inspirado en el sistema SIU Guaraní, como parte del Trabajo Práctico 2 de la materia "Algoritmos y Estructuras de Datos".

## Descripción

El sistema permite definir carreras de grado, materias y planteles docentes, y gestionar la inscripción de estudiantes a materias a través de sus libretas universitarias. Además, administra cupos de inscripción en base a la cantidad de docentes disponibles por materia y carrera, y contempla la posibilidad de que una misma materia tenga diferentes nombres según la carrera.

### Funcionalidades principales
- Alta de carreras, materias y estudiantes.
- Inscripción de estudiantes a materias, respetando cupos definidos por la cantidad de docentes (PROFE, JTP, AY1, AY2).
- Gestión de planteles docentes por materia y carrera.
- Consulta de inscriptos, cupos, carreras, materias y materias inscriptas por estudiante.
- Cierre de materias por falta de docentes.

### Estructuras utilizadas
La solución utiliza únicamente estructuras de datos vistas en la materia (listas enlazadas, secuencias, tries, etc.), implementadas por el propio estudiante, salvo las excepciones permitidas (ArrayList, String, StringBuffer).

### Complejidad
Cada operación implementada cumple con las restricciones de complejidad temporal detalladas en el enunciado, garantizando eficiencia en la gestión de inscripciones y consultas.

## Archivos principales
- `SistemaSIU.java`: clase principal que implementa la lógica del sistema.
- Otras clases de apoyo: `Diccionario.java`, `InfoMateria.java`, `Iterador.java`, `ListaEnlazada.java`, `Materia.java`, `ParCarreraMateria.java`, `Secuencia.java`, `Trie.java`, `Tupla.java`, `Wrapper.java`.

## Equipo de Desarrollo

- **Arazi Matias Federico**
- **Diessler Bernardo**
- **Grasso Ramos Lucas Martin**
- **Tag Lucio**

## Ejemplo de uso
El sistema permite, por ejemplo, inscribir estudiantes a materias, agregar docentes, consultar cupos y cerrar materias, siguiendo las reglas y restricciones del enunciado.

---

Este proyecto es parte de la cursada de "Algoritmos y Estructuras de Datos" (UBA, 2024).
