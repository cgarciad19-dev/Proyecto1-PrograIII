package logica;

import java.util.LinkedHashSet;
import java.util.Set;

public class GestorVariables {

    // Recorre la expresión y devuelve las variables únicas encontradas (letras)
    // Usa LinkedHashSet para mantener el orden en que aparecen
    public Set<String> detectarVariables(String expresion) {
        Set<String> variables = new LinkedHashSet<>();

        for (char c : expresion.toCharArray()) {
            if (Character.isLetter(c)) {
                variables.add(String.valueOf(c));
            }
        }

        return variables;
    }
}