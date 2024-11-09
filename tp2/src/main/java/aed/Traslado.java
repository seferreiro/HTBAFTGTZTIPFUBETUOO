package aed;

public class Traslado{
    
    private int id;
    private int origen;
    private int destino;
    private int gananciaNeta;
    private int timestamp;
    private int indiceTime = -1;                // Estos indices nos van a ayudar a remover un traslado de un heap en O(log n)
    private int indiceRedito = -1;              // ya que indexar en un array cuesta O(1) y luego tenemos que hacer swap entre 
                                                // este elemento y el ultimo del array y hacer sift up + sift down que cuesta O(log n)


    public Traslado(int id, int origen, int destino, int gananciaNeta, int timestamp){
        this.id = id;
        this.origen = origen;         
        this.destino = destino;
        this.gananciaNeta = gananciaNeta;
        this.timestamp = timestamp;
    }
                                                 // Aqui tenemos los getters con la opcion de cambiar los indices Time y Redito
    public int id(){
        return this.id;
    }

    public int indiceTime(){
        return this.indiceTime;
    }

    public void changeIndiceTime(int i){
        this.indiceTime = i;
    }
    
    public int indiceRedito(){
        return this.indiceRedito;
    }

    public void changeIndiceRedito(int i){
        this.indiceRedito = i;
    }

    public int origen(){
        return this.origen;
    }

    public int destino(){
        return this.destino;
    }

    public int gananciaNeta(){
        return this.gananciaNeta;
    }

    public int timestamp(){
        return this.timestamp;
    }                                      
}


