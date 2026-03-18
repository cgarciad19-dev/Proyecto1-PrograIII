package logica;

public class Nodo {

    public String valor;      // puede ser un operador (+, -, *, /) o un operando (a, b, 3...)
    public Nodo izquierdo;
    public Nodo derecho;

    public Nodo(String valor) {
        this.valor = valor;
        this.izquierdo = null;
        this.derecho = null;
    }
}