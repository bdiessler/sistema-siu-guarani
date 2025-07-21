package aed;

import java.util.ArrayList;

/**
 * Clase Materia del Sistema SIU.
 *
 * @InvRep(Materia mat){ 
 *                       ∀ cargo in mat.docentes. cargo >= 0 &&
 *                       mat.docentes.length == 4 && mat.inscriptos != null &&
 *                       esListaEnlazada(mat.inscriptos) &&
 *                       sinRepetidos(mat.inscriptos) &&
 *                       ((mat.nombresEnCarreras != null) =>
 *                            mat.nombresEnCarreras.size() > 0) &&
 *                       ((mat.nombresEnCarreras != null) => ∀ (c1,m1) in
 *                            mat.nombresEnCarreras.(∄
 *                       (c1,m2) in mat.nombresEnCarreras. c1 != c2))) &&
 *                       ((mat.nombresEnCarreras != null) =>
 *                            ∀ (c,m) in mat.nombresEnCarreras. esTrie(c) && c.esta(m))) 
 *                     }
 */
public class Materia {

    // PROF; JTP, AY1, AY2
    private final int docentes[] = { 0, 0, 0, 0 };
    private final ListaEnlazada<String> inscriptos = new ListaEnlazada<>();
    private ArrayList<Tupla<Trie<Materia>, String>> nombresEncarreras;

    /**
     * Crea una nueva materia.
     *
     * @Complejidad: O(1). Crea una nueva materia con null en nombresEncarreras.
     */
    public Materia() {
        nombresEncarreras = null;
    }

    /**
     * Inicializa nombresEncarreras con info.
     *
     * @Complejidad: O(1). La asignación es constante.
     */
    public void init(ArrayList<Tupla<Trie<Materia>, String>> info) {
        nombresEncarreras = info;
    }

    /**
     * Inscribe un alumno a la materia.
     *
     * @Complejidad: O(1). Agrega el alumno al final de la lista enlazada.
     */
    public void inscribirAlumno(String lu) {
        inscriptos.agregarAtras(lu);
    }

    /**
     * Devuelve la cantidad de inscriptos en la materia.
     *
     * @Complejidad: O(1).
     * @see ListaEnlazada#longitud
     */
    public int cantidadInscriptos() {
        return inscriptos.longitud();
    }

    /**
     * Agrega un docente a la materia en el cargo correspondiente.
     *
     * @Complejidad: O(1). Aumenta el contador del cargo correspondiente en el arreglo `docentes`.
     */
    public void agregarDocente(SistemaSIU.CargoDocente cargo) {
        docentes[3 - cargo.ordinal()] += 1;
    }

    /**
     * Devuelve el plantel docente.
     *
     * @Complejidad: O(1). Devuelve el arreglo docentes.
     */
    public int[] plantelDocente() {
        return docentes;
    }

    /**
     * Calcula el cupo máximo de alumnos según la cantidad de docentes.
     *
     * @Complejidad: O(1).
     */
    public int cupo() {
        return Math.min(
                docentes[0] * 250,
                Math.min(
                        docentes[1] * 100,
                        Math.min(
                                docentes[2] * 20,
                                docentes[3] * 30)));
    }

    /**
     * Verifica si la cantidad de inscriptos excede el cupo.
     *
     * @Complejidad: O(1). Compara la cantidad de inscriptos con el cupo.
     */
    public boolean excedeCupo() {
        return this.cantidadInscriptos() > this.cupo();
    }

    /**
     * Cierra la materia de todas las carreras en las que se encuentra.
     *
     * @Complejidad: O(Σ|Nm|). Recorre todas las tuplas y borra la materia de cada
     *               trie (cada operación de borrar es O(|Nm|)).
     *
     * @see Trie#borrar
     */
    public void cerrarMateria() {
        for (Tupla<Trie<Materia>, String> tuplaCarrera : nombresEncarreras) {
            Trie<Materia> carrera = tuplaCarrera.fst();
            String nombreMateria = tuplaCarrera.snd();
            carrera.borrar(nombreMateria); // O(|Nm|)
        }
    }

    /**
     * Retorna un iterador para los alumnos inscriptos.
     *
     * @Complejidad: O(1). Retorna un iterador sobre la lista enlazada.
     */
    public Iterador<String> iteradorAlumnos() {
        return inscriptos.iterador();
    }
}
