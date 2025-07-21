package aed;

interface Diccionario<K, V> {
	/**
	 * proc esta(in d: Diccionario<K,V>, in k: K): bool
	 * <p>
	 * asegura {res = true ↔ k ∈ d.data}
	 */
	boolean esta(K k);

	/**
	 * proc definir(inout d: Diccionario<K,V>, in k: K, in v: V)
	 * <p>
	 * requiere {d = D0 ∧ k /∈ d.data}
	 * <p>
	 * asegura {d.data = setKey(D0.data, k, v)}
	 */
	void definir(K k, V v);

	/**
	 * proc obtener(in d: Diccionario<K,V>, in k: K): V
	 * <p>
	 * requiere {k ∈ d.data}
	 * <p>
	 * asegura {res = d.data[k]}
	 */
	V obtener(K k);

	/**
	 * proc borrar(inout d: Diccionario<K,V>, in k: K)
	 * <p>
	 * requiere {d = D0 ∧ k ∈ d.data}
	 * <p>
	 * asegura {d.data = delKey(D0.data, k)}
	 */
	void borrar(K k);

	/**
	 * proc size(in d: Diccionario<K,V>): Z
	 * <p>
	 * asegura {res = |d.data|}
	 */
	int size();
}