package aed;

public class TimeHeap{                              // Vamos a usar un Min-Heap para que deseconlarMin devuelva el traslado mas antiguo
                                                    // Utilizamos la operacion desencolarPorIndice para desencolar en O(log n)
    private ArregloRedimensionableDeTraslado heap;  // apovechando que nuestra estructura interna es un ArregloRedimensionable e indexar
                                                    // e indezar cuesta O(1)
    public TimeHeap(){
        this.heap  = new ArregloRedimensionableDeTraslado();
    }

    public Traslado Minimo(){                               // Lo usamos para ver el Minimo sin eliminarlo. Costo: O(1)
        Traslado res = null;    
        if (heap.longitud() != 0)
            res = this.heap.obtener(0);
        return res;
    }

    public void ArrayToHeap(Traslado[] Traslados){          // Algoritmo de Floyd/Heapify. Costo: O(n)   
        for (Traslado traslado : Traslados){                // Pasamos el Array a nuestro Heap. O(n)
            heap.agregarAtras(traslado);
            traslado.changeIndiceTime(heap.longitud() - 1);
        }
        int i = (heap.longitud())/2-1;                          
        while (i>=0){                                       // Hacemos sift down para cada elemento arrancando en la mitad
            siftDown(i);                                    // ya que la otra mitad del Array son hojas. O(n)
            i--;
        }
    }

    public void encolar(Traslado traslado){                 // Encolamos, asignamos indice y luego aplicamos sift up.
        heap.agregarAtras(traslado);                                 // Costo: O(log n)
        traslado.changeIndiceTime(this.heap.longitud()-1);
        siftUp(heap.longitud() - 1);
    }

    public Traslado desencolarMin(){                        // Desencolamos el traslado mas viejo y reacomodamos el Heap
        Traslado res = null;
        if (heap.longitud() != 0)
            res = desencolarPorIndice(0);                 // Costo: igual a desencolarPorIndice (O (log n))
        return res;
    }

    public Traslado desencolarPorIndice(int i){             // Devolvemos el traslado en la i-esima posicion y reacomodamos el Heap
        Traslado res = null;
        if (i!=-1){                                         // Si el indice es distinto del default hacemos swap entre el eliminado
            Traslado eliminado = heap.obtener(i);               // y el ultimo, luego hacemos sift up y sift down para el reemplazo
            res = eliminado;                                // Por ultimo eliminamos el ultimo del ArrayList      
            swap(i, heap.longitud()-1);
            heap.quitarAtras();
            siftUp(i);                                      // Este proceso cuesta O(log n) independientemente del indice
            siftDown(i);                                    // sift up y sift down cuestan O(log n), el resto de las operaciones O(1)
        }
        return res;
    }

    private void siftUp(int i){                             // Tanto sift up como sift down los voy a manejar con indices
        int indicePadre = (i-1)/2;                     
        while (indicePadre != -1 && masViejo(i,indicePadre) == i){
            swap(i, indicePadre);                           // Buscamos el indice del padre y si el tiempo de nuestro traslado es menor
            i = indicePadre;                                // hacemos swap con el padre.
            indicePadre = ((i-1)/2);                        // En en el peor de los casos hay que hacer "altura del Heap" cambios
        }  
    }                                                        // Esto es O(log n)
    

    private void siftDown(int i){
        int hijoIzq = 2*i+1;                                // Buscamos ambos hijos, determinamos el traslado mas viejo 
        int hijoDer = 2*i+2;                                // y reacomodamos nuestro traslado hasta que sea mas viejo que
        int masViejo = masViejo(hijoIzq,hijoDer);           // el mayor de sus hijo o que sea una hoja
        Traslado actual = heap.obtener(i);
        while (hijoIzq < heap.longitud() && actual.timestamp() > heap.obtener(masViejo).timestamp()){
            swap(i, masViejo);
            i = masViejo;
            hijoIzq = 2*i+1;                                // Nuevamente en el peor de los casos hay que hacer "altura del Heap" cambios
            hijoDer = 2*i+2;                                // Este proceso cuesta O(log n)
            masViejo = masViejo(hijoIzq,hijoDer);
        }
    }

    private int masViejo(int i, int j){                     // Funcion Auxiliar que determina el traslado mas viejo
        int res = 0;                                        // Simplemente compara atributos de traslado. O(1)
        if (i < heap.longitud()){
            res = i;
            if (j < heap.longitud() && heap.obtener(j).timestamp() < heap.obtener(i).timestamp()){
                res = j;
            }
        }   
        return res;
    }

    private void swap(int i, int j){                        // Swap tiene un costo de O(1) ya que indexamos, seteamos y reestablecemos indices
        Traslado cambio = heap.obtener(i);                      
        heap.definir(i, heap.obtener(j));                   // Cambiamos posiciones
        heap.definir(j, cambio);

        heap.obtener(i).changeIndiceTime(i);                // Actualizamos los indices
        heap.obtener(j).changeIndiceTime(j);
    }
}
