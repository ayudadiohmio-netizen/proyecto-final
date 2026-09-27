import java.util.ArrayList;
import java.util.List;

public class DivideYConquista {

    // Ordena las misiones (por prioridad, y si empatan por dias para expirar)
    // usando merge sort: partimos la lista a la mitad, ordenamos cada mitad
    // por separado, y al final las juntamos ya ordenadas
    public static List<Mision> ordenarMisiones(List<Mision> misiones) {
        if (misiones.size() <= 1) {
            return misiones; // caso base: una lista de 0 o 1 ya esta ordenada
        }

        int medio = misiones.size() / 2;
        List<Mision> izquierda = ordenarMisiones(new ArrayList<>(misiones.subList(0, medio)));
        List<Mision> derecha = ordenarMisiones(new ArrayList<>(misiones.subList(medio, misiones.size())));

        return combinar(izquierda, derecha);
    }

    private static List<Mision> combinar(List<Mision> izquierda, List<Mision> derecha) {
        List<Mision> resultado = new ArrayList<>();
        int i = 0, j = 0;

        while (i < izquierda.size() && j < derecha.size()) {
            if (comparar(izquierda.get(i), derecha.get(j)) <= 0) {
                resultado.add(izquierda.get(i++));
            } else {
                resultado.add(derecha.get(j++));
            }
        }
        while (i < izquierda.size()) resultado.add(izquierda.get(i++));
        while (j < derecha.size()) resultado.add(derecha.get(j++));

        return resultado;
    }

    private static int comparar(Mision a, Mision b) {
        if (a.getPrioridad() != b.getPrioridad()) {
            return Integer.compare(a.getPrioridad(), b.getPrioridad());
        }
        return Integer.compare(a.getDiasParaExpirar(), b.getDiasParaExpirar());
    }

    // reparte una lista de misiones ya ordenada entre un grupo de personajes,
    // dividiendo tanto misiones como personajes a la mitad una y otra vez,
    // hasta que a cada grupo solo le queda un personaje (caso base)
    public static void distribuirMisiones(List<Mision> misiones, List<Personaje> personajes) {
        if (personajes.isEmpty()) {
            System.out.println("No hay personajes para asignarles misiones.");
            return;
        }
        distribuir(misiones, 0, misiones.size(), personajes, 0, personajes.size());
    }

    private static void distribuir(List<Mision> misiones, int iniM, int finM,
                                    List<Personaje> personajes, int iniP, int finP) {
        int numPersonajes = finP - iniP;
        if (numPersonajes <= 0 || iniM >= finM) {
            return;
        }

        // caso base: ya nada mas queda un personaje, le toca todo este pedazo
        if (numPersonajes == 1) {
            Personaje p = personajes.get(iniP);
            for (int i = iniM; i < finM; i++) {
                System.out.println(p.getNombre() + " <- " + misiones.get(i));
            }
            return;
        }

        // dividimos personajes a la mitad, y las misiones en la misma proporcion
        int medioP = iniP + numPersonajes / 2;
        int totalMisiones = finM - iniM;
        int medioM = iniM + totalMisiones * (medioP - iniP) / numPersonajes;

        distribuir(misiones, iniM, medioM, personajes, iniP, medioP);
        distribuir(misiones, medioM, finM, personajes, medioP, finP);
    }
}
