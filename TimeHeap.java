package aed;

import java.util.ArrayList;

public class TimeHeap{                                    
    private ArrayList<Traslado> heap;                         

    public TimeHeap(){                            
        this.heap = new ArrayList<>();                          
    }

    public Traslado minimo(){                                
        return heap.get(0);
    }

    public void ArrayToHeap(Traslado[] Traslados){             
        for (int i = 0; i<Traslados.length; i++){
            Traslado traslado = Traslados[i];
            heap.add(traslado);
            traslado.changeIndiceTime(i);
        }                                                
        int i = (heap.size())/2;
        while (i >= 0){                                        
            siftDown(i);                                        
            i--;
        }
    }

    public void encolar(Traslado traslado){
        heap.add(traslado);
        traslado.changeIndiceTime(heap.size()-1);
        siftUp(heap.size()-1);
    }

    public Traslado desencolarMin(){
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
            if (i < heap.size())
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
        while (i>0 && masViejo(i, indicePadre) == i) {  
            swap(i,indicePadre);  
            i = indicePadre;  
            indicePadre = (i-1)/2;
        }
    }
    

    private void siftDown(int i) {
        int hijoIzq = (2*i) + 1;                             
        int hijoDer = (2*i) + 2;
        int hijoMasViejo = hermanoMasViejo(hijoIzq, hijoDer);
        while (hijoIzq < heap.size() && masViejo(i, hijoMasViejo) != i) {
            swap(i, hijoMasViejo);
            i = hijoMasViejo;
            hijoIzq = (2*i) + 1;
            hijoDer = (2*i) + 2;
            hijoMasViejo = hermanoMasViejo(hijoIzq, hijoDer);
        }
    }

    public int masViejo(int i, int j) {
        Traslado T1 = heap.get(i);
        Traslado T2 = heap.get(j);
        int res = i;
        if (T2.timestamp() < T1.timestamp())
            res = j;
        return res;
    }

    private int hermanoMasViejo(int i, int j) {          
        int res = 0;
        if (i < heap.size()){           
            res = i;
            if (j < heap.size() && masViejo(i,j) == j) 
                res = j; 
        }
        return res;
    }

    public void swap(int i, int j) {
        Traslado T1 = heap.get(i);
        Traslado T2 = heap.get(j);
        heap.set(i, T2);
        T2.changeIndiceTime(i);  
        heap.set(j, T1);
        T1.changeIndiceTime(j); 
    }

    public ArrayList<Traslado> heap() {
        return heap;
    }
}
