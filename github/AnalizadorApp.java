import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AnalizadorApp extends JFrame {
    
    // Componentes de la GUI
    private JTextField txtEntrada;
    private JButton btnAnalizarTexto, btnOperarNumero, btnJuego;
    private JLabel lblResultadoTexto, lblResultadoNumero, lblResultadoJuego;
    private JLabel lblError;
    
    // Número secreto para el juego (generado una sola vez al iniciar)
    private int numeroSecreto;

    public AnalizadorApp() {
        // Configuración de la Ventana
        setTitle("Analizador con java.lang - Actividad");
        setSize(500, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Inicializar número secreto (1 al 10 usando Math.random())
        generarNuevoSecreto();

        // Panel superior para la entrada de datos
        JPanel panelNorte = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelNorte.add(new JLabel("Ingrese un dato:"));
        txtEntrada = new JTextField(20);
        panelNorte.add(txtEntrada);
        add(panelNorte, BorderLayout.NORTH);

        // Panel central para los botones y resultados
        JPanel panelCentro = new JPanel();
        panelCentro.setLayout(new BoxLayout(panelCentro, BoxLayout.Y_AXIS));
        panelCentro.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // 1. Sección Analizar Texto
        JPanel panelTexto = new JPanel(new GridLayout(2, 1, 5, 2));
        panelTexto.setBorder(BorderFactory.createTitledBorder("Analizar Texto"));
        btnAnalizarTexto = new JButton("Analizar Texto");
        lblResultadoTexto = new JLabel("Resultado: -");
        panelTexto.add(btnAnalizarTexto);
        panelTexto.add(lblResultadoTexto);
        panelCentro.add(panelTexto);
        panelCentro.add(Box.createRigidArea(new Dimension(0, 10)));

        // 2. Sección Operar Número
        JPanel panelNumero = new JPanel(new GridLayout(2, 1, 5, 2));
        panelNumero.setBorder(BorderFactory.createTitledBorder("Operar Número"));
        btnOperarNumero = new JButton("Operar Número");
        lblResultadoNumero = new JLabel("Resultado: -");
        panelNumero.add(btnOperarNumero);
        panelNumero.add(lblResultadoNumero);
        panelCentro.add(panelNumero);
        panelCentro.add(Box.createRigidArea(new Dimension(0, 10)));

        // 3. Sección Juego
        JPanel panelJuego = new JPanel(new GridLayout(2, 1, 5, 2));
        panelJuego.setBorder(BorderFactory.createTitledBorder("Juego (Adivina 1-10)"));
        btnJuego = new JButton("Juego");
        lblResultadoJuego = new JLabel("Resultado: -");
        panelJuego.add(btnJuego);
        panelJuego.add(lblResultadoJuego);
        panelCentro.add(panelJuego);

        add(panelCentro, BorderLayout.CENTER);

        // Panel inferior para errores
        JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.CENTER));
        lblError = new JLabel(" ");
        lblError.setForeground(Color.RED);
        panelSur.add(lblError);
        add(panelSur, BorderLayout.SOUTH);

        // --- ASIGNACIÓN DE EVENTOS (LÓGICA DE BOTONES) ---

        // Botón 1: Analizar Texto (Usa String.length() y String.toUpperCase())
        btnAnalizarTexto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarError();
                String texto = txtEntrada.getText();
                if (texto.isEmpty()) {
                    mostrarError("El campo de texto no puede estar vacío para analizar.");
                    return;
                }
                int longitud = texto.length(); // API String
                String mayuscula = texto.toUpperCase(); // API String
                lblResultadoTexto.setText("<html>Longitud: " + longitud + "<br>Mayúsculas: " + mayuscula + "</html>");
            }
        });

        // Botón 2: Operar Número (Usa Double.parseDouble(), Math.sqrt() y Math.pow())
        btnOperarNumero.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarError();
                try {
                    String texto = txtEntrada.getText();
                    double numero = Double.parseDouble(texto); // API Double
                    
                    if (numero < 0) {
                        mostrarError("No se puede calcular la raíz cuadrada de un número negativo.");
                        return;
                    }

                    double raiz = Math.sqrt(numero); // API Math
                    double cuadrado = Math.pow(numero, 2); // API Math

                    lblResultadoNumero.setText(String.format("Raíz: %.2f | Cuadrado: %.2f", raiz, cuadrado));
                } catch (NumberFormatException ex) {
                    mostrarError("Error: Ingrese un número válido para operar.");
                }
            }
        });

        // Botón 3: Juego (Usa Math.random() e Integer.parseInt())
        btnJuego.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarError();
                try {
                    String texto = txtEntrada.getText();
                    int intento = Integer.parseInt(texto); // API Integer

                    if (intento == numeroSecreto) {
                        lblResultadoJuego.setText("¡Felicidades! Adivinaste el número (" + numeroSecreto + ").");
                        generarNuevoSecreto(); // Reiniciar juego con nuevo número
                    } else if (intento < numeroSecreto) {
                        lblResultadoJuego.setText("Incorrecto. El número secreto es mayor.");
                    } else {
                        lblResultadoJuego.setText("Incorrecto. El número secreto es menor.");
                    }
                } catch (NumberFormatException ex) {
                    mostrarError("Error: Ingrese un número entero válido para jugar.");
                }
            }
        });
    }

    private void generarNuevoSecreto() {
        // Math.random() genera entre 0.0 y 0.999..., multiplicamos por 10 y sumamos 1 para obtener 1-10
        numeroSecreto = (int) (Math.random() * 10) + 1;
    }

    private void mostrarError(String mensaje) {
        lblError.setText(mensaje);
    }

    private void limpiarError() {
        lblError.setText(" ");
    }

    public static void main(String[] args) {
        // Ejecutar la interfaz gráfica en el hilo de eventos de Swing
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AnalizadorApp().setVisible(true);
            }
        });
    }
}