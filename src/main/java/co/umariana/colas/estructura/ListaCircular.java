package co.umariana.colas.estructura;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Lista enlazada CIRCULAR del equipo (tarea de ESTRUCTURA).
 *
 * <p>El último nodo apunta de nuevo al primero. Implemente la lista con sus propios nodos: no se
 * permite usar {@code ArrayList}, {@code LinkedList} ni ninguna otra colección de {@code java.util}
 * para guardar los elementos (las pruebas lo revisan).
 *
 * <p>Comportamiento especial por ser circular: el cursor NUNCA se detiene. {@link #avanzar()} desde
 * el último elemento vuelve al primero, y {@link #retroceder()} desde el primero va al último.
 * Ambos devuelven {@code true} mientras la lista no esté vacía.
 *
 * <p>El iterador recorre los elementos UNA sola vez, desde el primero hasta el último (¡sin quedar
 * dando vueltas para siempre!).
 */
public class ListaCircular<T> implements Lista<T> {

       /** Nodo de la lista: guarda un dato y apunta al siguiente. El último apunta a la cabeza. */
    private class Nodo {
        private T dato;
        private Nodo siguiente;

        private Nodo(T dato) {
            this.dato = dato;
        }
    }

    /** Primer nodo de la lista (null cuando está vacía). */
    private Nodo cabeza;

    /** Nodo donde está parado el cursor del visor (null cuando la lista está vacía). */
    private Nodo cursor;

    /** Cantidad de elementos. Se mantiene actualizada en cada operación. */
    private int cantidad;

       @Override
    public void agregar(T elemento) {
        // Agregar al final es insertar en la posición que hoy no existe todavía.
        insertar(cantidad, elemento);
    }

    @Override
    public void insertar(int indice, T elemento) {
        // Aquí sí se admite indice == cantidad, porque insertar al final es válido.
        if (indice < 0 || indice > cantidad) {
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + indice);
        }

        Nodo nuevo = new Nodo(elemento);

        if (cantidad == 0) {
            // Primer nodo: se apunta a sí mismo para cerrar el círculo, y el cursor arranca en él.
            nuevo.siguiente = nuevo;
            cabeza = nuevo;
            cursor = nuevo;
        } else if (indice == 0) {
            // Al entrar antes de la cabeza, el último debe pasar a apuntar al nuevo primero.
            Nodo ultimo = nodoEn(cantidad - 1);
            nuevo.siguiente = cabeza;
            ultimo.siguiente = nuevo;
            cabeza = nuevo;
        } else {
            // En cualquier otra posición se engancha después del nodo anterior.
            Nodo anterior = nodoEn(indice - 1);
            nuevo.siguiente = anterior.siguiente;
            anterior.siguiente = nuevo;
        }

        cantidad++;
    }
    @Override
    public T obtener(int indice) {
        validarIndice(indice);
        return nodoEn(indice).dato;
    }

     @Override
    public T eliminar(int indice) {
        validarIndice(indice);

        Nodo eliminado;

        if (cantidad == 1) {
            // Era el único nodo: la lista queda vacía y el cursor se apaga.
            eliminado = cabeza;
            cabeza = null;
            cursor = null;
            cantidad = 0;
            return eliminado.dato;
        }

        if (indice == 0) {
            // El último tiene que seguir cerrando el círculo contra la nueva cabeza.
            Nodo ultimo = nodoEn(cantidad - 1);
            eliminado = cabeza;
            cabeza = cabeza.siguiente;
            ultimo.siguiente = cabeza;
        } else {
            // El nodo anterior se engancha directamente con el siguiente del eliminado.
            Nodo anterior = nodoEn(indice - 1);
            eliminado = anterior.siguiente;
            anterior.siguiente = eliminado.siguiente;
        }

        // Si se eliminó justo el nodo del cursor, el cursor pasa al que le seguía.
        if (cursor == eliminado) {
            cursor = eliminado.siguiente;
        }

        cantidad--;
        return eliminado.dato;
    }

       @Override
    public int tamano() {
        return cantidad;
    }

    @Override
    public boolean estaVacia() {
        return cantidad == 0;
    }

    @Override
    public void vaciar() {
        // Al soltar la cabeza, todo el círculo queda sin referencias.
        cabeza = null;
        cursor = null;
        cantidad = 0;
    }

       @Override
    public T actual() {
        return cursor == null ? null : cursor.dato;
    }

    @Override
    public boolean avanzar() {
        if (cursor == null) {
            return false;
        }
        // Como el último apunta a la cabeza, avanzar da la vuelta sin ningún caso especial.
        cursor = cursor.siguiente;
        return true;
    }

    @Override
    public boolean retroceder() {
        if (cursor == null) {
            return false;
        }

        // Los nodos solo apuntan hacia adelante, así que para ir atrás hay que dar la vuelta
        // buscando el nodo cuyo siguiente es el cursor.
        Nodo anterior = cursor;
        while (anterior.siguiente != cursor) {
            anterior = anterior.siguiente;
        }

        cursor = anterior;
        return true;
    }
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {

            private Nodo siguienteNodo = cabeza;
            private int visitados = 0;

            @Override
            public boolean hasNext() {
                // Se cuentan las visitas: sin este contador el recorrido no terminaría nunca.
                return visitados < cantidad;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException("No hay más elementos");
                }

                T dato = siguienteNodo.dato;
                siguienteNodo = siguienteNodo.siguiente;
                visitados++;
                return dato;
            }
        };
    }

        /**
     * Valida que el índice corresponda a una posición existente.
     *
     * @throws IndexOutOfBoundsException si indice < 0 o indice >= tamano()
     */
    private void validarIndice(int indice) {
        if (indice < 0 || indice >= cantidad) {
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + indice);
        }
    }

    /** Recorre desde la cabeza y devuelve el nodo de la posición pedida. */
    private Nodo nodoEn(int indice) {
        Nodo actual = cabeza;
        for (int paso = 0; paso < indice; paso++) {
            actual = actual.siguiente;
        }
        return actual;
    }
}
