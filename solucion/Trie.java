package aed;

import java.util.ArrayList;
import java.util.function.Function;

/**
 * Implementación de un Trie. Representa un diccionario de claves de tipo String
 * y valores de tipo V.
 *
 * aux cantHijosDefinidos(n: Trie.Nodo): int = if (n != null) then ((if n.valor
 * != null then 1 else 0) + ΣcantHijosDefinidos(n.hijos[i])) else 0
 *
 * pred unicidadDeHijos(n: Trie.Nodo) { ∀ i, j ∈ [0, n.hijos.size()). i != j ^
 * n.hijos[i], n.hijos[j] !== null => n.hijos[i] != n.hijos[j]}
 *
 * pred noCompartenHijos(n: Trie.Nodo, p: Trie.Nodo) { (Set(n.hijos) \ {null}) ∩
 * (Set(p.hijos) \ {null})= ∅ }
 *
 * aux cantHojas(n: Trie.Nodo): int = if (n != null) then (if Set(m.hijos) !=
 * {null} then ΣcantHojas(m.hijo[i]) else 1) else 0
 *
 * pred noTieneRamasInutiles(t: Trie) { cantHojas(t.root) =
 * cantHijosDefinidos(t.root) }
 *
 * aux nodos(t:Trie, n: Trie.Nodo): Conj<Trie.nodo> = (if (n != null and n !=
 * t.root) then {n} else {}) U nodos(t, n.hijos[i])
 *
 * @InvRep(Trie t) = { 
 * 						t.root != null && t.size >= 0 &&
 * 						t.size == cantHijosDefinidos(t.root) &&
 * 						∀ Nodo n in nodos(t,t.root): (n.hijos.size() = 256 ^ unicidadDeHijos(n)) &&
 * 						∀ Nodo n, Nodo p in nodos(t,t.root). n != p => noCompartenHijos(n,p) &&
 * 						(Set(t.root.hijos) != {null}) => noTieneRamasInutiles(t))
 * 					}
 */
public class Trie<V> implements Diccionario<String, V> {

    Nodo root = new Nodo(null);
    int size = 0;

    private class Nodo {

        V significado;
        ArrayList<Nodo> hijos;

        public Nodo(V val) {
            significado = val;
            hijos = new ArrayList<>(256);
            for (int i = 0; i < 256; i++) {
                hijos.add(null);
            }
        }
    }

    public Trie() {
    }

    /**
     * Verifica si la clave k está en el Trie.
     *
     * @Complejidad: O(|k|). O(1) + O(|k|) = O(|k|). Iterar un trie es O(|k|)
     * pues en el peor caso, `for (int i = 0; i < k.length(); i++){}` se ejecuta
     * |k| veces.
     *
     * @see Diccionario#esta
     */
    @Override
    public boolean esta(String k) {
        Nodo actual = root;
        if (root == null || k.equals("")) {
            return false;
        }
        for (int i = 0; i < k.length(); i++) {
            char c = k.charAt(i);
            int index = (int) c;
            if (actual.hijos.get(index) == null) {
                return false;
            }
            actual = actual.hijos.get(index);
        }
        return actual.significado != null;
    }

    /**
     * Define la clave k con el significado value en el Trie.
     *
     * @Complejidad: O(|k|).
     *
     * @see Diccionario#definir
     */
    @Override
    public void definir(String k, V value) {
        if (k.equals("")) {
            return;
        }
        Nodo actual = root;
        for (int i = 0; i < k.length(); i++) {
            char c = k.charAt(i);
            int index = (int) c;
            if (actual.hijos.get(index) == null) {
                actual.hijos.set(index, new Nodo(null));
            }
            actual = actual.hijos.get(index);
        }
        actual.significado = value;
        size++;
    }

    /**
     * Obtiene el significado de la clave k.
     *
     * @Complejidad: O(|k|).
     *
     * @see Diccionario#obtener
     */
    @Override
    public V obtener(String k) {
        Nodo actual = root;
        for (int i = 0; i < k.length(); i++) {
            char c = k.charAt(i);
            int index = (int) c;
            actual = actual.hijos.get(index);
        }
        return actual.significado;
    }

