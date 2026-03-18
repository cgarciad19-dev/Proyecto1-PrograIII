# Árbol de Expresiones Matemáticas - Proyecto 1 Programación III

Aplicación desarrollada en Java con interfaz gráfica JavaFX que construye un árbol binario a partir de una expresión matemática, muestra sus recorridos y evalúa el resultado usando notación polaca postfija.

---

## ¿Qué hace la aplicación?

- Recibe una expresión matemática con `+` `-` `*` `/` `^` y paréntesis
- Valida que la expresión solo tenga caracteres permitidos
- Si la expresión tiene variables (letras), solicita su valor
- Construye un árbol de expresión y muestra sus 3 recorridos:
  - **Inorden** (notación infija)
  - **Preorden** (notación prefija)
  - **Postorden** (notación polaca postfija)
- Muestra el comportamiento de la pila al evaluar la expresión
- Dibuja el árbol de forma gráfica

---

## Requisitos para ejecutar

- Tener instalado **Java 21** o superior
- Descargar el archivo **JavaFX 21 SDK** desde [gluonhq.com/products/javafx](https://gluonhq.com/products/javafx)
- Extraer el SDK en una carpeta, por ejemplo: `C:\javafx-sdk-21`

---

## ¿Cómo ejecutar el JAR?

### Windows

Abre la terminal (cmd) en la carpeta donde está el `.jar` y ejecuta:

```
java --module-path "C:\javafx-sdk-21\lib" --add-modules javafx.controls,javafx.fxml -jar ArbolExpresiones.jar
```

### Mac / Linux

```
java --module-path "/ruta/a/javafx-sdk-21/lib" --add-modules javafx.controls,javafx.fxml -jar ArbolExpresiones.jar
```

> **Nota:** Reemplaza `C:\javafx-sdk-21\lib` con la ruta donde extrajiste el JavaFX SDK en tu computadora.

---

## Ejemplo de uso

1. Ingresa una expresión como: `a + b - (c - b) + e`
2. La aplicación detecta las variables y pide sus valores:
   - a = 2, b = 3, c = 1, e = 4
3. Muestra los recorridos del árbol y el resultado final: **11**

---

## Estructura del proyecto

```
src/
├── logica/
│   ├── Nodo.java
│   ├── Parser.java
│   ├── ArbolExpresion.java
│   ├── EvaluadorPostfijo.java
│   └── GestorVariables.java
└── interfaz/
    ├── VentanaPrincipal.java
    ├── VentanaVariables.java
    └── VentanaArbol.java
```

---

## Desarrollado por

- **Cristian Roméo García** — Lógica del árbol (IntelliJ IDEA)
- **Fidian Bianchi Morales** — Interfaz gráfica (Apache NetBeans)
