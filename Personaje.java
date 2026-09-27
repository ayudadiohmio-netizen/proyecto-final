public class Personaje implements Comparable<Personaje> {
    private int id;
    private String nombre;
    private String clase; // Guerrero, Mago, Arquero, Picaro, Clerigo...
    private int nivel;

    public Personaje(int id, String nombre, String clase, int nivel) {
        this.id = id;
        this.nombre = nombre;
        this.clase = clase;
        this.nivel = nivel;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getClase() { return clase; }
    public int getNivel() { return nivel; }

    // se ordena primero por clase (para que el arbol los agrupe)
    // y ya dentro de la misma clase, por id
    @Override
    public int compareTo(Personaje otro) {
        int comparacion = this.clase.compareToIgnoreCase(otro.clase);
        if (comparacion != 0) {
            return comparacion;
        }
        return Integer.compare(this.id, otro.id);
    }

    @Override
    public String toString() {
        return "#" + id + " " + nombre + " (" + clase + ", nivel " + nivel + ")";
    }
}
