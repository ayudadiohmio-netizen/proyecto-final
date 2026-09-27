public class ArbolBinario<T extends Comparable<T>> {
    public Nodo<T> raiz;

    public ArbolBinario() {
        raiz = null;
    }

    public boolean estaVacio() {
        return raiz == null;
    }

    // insertar y buscar son iterativas (con while) para que no truenen aunque el arbol crezca muy grande 

    public void insertar(T dato) {
        if (raiz == null) {
            raiz = new Nodo<>(dato);
            return;
        }
        Nodo<T> actual = raiz;
        while (true) {
            int comparacion = dato.compareTo(actual.dato);
            if (comparacion < 0) {
                if (actual.izquierdo == null) {
                    actual.izquierdo = new Nodo<>(dato);
                    return;
                }
                actual = actual.izquierdo;
            } else if (comparacion > 0) {
                if (actual.derecho == null) {
                    actual.derecho = new Nodo<>(dato);
                    return;
                }
                actual = actual.derecho;
            } else {
                return; // ya existe 
            }
        }
    }

    public boolean buscar(T dato) {
        Nodo<T> actual = raiz;
        while (actual != null) {
            int comparacion = dato.compareTo(actual.dato);
            if (comparacion == 0) return true;
            actual = comparacion < 0 ? actual.izquierdo : actual.derecho;
        }
        return false;
    }

    public void eliminar(T dato) {
        Nodo<T> padre = null;
        Nodo<T> actual = raiz;

        while (actual != null && dato.compareTo(actual.dato) != 0) {
            padre = actual;
            actual = dato.compareTo(actual.dato) < 0 ? actual.izquierdo : actual.derecho;
        }
        if (actual == null) return;

        if (actual.izquierdo != null && actual.derecho != null) {
            Nodo<T> padreSucesor = actual;
            Nodo<T> sucesor = actual.derecho;
            while (sucesor.izquierdo != null) {
                padreSucesor = sucesor;
                sucesor = sucesor.izquierdo;
            }
            actual.dato = sucesor.dato;
            padre = padreSucesor;
            actual = sucesor;
        }

        Nodo<T> hijo = (actual.izquierdo != null) ? actual.izquierdo : actual.derecho;

        if (padre == null) {
            raiz = hijo;
        } else if (padre.izquierdo == actual) {
            padre.izquierdo = hijo;
        } else {
            padre.derecho = hijo;
        }
    }

    // recorridos, si son recursivos

    public void inorden() {
        inorden(raiz);
        System.out.println();
    }

    private void inorden(Nodo<T> nodo) {
        if (nodo == null) return;
        inorden(nodo.izquierdo);
        System.out.print(nodo.dato + " | ");
        inorden(nodo.derecho);
    }

    public void preorden() {
        preorden(raiz);
        System.out.println();
    }

    private void preorden(Nodo<T> nodo) {
        if (nodo == null) return;
        System.out.print(nodo.dato + " | ");
        preorden(nodo.izquierdo);
        preorden(nodo.derecho);
    }

    public void postorden() {
        postorden(raiz);
        System.out.println();
    }

    private void postorden(Nodo<T> nodo) {
        if (nodo == null) return;
        postorden(nodo.izquierdo);
        postorden(nodo.derecho);
        System.out.print(nodo.dato + " | ");
    }
}
