package aed;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ArregloRedimensionableTest {

    @Test
    public void testAgregarAtras() {
        ArregloRedimensionable arreglo = new ArregloRedimensionable();

        // Crear un par de objetos Traslado
        Traslado t1 = new Traslado(1, 0, 1, 100, 10);
        Traslado t2 = new Traslado(2, 1, 2, 200, 20);

        // Agregar traslados
        arreglo.agregarAtras(t1);
        arreglo.agregarAtras(t2);

        // Verificar que los traslados han sido agregados correctamente
        assertEquals(2, arreglo.longitud()); // La longitud debe ser 2
        assertEquals(t1, arreglo.obtener(0)); // El primer traslado debe ser t1
        assertEquals(t2, arreglo.obtener(1)); // El segundo traslado debe ser t2
    }

    @Test
    public void testRedimensionar() {
        ArregloRedimensionable arreglo = new ArregloRedimensionable();

        // Crear y agregar más de 50 traslados para provocar la redimensión
        for (int i = 0; i < 60; i++) {
            Traslado t = new Traslado(i, 0, 1, i * 10, 100 + i);
            arreglo.agregarAtras(t);
        }

        // Verificar que el arreglo ha redimensionado
        assertEquals(60, arreglo.longitud()); // La longitud debe ser 60 después de agregar 60 elementos
    }

    @Test
    public void testQuitarAtras() {
        ArregloRedimensionable arreglo = new ArregloRedimensionable();

        // Crear traslados
        Traslado t1 = new Traslado(1, 0, 1, 100, 10);
        Traslado t2 = new Traslado(2, 1, 2, 200, 20);
        Traslado t3 = new Traslado(3, 2, 3, 300, 30);

        // Agregar traslados
        arreglo.agregarAtras(t1);
        arreglo.agregarAtras(t2);
        arreglo.agregarAtras(t3);

        // Verificar longitud antes de quitar
        assertEquals(3, arreglo.longitud());

        // Quitar el último traslado
        arreglo.quitarAtras();

        // Verificar longitud después de quitar
        assertEquals(2, arreglo.longitud()); // Ahora la longitud debe ser 2
        assertEquals(t1, arreglo.obtener(0)); // El primer traslado debe seguir siendo t1
        assertEquals(t2, arreglo.obtener(1)); // El segundo traslado debe seguir siendo t2
    }

    @Test
    public void testDefinir() {
        ArregloRedimensionable arreglo = new ArregloRedimensionable();

        // Crear un traslado
        Traslado t1 = new Traslado(1, 0, 1, 100, 10);

        // Agregar el traslado al arreglo
        arreglo.agregarAtras(t1);

        // Verificar que el traslado esté en la posición correcta
        assertEquals(t1, arreglo.obtener(0));

        // Crear un nuevo traslado
        Traslado t2 = new Traslado(2, 1, 2, 200, 20);

        // Reemplazar el primer traslado
        arreglo.definir(0, t2);

        // Verificar que el nuevo traslado ha sido agregado correctamente
        assertEquals(t2, arreglo.obtener(0)); // Ahora la posición 0 debe contener t2
    }

    @Test
    public void testObtener() {
        ArregloRedimensionable arreglo = new ArregloRedimensionable();

        // Crear traslados
        Traslado t1 = new Traslado(1, 0, 1, 100, 10);
        Traslado t2 = new Traslado(2, 1, 2, 200, 20);

        // Agregar traslados
        arreglo.agregarAtras(t1);
        arreglo.agregarAtras(t2);

        // Verificar que obtener() devuleve los elementos correctos
        assertEquals(t1, arreglo.obtener(0));
        assertEquals(t2, arreglo.obtener(1));
    }
}
