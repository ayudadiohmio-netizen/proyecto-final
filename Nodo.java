public class Nodo<T> {
    public T dato;
    public Nodo<T> izquierdo;
    public Nodo<T> derecho;

    public Nodo(T dato) {
        this.dato = dato;
        this.izquierdo = null;
        this.derecho = null;
    }
}
