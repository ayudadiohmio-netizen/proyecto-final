import java.util.List;

//Calcula estadisticas de una lista de misiones usando recursividad,
// en vez de un simple ciclo for

public class Estadisticas {

    // suma el xp de todas las misiones, una por una
    public static int sumarXpTotal(List<Mision> misiones, int indice) {
        // caso base: ya recorrimos toda la lista
        if (indice == misiones.size()) {
            return 0;
        }
        // caso recursivo: el xp de esta mision, mas el del resto
        return misiones.get(indice).getXpRecompensa() + sumarXpTotal(misiones, indice + 1);
    }

    public static int sumarXpTotal(List<Mision> misiones) {
        return sumarXpTotal(misiones, 0);
    }

    public static double promedioXp(List<Mision> misiones) {
        if (misiones.isEmpty()) return 0;
        return (double) sumarXpTotal(misiones) / misiones.size();
    }

    // encuentra la mision mas urgente (numero de prioridad mas chico) de forma recursiva
    public static Mision misionMasUrgente(List<Mision> misiones, int indice) {
        // caso base: ya solo queda una mision por comparar
        if (indice == misiones.size() - 1) {
            return misiones.get(indice);
        }
        Mision restoMasUrgente = misionMasUrgente(misiones, indice + 1);
        Mision actual = misiones.get(indice);
        return actual.getPrioridad() <= restoMasUrgente.getPrioridad() ? actual : restoMasUrgente;
    }

    public static Mision misionMasUrgente(List<Mision> misiones) {
        if (misiones.isEmpty()) return null;
        return misionMasUrgente(misiones, 0);
    }
}
