# 🐍 Juego de la Serpiente en Java

Un clásico Juego de la Serpiente desarrollado en Java utilizando Java Swing para la interfaz gráfica.

Este proyecto fue creado con fines educativos y demuestra cómo funciona un juego básico con interfaz gráfica, control por teclado, temporizadores y sistema de puntuación.

## 🎮 ¿Cómo funciona el juego?

El jugador controla una serpiente que debe comer manzanas para crecer y ganar puntos.

El juego incluye:

✅ Pantalla de inicio

✅ Conteo regresivo antes de empezar

✅ Sistema de puntuación

✅ Récord máximo

✅ Pantalla de fin del juego

✅ Reinicio con tecla ENTER

Si la serpiente:

Choca contra los bordes ❌

Choca contra su propio cuerpo ❌

El juego termina.

## 🖥 Tecnologías utilizadas
- Java

- Java Swing (para la interfaz gráfica)

- Eventos de teclado (KeyListener)

- Temporizadores (Timer)

- Dibujado gráfico con Graphics

## 🧠 Explicación sencilla del código

Aunque no sepas programar, aquí te explico qué hace cada parte importante:

### 1️⃣ Configuración del juego

Se definen:

Tamaño de la ventana (800x600)

Tamaño de cada bloque (25px)

Velocidad del juego

Variables para la serpiente y la manzana

### 2️⃣ Pantallas del juego

El juego tiene 4 estados principales:

1. 🟢 Pantalla de inicio

2. ⏳ Conteo regresivo

3. 🎮 Juego en ejecución

4. 🔴 Fin del juego

El método paintComponent() se encarga de dibujar lo que corresponde según el estado actual.

### 3️⃣ Movimiento de la serpiente

La serpiente:

- Guarda la posición de cada parte en arreglos (serpienteX y serpienteY)

- Cada parte sigue a la anterior

- La cabeza se mueve según la tecla presionada

### 4️⃣ Sistema de puntuación

Cada vez que la serpiente come una manzana:

- Crece en tamaño

- Suma 10 puntos

- Se actualiza el récord si es necesario

### 5️⃣ Colisiones

El juego detecta si:

- La cabeza toca el cuerpo

- La cabeza sale de la pantalla

Si ocurre alguna de estas condiciones → el juego termina.

### 6️⃣ Temporizador (el corazón del juego)

El Timer ejecuta constantemente:

- Movimiento

- Verificación de manzana

- Verificación de colisiones

- Redibujado de pantalla

Esto crea la animación del juego.

## ▶️ Cómo ejecutar el juego
Requisitos

- Tener instalado Java JDK 8 o superior

Pasos

1. Guarda el archivo JuegoSerpiente.java

2. Abre una terminal en la carpeta del archivo

3. Compila:
```bash
javac JuegoSerpiente.java
```
2. Ejecuta:
```bash
java com.java.games.JuegoSerpiente
```

#### O si usas tu IDE, solo presiona "RUN"

## 📂 Estructura del Proyecto

juegosjava/

│

├── com/

│   └── java/

│       └── games/

│           └── JuegoSerpiente.java

│

└── README.md




## 👨‍💻 Autor

[Rodrigo Vivar](https://github.com/vivarrodrigo)
