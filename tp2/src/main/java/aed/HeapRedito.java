package aed;

import java.util.ArrayList;

public class HeapRedito{                                    
    private ArrayList<Traslado> heap;                         

    public HeapRedito(){                            
        this.heap = new ArrayList<>();                          
    }

    public Traslado maximo(){                                
        return heap.get(0);
    }

    public void ArrayToHeap(Traslado[] Traslados){             
        for (int i = 0; i<Traslados.length; i++){
            Traslado traslado = Traslados[i];
            heap.add(traslado);
            traslado.changeIndiceRedito(i);
        }                                                
        int i = (heap.size())/2-1;
        while (i >= 0){                                        
            siftDown(i);                                        
            i--;
        }
    }

    public void encolar(Traslado traslado){
        heap.add(traslado);
        traslado.changeIndiceRedito(heap.size()-1);
        siftUp(heap.size()-1);
    }

    public Traslado desencolarMax(){
        Traslado res = null;
        if (!heap.isEmpty())
            res = desencolarPorIndice(0);
        return res;
    }

    public Traslado desencolarPorIndice(int i){
        Traslado res = null;
        if (i!=-1 && i<heap.size()){
            res = heap.get(i);
            swap(i,heap.size()-1);
            heap.remove(heap.size()-1);
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
        while (i>0 && heap.get(indicePadre)!=null && masRedituable(i, indicePadre) == i) {  
            swap(i,indicePadre);  
            i = indicePadre;  
            indicePadre = (i-1)/2;
        }
    }
    

    private void siftDown(int i) {
        int hijoIzq = (2*i) + 1;
        int hijoDer = (2*i) + 2;
        int hijoMasRedituable = hermanoMasRedituable(hijoIzq, hijoDer);

        while (hijoIzq < heap.size() && masRedituable(i, hijoMasRedituable) != i) {
            swap(i, hijoMasRedituable);
            i = hijoMasRedituable;
            hijoIzq = (2*i) + 1;
            hijoDer = (2*i) + 2;
            hijoMasRedituable = hermanoMasRedituable(hijoIzq, hijoDer);
        }
    }

    public int masRedituable(int i, int j) {
        Traslado T1 = heap.get(i);
        Traslado T2 = heap.get(j);
        int res = i;
        if (T2.gananciaNeta() > T1.gananciaNeta() || (T1.gananciaNeta() == T2.gananciaNeta() && T2.id() < T1.id()))
            res = j;
        return res;
    }

    private int hermanoMasRedituable(int i, int j) {          
        int res = 0;
        if (i < heap.size()){           
            res = i;
            if (j < heap.size() && masRedituable(i,j) == j) 
                res = j; 
        }
        return res;
    }

    public void swap(int i, int j) {
        Traslado T1 = heap.get(i);
        Traslado T2 = heap.get(j);
        heap.set(i, T2);
        T2.changeIndiceRedito(i);  
        heap.set(j, T1);
        T1.changeIndiceRedito(j); 
    }
    


    public ArrayList<Traslado> heap() {
        return heap;
    }
}
