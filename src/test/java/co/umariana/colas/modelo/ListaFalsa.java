package co.umariana.colas.modelo;

import co.umariana.colas.estructura.Lista;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Lista de mentira (con ArrayList) para probar el modelo sin depender de la ListaCircular del equipo.
 * Es circular: el cursor da la vuelta.
 */
class ListaFalsa<T> implements Lista<T> {

    private final List<T> datos = new ArrayList<>();
    private int cursor = 0;

    @Override public void agregar(T e) { datos.add(e); }
    @Override public void insertar(int i, T e) { datos.add(i, e); }
    @Override public T obtener(int i) { return datos.get(i); }
    @Override public T eliminar(int i) {
        T r = datos.remove(i);
        if (cursor >= datos.size()) {
            cursor = 0;
        }
        return r;
    }
    @Override public int tamano() { return datos.size(); }
    @Override public boolean estaVacia() { return datos.isEmpty(); }
    @Override public void vaciar() { datos.clear(); cursor = 0; }
    @Override public T actual() { return datos.isEmpty() ? null : datos.get(cursor); }
    @Override public boolean avanzar() {
        if (datos.isEmpty()) return false;
        cursor = (cursor + 1) % datos.size();
        return true;
    }
    @Override public boolean retroceder() {
        if (datos.isEmpty()) return false;
        cursor = (cursor - 1 + datos.size()) % datos.size();
        return true;
    }
    @Override public Iterator<T> iterator() { return new ArrayList<>(datos).iterator(); }
}