    /**
     * Borra la clave k del Trie.
     *
     * @Complejidad: O(|k|). Siempre realiza |k| * c operaciones(c es la
     * cantidad de comparaciones, retornos, etc... que se realizan en cada
     * llamada iterativa - ver `borrar(Nodo,String,int)` ).
     *
     * @see Diccionario#borrar
     */
    @Override
    public void borrar(String k) {
        this.root = borrar(root, k, 0);
        size--;
    }

    private Nodo borrar(Nodo root, String key, int profundidad) {
        if (root == null) {
            return null;
        }
        if (profundidad == key.length()) {
            root.significado = null;
        } else {
            char c = key.charAt(profundidad);
            int index = (int) c;
            root.hijos.set(index, borrar(root.hijos.get(index), key, profundidad + 1));
        }
        if (root.significado != null) {
            return root;
        }
        if (noTieneHijos(root)) {
            root = null;
        }
        return root;
    }

    /**
     * Verifica si el nodo no tiene hijos.
     *
     * @Complejidad: O(1). Pues el for se ejecuta solo 26 veces. O(26) = O(1).
     */
    private boolean noTieneHijos(Nodo n) {
        for (int i = 0; i < n.hijos.size(); i++) {
            if (n.hijos.get(i) != null) {
                return false;
            }
        }
        return true;
    }

    /**
     * Retorna la cantidad de elementos en el Trie.
     *
     * @Complejidad: O(1).
     *
     * @see Diccionario#size
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Redefine el valor de la clave k con el resultado de aplicar la función f
     * al valor actual.
     *
     * <p>
     * requiere {∃f(α(this).data[k]), esta(k)}
     * <p>
     * asegura {α(this).data[k] = f(α(old(this)).data[k])}
     *
     * @Complejidad: O(|k|) + O(f). [O(f) es el costo de computar f, no la cota
     * asintotica de f]
     */
    public void modificar(String k, Function<V, V> f) {
        Nodo actual = root;
        for (int i = 0; i < k.length(); i++) {
            char c = k.charAt(i);
            int index = (int) c;
            actual = actual.hijos.get(index);
        }
        actual.significado = f.apply(actual.significado);
    }

    /**
     * Retorna las claves del Trie. Las claves se retornan en un arreglo de
     * Strings ordenadas lexicograficamente [Esto es asi ya que accede y agrega
     * antes al array respuesta los hijos de mas a la izquierda, que por
     * definicion son los mayores lexicograficamente (Menor valor ASCII)]
     *
     * @Complejidad: Realiza 256 operaciones por cada nodo del Trie, y puede
     * llegar a haber tantos nodos como caracteres en las claves. Luego la
     * complejidad es O(256 * Σ|k|) = O(Σ|k|).
     */
    public String[] keys() {
        String keys[] = new String[this.size];
        Wrapper<Integer> index = new Wrapper<>(0);
        return keys(root, "", keys, index);
    }

    private String[] keys(Nodo actual, String key, String[] keys, Wrapper<Integer> index) {
        if (actual == null) {
            return keys;
        }
        if (actual.significado != null) {
            keys[index.get()] = key;
            index.set(index.get() + 1);
        }
        for (int i = 0; i < actual.hijos.size(); i++) {
            if (actual.hijos.get(i) != null) {
                char c = (char) i;
                keys(actual.hijos.get(i), key + c, keys, index);
            }
        }
        return keys;
    }

    /**
     * Retorna los valores definidos en el Trie.
     *
     * @Complejidad: O(Σ|k|). Mismo razonamiento que `keys`.
     */
    public ArrayList<V> values() {
        ArrayList<V> values = new ArrayList<>();
        return values(root, values);
    }

    private ArrayList<V> values(Nodo actual, ArrayList<V> values) {
        if (actual == null) {
            return values;
        }
        if (actual.significado != null) {
            values.add(actual.significado);
        }
        for (int i = 0; i < actual.hijos.size(); i++) {
            if (actual.hijos.get(i) != null) {
                values(actual.hijos.get(i), values);
            }
        }
        return values;
    }
}
