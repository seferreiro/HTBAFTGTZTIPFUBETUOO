package aed;

import java.util.ArrayList;

public class BestEffort {

    private HeapRedito trasMasRedituables;           
    private TimeHeap trasMasAntiguos;    
    private HeapCiudad ciudadesSuperavit;
    private Ciudad[] ciudadesBalance;
    private ArrayList<Integer> ciudadesMasGanancia;
    private ArrayList<Integer> ciudadresMasPerdida;
    private GananciaPromedio gananciaPromedio;

    private class GananciaPromedio{         // Creamos la clase Ganancia Promedio donde solo podemos aumentar la ganacia
        int gananciaTotal=0;                // y los traslados totales
        int trasladosTotales=0; 

        void sumarGanancia(int i){
            this.gananciaTotal+=i;
        }

        void sumarTraslado(){
            this.trasladosTotales+=1;
        }

    }

    public BestEffort(int cantCiudades, Traslado[] traslados){
        trasMasRedituables = new HeapRedito();
        trasMasAntiguos = new TimeHeap();
        trasMasRedituables.ArrayToHeap(traslados);
        trasMasAntiguos.ArrayToHeap(traslados);
        registrarCiudades(cantCiudades);                                              
    }                                                                               // Este proceso nos cuesta O(|C|+|T|)

    private void registrarCiudades(int cantidad){ 
        ciudadesMasGanancia = new ArrayList<>();
        ciudadresMasPerdida = new ArrayList<>(); 
        ciudadesSuperavit = new HeapCiudad();
        ciudadesBalance = new Ciudad[cantidad];
        for (int i = 0; i<cantidad; i++){
            Ciudad ciudad = new Ciudad(i, 0, 0, 0);
            ciudadesBalance[i] = ciudad;
        }
        ciudadesSuperavit.ArrayToHeap(ciudadesBalance);
    }

    public final void registrarTraslados(Traslado[] traslados){  
        for (Traslado traslado : traslados){
            trasMasRedituables.encolar(traslado);
            trasMasAntiguos.encolar(traslado);
        }   
    }

    public int[] despacharMasRedituables(int n){
        int[] res = new int[n];
        for (int i=0; i< n; i++){ 
            Traslado T = trasMasRedituables.desencolarMax();
            res[i] = T.id();
            trasMasAntiguos.desencolarPorIndice(T.indiceTime());
            actualizarBalance(T); 
        }
        return res;
    }

    public int[] despacharMasAntiguos(int n){
        int[] res = new int[n];
        for (int i=0; i< n; i++){ 
            Traslado T = trasMasAntiguos.desencolarMin();
            res[i] = T.id();
            trasMasRedituables.desencolarPorIndice(T.indiceRedito());
            actualizarBalance(T); 
        }
        return res;
    }

    private void actualizarBalance(Traslado traslado){
        Ciudad origen = ciudadesBalance[traslado.origen()];
        Ciudad destino = ciudadesBalance[traslado.destino()];
        origen.aumentarGanancia(traslado.gananciaNeta());
        origen.actualizarBalance();
        destino.aumentarPerdida(traslado.gananciaNeta());
        destino.actualizarBalance();
        ciudadesSuperavit.reacomodar(origen.indice());
        ciudadesSuperavit.reacomodar(destino.indice());
        actualizarMaximosYMinimos(origen);
        actualizarMaximosYMinimos(destino);
    }

    private void actualizarMaximosYMinimos(Ciudad ciudad){
        if (ciudadesConMayorGanancia().isEmpty())
            ciudadesMasGanancia.add(ciudad.id());
        else{
            Ciudad masGanancia = ciudadesBalance[ciudadesMasGanancia.get(0)];
            if (ciudad.ganancia() == masGanancia.ganancia())
                ciudadesMasGanancia.add(ciudad.id());
            if (ciudad.ganancia() > masGanancia.ganancia()){
                ciudadesMasGanancia.clear();
                ciudadesMasGanancia.add(ciudad.id());
            }
        }
        if (ciudadesConMayorPerdida().isEmpty())
            ciudadresMasPerdida.add(ciudad.id());
        else{
            Ciudad masPerdida = ciudadesBalance[ciudadresMasPerdida.get(0)];
            if (ciudad.perdida() == masPerdida.perdida())
                ciudadresMasPerdida.add(ciudad.id());
            if (ciudad.perdida() > masPerdida.perdida()){
                ciudadresMasPerdida.clear();
                ciudadresMasPerdida.add(ciudad.id());
            }
        }
    }

    public int ciudadConMayorSuperavit(){                                   // Devolvemos el maximo de nuestro Heap de balance
        return this.ciudadesSuperavit.maximo().id();                        // Costo: O(1)
    }

    public ArrayList<Integer> ciudadesConMayorGanancia(){                   // Devolvemos nuestro Array con las ciudades de mayor ganancia
        return this.ciudadesMasGanancia;                                    // Costo: O(1)
    }

    public ArrayList<Integer> ciudadesConMayorPerdida(){                    // Devolvemos nuestro Array con las ciudades de mayor perdida
        return this.ciudadresMasPerdida;                                    // Costo: O(1)
    }   

    public int gananciaPromedioPorTraslado(){  
        int res = 0;                                                        // Si no tenemos traslados devolvemos 0
        if(gananciaPromedio.trasladosTotales!=0)                            // Calculamos la ganancia promedio y la devolvemos 
            res = (gananciaPromedio.gananciaTotal)/gananciaPromedio.trasladosTotales;
        return res;                                                         // Costo: O(1)
    }
    
}
