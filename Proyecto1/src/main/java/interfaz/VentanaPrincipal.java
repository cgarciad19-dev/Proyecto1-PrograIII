/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaz;

/**
 *
 * @author Morales
 */
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import logica.*;

import java.util.*;

public class VentanaPrincipal extends Application {

    // Componentes principales
    private TextField campoExpresion = new TextField();
    private TextArea areaResultados  = new TextArea();
    private Button   btnProcesar     = new Button("Procesar");

    // Objetos de la lógica
    private Parser           parser    = new Parser();
    private GestorVariables  gestor    = new GestorVariables();
    private EvaluadorPostfijo evaluador = new EvaluadorPostfijo();

    @Override
    public void start(Stage stage) {
        stage.setTitle("Árbol de Expresiones");

        // --- Sección superior: entrada de expresión ---
        Label lblExpresion = new Label("Expresión matemática:");
        campoExpresion.setPromptText("Ej: a + b - (c - b) + e");
        campoExpresion.setPrefWidth(400);

        btnProcesar.setOnAction(e -> procesarExpresion(stage));
        btnProcesar.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px;");

        HBox filaSuperior = new HBox(10, campoExpresion, btnProcesar);
        filaSuperior.setAlignment(Pos.CENTER_LEFT);

        // --- Área de resultados ---
        areaResultados.setEditable(false);
        areaResultados.setPrefHeight(300);
        areaResultados.setStyle("-fx-font-family: monospace; -fx-font-size: 13px;");

        // --- Layout general ---
        VBox layout = new VBox(15, lblExpresion, filaSuperior, new Label("Resultados:"), areaResultados);
        layout.setPadding(new Insets(20));

        stage.setScene(new Scene(layout, 620, 450));
        stage.show();
    }

    private void procesarExpresion(Stage stage) {
        String expresion = campoExpresion.getText().trim();

        // Validar que no esté vacía
        if (expresion.isEmpty()) {
            mostrarAlerta("Campo vacío", "Por favor ingresa una expresión.");
            return;
        }

        // Validar caracteres permitidos
        if (!parser.esValida(expresion)) {
            mostrarAlerta("Expresión inválida", "La expresión contiene caracteres no permitidos.\n"
                    + "Solo se permiten: letras, números, +  -  *  /  ^  ( )");
            return;
        }

        // Detectar variables
        Set<String> variables = gestor.detectarVariables(expresion);

        if (variables.isEmpty()) {
            // No hay variables, evaluar directo
            construirYMostrar(expresion, new HashMap<>(), stage);
        } else {
            // Hay variables, abrir ventana para pedir valores
            VentanaVariables ventanaVars = new VentanaVariables(variables, valores -> {
                construirYMostrar(expresion, valores, stage);
            });
            ventanaVars.mostrar();
        }
    }

    // Construye el árbol, muestra recorridos y evalúa
    void construirYMostrar(String expresion, Map<String, Double> valores, Stage stage) {
        // Construir árbol
        Nodo raiz = parser.construir(expresion);
        ArbolExpresion arbol = new ArbolExpresion(raiz);

        // Obtener recorridos
        String inorden   = arbol.inorden();
        String preorden  = arbol.preorden();
        String postorden = arbol.postorden();

        // Evaluar resultado
        double resultado = evaluador.evaluar(postorden, valores);
        List<String> historial = evaluador.getHistorialPila();

        // Mostrar en el área de texto
        StringBuilder sb = new StringBuilder();
        sb.append("=== RECORRIDOS DEL ÁRBOL ===\n");
        sb.append("Inorden   (infija):   ").append(inorden).append("\n");
        sb.append("Preorden  (prefija):  ").append(preorden).append("\n");
        sb.append("Postorden (postfija): ").append(postorden).append("\n");
        sb.append("\n=== COMPORTAMIENTO DE LA PILA ===\n");
        for (String paso : historial) {
            sb.append(paso).append("\n");
        }
        sb.append("\n=== RESULTADO FINAL: ").append(resultado).append(" ===");

        areaResultados.setText(sb.toString());

        // Abrir ventana con el árbol gráfico
        VentanaArbol ventanaArbol = new VentanaArbol(raiz);
        ventanaArbol.mostrar();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}