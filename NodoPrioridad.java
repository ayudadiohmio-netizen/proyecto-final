public class NodoPrioridad<T> {
    public T data;
    public int prioridad;
    public NodoPrioridad<T> next;

    public NodoPrioridad(T data, int prioridad) {
        this.data = data;
        this.prioridad = prioridad;
        this.next = null;
    }
}
