package aed;

import java.util.Random;

public class TestHeapCiudad {
    public static void main(String[] args) {
        int cantidadCiudades = 100;
        Ciudad[] ciudades = new Ciudad[cantidadCiudades];
        Random random = new Random();

        // Paso 1: Inicializar ciudades con valores aleatorios de ganancia y pérdida
        for (int i = 0; i < cantidadCiudades; i++) {
            int ganancia = random.nextInt(100);  // Ganancia aleatoria entre 0 y 99
            int perdida = random.nextInt(50);    // Pérdida aleatoria entre 0 y 49
            int balance = ganancia - perdida;
            ciudades[i] = new Ciudad(i, ganancia, perdida, balance);
        }

        // Paso 2: Crear el heap y convertir el arreglo en un heap
        HeapCiudad heap = new HeapCiudad(cantidadCiudades);
        heap.ArrayToHeap(ciudades);

        // Imprimir estado inicial del heap
        System.out.println("Estado inicial del heap:");
        for (Ciudad ciudad : heap.heap()) {
            System.out.println("Ciudad ID: " + ciudad.id() + ", Balance: " + ciudad.balance() + ", Indice: " + ciudad.indice());
        }

        // Paso 3: Actualizar balances aleatoriamente y reacomodar el heap
        System.out.println("\nActualizando balances y reacomodando el heap...");

        for (int j = 0; j < 50; j++) { // Realizar 50 actualizaciones aleatorias
            int idx = random.nextInt(cantidadCiudades); // Seleccionar una ciudad aleatoria

            // Modificar la ganancia o pérdida de la ciudad seleccionada
            if (random.nextBoolean()) {
                int incrementoGanancia = random.nextInt(20);  // Incremento de ganancia aleatorio entre 0 y 19
                ciudades[idx].aumentarGanancia(incrementoGanancia);
            } else {
                int incrementoPerdida = random.nextInt(20);  // Incremento de pérdida aleatorio entre 0 y 19
                ciudades[idx].aumentarPerdida(incrementoPerdida);
            }

            // Actualizar balance y reacomodar en el heap
            ciudades[idx].actualizarBalance();
            heap.reacomodar(ciudades[idx].indice());
        }

        // Paso 4: Imprimir el estado final del heap después de reacomodar
        System.out.println("\nEstado final del heap después de reacomodar:");
        for (Ciudad ciudad : heap.heap()) {
            System.out.println("Ciudad ID: " + ciudad.id() + ", Balance: " + ciudad.balance() + ", Indice: " + ciudad.indice());
        }

        // Validación: Verificar que los índices coinciden con la posición en el heap
        System.out.println("\nValidación de índices:");
        boolean indicesCorrectos = true;
        for (int i = 0; i < heap.heap().length; i++) {
            if (heap.heap()[i].indice() != i) {
                indicesCorrectos = false;
                System.out.println("Error: Ciudad ID " + heap.heap()[i].id() + " tiene índice " + heap.heap()[i].indice() + " pero está en posición " + i);
            }
        }
        if (indicesCorrectos) {
            System.out.println("Todos los índices son correctos.");
        } else {
            System.out.println("Algunos índices son incorrectos.");
        }
    }
}

