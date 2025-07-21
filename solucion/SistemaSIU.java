package aed;

import java.util.ArrayList;

/**
 * Implementación del Sistema SIU.
 *
 * @Nota: Cuando decimos esTrie(t), por ejemplo, estamos diciendo que t cumple
 *        con el invariante de Trie.
 *
 * @InvRep(SistemaSIU siu){
 *                         siu.materiasEstudiante != null &&
 *                         ∀ materiaEnEstudiante in siu.materiasEstudiante. materiaEnEstudiante >= 0 &&
 *                         siu.carreras != null &&
 *                         ∀ carrera in siu.carreras. (esTrie(siu.carreras) &&
 *                          (∀ trieMateria in siu.carreras.(esTrie(trieMateria) && ∀
 *                                        materia in trieMateria. esMateria(materia)) )
 *                         }
 */
public class SistemaSIU {

    public enum CargoDocente {
        AY2,
        AY1,
        JTP,
        PROF
    }

    private final Trie<Integer> materiasEstudiante = new Trie<>();
    private final Trie<Trie<Materia>> carreras = new Trie<>();

    /**
     * Constructor del SistemaSIU.
     *
     * @Complejidad: O(|libretasUniversitarias| + |infoMaterias|*|c|*|m|). Recorre todas las
     *               libretasUniversitarias y las
     *               define y luego por cada infoMat en materiasEnCarreras recorre
     *               todas las carreras y la define en el trie de su carrera.
     */
    public SistemaSIU(InfoMateria[] infoMaterias, String[] libretasUniversitarias) {
        for (String lu : libretasUniversitarias) {
            materiasEstudiante.definir(lu, 0); // O(|lu|)
        }
        for(InfoMateria infoMat : infoMaterias){
            ParCarreraMateria[] paresCarreraMateria = infoMat.getParesCarreraMateria();
            Materia mat = new Materia();
            ArrayList<Tupla<Trie<Materia>, String>> info = new ArrayList<>();
            for (ParCarreraMateria parCarreraMateria : paresCarreraMateria) {
                String nombreCarrera = parCarreraMateria.getCarrera();
                String nombreMateria = parCarreraMateria.getNombreMateria();
                if (!carreras.esta(nombreCarrera)) {
                    carreras.definir(nombreCarrera, new Trie<>());
                }
                Trie<Materia> carreraRef = carreras.obtener(nombreCarrera);
                carreraRef.definir(nombreMateria, mat);
                info.add(new Tupla<>(carreraRef, nombreMateria));
            }
            mat.init(info);
        }
    }

    /**
     * Inscribe un alumno a la materia de una carrera.
     *
     * @Complejidad: O(|c| + |m| + |estudiante|).
     *
     * @see Trie#obtener
     * @see Materia#inscribirAlumno
     */
    public void inscribir(String estudiante, String carrera, String materia) {
        carreras.obtener(carrera).obtener(materia).inscribirAlumno(estudiante);
        materiasEstudiante.modificar(estudiante, x -> x + 1);
    }

    /**
     * Agrega un docente del cargo correspondiente a la materia de una carrera.
     *
     * @Complejidad: O(|c| + |m|).
     *
     * @see Trie#obtener
     * @see Materia#agregarDocente
     */
    public void agregarDocente(CargoDocente cargo, String carrera, String materia) {
        carreras.obtener(carrera).obtener(materia).agregarDocente(cargo);
    }

    /**
     * Devuelve el plantel docente de la materia de una carrera.
     *
     * @Complejidad: O(|c| + |m|).
     *
     * @see Trie#obtener
     * @see Materia#plantelDocente
     */
    public int[] plantelDocente(String materia, String carrera) {
        return carreras.obtener(carrera).obtener(materia).plantelDocente();
    }

    /**
     * Cierra una materia de una carrera.
     *
     * @Complejidad: O(|c| + |m| + |m.inscriptos| + Σ|Nm|).
     *
     * @see Trie#obtener
     * @see Trie#modificar
     * @see Materia#cerrarMateria
     * @see Materia#iteradorAlumnos
     */
    public void cerrarMateria(String materia, String carrera) {
        Materia mat = carreras.obtener(carrera).obtener(materia);
        Iterador<String> it = mat.iteradorAlumnos();
        while (it.haySiguiente()) {
            String alumno = it.siguiente();
            materiasEstudiante.modificar(alumno, x -> x - 1);
        }
        carreras.obtener(carrera).obtener(materia).cerrarMateria();
    }

    /**
     * Devuelve la cantidad de inscriptos en una materia de una carrera.
     *
     * @Complejidad: O(|c| + |m|).
     *
     * @see Trie#obtener
     */
    public int inscriptos(String materia, String carrera) {
        return carreras.obtener(carrera).obtener(materia).cantidadInscriptos();
    }

    /**
     * Devuelve si una materia excede el cupo determinado por la cantidad de
     * docentes.
     *
     * @Complejidad: O(|c| + |m|).
     *
     * @see Trie#obtener
     * @see Materia#excedeCupo
     */
    public boolean excedeCupo(String materia, String carrera) {
        return carreras.obtener(carrera).obtener(materia).excedeCupo();
    }

    /**
     * Devuelve las carreras.
     *
     * @Complejidad: O(Σ|c|).
     *
     * @see Trie#keys
     */
    public String[] carreras() {
        return carreras.keys();
    }

    /**
     * Devuelve las materias de una carrera.
     *
     * @Complejidad: O(|c| + Σ|m|).
     *
     * @see Trie#obtener
     * @see Trie#keys
     */
    public String[] materias(String carrera) {
        return carreras.obtener(carrera).keys();
    }

    /**
     * Devuelve la cantidad de materias en las que está inscripto un estudiante.
     *
     * @Complejidad: O(1). En ese caso es O(1). pues la longitud de la clave
     *               (LU) es constante para cualquier estudiante.
     *
     * @see Trie#obtener
     */
    public int materiasInscriptas(String estudiante) {
        return materiasEstudiante.obtener(estudiante);
    }
}
