package aed;

public class ArregloRedimensionable{                   
    private int longitud;
    private Traslado [] traslados;

    public ArregloRedimensionable(){
        this.longitud = 0;
        this.traslados = new Traslado [50];
    }

    public int longitud() {
        int res = this.longitud;
        return res;
    }

    public void agregarAtras(Traslado T) {
        if (this.longitud == traslados.length){
            Traslado [] nuevoArray = new Traslado [longitud*2];
            for (int i = 0; i<longitud; i++){
                nuevoArray[i] = traslados[i];
            }
            this.traslados = nuevoArray;
        }
        this.traslados[longitud] = T;    
        this.longitud ++;
    }

    public Traslado obtener(int i) {
        Traslado res = this.traslados[i];
        return res;
    }

    public void quitarAtras() {
        traslados[longitud-1] = null;
        this.longitud--;
    }

    public void definir(int indice, Traslado  valor) {
        this.traslados[indice] = valor;
    }

}
