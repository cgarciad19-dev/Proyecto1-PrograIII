package logica;

import java.util.ArrayList;
import java.util.List;

public class ArbolExpresion {

    private Nodo raiz;

    public ArbolExpresion(Nodo raiz) {
        this.raiz = raiz;
    }

    public Nodo getRaiz() {
        return raiz;
    }

    // --- INORDEN: izquierdo → raíz → derecho  (da la expresión original) ---
    public String inorden() {
        List<String> resultado = new ArrayList<>();
        inordenRec(raiz, resultado);
        return String.join(" ", resultado);
    }

    private void inordenRec(Nodo nodo, List<String> resultado) {
        if (nodo == null) return;
        inordenRec(nodo.izquierdo, resultado);
        resultado.add(nodo.valor);
        inordenRec(nodo.derecho, resultado);
    }

    // --- PREORDEN: raíz → izquierdo → derecho  (notación prefija) ---
    public String preorden() {
        List<String> resultado = new ArrayList<>();
        preordenRec(raiz, resultado);
        return String.join(" ", resultado);
    }

    private void preordenRec(Nodo nodo, List<String> resultado) {
        if (nodo == null) return;
        resultado.add(nodo.valor);
        preordenRec(nodo.izquierdo, resultado);
        preordenRec(nodo.derecho, resultado);
    }

    // --- POSTORDEN: izquierdo → derecho → raíz  (notación polaca postfija) ---
    public String postorden() {
        List<String> resultado = new ArrayList<>();
        postordenRec(raiz, resultado);
        return String.join(" ", resultado);
    }

    private void postordenRec(Nodo nodo, List<String> resultado) {
        if (nodo == null) return;
        postordenRec(nodo.izquierdo, resultado);
        postordenRec(nodo.derecho, resultado);
        resultado.add(nodo.valor);
    }
}