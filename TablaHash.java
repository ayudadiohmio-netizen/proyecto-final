// tabla hash sencilla para guardar y encontrar tareas 

@SuppressWarnings("unchecked")
public class TablaHash<K, V> {
    public HashNode<K, V>[] buckets;
    public int capacity;
    public int size;

    public TablaHash(int capacity) {
        this.capacity = capacity;
        this.buckets = new HashNode[capacity];
        this.size = 0;
    }

    // convierte la llave en un indice valido dentro del arreglo
    public int getBucketIndex(K key) {
        int hashCode = key.hashCode();
        return Math.abs(hashCode) % capacity;
    }

    // agrega o actualiza el valor de una llave
    public void put(K key, V value) {
        int bucketIndex = getBucketIndex(key);
        HashNode<K, V> actual = buckets[bucketIndex];

        while (actual != null) {
            if (actual.key.equals(key)) {
                actual.value = value;  //actualizamos
                return;
            }
            actual = actual.next;
        }

        // no existia, metemos un nodo nuevo al inicio de la cadena
        HashNode<K, V> nuevo = new HashNode<>(key, value);
        nuevo.next = buckets[bucketIndex];
        buckets[bucketIndex] = nuevo;
        size++;
    }

    // busca un valor a partir de su llave
    public V get(K key) {
        int bucketIndex = getBucketIndex(key);
        HashNode<K, V> actual = buckets[bucketIndex];

        while (actual != null) {
            if (actual.key.equals(key)) {
                return actual.value;
            }
            actual = actual.next;
        }
        return null; // no se encontro
    }

    public boolean contiene(K key) {
        return get(key) != null;
    }

    public int getSize() {
        return size;
    }

    public void mostrarTabla() {
        for (int i = 0; i < capacity; i++) {
            System.out.print("Bucket " + i + ": ");
            HashNode<K, V> actual = buckets[i];
            if (actual == null) {
                System.out.println("(vacio)");
                continue;
            }
            while (actual != null) {
                System.out.print("[" + actual.key + " = " + actual.value + "] ");
                actual = actual.next;
            }
            System.out.println();
        }
    }
}
