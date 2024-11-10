package aed;

import java.util.ArrayList;
import java.util.Random;

public class TestHeapSincronizacion {

    public static void main(String[] args) {
        testHeapSincronizacion();
    }

    public static void testHeapSincronizacion() {
        System.out.println("Inicio del test de sincronización entre TimeHeap y HeapRedito.");

        // Crear 100 traslados con datos aleatorios
        ArrayList<Traslado> trasladosList = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            trasladosList.add(new Traslado(i, random.nextInt(10), random.nextInt(10), random.nextInt(1000), random.nextInt(1000)));
        }

        Traslado[] traslados = new Traslado[trasladosList.size()];
        trasladosList.toArray(traslados);

        // Crear los dos heaps
        TimeHeap heapTime = new TimeHeap();
        HeapRedito heapRedito = new HeapRedito();

        // Llenar ambos heaps con los mismos traslados
        heapTime.ArrayToHeap(traslados);
        heapRedito.ArrayToHeap(traslados);

        // Verificar que los índices de Time y Redito estén sincronizados
        for (int i = 0; i < traslados.length; i++) {
            Traslado traslado = traslados[i];

            // Verificar índice en TimeHeap
            int indiceTime = traslado.indiceTime();
            assert indiceTime == i : "Error: El índiceTime en el traslado con id " + traslado.id() + " debería ser " + i + ", pero es " + indiceTime;

            // Verificar índice en HeapRedito
            int indiceRedito = traslado.indiceRedito();
            assert indiceRedito == i : "Error: El índiceRedito en el traslado con id " + traslado.id() + " debería ser " + i + ", pero es " + indiceRedito;
        }

        System.out.println("Fin del test de sincronización entre TimeHeap y HeapRedito.");
    }
}
