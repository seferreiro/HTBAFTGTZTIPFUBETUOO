package aed;

import java.util.ArrayList;

public class HeapCiudad{                                    // Aqui utilizamos un Max-Heap de ciudad donde el criterio de comparacion
    private ArrayList<Ciudad> heap;                         // es el balance de la ciudad con el fin de desencolar la que mejor balance tiene

    public HeapCiudad(){                            
        this.heap = new ArrayList<>();                          
    }

    public Ciudad maximo(){                                 // Este metodo cuesta O(1) ya que de tener un heap no vacio, devuelve el maximo
        Ciudad res = null;                                    
        if (!heap.isEmpty())                                    
            res = heap.get(0);
        return res;
    }

    public void ArrayToHeap(Ciudad[] Ciudades){             // Algortimo de Floyd / Heapify. Lo utilizamos para construir un Heap a partir
        for (Ciudad ciudad : Ciudades) {                    // de un arreglo en O(n)
            heap.add(ciudad);                                
            ciudad.changeIndice(heap.size()-1);             // Agregamos las ciudades al heap y aplicamos sift down desde la mitad del arreglo
        }                                                   // hasta la posicion 0
        int i = (heap.size()) / 2 - 1;
        while (i >= 0) {                                        
            siftDown(i);                                        
            i--;
        }
    }

    public void reacomodar(int i){                          // En HeapCiudad no nos interesa desencolar ya que las ciudades van a seguir
        if (i != -1) {                                      // existiendo. Por lo tanto cada vez que modifiquemos el balance vamos a utilizar 
            siftUp(i);                                      // el metodo reacomodar que cuesta O(log n).
            siftDown(i);
        }
    }

    private void siftUp(int i){
        int indicePadre = (i - 1) / 2;
        while (indicePadre != -1 && (mejorBalance(i,indicePadre) != i)) {
            swap(i, indicePadre);                               
            i = indicePadre;                                // Totalmente analogo a los otros dos heaps, el metodo sift up reacomoda una ciudad
            indicePadre = ((i - 1) / 2);                    // segun su balance con respecto a su padre/antecesores. Costo(log n) 
        }
    }

    private void siftDown(int i){                           // Algoritmo sift down para reacomodar una ciudad segun su balance con respecto
        int hijoIzq = 2 * i + 1;                            // a sus hijos/descendientes. Costo(log n)
        int hijoDer = 2 * i + 2;
        int hijoMejorBalance = hermanoMejorBalance(hijoIzq, hijoDer);
        while (hijoIzq < heap.size() && mejorBalance(i, hijoMejorBalance) != i) {
            swap(i, hijoMejorBalance);
            i = hijoMejorBalance;
            hijoIzq = 2 * i + 1;
            hijoDer = 2 * i + 2;
            hijoMejorBalance = hermanoMejorBalance(hijoIzq, hijoDer);
        }
    }

    private int mejorBalance(int i, int j){                                 // Funcion Auxiliar que determina el traslado mas viejo
        int res = 0;                                                    // Simplemente compara atributos de traslado. O(1)
        if (i < heap.size()){
            Ciudad C1 = heap.get(i);
            res = i;
            if (j < heap.size()){
                Ciudad C2 = heap.get(j);
                if (C2.balance() > C1.balance())
                    res = j;
            }
        }   
        return res;
    }

    private int hermanoMejorBalance(int i, int j){          
        int res = 0;
        if (i < heap.size()){           
            res = i;
            if (j < heap.size() && mejorBalance(i,j) == j) 
                res = j;
            
        }
        return res;
    }

    private void swap(int i, int j){                        // Metodo swap para intercambiar posiciones de las ciudades en el arreglo.
        Ciudad cambio = heap.get(i);                        // Tiene un Costo de O(1)
        heap.set(i, heap.get(j));
        heap.set(j, cambio);

        heap.get(i).changeIndice(i);
        heap.get(j).changeIndice(j);
    }
}
