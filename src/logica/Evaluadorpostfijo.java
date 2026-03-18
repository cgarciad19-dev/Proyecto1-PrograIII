package logica;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class EvaluadorPostfijo {

    // Guarda cada paso de la pila para mostrarlo en la interfaz
    private List<String> historialPila = new ArrayList<>();

    public List<String> getHistorialPila() {
        return historialPila;
    }

    // Evalúa la expresión postfija usando los valores de las variables
    // variables es un mapa como: {"a" -> 2.0, "b" -> 3.0, ...}
    public double evaluar(String postfija, Map<String, Double> variables) {
        historialPila.clear();
        Stack<Double> pila = new Stack<>();
        String[] tokens = postfija.split(" ");

        for (String token : tokens) {
            if (token.isEmpty()) continue;

            if (esOperador(token.charAt(0)) && token.length() == 1) {
                // Sacar dos operandos y aplicar la operación
                double b = pila.pop();
                double a = pila.pop();
                double resultado = operar(a, b, token.charAt(0));
                pila.push(resultado);

                // Guardar el estado actual de la pila como texto
                historialPila.add("Operación " + a + " " + token + " " + b + " = " + resultado
                        + "  →  Pila: " + pila.toString());

            } else {
                // Es un operando: puede ser variable o número
                double valor;
                if (variables.containsKey(token)) {
                    valor = variables.get(token);
                } else {
                    valor = Double.parseDouble(token);
                }
                pila.push(valor);
                historialPila.add("Push " + token + " (" + valor + ")  →  Pila: " + pila.toString());
            }
        }

        return pila.pop();
    }

    private double operar(double a, double b, char op) {
        switch (op) {
            case '+': return a + b;
            case '-': return a - b;
            case '*': return a * b;
            case '/': return a / b;
            case '^': return Math.pow(a, b);
            default: throw new IllegalArgumentException("Operador desconocido: " + op);
        }
    }

    private boolean esOperador(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '^';
    }
}