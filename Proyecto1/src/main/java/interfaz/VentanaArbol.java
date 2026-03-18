/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaz;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import logica.Nodo;

public class VentanaArbol {

    private Nodo raiz;
    private static final int RADIO_NODO  = 20;  // radio del círculo de cada nodo
    private static final int ALTO_NIVEL  = 70;  // separación vertical entre niveles

    public VentanaArbol(Nodo raiz) {
        this.raiz = raiz;
    }

    public void mostrar() {
        Stage stage = new Stage();
        stage.setTitle("Árbol de Expresión");

        Canvas canvas = new Canvas(800, 500);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, 800, 500);

        // Empezar a dibujar desde la raíz, centrado en x=400, y=40
        dibujarNodo(gc, raiz, 400, 40, 180);

        StackPane root = new StackPane(canvas);
        stage.setScene(new Scene(root, 800, 500));
        stage.show();
    }

    // Dibuja el nodo actual y luego recursivamente sus hijos
    // offsetX controla cuánto se separan horizontalmente los hijos (se reduce por nivel)
    private void dibujarNodo(GraphicsContext gc, Nodo nodo, double x, double y, double offsetX) {
        if (nodo == null) return;

        // Dibujar líneas hacia los hijos antes de dibujar el nodo (para que queden detrás)
        if (nodo.izquierdo != null) {
            gc.setStroke(Color.GRAY);
            gc.setLineWidth(1.5);
            gc.strokeLine(x, y, x - offsetX, y + ALTO_NIVEL);
            dibujarNodo(gc, nodo.izquierdo, x - offsetX, y + ALTO_NIVEL, offsetX / 2);
        }

        if (nodo.derecho != null) {
            gc.setStroke(Color.GRAY);
            gc.setLineWidth(1.5);
            gc.strokeLine(x, y, x + offsetX, y + ALTO_NIVEL);
            dibujarNodo(gc, nodo.derecho, x + offsetX, y + ALTO_NIVEL, offsetX / 2);
        }

        // Color del nodo: azul para operadores, verde para operandos
        boolean esOperador = "+-*/^".contains(nodo.valor);
        gc.setFill(esOperador ? Color.web("#1565C0") : Color.web("#2E7D32"));
        gc.fillOval(x - RADIO_NODO, y - RADIO_NODO, RADIO_NODO * 2, RADIO_NODO * 2);

        // Borde del círculo
        gc.setStroke(Color.web("#333333"));
        gc.setLineWidth(1);
        gc.strokeOval(x - RADIO_NODO, y - RADIO_NODO, RADIO_NODO * 2, RADIO_NODO * 2);

        // Texto del nodo
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Arial", 14));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText(nodo.valor, x, y + 5);
    }
}
