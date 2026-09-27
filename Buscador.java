import java.util.List;

//Busqueda binaria sobre una lista de misiones ordenada por id, sirve para comparar contra la tabla hash: la hash es mas rapida,
//pero esta es la busqueda clasica que hay que saber hacer a mano

public class Buscador {

    public static Mision buscarPorId(List<Mision> misionesOrdenadasPorId, int id) {
        int inicio = 0;
        int fin = misionesOrdenadasPorId.size() - 1;

        while (inicio <= fin) {
            int medio = (inicio + fin) / 2;
            Mision actual = misionesOrdenadasPorId.get(medio);

            if (actual.getId() == id) {
                return actual;
            } else if (actual.getId() < id) {
                inicio = medio + 1;
            } else {
                fin = medio - 1;
            }
        }
        return null; // no se encontro
    }
}
