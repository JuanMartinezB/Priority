package Implementacion;
import java.util.PriorityQueue;
import java.util.Comparator;

public class ColaPrioridad <T> { //Clase génerica de la cola de prioridad

    private PriorityQueue<T> cola; //Creación de la cola 

    //Comparador para definir la prioridad de los vuelos 
    public ColaPrioridad(Comparator<T> comparador) {
        cola = new PriorityQueue<>(comparador);
    }

    //Agregar un elemento a la cola
    public void agregar(T elemento) {
        cola.offer(elemento);
    }

    //Obtener el elemento de mayor prioridad
    public T cima() {
        return cola.peek();  
    }

    //Elimina el elemento de mayor prioridad
    public void eliminar() {
        cola.poll();
    }

    //Verifica si la cola esta vacia
    public boolean estaVacia() {
        return cola.isEmpty();
    }

    //Tamano de la cola
    public int tamano(){
        return cola.size();
    }

    //Obtiene y elimina el elemento de mayor prioridad
    public T poll (){
        return cola.poll();
    }

    //Método para imprimir la cola
    public void mostrar() {
        if (cola.isEmpty()) {
            System.out.println("La cola está vacía.");
        } else {
            System.out.println("Elementos en la cola:");
            for (T elemento : cola) {
                System.out.println(elemento);
            }
        }
    }

}
