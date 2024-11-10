package aed;

import java.util.ArrayList;
import java.util.Random;

public class TestHeapTime {

    public static void main(String[] args) {
        testHeapTimeBasico();
        testHeapTimeEncoladoDesencolado();
        testHeapTimeIndicesCorrectos();
        testHeapTimeConDatosExtremadamenteGrandes();
    }

    public static void testHeapTimeBasico() {
        System.out.println("Inicio del test básico de TimeHeap.");
        TimeHeap heapTime = new TimeHeap();

        Traslado[] traslados = {
            new Traslado(1, 0, 2, 200, 100),
            new Traslado(2, 0, 3, 150, 110),
            new Traslado(3, 0, 4, 300, 120),
            new Traslado(4, 1, 5, 250, 130)
        };

        heapTime.ArrayToHeap(traslados);

        assert heapTime.minimo().timestamp() == 100 : "Error: el mínimo debería tener timestamp 100";
        System.out.println("Fin del test básico de TimeHeap.");
    }

    public static void testHeapTimeEncoladoDesencolado() {
        System.out.println("Inicio del test de encolado y desencolado en TimeHeap.");
        TimeHeap heapTime = new TimeHeap();

        Traslado[] traslados = {
            new Traslado(1, 0, 2, 200, 100),
            new Traslado(2, 0, 3, 150, 110),
            new Traslado(3, 0, 4, 300, 120)
        };

        heapTime.ArrayToHeap(traslados);
        Traslado nuevoTraslado = new Traslado(4, 1, 5, 350, 130);
        heapTime.encolar(nuevoTraslado);

        assert heapTime.minimo().timestamp() == 100 : "Error: el mínimo debería tener timestamp 100";

        Traslado desencolado = heapTime.desencolarMin();
        assert desencolado.timestamp() == 100 : "Error: el desencolado debería tener timestamp 100";
        assert heapTime.minimo().timestamp() == 110 : "Error: el mínimo después de desencolar debería tener timestamp 110";

        System.out.println("Fin del test de encolado y desencolado en TimeHeap.");
    }

    public static void testHeapTimeIndicesCorrectos() {
        System.out.println("Inicio del test de índices correctos en TimeHeap.");
        TimeHeap heapTime = new TimeHeap();

        Traslado[] traslados = {
            new Traslado(1, 0, 2, 200, 100),
            new Traslado(2, 0, 3, 150, 110),
            new Traslado(3, 0, 4, 300, 120),
            new Traslado(4, 1, 5, 250, 130)
        };

        heapTime.ArrayToHeap(traslados);

        // Verifica que los índices de los traslados coinciden con sus posiciones en el heap
        for (int i = 0; i < traslados.length; i++) {
            Traslado traslado = traslados[i];
            int indiceTime = traslado.indiceTime();
            assert indiceTime == i : "Error: el índice de Time en el traslado con id " + traslado.id() + " debería ser " + i + ", pero es " + indiceTime;
        }

        System.out.println("Fin del test de índices correctos en TimeHeap.");
    }

    public static void testHeapTimeConDatosExtremadamenteGrandes() {
        System.out.println("Inicio del test con 100 elementos en TimeHeap.");
        TimeHeap heapTime = new TimeHeap();

        // Generar 100 traslados con IDs aleatorios y timestamps aleatorios
        ArrayList<Traslado> trasladosList = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            trasladosList.add(new Traslado(i, random.nextInt(10), random.nextInt(10), random.nextInt(1000), random.nextInt(1000)));
        }

        Traslado[] traslados = new Traslado[trasladosList.size()];
        trasladosList.toArray(traslados);
        heapTime.ArrayToHeap(traslados);

        // Verifica que los índices de los traslados coinciden con sus posiciones en el heap
        for (int i = 0; i < traslados.length; i++) {
            Traslado traslado = traslados[i];
            int indiceTime = traslado.indiceTime();
            assert indiceTime == i : "Error: el índice de Time en el traslado con id " + traslado.id() + " debería ser " + i + ", pero es " + indiceTime;
        }

        System.out.println("Fin del test con 100 elementos en TimeHeap.");
    }
}
