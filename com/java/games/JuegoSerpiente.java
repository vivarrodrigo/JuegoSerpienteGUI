package com.java.games;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

/**
 * ============================================================
 *              JUEGO DE LA SERPIENTE - VERSIÓN FINAL
 * ============================================================
 *
 * Este juego incluye:
 * ✔ Pantalla de inicio
 * ✔ Conteo regresivo antes de comenzar
 * ✔ Sistema de puntuación
 * ✔ Récord máximo
 * ✔ Pantalla de reinicio cuando se pierde
 *
 * Está desarrollado usando Java Swing.
 */
public class JuegoSerpiente extends JPanel implements KeyListener, ActionListener {

    // ================= CONFIGURACIÓN GENERAL =================
    // Tamaño total de la ventana del juego
    private static final int ANCHO_PANTALLA = 800;
    private static final int ALTO_PANTALLA = 600;

    // Tamaño de cada bloque (cada parte de la serpiente y la manzana)
    private static final int TAMANO_UNIDAD = 25;

    // Número máximo de bloques posibles en la pantalla
    private static final int UNIDADES_JUEGO =
            (ANCHO_PANTALLA * ALTO_PANTALLA) / TAMANO_UNIDAD;

    // Velocidad del juego (entre menor número, más rápido se mueve)
    private static final int RETARDO = 100;

    // Arreglos que guardan la posición X y Y de cada parte de la serpiente
    private final int[] serpienteX = new int[UNIDADES_JUEGO];
    private final int[] serpienteY = new int[UNIDADES_JUEGO];

    // Longitud inicial de la serpiente
    private int longitudSerpiente = 6;

    // Dirección inicial (R = Right = Derecha)
    private char direccion = 'R';

    // Indica si el juego está en ejecución
    private boolean enEjecucion = false;

    // Temporizador principal del juego (controla el movimiento)
    private Timer temporizador;

    // Temporizador usado para el conteo regresivo
    private Timer temporizadorConteo;

    // Posición de la manzana
    private int manzanaX;
    private int manzanaY;

    // ================= VARIABLES DE ESTADO =================

    // Indica si estamos en la pantalla inicial
    private boolean pantallaInicio = true;

    // Indica si está activo el conteo regresivo
    private boolean enConteo = false;

    // Indica si el juego terminó
    private boolean finJuego = false;

    // Número que se muestra en el conteo regresivo
    private int conteo = 3;

    // ================= SISTEMA DE PUNTUACIÓN =================

    // Puntos actuales
    private int puntuacion = 0;

    // Récord máximo alcanzado
    private int recordMaximo = 0;

    /**
     * ================= CONSTRUCTOR =================
     * Se ejecuta cuando se crea el objeto del juego.
     * Configura el tamaño, color y eventos del panel.
     */
    public JuegoSerpiente() {

        setPreferredSize(new Dimension(ANCHO_PANTALLA, ALTO_PANTALLA));
        setBackground(Color.black);
        setFocusable(true); // Permite detectar teclado
        addKeyListener(this); // Escucha eventos de teclado
    }

