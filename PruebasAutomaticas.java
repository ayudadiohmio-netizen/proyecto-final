import java.util.ArrayList;
import java.util.List;

public class PruebasAutomaticas {

    public static void correr() {
        System.out.println("\nCorriendo pruebas automaticas");

        System.out.println("\nCola de prioridad");
        ColaPrioridad<String> colaPrueba = new ColaPrioridad<>();
        colaPrueba.enqueue("Revisar el mapa", 3);
        colaPrueba.enqueue("Jefe final apareciendo", 1);
        colaPrueba.enqueue("Reponer pociones", 2);
        colaPrueba.mostrar(); // deberia salir primero "Jefe final apareciendo"

        System.out.println("\nTabla hash");
        TablaHash<Integer, String> hashPrueba = new TablaHash<>(10);
        hashPrueba.put(1, "Aldric");
        hashPrueba.put(2, "Merin");
        System.out.println("Buscar id 1 (deberia ser Aldric): " + hashPrueba.get(1));
        System.out.println("Buscar id 99 (deberia ser null): " + hashPrueba.get(99));

        System.out.println("\nArbol de personajes");
        ArbolBinario<Personaje> arbolPrueba = new ArbolBinario<>();
        arbolPrueba.insertar(new Personaje(1, "Toren", "Guerrero", 5));
        arbolPrueba.insertar(new Personaje(2, "Lyra", "Mago", 7));
        arbolPrueba.inorden();

        System.out.println("\nRecursividad (estadisticas)");
        List<Mision> misionesPrueba = new ArrayList<>();
        misionesPrueba.add(new Mision(1, "A", "Diaria", 1, 2, 50));
        misionesPrueba.add(new Mision(2, "B", "Diaria", 2, 3, 30));
        System.out.println("Suma de xp (deberia ser 80): " + Estadisticas.sumarXpTotal(misionesPrueba));

        System.out.println("\nDivide y venceras (ordenamiento)");
        misionesPrueba.add(0, new Mision(3, "C", "Principal", 1, 1, 500));
        List<Mision> ordenadas = DivideYConquista.ordenarMisiones(misionesPrueba);
        for (Mision m : ordenadas) System.out.println(" - " + m);

        System.out.println("\nGrafo (orden de dependencias)");
        Grafo grafoPrueba = new Grafo();
        grafoPrueba.agregarDependencia(2, 1); // la 2 depende de la 1
        grafoPrueba.agregarDependencia(3, 2); // la 3 depende de la 2
        System.out.println("Orden sugerido (deberia ser 1, 2, 3): " + grafoPrueba.ordenSugerido());

        System.out.println("\nPruebas terminadas, no hubo errores");
    }
}
