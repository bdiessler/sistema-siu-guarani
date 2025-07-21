package aed;

public class Wrapper<T> {
	private T val;

	public Wrapper(T val) {
		this.val = val;
	}

	public T get() {
		return val;
	}

	public void set(T val) {
		this.val = val;
	}
}