    /**
     * ================= MÉTODO PRINCIPAL DE DIBUJO =================
     * Se ejecuta cada vez que se llama repaint().
     * Aquí se dibuja todo lo que aparece en pantalla.
     */
    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g); // Limpia la pantalla antes de dibujar

        FontMetrics metrics;

        // ---------- PANTALLA DE INICIO ----------
        if (pantallaInicio) {

            g.setColor(Color.green);
            g.setFont(new Font("Arial", Font.BOLD, 60));
            metrics = g.getFontMetrics();

            // Título centrado
            g.drawString("Juego de la Serpiente",
                    (ANCHO_PANTALLA - metrics.stringWidth("Juego de la Serpiente")) / 2,
                    ALTO_PANTALLA / 3);

            g.setColor(Color.red);
            g.setFont(new Font("Arial", Font.PLAIN, 30));
            metrics = g.getFontMetrics();

            // Instrucción para comenzar
            g.drawString("Presiona ENTER para comenzar",
                    (ANCHO_PANTALLA - metrics.stringWidth("Presiona ENTER para comenzar")) / 2,
                    ALTO_PANTALLA / 2);
        }

        // ---------- CONTEO REGRESIVO ----------
        else if (enConteo) {

            g.setColor(Color.yellow);
            g.setFont(new Font("Arial", Font.BOLD, 120));

            String texto = String.valueOf(conteo);
            metrics = g.getFontMetrics();

            // Número centrado en pantalla
            g.drawString(texto,
                    (ANCHO_PANTALLA - metrics.stringWidth(texto)) / 2,
                    ALTO_PANTALLA / 2);
        }

        // ---------- JUEGO EN EJECUCIÓN ----------
        else if (enEjecucion) {

            dibujarSerpiente(g);
            dibujarManzana(g);
            dibujarPuntuacion(g);
        }

        // ---------- FIN DEL JUEGO ----------
        else if (finJuego) {

            g.setColor(Color.red);
            g.setFont(new Font("Arial", Font.BOLD, 70));
            String fin = "Fin del Juego";
            metrics = g.getFontMetrics();

            g.drawString(fin,
                    (ANCHO_PANTALLA - metrics.stringWidth(fin)) / 2,
                    ALTO_PANTALLA / 2);

            g.setFont(new Font("Arial", Font.PLAIN, 30));
            String reinicio = "Presiona ENTER para reiniciar";
            metrics = g.getFontMetrics();

            g.drawString(reinicio,
                    (ANCHO_PANTALLA - metrics.stringWidth(reinicio)) / 2,
                    ALTO_PANTALLA / 2 + 60);
        }
    }

    /**
     * Dibuja cada parte de la serpiente.
     * La cabeza es verde brillante y el cuerpo verde más oscuro.
     */
    private void dibujarSerpiente(Graphics g) {

        for (int i = 0; i < longitudSerpiente; i++) {

            if (i == 0) g.setColor(Color.green); // Cabeza
            else g.setColor(new Color(45, 180, 0)); // Cuerpo

            g.fillRect(serpienteX[i], serpienteY[i],
                    TAMANO_UNIDAD, TAMANO_UNIDAD);
        }
    }

    /**
     * Dibuja la manzana como un círculo rojo.
     */
    private void dibujarManzana(Graphics g) {

        g.setColor(Color.red);
        g.fillOval(manzanaX, manzanaY,
                TAMANO_UNIDAD, TAMANO_UNIDAD);
    }

    /**
     * Muestra la puntuación actual y el récord.
     */
    private void dibujarPuntuacion(Graphics g) {

        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.BOLD, 20));

        g.drawString("Puntuación: " + puntuacion, 20, 30);
        g.drawString("Récord: " + recordMaximo, 20, 55);
    }

    /**
     * Inicia el conteo regresivo antes de comenzar el juego.
     */
    private void iniciarConteo() {

        enConteo = true;
        conteo = 3;
        repaint();

        temporizadorConteo = new Timer(1000, e -> {

            conteo--; // Reduce el número cada segundo

            if (conteo == 0) {
                temporizadorConteo.stop(); // Detiene el conteo
                enConteo = false;
                iniciarJuego(); // Comienza el juego
            }

            repaint();
        });

        temporizadorConteo.setInitialDelay(1000);
        temporizadorConteo.start();
    }

    /**
     * Inicia el juego:
     * - Inicializa la serpiente
     * - Genera una manzana
     * - Activa el temporizador principal
     */
    private void iniciarJuego() {

        inicializarSerpiente();
        nuevaManzana();
        puntuacion = 0;
        enEjecucion = true;
        finJuego = false;

        temporizador = new Timer(RETARDO, this);
        temporizador.start();
    }

    /**
     * Reinicia el juego volviendo a la pantalla inicial.
     */
    private void reiniciarJuego() {

        pantallaInicio = true;
        finJuego = false;
        enEjecucion = false;
        repaint();
    }

    /**
     * Coloca la serpiente en el centro de la pantalla.
     */
    private void inicializarSerpiente() {

        longitudSerpiente = 6;
        direccion = 'R';

        serpienteX[0] = ANCHO_PANTALLA / 2;
        serpienteY[0] = ALTO_PANTALLA / 2;

        // Coloca el resto del cuerpo detrás de la cabeza
        for (int i = 1; i < longitudSerpiente; i++) {
            serpienteX[i] = serpienteX[0] - i * TAMANO_UNIDAD;
            serpienteY[i] = serpienteY[0];
        }
    }

    /**
     * Genera una nueva manzana en una posición aleatoria.
     */
    private void nuevaManzana() {

        manzanaX = new Random().nextInt(ANCHO_PANTALLA / TAMANO_UNIDAD) * TAMANO_UNIDAD;
        manzanaY = new Random().nextInt(ALTO_PANTALLA / TAMANO_UNIDAD) * TAMANO_UNIDAD;
    }

    /**
     * Mueve la serpiente:
     * - Cada parte toma la posición de la anterior
     * - La cabeza se mueve según la dirección actual
     */
    private void mover() {

        for (int i = longitudSerpiente; i > 0; i--) {
            serpienteX[i] = serpienteX[i - 1];
            serpienteY[i] = serpienteY[i - 1];
        }

        switch (direccion) {
            case 'U': serpienteY[0] -= TAMANO_UNIDAD; break;
            case 'D': serpienteY[0] += TAMANO_UNIDAD; break;
            case 'L': serpienteX[0] -= TAMANO_UNIDAD; break;
            case 'R': serpienteX[0] += TAMANO_UNIDAD; break;
        }
    }

    /**
     * Verifica si la serpiente comió la manzana.
     * Si la come:
     * - Crece
     * - Suma puntos
     * - Actualiza récord si es necesario
     */
    private void verificarManzana() {

        if (serpienteX[0] == manzanaX &&
                serpienteY[0] == manzanaY) {

            longitudSerpiente++;
            puntuacion += 10;

            if (puntuacion > recordMaximo) {
                recordMaximo = puntuacion;
            }

            nuevaManzana();
        }
    }

    /**
     * Verifica si la serpiente chocó:
     * - Contra su propio cuerpo
     * - Contra los bordes de la pantalla
     */
    private void verificarColisiones() {

        for (int i = longitudSerpiente; i > 0; i--) {

            if (serpienteX[0] == serpienteX[i] &&
                    serpienteY[0] == serpienteY[i]) {

                enEjecucion = false;
            }
        }

        if (serpienteX[0] < 0 ||
                serpienteX[0] >= ANCHO_PANTALLA ||
                serpienteY[0] < 0 ||
                serpienteY[0] >= ALTO_PANTALLA) {

            enEjecucion = false;
        }

        if (!enEjecucion && temporizador != null) {
            temporizador.stop();
            finJuego = true;
        }
    }

    /**
     * Se ejecuta automáticamente cada vez que el temporizador se activa.
     * Es el "corazón" del juego.
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        if (enEjecucion) {
            mover();
            verificarManzana();
            verificarColisiones();
        }

        repaint();
    }

    /**
     * Detecta las teclas presionadas.
     */
    @Override
    public void keyPressed(KeyEvent e) {

        // Comenzar juego desde pantalla inicial
        if (pantallaInicio && e.getKeyCode() == KeyEvent.VK_ENTER) {
            pantallaInicio = false;
            iniciarConteo();
        }

        // Reiniciar después de perder
        if (finJuego && e.getKeyCode() == KeyEvent.VK_ENTER) {
            reiniciarJuego();
        }

        // Control de movimiento
        if (enEjecucion) {

            switch (e.getKeyCode()) {

                case KeyEvent.VK_LEFT:
                    if (direccion != 'R') direccion = 'L';
                    break;

                case KeyEvent.VK_RIGHT:
                    if (direccion != 'L') direccion = 'R';
                    break;

                case KeyEvent.VK_UP:
                    if (direccion != 'D') direccion = 'U';
                    break;

                case KeyEvent.VK_DOWN:
                    if (direccion != 'U') direccion = 'D';
                    break;
            }
        }
    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}

    /**
     * Método principal.
     * Crea la ventana y ejecuta el juego.
     */
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            JFrame ventana = new JFrame("Juego de la Serpiente");

            ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            ventana.setResizable(false);
            ventana.add(new JuegoSerpiente());
            ventana.pack();
            ventana.setLocationRelativeTo(null);
            ventana.setVisible(true);
        });
    }
}