package aed;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class HeapCiudadTest {

    // Test para siftUp
    @Test
    void testSiftUp() {
        // Crear algunas ciudades con balances variados
        Ciudad[] ciudades = new Ciudad[] {
            new Ciudad(1, 100, 50, 100 - 50),  // balance = 100 - 50 = 50
            new Ciudad(2, 200, 100, 200 - 100),// balance = 200 - 100 = 100
            new Ciudad(3, 150, 70, 150 - 70),  // balance = 150 - 70 = 80
            new Ciudad(4, 300, 150, 300 - 150),// balance = 300 - 150 = 150
            new Ciudad(5, 50, 20, 50 - 20)     // balance = 50 - 20 = 30
        };

        // Crear el heap y cargar las ciudades
        HeapCiudad heap = new HeapCiudad(ciudades.length);
        heap.ArrayToHeap(ciudades);

        // Cambiar el balance de una ciudad (por ejemplo, aumentar la ganancia de la ciudad en índice 4)
        Ciudad ciudad = heap.heap()[4];  // Ciudad con índice 4 (balance=30)
        ciudad.aumentarGanancia(200);     // Aumentamos la ganancia para cambiar el balance
        ciudad.actualizarBalance();      // Actualizamos el balance
        heap.siftUp(4);
        System.out.println(heap.mejorBalance(4,1));              // Reacomodamos la ciudad (siftUp y siftDown)

        // Verificar que la ciudad con mayor balance esté en la raíz
        assertEquals(230, heap.maximo().balance(), "La ciudad con mayor balance debe estar en la raíz después de siftUp");
        
        // Verificar la propiedad Max-Heap después del siftUp
        Ciudad[] heapArray = heap.heap();
        for (int i = 0; i < heapArray.length; i++) {
            int leftChildIdx = 2 * i + 1;
            int rightChildIdx = 2 * i + 2;

            if (leftChildIdx < heapArray.length) {
                assertTrue(heapArray[i].balance() >= heapArray[leftChildIdx].balance(), "La propiedad Max-Heap no se mantiene en el hijo izquierdo después de siftUp");
            }
            if (rightChildIdx < heapArray.length) {
                assertTrue(heapArray[i].balance() >= heapArray[rightChildIdx].balance(), "La propiedad Max-Heap no se mantiene en el hijo derecho después de siftUp");
            }
        }
    }

    // Test para siftDown
    @Test
    void testSiftDown() {
        // Crear algunas ciudades con balances variados
        Ciudad[] ciudades = new Ciudad[] {
            new Ciudad(1, 100, 50, 100 - 50),  // balance = 100 - 50 = 50
            new Ciudad(2, 200, 100, 200 - 100),// balance = 200 - 100 = 100
            new Ciudad(3, 150, 70, 150 - 70),  // balance = 150 - 70 = 80
            new Ciudad(4, 300, 150, 300 - 150),// balance = 300 - 150 = 150
            new Ciudad(5, 50, 20, 50 - 20)     // balance = 50 - 20 = 30
        };

        // Crear el heap y cargar las ciudades
        HeapCiudad heap = new HeapCiudad(ciudades.length);
        heap.ArrayToHeap(ciudades);

        // Intercambiar las primeras ciudades para verificar siftDown
        heap.swap(0, 4);  // Swap la raíz con el último elemento (ciudad con menor balance)

        // Verificar que la ciudad con mayor balance esté en la raíz después del swap
        assertEquals(30, heap.heap()[0].balance(), "La raíz debe tener el menor balance después del swap");

        // Ahora hacer un siftDown en la raíz
        heap.reacomodar(0);  // Debe mover la ciudad de menor balance hacia abajo
        heap.reacomodar(1);
        // Verificar que la propiedad Max-Heap se mantenga
        Ciudad[] heapArray = heap.heap();
        assertTrue(heapArray[0].balance() >= heapArray[1].balance(), "La propiedad Max-Heap no se mantiene en el índice 0 después de siftDown");
        assertTrue(heapArray[1].balance() >= heapArray[3].balance(), "La propiedad Max-Heap no se mantiene en el índice 1 después de siftDown");
        assertTrue(heapArray[1].balance() >= heapArray[4].balance(), "La propiedad Max-Heap no se mantiene en el índice 2 después de siftDown");
    }

    // Test con elementos adicionales y reacomodación
    @Test
    void testReacomodarConCambiosDinamicos() {
        // Crear algunas ciudades con balances variados
        Ciudad[] ciudades = new Ciudad[] {
            new Ciudad(1, 100, 50, 100 - 50),   // balance = 100 - 50 = 50
            new Ciudad(2, 200, 100, 200 - 100), // balance = 200 - 100 = 100
            new Ciudad(3, 150, 70, 150 - 70),   // balance = 150 - 70 = 80
            new Ciudad(4, 300, 150, 300 - 150), // balance = 300 - 150 = 150
            new Ciudad(5, 50, 20, 50 - 20)      // balance = 50 - 20 = 30
        };

        // Crear un heap y cargar las ciudades
        HeapCiudad heap = new HeapCiudad(ciudades.length);
        heap.ArrayToHeap(ciudades);

        // Verificar que el valor máximo está en la raíz (índice 0)
        assertEquals(150, heap.maximo().balance(), "La raíz debe tener el mayor balance");

        // Modificar el balance de la ciudad en el índice 2
        Ciudad ciudad = heap.heap()[2];
        ciudad.aumentarGanancia(150);   // Aumentamos la ganancia para cambiar su balance
        ciudad.actualizarBalance();     // Actualizamos el balance
        heap.reacomodar(2);             // Reacomodamos la ciudad después de modificar su balance

        // Verificar que el balance de la ciudad 2 se ha actualizado correctamente
        assertEquals(230, heap.maximo().balance(), "La raíz debe seguir siendo la ciudad con mayor balance");

        // Verificar el nuevo estado del heap
        Ciudad[] heapArray = heap.heap();
        assertTrue(heapArray[0].balance() >= heapArray[1].balance(), "La propiedad Max-Heap no se mantiene en el índice 0");
        assertTrue(heapArray[1].balance() >= heapArray[3].balance(), "La propiedad Max-Heap no se mantiene en el índice 1");
    }

    // Test con muchos elementos (gran escala)
    @Test
    void testHeapConGranCantidadDeElementos() {
        int numCiudades = 10000;
        Ciudad[] ciudades = new Ciudad[numCiudades];

        // Crear 10,000 ciudades con balances aleatorios
        for (int i = 0; i < numCiudades; i++) {
            int ganancia = (int) (Math.random() * 1000);
            int perdida = (int) (Math.random() * 500);
            int balance = ganancia - perdida; // balance = ganancia - perdida
            ciudades[i] = new Ciudad(i, ganancia, perdida, balance);
        }

        // Crear el heap y cargar las ciudades
        HeapCiudad heap = new HeapCiudad(numCiudades);
        heap.ArrayToHeap(ciudades);

        // Verificar que el primer elemento (raíz) tiene el mayor balance
        Ciudad raiz = heap.maximo();
        for (int i = 1; i < numCiudades; i++) {
            assertTrue(raiz.balance() >= heap.heap()[i].balance(), "La propiedad Max-Heap no se mantiene");
        }
    }

    // Test de comportamiento con `swap` y reacomodación
    @Test
    void testSwapYReacomodacion() {
        // Crear algunas ciudades con balances variados
        Ciudad[] ciudades = new Ciudad[] {
            new Ciudad(1, 100, 50, 100 - 50),  // balance = 100 - 50 = 50
            new Ciudad(2, 200, 100, 200 - 100),// balance = 200 - 100 = 100
            new Ciudad(3, 150, 70, 150 - 70),  // balance = 150 - 70 = 80
            new Ciudad(4, 300, 150, 300 - 150),// balance = 300 - 150 = 150
            new Ciudad(5, 50, 20, 50 - 20)     // balance = 50 - 20 = 30
        };

        // Crear el heap y cargar las ciudades
        HeapCiudad heap = new HeapCiudad(ciudades.length);
        heap.ArrayToHeap(ciudades);

        // Verificar que la raíz tiene el mayor balance
        assertEquals(150, heap.maximo().balance(), "La raíz debe tener el mayor balance");

        // Hacer un swap entre la raíz y otro elemento
        heap.swap(0, 4);  // swap entre la ciudad en el índice 0 (balance 50) y la ciudad en el índice 4 (balance 30)

        // Verificar la raíz después del swap
        assertEquals(30, heap.heap()[0].balance(), "La raíz debe tener el balance más bajo después del swap");

        // Reacomodar el heap para que la propiedad Max-Heap se mantenga
        heap.reacomodar(0);

        // Verificar que la propiedad Max-Heap se mantiene
        Ciudad[] heapArray = heap.heap();
        assertTrue(heapArray[0].balance() <= heapArray[1].balance(), "La propiedad Max-Heap no se mantiene en el índice 0 después del reacomodo");
    }
}
