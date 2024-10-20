package Implementacion;
public class Vuelos {

    //Clase vuelos
    private String nombre;
    private int prioridad;
    private int duracion;  

    //Constructor
    public Vuelos(String nombre, int prioridad, int duracion) {
        this.nombre = nombre;
        this.prioridad = prioridad;
        this.duracion = duracion;
    }

    //Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion){
        this.duracion=duracion;
    }

    @Override
    public String toString() {
        return "Vuelo: " + nombre + " (Prioridad: " + prioridad + ", Duración: " + duracion + " segundos)";
    }
}
