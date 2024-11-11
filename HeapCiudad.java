package aed;

public class HeapCiudad {                                    
    private Ciudad[] heap;                         

    public HeapCiudad(int cantidad) {                            
        this.heap = new Ciudad[cantidad];                          
    }

    public Ciudad maximo() {                                
        return heap[0];
    }

    public void ArrayToHeap(Ciudad[] Ciudades) {             
        for (int i = 0; i<Ciudades.length; i++){
            heap[i] = Ciudades[i];
            Ciudades[i].changeIndice(i);
        }                                                
        int i = (heap.length)/2-1;
        while (i >= 0) {                                        
            siftDown(i);                                        
            i--;
        }
    }

    public void reacomodar(int i) {                                                               
        siftUp(i);                                       
        siftDown(i);
    }

    public void siftUp(int i){
        int indicePadre = (i-1)/2;  
        while (i > 0 && mejorBalance(i, indicePadre) == i) {  
            swap(i,indicePadre);  
            i = indicePadre;  
            indicePadre = (i-1)/2;  
        }
    }
    

    private void siftDown(int i){
        int hijoIzq = (2*i) + 1;                             
        int hijoDer = (2*i) + 2;
        int hijoMejorBalance = hermanoMejorBalance(hijoIzq, hijoDer);
        while (hijoIzq < heap.length && mejorBalance(i, hijoMejorBalance) != i) {
            swap(i, hijoMejorBalance);
            i = hijoMejorBalance;
            hijoIzq = (2*i) + 1;
            hijoDer = (2*i) + 2;
            hijoMejorBalance = hermanoMejorBalance(hijoIzq, hijoDer);
        }
    }

    public int mejorBalance(int i, int j){
        int res = j;
        if (heap[i].balance() > heap[j].balance())
            res = i;
        return res;
    }

    private int hermanoMejorBalance(int i, int j){          
        int res = 0;
        if (i < heap.length) {           
            res = i;
            if (j < heap.length && mejorBalance(i,j) == j) 
                res = j; 
        }
        return res;
    }

    public void swap(int i, int j) {
        Ciudad C1 = heap[i];
        Ciudad C2 = heap[j];
        heap[i] = C2;
        C2.changeIndice(i);  
        heap[j] = C1;
        C1.changeIndice(j); 
    }
    public Ciudad[] heap() {
        return heap;
    }
}
