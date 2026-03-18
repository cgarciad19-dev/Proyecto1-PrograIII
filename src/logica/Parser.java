package logica;

import java.util.Stack;

public class Parser {

    // Devuelve la raíz del árbol construido a partir de la expresión
    public Nodo construir(String expresion) {
        // Primero convertimos la expresión infija a postfija
        String postfija = aPostfija(expresion);
        // Luego construimos el árbol desde la postfija
        return construirDesdePostfija(postfija);
    }

    // --- Convierte expresión infija a postfija (Shunting Yard) ---
    private String aPostfija(String expresion) {
        StringBuilder salida = new StringBuilder();
        Stack<Character> pilaOp = new Stack<>();

        for (int i = 0; i < expresion.length(); i++) {
            char c = expresion.charAt(i);

            if (c == ' ') continue; // ignorar espacios

            if (esOperando(c)) {
                // Si es letra o número, va directo a la salida con espacio separador
                salida.append(c).append(' ');
            } else if (c == '(') {
                pilaOp.push(c);
            } else if (c == ')') {
                // Sacar operadores hasta encontrar el paréntesis abierto
                while (!pilaOp.isEmpty() && pilaOp.peek() != '(') {
                    salida.append(pilaOp.pop()).append(' ');
                }
                pilaOp.pop(); // quitar el '('
            } else if (esOperador(c)) {
                // Sacar operadores con mayor o igual precedencia
                while (!pilaOp.isEmpty() && precedencia(pilaOp.peek()) >= precedencia(c)) {
                    salida.append(pilaOp.pop()).append(' ');
                }
                pilaOp.push(c);
            }
        }

        // Vaciar lo que quede en la pila
        while (!pilaOp.isEmpty()) {
            salida.append(pilaOp.pop()).append(' ');
        }

        return salida.toString().trim();
    }

    // --- Construye el árbol desde una expresión postfija ---
    private Nodo construirDesdePostfija(String postfija) {
        Stack<Nodo> pila = new Stack<>();
        String[] tokens = postfija.split(" ");

        for (String token : tokens) {
            if (token.isEmpty()) continue;

            char c = token.charAt(0);

            if (esOperador(c)) {
                // El operador toma los dos últimos nodos como hijos
                Nodo nodo = new Nodo(token);
                nodo.derecho = pila.pop();
                nodo.izquierdo = pila.pop();
                pila.push(nodo);
            } else {
                // Es un operando, simplemente lo apilamos
                pila.push(new Nodo(token));
            }
        }

        return pila.pop(); // la raíz del árbol
    }

    // --- Helpers ---

    private boolean esOperando(char c) {
        return Character.isLetterOrDigit(c);
    }

    private boolean esOperador(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '^';
    }

    // Mayor número = mayor precedencia
    private int precedencia(char op) {
        switch (op) {
            case '+': case '-': return 1;
            case '*': case '/': return 2;
            case '^':           return 3;
            default:            return 0;
        }
    }

    // Valida que la expresión solo tenga caracteres permitidos
    public boolean esValida(String expresion) {
        for (char c : expresion.toCharArray()) {
            if (!Character.isLetterOrDigit(c) && !esOperador(c) && c != '(' && c != ')' && c != ' ') {
                return false;
            }
        }
        return true;
    }
}