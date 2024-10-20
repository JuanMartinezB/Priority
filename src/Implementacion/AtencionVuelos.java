package Implementacion;

public class AtencionVuelos implements Runnable {
    private ColaPrioridad<Vuelos> colaVuelos;
    private VueloMonitor monitor;
    private Vuelos vueloEnCurso = null;
    private long tiempoRestante = 0;

    public AtencionVuelos(ColaPrioridad<Vuelos> colaVuelos, VueloMonitor monitor) {
        this.colaVuelos = colaVuelos;
        this.monitor = monitor;
    }

    @Override
    public void run() {
        while (true) {
            if (!colaVuelos.estaVacia()) {

                //Verifica que no haya ningún vuelo para salir
                if (vueloEnCurso == null) {
                    vueloEnCurso = colaVuelos.cima(); //Obtiene el vuelo de mayir prioridad
                    tiempoRestante = vueloEnCurso.getDuracion() * 1000; 
                    monitor.actualizarPendientes("Vuelo con mayor prioridad de salida " + vueloEnCurso);
     
                }

                try {
                    // Verifica si hay un vuelo de mayor prioridad que interrumpa el actual
                    if (colaVuelos.cima().getPrioridad() < vueloEnCurso.getPrioridad()) {

                        // Guarda el tiempo restante del vuelo interrumpido
                        vueloEnCurso.setDuracion((int)(tiempoRestante / 1000)); // Guardamos en segundo

                        // Atiende el vuelo que se encuentre actualmente en la cima de la cola
                        vueloEnCurso = colaVuelos.cima(); 
                        tiempoRestante = vueloEnCurso.getDuracion() * 1000;

                        monitor.actualizarPendientes("Nuevo vuelo con mayor prioridad de salida: " + vueloEnCurso);
                    }

                    // Simulama el tiempo de la salida del vuelo 
                    if (tiempoRestante > 0) {
                        Thread.sleep(Math.min(tiempoRestante, 1000));  // Pausa de 1 segundo o menos
                        tiempoRestante -= 1000;  // Reduce el tiempo restante
                    }

                    // Si el tiempo restante llega a 0, el vuelo sale
                    if (tiempoRestante <= 0) {
                        // Actualizamos la interfaz gráfica con el vuelo completado
                        monitor.actualizarAtendidos("Salió el vuelo: " + vueloEnCurso);
                        colaVuelos.eliminar();
                        vueloEnCurso = null; //Reinicia el vuelo 
                    }
                } catch (InterruptedException e) {
  
                    monitor.actualizarAtendidos("Error durante la salida de vuelos: " + e.getMessage());

                }
            } else {
                // No hay vuelos en la cola, se hace una pausa
                try {
                    Thread.sleep(1000);  // Pausa de 1 segundo si no hay vuelos
                } catch (InterruptedException e) {
                    monitor.actualizarAtendidos("Error al pausar la salida de vuelos: " + e.getMessage());
 
                }
            }
        }
    }
}
