// cola de prioridad para las tareas: entre mas chico el numero de
// prioridad, mas urgente es (1 = lo mas urgente) se usa para decidir
// que tarea se atiende primero segun su urgencia y su fecha de entrega

public class ColaPrioridad<T> {
    public NodoPrioridad<T> front;
    public int size;

    public ColaPrioridad() {
        this.front = null;
        this.size = 0;
    }

    public boolean isEmpty() {
        return front == null;
    }

    // agrega un elemento en el lugar que le toque segun su prioridad
    public void enqueue(T data, int prioridad) {
        NodoPrioridad<T> nuevo = new NodoPrioridad<>(data, prioridad);

        if (isEmpty() || prioridad < front.prioridad) {
            nuevo.next = front;
            front = nuevo;
        } else {
            NodoPrioridad<T> actual = front;
            while (actual.next != null && actual.next.prioridad <= prioridad) {
                actual = actual.next;
            }
            nuevo.next = actual.next;
            actual.next = nuevo;
        }
        size++;
        System.out.println("Tarea agregada a la cola de prioridad.");
    }

    // saca y regresa el elemento con mayor prioridad (el primero de la fila)
    public T dequeue() {
        if (isEmpty()) {
            System.out.println("Cola vacia, nada que atender.");
            return null;
        }
        T atendido = front.data;
        front = front.next;
        size--;
        System.out.println("Se atendio el elemento con mayor prioridad.");
        return atendido;
    }

    // revisa cual es el siguiente sin quitarlo
    public T peek() {
        if (isEmpty()) {
            System.out.println("Cola vacia, nada que atender.");
            return null;
        }
        return front.data;
    }

    public void mostrar() {
        if (isEmpty()) {
            System.out.println("Cola vacia, nada que mostrar.");
            return;
        }
        NodoPrioridad<T> actual = front;
        System.out.print("Frente -> ");
        while (actual != null) {
            System.out.print(actual.data + " (prioridad " + actual.prioridad + ") ");
            actual = actual.next;
        }
        System.out.println();
    }
}
