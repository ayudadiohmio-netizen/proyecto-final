import java.util.ArrayList;
import java.util.List;

public class Mision {
    private int id;
    private String nombre;
    private String tipo; // Principal, Secundaria, Diaria...
    private int prioridad;        // 1 = muy urgente, 5 = poco urgente
    private int diasParaExpirar;  // entre menos dias, mas apremia
    private int xpRecompensa;
    private List<Integer> dependencias; // ids de misiones que hay que completar antes

    public Mision(int id, String nombre, String tipo, int prioridad, int diasParaExpirar, int xpRecompensa) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.prioridad = prioridad;
        this.diasParaExpirar = diasParaExpirar;
        this.xpRecompensa = xpRecompensa;
        this.dependencias = new ArrayList<>();
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getTipo() { return tipo; }
    public int getPrioridad() { return prioridad; }
    public int getDiasParaExpirar() { return diasParaExpirar; }
    public int getXpRecompensa() { return xpRecompensa; }
    public List<Integer> getDependencias() { return dependencias; }

    public void agregarDependencia(int idMisionRequisito) {
        dependencias.add(idMisionRequisito);
    }

    @Override
    public String toString() {
        return "Mision #" + id + " - " + nombre + " (" + tipo + ", prioridad " + prioridad
                + ", expira en " + diasParaExpirar + " dias, " + xpRecompensa + " xp)";
    }
}
