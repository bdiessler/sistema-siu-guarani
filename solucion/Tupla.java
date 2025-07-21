package aed;

class Tupla<A, B> {
    private final A fst;
    private final B snd;

    public Tupla(A fst_, B snd_) {
        this.fst = fst_;
        this.snd = snd_;
    }

    public A fst() {
        return fst;
    }

    public B snd() {
        return snd;
    }
}