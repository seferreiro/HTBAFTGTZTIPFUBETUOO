package aed;

public class HeapRedito{                                    
    private ArregloRedimensionable heap;                         

    public HeapRedito(){                            
        this.heap = new ArregloRedimensionable();                          
    }

    public Traslado maximo(){                                
        return heap.obtener(0);
    }

    public void ArrayToHeap(Traslado[] Traslados){             
        for (int i = 0; i<Traslados.length; i++){
            Traslado traslado = Traslados[i];
            heap.agregarAtras(traslado);
            traslado.changeIndiceRedito(i);
        }                                                
        int i = (heap.longitud())/2-1;
        while (i >= 0){                                        
            siftDown(i);                                        
            i--;
        }
    }

    public void encolar(Traslado traslado){
        heap.agregarAtras(traslado);
        traslado.changeIndiceRedito(heap.longitud()-1);
        siftUp(heap.longitud()-1);
    }

    public Traslado desencolarMax(){
        Traslado res = null;
        if (heap.longitud()!=0)
            res = desencolarPorIndice(0);
        return res;
    }

    public Traslado desencolarPorIndice(int i){
        Traslado res = null;
        if (i!=-1 && i<heap.longitud()){
            res = heap.obtener(i);
            swap(i,heap.longitud()-1);
            heap.quitarAtras();
            reacomodar(i);
        }
        return res;
    }

    public void reacomodar(int i){                                                               
        siftUp(i);                                       
        siftDown(i);
    }

    public void siftUp(int i){
        int indicePadre = (i-1) / 2;  
        while (i > 0 && masRedituable(i, indicePadre) == i) {  
            swap(i,indicePadre);  
            i = indicePadre;  
            indicePadre = (i-1)/2;  
        }
    }
    

    private void siftDown(int i) {
        int hijoIzq = (2*i) + 1;                             
        int hijoDer = (2*i) + 2;
        int hijoMejorRedito = hermanoMasRedituable(hijoIzq, hijoDer);
        while (hijoIzq < heap.longitud() && masRedituable(i, hijoMejorRedito) != i) {
            swap(i, hijoMejorRedito);
            i = hijoMejorRedito;
            hijoIzq = (2*i) + 1;
            hijoDer = (2*i) + 2;
            hijoMejorRedito = hermanoMasRedituable(hijoIzq, hijoDer);
        }
    }

    public int masRedituable(int i, int j) {
        Traslado T1 = heap.obtener(i);
        Traslado T2 = heap.obtener(j);
        int res = i;
        if (T2.gananciaNeta() > T1.gananciaNeta() || (T1.gananciaNeta() == T2.gananciaNeta() && T2.id() < T1.id()))
            res = j;
        return res;
    }

    private int hermanoMasRedituable(int i, int j) {          
        int res = 0;
        if (i < heap.longitud()){           
            res = i;
            if (j < heap.longitud() && masRedituable(i,j) == j) 
                res = j; 
        }
        return res;
    }

    public void swap(int i, int j) {
        Traslado C1 = sinAliasing(heap.obtener(i));
        Traslado C2 = sinAliasing(heap.obtener(j));
        heap.definir(i, C2);
        C2.changeIndiceRedito(i);
        heap.definir(j, C1);
        C1.changeIndiceRedito(i);
    }

    private Traslado sinAliasing(Traslado traslado) {
        return new Traslado(traslado.id(), traslado.origen(), traslado.destino(), traslado.gananciaNeta(), traslado.timestamp());
    }

    public ArregloRedimensionable heap() {
        return heap;
    }
}
