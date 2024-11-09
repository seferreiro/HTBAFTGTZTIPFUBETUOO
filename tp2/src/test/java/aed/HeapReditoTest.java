package aed;

import java.util.Random;

public class HeapReditoTest {
    
    // Prueba con 100 elementos
    public static void testHeapWith100Elements() {
        System.out.println("\nTest: Heap con 100 elementos");

        Random random = new Random();
        Traslado[] traslados = new Traslado[100];

        // Crear 100 traslados con valores aleatorios
        for (int i = 0; i < traslados.length; i++) {
            int id = i + 1;
            int origen = random.nextInt(1000);
            int destino = random.nextInt(1000);
            int gananciaNeta = random.nextInt(10000);  // Ganancia aleatoria
            int timestamp = 1627545600 + random.nextInt(1000000); // Timestamp aleatorio
            traslados[i] = new Traslado(id, origen, destino, gananciaNeta, timestamp);
        }

        HeapRedito heap = new HeapRedito();
        heap.ArrayToHeap(traslados); // Crear el heap

        // Verificar el orden del heap
        System.out.println("Heap creado con 100 elementos.");
        System.out.println("El máximo (elemento en la raíz) tiene ID=" + heap.maximo().id() +
                           " con GananciaNeta=" + heap.maximo().gananciaNeta());

        // Desencolar todos los elementos
        int count = 0;
        while (heap.heap().longitud() > 0) {
            Traslado max = heap.desencolarMax();
            count++;
            if (count % 10 == 0) { // Solo imprimir cada 10 elementos para evitar sobrecargar la salida
                System.out.println("Elemento desencolado ID=" + max.id() + ", GananciaNeta=" + max.gananciaNeta());
            }
        }

        System.out.println("Desencolados todos los elementos (100).");
    }

    public static void main(String[] args) {
        testHeapWith100Elements();
    }
}
