package aed;


import java.util.ArrayList;
import java.util.Random;

public class TestHeapRedito {

    public static void main(String[] args) {
        testHeapReditoBasico();
        testHeapReditoEncoladoDesencolado();
        testHeapReditoIndicesCorrectos();
        testHeapReditoConDatosExtremadamenteGrandes();
    }

    public static void testHeapReditoBasico() {
        System.out.println("Inicio del test básico de HeapRedito.");
        HeapRedito heapRedito = new HeapRedito();

        Traslado[] traslados = {
            new Traslado(1, 0, 2, 200, 100),
            new Traslado(2, 0, 3, 150, 110),
            new Traslado(3, 0, 4, 300, 120),
            new Traslado(4, 1, 5, 250, 130)
        };

        heapRedito.ArrayToHeap(traslados);

        assert heapRedito.maximo().gananciaNeta() == 300 : "Error: el máximo debería tener ganancia neta 300";
        System.out.println("Fin del test básico de HeapRedito.");
    }

    public static void testHeapReditoEncoladoDesencolado() {
        System.out.println("Inicio del test de encolado y desencolado en HeapRedito.");
        HeapRedito heapRedito = new HeapRedito();

        Traslado[] traslados = {
            new Traslado(1, 0, 2, 200, 100),
            new Traslado(2, 0, 3, 150, 110),
            new Traslado(3, 0, 4, 300, 120)
        };

        heapRedito.ArrayToHeap(traslados);
        Traslado nuevoTraslado = new Traslado(4, 1, 5, 350, 130);
        heapRedito.encolar(nuevoTraslado);

        assert heapRedito.maximo().gananciaNeta() == 350 : "Error: el máximo debería tener ganancia neta 350";

        Traslado desencolado = heapRedito.desencolarMax();
        assert desencolado.gananciaNeta() == 350 : "Error: el desencolado debería tener ganancia neta 350";
        assert heapRedito.maximo().gananciaNeta() == 300 : "Error: el máximo después de desencolar debería tener ganancia neta 300";

        System.out.println("Fin del test de encolado y desencolado en HeapRedito.");
    }

    public static void testHeapReditoIndicesCorrectos() {
        System.out.println("Inicio del test de índices correctos en HeapRedito.");
        HeapRedito heapRedito = new HeapRedito();

        Traslado[] traslados = {
            new Traslado(1, 0, 2, 200, 100),
            new Traslado(2, 0, 3, 150, 110),
            new Traslado(3, 0, 4, 300, 120),
            new Traslado(4, 1, 5, 250, 130)
        };

        heapRedito.ArrayToHeap(traslados);

        // Verifica que los índices de los traslados coinciden con sus posiciones en el heap
        for (int i = 0; i < traslados.length; i++) {
            Traslado traslado = traslados[i];
            int indiceRedito = traslado.indiceRedito();
            assert indiceRedito == i : "Error: el índice de redito para el traslado " + traslado.id() + " debería ser " + i + " pero es " + indiceRedito;
        }

        System.out.println("Fin del test de índices correctos en HeapRedito.");
    }

    public static void testHeapReditoConDatosExtremadamenteGrandes() {
        System.out.println("Inicio del test con 100 elementos en HeapRedito.");
        HeapRedito heapRedito = new HeapRedito();
        Random random = new Random();

        ArrayList<Traslado> traslados = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            int id = i + 1;
            int origen = random.nextInt(10);
            int destino = random.nextInt(10);
            int gananciaNeta = random.nextInt(1000);
            int timestamp = random.nextInt(1000);
            Traslado t = new Traslado(id, origen, destino, gananciaNeta, timestamp);
            traslados.add(t);
        }

        // Convierte el arreglo a un heap
        heapRedito.ArrayToHeap(traslados.toArray(new Traslado[0]));

        // Verifica que todos los traslados tengan sus índices correctamente asignados
        for (int i = 0; i < traslados.size(); i++) {
            Traslado traslado = traslados.get(i);
            int indiceRedito = traslado.indiceRedito();
            assert indiceRedito == i : "Error: el índice de redito para el traslado " + traslado.id() + " debería ser " + i + " pero es " + indiceRedito;
        }

        // Verifica que el heap funcione correctamente con los índices
        for (int i = 0; i < traslados.size(); i++) {
            Traslado max = heapRedito.desencolarMax();
            System.out.println("Desencolado traslado id: " + max.id() + " con ganancia " + max.gananciaNeta());
        }

        System.out.println("Fin del test con 100 elementos en HeapRedito.");
    }
}
