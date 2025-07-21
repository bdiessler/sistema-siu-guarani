package aed;

/**
 * Implementación de una lista enlazada de elementos de tipo T.
 *
 * Sea T un tipo.
 *
 * list?(Arr l, Nodo x) =def (l=<> <=> x=null) ^L (x!=null =>l (x.val=head(l) ∧
 * list?(tail(l),x.next)))
 *
 * @InvRep(ListaEnlazada l){ l != null ^L ∃l:Seq<T>.list?(l,sll.head) }
 */
public class ListaEnlazada<T> implements Secuencia<T> {

    private Nodo fst = null;
    private Nodo lst = null;
    private int size = 0;

    private class Nodo {

        T val;
        Nodo next;

        Nodo(T e) {
            val = e;
        }

        Nodo(Nodo n) {
            val = n.val;
            next = n.next;
        }
    }

    public ListaEnlazada() {
    }

    /**
     * Devuelve la longitud de la lista.
     *
     * @Complejidad: O(1).
     */
    @Override
    public int longitud() {
        return this.size;
    }

    /**
     * Agrega un elemento al principio de la lista.
     *
     * @Complejidad: O(1).
     */
    @Override
    public void agregarAdelante(T elem) {
        Nodo n = new Nodo(elem);
        n.next = this.fst;
        this.fst = n;
        if (this.lst == null) {
            this.lst = n;
        }
        this.size++;
    }

    /**
     * Agrega un elemento al final de la lista.
     *
     * @Complejidad: O(1).
     */
    @Override
    public void agregarAtras(T elem) {
        Nodo n = new Nodo(elem);
        if (this.fst == null) {
            this.fst = n;
        }
        if (this.lst != null) {
            this.lst.next = n;
        }
        this.lst = n;
        this.size++;
    }

    /**
     * Devuelve el elemento en la posición i.
     *
     * @Complejidad: O(i). En el peor caso, O(n).
     */
    @Override
    public T obtener(int i) {
        Nodo n = this.fst;
        for (int j = 0; j < i; j++) {
            n = n.next;
        }
        return n.val;
    }

    /**
     * Elimina el elemento en la posición i.
     *
     * @Complejidad: O(i). En el peor caso, O(n).
     */
    @Override
    public void eliminar(int i) {
        Nodo curr = this.fst;
        Nodo prev = curr;
        if (i == 0) {
            this.fst = curr.next;
        } else {
            for (int j = 0; j < i; j++) {
                prev = curr;
                curr = curr.next;
            }
            prev.next = curr.next;
        }
        this.size--;
    }

    /**
     * Modifica el elemento en la posición i.
     *
     * @Complejidad: O(i). En el peor caso, O(n).
     */
    @Override
    public void modificarPosicion(int indice, T elem) {
        Nodo n = this.fst;
        for (int j = 0; j < indice; j++) {
            n = n.next;
        }
        n.val = elem;
    }

    /**
     * Devuelve una copia de la lista.
     *
     * @Complejidad: O(n).
     */
    @Override
    public ListaEnlazada<T> copiar() {
        ListaEnlazada<T> res = new ListaEnlazada<>();
        Nodo n = new Nodo(this.fst);
        while (n != null) {
            res.agregarAtras(n.val);
            n = n.next;
        }
        res.size = this.size;
        return res;
    }

    /**
     * Inicializa la lista con los elementos de otra lista.
     *
     * @Complejidad: O(n).
     */
    public ListaEnlazada(ListaEnlazada<T> lista) {
        ListaEnlazada<T> temp = lista.copiar();
        this.fst = temp.fst;
        this.size = temp.size;
    }

    private class ListaIterador implements Iterador<T> {

        private Nodo current = fst;

        @Override
        public boolean haySiguiente() {
            return current != null;
        }

        @Override
        public T siguiente() {
            T val = current.val;
            current = current.next;
            return val;
        }
    }

    public Iterador<T> iterador() {
        return new ListaIterador();
    }
}
