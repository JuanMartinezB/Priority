package Implementacion;

import javax.swing.*;
import java.awt.*;
import java.util.Comparator;

public class VueloMonitor extends JFrame { //Interfaz gráfica

    private ColaPrioridad<Vuelos> colaVuelos;
    private JTextField nombreField;
    private JTextField prioridadField;
    private JTextField duracionField;
    private JTextArea textoPendientes;
    private JTextArea textoAtendidos;
    
    public VueloMonitor() {

        //Comparar los vuelos por su prioridad
        colaVuelos = new ColaPrioridad<>(Comparator.comparingInt(Vuelos::getPrioridad));
        setTitle("VUELOS");
        setSize(1000, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Panel izquierdo para la entrada de datos
        JPanel panelIzquierdo = new JPanel();
        panelIzquierdo.setLayout(new BoxLayout(panelIzquierdo, BoxLayout.Y_AXIS));
        panelIzquierdo.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Espacio alrededor
        panelIzquierdo.setBackground(new Color(255, 255, 255)); // Fondo blanco

        // Campo de nombre
        JLabel nombreLabel = new JLabel("Nombre del vuelo:");
        nombreField = new JTextField(20);
        panelIzquierdo.add(nombreLabel);
        panelIzquierdo.add(Box.createRigidArea(new Dimension(0, 10))); // Espacio vertical
        panelIzquierdo.add(nombreField);

        // Campo de prioridad
        JLabel prioridadLabel = new JLabel("Prioridad:");
        prioridadField = new JTextField(20);
        panelIzquierdo.add(prioridadLabel);
        panelIzquierdo.add(Box.createRigidArea(new Dimension(0, 10))); // Espacio vertical
        panelIzquierdo.add(prioridadField);

        // Campo de duración
        JLabel duracionLabel = new JLabel("Tiempo hasta la salida(Segundos): ");
        duracionField = new JTextField(20);
        panelIzquierdo.add(duracionLabel);
        panelIzquierdo.add(Box.createRigidArea(new Dimension(0, 10))); // Espacio vertical
        panelIzquierdo.add(duracionField);

        // Panel para los botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT)); // FlowLayout para colocar los botones en la misma línea

        // Botón para agregar vuelo
        JButton agregarButton = new JButton("Agregar Vuelo");
        agregarButton.setBackground(new Color(70, 130, 180)); // Steel Blue
        agregarButton.setForeground(Color.WHITE);
        agregarButton.setFont(new Font("Arial", Font.BOLD, 16));
        panelBotones.add(agregarButton);

        // Botón para ver cola
        JButton verColaButton = new JButton("Ver Cola");
        verColaButton.setBackground(new Color(60, 179, 113)); // Sea Green
        verColaButton.setForeground(Color.WHITE);
        verColaButton.setFont(new Font("Arial", Font.BOLD, 16));
        panelBotones.add(verColaButton);

        // Añadir el panel de botones al panel izquierdo
        panelIzquierdo.add(Box.createRigidArea(new Dimension(0, 20))); // Espacio vertical antes de los botones
        panelIzquierdo.add(panelBotones);

        // Acción del botón Agregar Vuelo
        verColaButton.addActionListener(e -> colaVuelos.mostrar());
        agregarButton.addActionListener(e -> agregarVuelo());

        // Imagen debajo de los botones
        ImageIcon imageIcon = new ImageIcon("src\\Imagenes\\Avion.png");
        Image image = imageIcon.getImage().getScaledInstance(250, 150, Image.SCALE_SMOOTH); // Redimensionar la imagen
        JLabel imagenLabel = new JLabel(new ImageIcon(image));
        panelIzquierdo.add(Box.createRigidArea(new Dimension(0, 20))); // Espacio vertical antes de la imagen
        panelIzquierdo.add(imagenLabel);

        // Añadir el panel izquierdo
        add(panelIzquierdo, BorderLayout.WEST);

        // Panel derecho para vuelos pendientes y atendidos
        JPanel panelDerecho = new JPanel();
        panelDerecho.setLayout(new GridLayout(2, 1, 10, 10)); // Distribución vertical para los dos JTextArea
        panelDerecho.setBackground(new Color(240, 248, 255)); // Fondo Alice Blue
        
        // Área de texto para vuelos pendientes
        textoPendientes = new JTextArea();
        textoPendientes.setEditable(false);
        textoPendientes.setBackground(new Color(224, 255, 255)); // Fondo Light Cyan
        textoPendientes.setBorder(BorderFactory.createTitledBorder("Vuelos Pendientes"));
        
        // Área de texto para vuelos atendidos
        textoAtendidos = new JTextArea();
        textoAtendidos.setEditable(false);
        textoAtendidos.setBackground(new Color(240, 248, 255)); // Fondo Alice Blue
        textoAtendidos.setBorder(BorderFactory.createTitledBorder("Vuelos Atendidos"));

        // Añadir las áreas de texto al panel derecho
        panelDerecho.add(new JScrollPane(textoPendientes));
        panelDerecho.add(new JScrollPane(textoAtendidos));

        // Añadir el panel derecho al centro de la ventana
        add(panelDerecho, BorderLayout.CENTER);
        
        setVisible(true);

        // Iniciar el hilo de atención de vuelos
        Thread hiloAtencion = new Thread(new AtencionVuelos(colaVuelos, this));
        hiloAtencion.start();

    }

    //Método para obtener los datos ingresados y agregar el vuelo
    private void agregarVuelo() {
        try {
            String nombre = nombreField.getText();
            int prioridad = Integer.parseInt(prioridadField.getText());
            int duracion = Integer.parseInt(duracionField.getText());

            Vuelos vuelo = new Vuelos(nombre, prioridad, duracion);
            colaVuelos.agregar(vuelo);

            // Limpiar campos de entrada
            nombreField.setText("");
            prioridadField.setText("");
            duracionField.setText("");
        } catch (NumberFormatException e) {
            actualizarPendientes("Error: Por favor ingresa valores válidos.");
        }
    }

    //Métodos para actualizar las áreas de texto con los vuelos
    public void actualizarPendientes(String mensaje) {
        textoPendientes.append(mensaje + "\n");
        textoPendientes.setCaretPosition(textoPendientes.getDocument().getLength()); // Desplazar al final
    }

    public void actualizarAtendidos(String mensaje) {
        textoAtendidos.append(mensaje + "\n");
        textoAtendidos.setCaretPosition(textoAtendidos.getDocument().getLength()); // Desplazar al final
    }
}


