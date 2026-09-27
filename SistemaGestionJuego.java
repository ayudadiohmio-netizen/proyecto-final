import java.util.ArrayList;
import java.util.List;

public class SistemaGestionJuego {

    private TablaHash<Integer, Mision> misionesPorId;
    private TablaHash<Integer, Personaje> personajesPorId;
    private ColaPrioridad<Mision> colaPrioridad;
    private ArbolBinario<Personaje> arbolPersonajes;
    private Grafo grafoDependencias;

    private List<Mision> listaMisiones;       // para ordenar y hacer busqueda binaria
    private List<Personaje> listaPersonajes;  // para repartir misiones

    private int siguienteIdMision;
    private int siguienteIdPersonaje;

    public SistemaGestionJuego() {
        misionesPorId = new TablaHash<>(101);
        personajesPorId = new TablaHash<>(101);
        colaPrioridad = new ColaPrioridad<>();
        arbolPersonajes = new ArbolBinario<>();
        grafoDependencias = new Grafo();
        listaMisiones = new ArrayList<>();
        listaPersonajes = new ArrayList<>();
        siguienteIdMision = 1;
        siguienteIdPersonaje = 1;
    }

    // ---------- Personajes ----------

    public Personaje agregarPersonaje(String nombre, String clase, int nivel) {
        Personaje p = new Personaje(siguienteIdPersonaje++, nombre, clase, nivel);
        personajesPorId.put(p.getId(), p);
        arbolPersonajes.insertar(p);
        listaPersonajes.add(p);
        System.out.println("Personaje agregado: " + p);
        return p;
    }

    public void buscarPersonajePorId(int id) {
        Personaje p = personajesPorId.get(id);
        System.out.println(p != null ? "Encontrado: " + p : "No hay ningun personaje con ese id.");
    }

    public void mostrarPersonajesPorClase() {
        if (arbolPersonajes.estaVacio()) {
            System.out.println("No hay personajes registrados.");
            return;
        }
        arbolPersonajes.inorden();
    }

    // ---------- Misiones ----------

    public Mision agregarMision(String nombre, String tipo, int prioridad, int diasParaExpirar, int xpRecompensa) {
        Mision mision = new Mision(siguienteIdMision++, nombre, tipo, prioridad, diasParaExpirar, xpRecompensa);
        misionesPorId.put(mision.getId(), mision);
        colaPrioridad.enqueue(mision, prioridad);
        listaMisiones.add(mision);
        grafoDependencias.agregarTarea(mision.getId());
        return mision;
    }

    public void agregarDependencia(int idMision, int idRequisito) {
        grafoDependencias.agregarDependencia(idMision, idRequisito);
        Mision mision = misionesPorId.get(idMision);
        if (mision != null) {
            mision.agregarDependencia(idRequisito);
        }
        System.out.println("Listo, la mision " + idMision + " ahora depende de la mision " + idRequisito);
    }

    public void buscarMisionPorIdHash(int id) {
        long inicio = System.nanoTime();
        Mision mision = misionesPorId.get(id);
        long fin = System.nanoTime();
        System.out.println(mision != null ? "Encontrada: " + mision : "No existe esa mision");
        System.out.println("(tabla hash: " + (fin - inicio) + " ns)");
    }

    public void buscarMisionPorIdBinario(int id) {
        List<Mision> ordenadasPorId = new ArrayList<>(listaMisiones);
        ordenadasPorId.sort((a, b) -> Integer.compare(a.getId(), b.getId()));

        long inicio = System.nanoTime();
        Mision mision = Buscador.buscarPorId(ordenadasPorId, id);
        long fin = System.nanoTime();
        System.out.println(mision != null ? "Encontrada: " + mision : "No existe esa mision");
        System.out.println("(busqueda binaria: " + (fin - inicio) + " ns)");
    }

    // ---------- Cola de prioridad ----------

    public void atenderSiguienteUrgente() {
        colaPrioridad.dequeue();
    }

    public void mostrarColaPrioridad() {
        colaPrioridad.mostrar();
    }

    // ---------- Ordenamiento (divide y venceras) ----------

    public void mostrarMisionesOrdenadas() {
        if (listaMisiones.isEmpty()) {
            System.out.println("No hay misiones registradas.");
            return;
        }
        List<Mision> ordenadas = DivideYConquista.ordenarMisiones(new ArrayList<>(listaMisiones));
        System.out.println("Misiones ordenadas por prioridad y dias para expirar:");
        for (Mision m : ordenadas) {
            System.out.println(" - " + m);
        }
    }

    // ---------- Distribucion de misiones (divide y venceras) ----------

    public void distribuirMisionesEntrePersonajes() {
        if (listaPersonajes.isEmpty()) {
            System.out.println("No hay personajes registrados.");
            return;
        }
        List<Mision> ordenadas = DivideYConquista.ordenarMisiones(new ArrayList<>(listaMisiones));
        System.out.println("Repartiendo misiones entre los personajes:");
        DivideYConquista.distribuirMisiones(ordenadas, listaPersonajes);
    }

    // ---------- Estadisticas (recursividad) ----------

    public void mostrarEstadisticas() {
        if (listaMisiones.isEmpty()) {
            System.out.println("No hay misiones registradas.");
            return;
        }
        int totalXp = Estadisticas.sumarXpTotal(listaMisiones);
        double promedio = Estadisticas.promedioXp(listaMisiones);
        Mision masUrgente = Estadisticas.misionMasUrgente(listaMisiones);

        System.out.println("XP total de todas las misiones: " + totalXp);
        System.out.println("Promedio de xp por mision: " + promedio);
        System.out.println("Mision mas urgente: " + masUrgente);
    }

    // ---------- Grafo de dependencias ----------

    public void mostrarGrafo() {
        grafoDependencias.mostrar();
    }

    public void mostrarOrdenSugerido() {
        List<Integer> orden = grafoDependencias.ordenSugerido();
        if (!orden.isEmpty()) {
            System.out.println("Orden sugerido para completar las misiones: " + orden);
        }
    }
}
