import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

// grafo dirigido: una flecha de A hacia B quiere decir "a debe
// terminarse antes que b para saber en que orden se pueden
// hacer las tareas de un proyecto sin saltarse ningun requisito.

public class Grafo {
    private Map<Integer, List<Integer>> adyacencia;

    public Grafo() {
        adyacencia = new HashMap<>();
    }

    public void agregarTarea(int idTarea) {
        adyacencia.putIfAbsent(idTarea, new ArrayList<>());
    }

    // dice que "idTarea" depende de "idRequisito" (idRequisito va antes)
    public void agregarDependencia(int idTarea, int idRequisito) {
        agregarTarea(idTarea);
        agregarTarea(idRequisito);
        adyacencia.get(idRequisito).add(idTarea);
    }

    // regresa un orden valido para hacer las tareas respetando las dependencias
    // (si hay un ciclo, es decir tareas que dependen entre si sin fin, avisa y regresa vacio)
    public List<Integer> ordenSugerido() {
        List<Integer> resultado = new ArrayList<>();
        Set<Integer> visitados = new HashSet<>();
        Set<Integer> enProceso = new HashSet<>();

        for (int tarea : adyacencia.keySet()) {
            if (!visitados.contains(tarea)) {
                if (!visitarDFS(tarea, visitados, enProceso, resultado)) {
                    System.out.println("Hay un ciclo de dependencias, no se puede sugerir un orden.");
                    return new ArrayList<>();
                }
            }
        }

        // el recorrido nos da el orden al reves, hay que invertirlo
        List<Integer> ordenFinal = new ArrayList<>();
        for (int i = resultado.size() - 1; i >= 0; i--) {
            ordenFinal.add(resultado.get(i));
        }
        return ordenFinal;
    }

    private boolean visitarDFS(int tarea, Set<Integer> visitados, Set<Integer> enProceso, List<Integer> resultado) {
        visitados.add(tarea);
        enProceso.add(tarea);

        for (int vecino : adyacencia.getOrDefault(tarea, new ArrayList<>())) {
            if (enProceso.contains(vecino)) {
                return false; // ciclo detectado
            }
            if (!visitados.contains(vecino)) {
                if (!visitarDFS(vecino, visitados, enProceso, resultado)) {
                    return false;
                }
            }
        }

        enProceso.remove(tarea);
        resultado.add(tarea);
        return true;
    }

    public void mostrar() {
        if (adyacencia.isEmpty()) {
            System.out.println("El grafo esta vacio.");
            return;
        }
        for (int tarea : adyacencia.keySet()) {
            System.out.println("Tarea " + tarea + " habilita a: " + adyacencia.get(tarea));
        }
    }
}
