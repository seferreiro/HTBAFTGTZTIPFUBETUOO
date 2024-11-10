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
            res[i] = traslado.id();
            trasMasAntiguos.desencolarPorIndice(traslado.indiceTime());
            registrarGananciasYPerdidas(traslado);
        }
        return res;
    }

    public int[] despacharMasAntiguos(int n){
        int[] res = new int[n];
        for (int i = 0; i < n; i++){
            Traslado traslado = trasMasAntiguos.desencolarMin();
            res[i] = traslado.id();
            trasMasRedituables.desencolarPorIndice(traslado.indiceTime());
            registrarGananciasYPerdidas(traslado);
        }
        return res;
    }

    private void registrarGananciasYPerdidas(Traslado traslado){
        Ciudad origen = ciudades[traslado.origen()];
        Ciudad destino = ciudades[traslado.destino()];
        origen.aumentarGanancia(traslado.gananciaNeta());
        origen.actualizarBalance();
        ciudadesBalance.reacomodar(origen.indice());
        actualizarMaximosYMinimos(origen);
        destino.aumentarGanancia(traslado.gananciaNeta());
        destino.actualizarBalance();
        ciudadesBalance.reacomodar(destino.indice());
        actualizarMaximosYMinimos(destino);
        sumarGanancia(traslado.gananciaNeta());
        sumarTraslado();
    }

    private void actualizarMaximosYMinimos(Ciudad ciudad){
        if(ciudadesMasGanancia.isEmpty())
            ciudadesMasGanancia.add(ciudad.id());
        else{
            Ciudad mayor = ciudades[ciudadesMasGanancia.get(0)];
            if (ciudad == mayor || ciudad.ganancia() > mayor.ganancia()){
                ciudadesMasGanancia.clear();
                ciudadesMasGanancia.add(ciudad.id());
            } else if (ciudad.ganancia() == mayor.ganancia())
                ciudadesMasGanancia.add(ciudad.id());
        }
        if(ciudadesMasPerdida.isEmpty())
            ciudadesMasPerdida.add(ciudad.id());
        else{
            Ciudad mayor = ciudades[ciudadesMasPerdida.get(0)];
            if (ciudad == mayor || ciudad.perdida() > mayor.perdida()){
                ciudadesMasPerdida.clear();
                ciudadesMasPerdida.add(ciudad.id());
            } else if (ciudad.ganancia() == mayor.ganancia())
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
