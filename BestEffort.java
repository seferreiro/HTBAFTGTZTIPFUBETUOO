package aed;

import java.util.ArrayList;

public class BestEffort {
    
    private Ciudad[] ciudades;
    private HeapRedito trasMasRedituables;
    private TimeHeap trasMasAntiguos;
    private HeapCiudad ciudadesBalance;
    private ArrayList<Integer> ciudadesMasGanancia;
    private ArrayList<Integer> ciudadesMasPerdida;
    private int gananciaTotal = 0;
    private int trasladosTotales = 0;

    private void sumarGanancia(int i){
        gananciaTotal += i;
    }

    private void sumarTraslado(){
        trasladosTotales++;
    }

    public BestEffort(int cantCiudades, Traslado[] traslados){
        trasMasRedituables = new HeapRedito();
        trasMasAntiguos = new TimeHeap();
        ciudadesMasGanancia = new ArrayList<>();
        ciudadesMasPerdida = new ArrayList<>();
        trasMasRedituables.ArrayToHeap(traslados);
        trasMasAntiguos.ArrayToHeap(traslados);
        registrarCiudades(cantCiudades);
    }

    private void registrarCiudades(int cantidad){
        ciudadesBalance = new HeapCiudad(cantidad);
        ciudades = new Ciudad[cantidad];
        for (int i = 0; i < cantidad; i++){
            Ciudad ciudad = new Ciudad(i,0,0,0);
            ciudades[i] = ciudad;
        }
        ciudadesBalance.ArrayToHeap(ciudades);
    }

    public void registrarTraslados(Traslado[] traslados){
        for (Traslado traslado : traslados){
            trasMasRedituables.encolar(traslado);
            trasMasAntiguos.encolar(traslado);
        }
    }

    public int[] despacharMasRedituables(int n){
        int[] res = new int[n];
        for (int i = 0; i < n; i++){
            Traslado traslado = trasMasRedituables.desencolarMax();
            trasMasAntiguos.desencolarPorIndice(traslado.indiceTime());
            registrarGananciasYPerdidas(traslado);
            res[i] = traslado.id();
        }
        return res;
    }

    public int[] despacharMasAntiguos(int n){
        int[] res = new int[n];
        for (int i = 0; i < n; i++){
            Traslado traslado = trasMasAntiguos.desencolarMin();
            trasMasRedituables.desencolarPorIndice(traslado.indiceRedito());
            registrarGananciasYPerdidas(traslado);
            res[i] = traslado.id();
        }
        return res;
    }

    private void registrarGananciasYPerdidas(Traslado traslado){
        Ciudad origen = ciudades[traslado.origen()];
        Ciudad destino = ciudades[traslado.destino()];
        origen.aumentarGanancia(traslado.gananciaNeta());
        origen.actualizarBalance();
        ciudadesBalance.reacomodar(origen.indice());
        actualizarMaxGanancia(origen);
        destino.aumentarPerdida(traslado.gananciaNeta());
        destino.actualizarBalance();
        ciudadesBalance.reacomodar(destino.indice());
        actualizarMaxPerdida(destino);
        sumarGanancia(traslado.gananciaNeta());
        sumarTraslado();
    }

    private void actualizarMaxGanancia(Ciudad ciudad){
        if (ciudadesMasGanancia.isEmpty())
            ciudadesMasGanancia.add(ciudad.id());
        else{
            Ciudad mayor = ciudades[ciudadesMasGanancia.get(0)];
            if (ciudad == mayor || ciudad.ganancia() > mayor.ganancia()){
                ciudadesMasGanancia.clear();
                ciudadesMasGanancia.add(ciudad.id());
            }else if(ciudad.ganancia() == mayor.ganancia())
                ciudadesMasGanancia.add(ciudad.id());
        }
    }

    private void actualizarMaxPerdida(Ciudad ciudad){
        if (ciudadesMasPerdida.isEmpty())
            ciudadesMasPerdida.add(ciudad.id());
        else{
            Ciudad mayor = ciudades[ciudadesMasPerdida.get(0)];
            if (ciudad == mayor || ciudad.perdida() > mayor.perdida()){
                ciudadesMasPerdida.clear();
                ciudadesMasPerdida.add(ciudad.id());
            }else if(ciudad.perdida() == mayor.perdida())
                ciudadesMasPerdida.add(ciudad.id());
        }
    }

    public int ciudadConMayorSuperavit(){
        int res = ciudadesBalance.maximo().id();
        return res;
    }

    public ArrayList<Integer> ciudadesConMayorGanancia(){
        return this.ciudadesMasGanancia;
    }

    public ArrayList<Integer> ciudadesConMayorPerdida(){
        return this.ciudadesMasPerdida;
    }

    public int gananciaPromedioPorTraslado(){
        int res = 0;
        if (trasladosTotales != 0)
            res = gananciaTotal/trasladosTotales;
        return res;
    }
    
}
