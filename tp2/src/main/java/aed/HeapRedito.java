package aed;

public class HeapRedito {                                   // Totalmente analogo a TimeHeap, solo que aqui la estructura es un Max-Heap
                                                            // nuevamente nos manejamos con un ArregloRedimensionable para que desencolarPoIndice 
    private ArregloRedimensionableDeTraslado heap;          // tenga un costo de O(log n)

    public HeapRedito(){
        this.heap = new ArregloRedimensionableDeTraslado();             // Creamos el ArrayRedemensionable. Costo: O(1)
    }

    public Traslado Maximo(){
        Traslado res = null;                                            // De tener un Heap no nulo, devolvemos el traslado con mas redito
        if (heap.longitud() != 0)                                       // Costo: O(1)
            res = this.heap.obtener(0);
        return res;
    }

    public void ArrayToHeap(Traslado[] Traslados){                     // Algoritmo de FLoyd / Heapify. Costo: O(n)
        for (Traslado traslado : Traslados) {
            heap.agregarAtras(traslado);                                // Agregamos todos los traslados a nuestro Heap y asignamos indices
            traslado.changeIndiceRedito(heap.longitud() - 1);
        }
        int i = (heap.longitud()) / 2 - 1;
        while (i >= 0) {                                                // Hacemos sift down desde la mitad del Array hasta el principio
            siftDown(i);                                                // La otra mitad del Array son hojas
            i--;
        }
    }

    public void encolar(Traslado traslado){                            // Este proceso cuesta O(log n)
        heap.agregarAtras(traslado);
        traslado.changeIndiceRedito(this.heap.longitud() - 1);          // Basicamente agregamos el traslado, le asignamos indice y
                                                                        // acomodamos el traslado utilizando el metodo sift up (O(log n))
        siftUp(heap.longitud() - 1);                                    
    }

    public Traslado desencolarMax(){                                   // A diferencia de Maximo(), aqui devolvemos el maximo y lo desencolamos
        Traslado res = null;                                            // aprovechamos desencolarPorIndice y lo aplicamos en la posicion 0
        if (heap.longitud()!=0)                                         // Costo: idem desencolarPorIndeice (O(log n))
            res = desencolarPorIndice(0);
        return res;
    }

    public Traslado desencolarPorIndice(int i){                        // Aqui lo que hacemos es indexar en nuestro ArregloRedimensionable (O(1))
        Traslado res = null;                                            // reemplazar el eliminado por el ultimo y reacomodar el reemplazo
        if (i != -1) {                                                  // Tanto swap como quitarAtras tienen un costo de (O(1))
            Traslado eliminado = heap.obtener(i);                       // Sift up y sift down cuestan O(log n)
            res = eliminado;
            swap(i, heap.longitud() - 1);
            heap.quitarAtras();
            siftUp(i);                                                  
            siftDown(i);
        }
        return res;                                                     // Por lo tanto este proceso cuesta O(log n) 
    }

    private void siftUp(int i){
        int indicePadre = (i - 1) / 2;                                  // Buscamos el padre y hacemos un loop para ver si hay que rotar ambos traslados
        while (indicePadre != -1  && (masRedituable(i, indicePadre) != i)) {
            swap(i, indicePadre);
            i = indicePadre;
            indicePadre = ((i - 1) / 2);
        }                                                               // En este proceso como mucho hay que hacer "altura del Heap" saltos
    }                                                                   // Por lo tanto su costo es O(log n)

    private void siftDown(int i){                                       // Buscamos a los hijos, calculamos el mas redituable y lo comparamos con el padre
        int hijoIzq = 2 * i + 1;                                        // Reacomodamos nuestro traslado y repetimos el proceso
        int hijoDer = 2 * i + 2;
        int hijoMasRedituable = hermanoMasRedituable(hijoIzq, hijoDer);
        while (hijoIzq < heap.longitud() && (masRedituable(i, hijoMasRedituable) != i)){
            swap(i, hijoMasRedituable);
            i = hijoMasRedituable;
            hijoIzq = 2 * i + 1;
            hijoDer = 2 * i + 2;
            hijoMasRedituable = hermanoMasRedituable(hijoIzq, hijoDer); 
        }                                                               // Nuevamente este proceso se repite "altura del Heap" veces
    }                                                                   // Por lo tanto su costo es O(log n)

    private int masRedituable(int i, int j){                                 // Funcion Auxiliar que determina el traslado mas viejo
        int res = 0;                                                    // Simplemente compara atributos de traslado. O(1)
        if (i < heap.longitud()){
            Traslado T1 = heap.obtener(i);
            res = i;
            if (j < heap.longitud()){
                Traslado T2 = heap.obtener(j);
                if (T2.gananciaNeta() > T1.gananciaNeta() || (T1.gananciaNeta() == T2.gananciaNeta() && T2.id() < T1.id()))
                    res = j;
            }
        }   
        return res;
    }

    private int hermanoMasRedituable(int i, int j){
        int res = 0;
        if (i < heap.longitud()){
            res = i;
            if (j < heap.longitud() && (masRedituable(i, j) == j)){
                res = j;
            }
        }
        return res;
    }

    private void swap(int i, int j){                                    // Aqui realizamos un cambio posiciones en el arreglo 
        Traslado cambio = heap.obtener(i);                              // y actualizamos los indices. Costo: O(1)
        heap.definir(i, heap.obtener(j));   
        heap.definir(j, cambio);

        heap.obtener(i).changeIndiceRedito(i);
        heap.obtener(j).changeIndiceRedito(j);
    }
}
