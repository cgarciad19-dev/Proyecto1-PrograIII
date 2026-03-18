package interfaz;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.*;
import java.util.function.Consumer;

public class VentanaVariables {

    private Set<String> variables;
    private Consumer<Map<String, Double>> alTerminar; // callback cuando el usuario confirma

    // Map de variable → campo de texto para leer los valores
    private Map<String, TextField> campos = new LinkedHashMap<>();

    public VentanaVariables(Set<String> variables, Consumer<Map<String, Double>> alTerminar) {
        this.variables   = variables;
        this.alTerminar  = alTerminar;
    }

    public void mostrar() {
        Stage stage = new Stage();
        stage.setTitle("Valores de variables");
        stage.initModality(Modality.APPLICATION_MODAL); // bloquea la ventana principal

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));

        Label instruccion = new Label("Ingresa el valor de cada variable:");
        instruccion.setStyle("-fx-font-weight: bold;");
        layout.getChildren().add(instruccion);

        // Crear una fila por cada variable detectada
        for (String var : variables) {
            TextField campo = new TextField();
            campo.setPromptText("Número");
            campos.put(var, campo);

            Label lbl = new Label(var + " =");
            lbl.setMinWidth(30);

            HBox fila = new HBox(10, lbl, campo);
            fila.setAlignment(Pos.CENTER_LEFT);
            layout.getChildren().add(fila);
        }

        Button btnOk = new Button("Calcular");
        btnOk.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
        btnOk.setOnAction(e -> confirmar(stage));

        layout.getChildren().add(btnOk);

        stage.setScene(new Scene(layout, 280, 80 + variables.size() * 45));
        stage.showAndWait();
    }

    private void confirmar(Stage stage) {
        Map<String, Double> valores = new LinkedHashMap<>();

        for (Map.Entry<String, TextField> entry : campos.entrySet()) {
            String texto = entry.getValue().getText().trim();

            // Validar que cada campo tenga un número válido
            try {
                double valor = Double.parseDouble(texto);
                valores.put(entry.getKey(), valor);
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Valor inválido");
                alert.setHeaderText(null);
                alert.setContentText("El valor de '" + entry.getKey() + "' no es un número válido.");
                alert.showAndWait();
                return; // no cierra la ventana, deja corregir
            }
        }

        stage.close();
        alTerminar.accept(valores); // manda los valores de vuelta a VentanaPrincipal
    }
}
