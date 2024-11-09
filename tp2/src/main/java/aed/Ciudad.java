package aed;

public class Ciudad {
    private int id;
    private int ganancia;
    private int perdida;
    private int balance;
    private int indice = -1;
    
    
    public Ciudad(int id, int ganancia, int perdida, int balance){  
        this.id = id;
        this.ganancia = ganancia;               // Constructor Ciudad que incluye ganancia, perdida, balance, mientras que el indice 
        this.perdida = perdida;                 // se inicializa en -1 por defecto
        this.balance = balance;
    }

    public int indice(){                        // Aqui estan los getters
        return this.indice;                     // Podemos ver el Id, Ganancia, Perdida y Balance de la ciudad
    }

    public void changeIndice(int i){
        this.indice = i;
    }

    public int id(){
        return this.id;                          
    }                   

    public void aumentarGanancia(int cant){
        this.ganancia+=cant;                    // Tambien aumentar la ganancia, perdida, calcular el balance y cambiar el indice
    }

    public int ganancia(){
        return this.ganancia;
    }

    public void aumentarPerdida(int cant){
        this.perdida+=cant;
    }

    public int perdida(){
        return this.perdida;
    }

    public void actualizarBalance(){
        this.balance = this.ganancia - this.perdida;
    }

    public int balance(){
        return this.balance;
    }                                         
}
