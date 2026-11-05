package co.umariana.colas.modelo;

import co.umariana.colas.estructura.Lista;
import co.umariana.colas.persistencia.Repositorio;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Reglas de negocio del Oasis (tarea de MODELO).
 *
 * <p>Guarda los refrescos en una {@link Lista} (la estructura de datos del equipo) y, después de
 * cada cambio, los guarda con el {@link Repositorio}. No debe usar {@code ArrayList} ni otra
 * colección de Java para guardar los datos: eso lo hace la {@code Lista}.
 */
public class GestorRefrescos implements ServicioRefrescos {

    private final Lista<Refresco> lista;
    private final Repositorio repositorio;

    /**
     * Al crearse, carga con el repositorio los refrescos ya guardados y los agrega a la lista.
     * Si el repositorio lanza {@link java.io.IOException}, se relanza como {@link java.io.UncheckedIOException}.
     */
    public GestorRefrescos(Lista<Refresco> lista, Repositorio repositorio) {
        this.lista = lista;
        this.repositorio = repositorio;
        try {
            for (Refresco refresco : repositorio.cargar()) {
                lista.agregar(refresco);
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public void agregar(Refresco refresco) {
        if (refresco == null) {
            throw new IllegalArgumentException("El refresco no puede ser nulo.");
        }
        refresco.validar();
        if (buscar(refresco.getCodigo()) != null) {
            throw new IllegalArgumentException("Ya existe un refresco con el código " + refresco.getCodigo() + ".");
        }
        lista.agregar(refresco);
        guardar();
    }

    @Override
    public Refresco buscar(String codigo) {
        if (codigo == null) {
            return null;
        }
        for (Refresco refresco : lista) {
            if (refresco.getCodigo().equals(codigo)) {
                return refresco;
            }
        }
        return null;
    }

    @Override
    public boolean eliminar(String codigo) {
        if (codigo == null) {
            return false;
        }
        int indice = 0;
        for (Refresco refresco : lista) {
            if (refresco.getCodigo().equals(codigo)) {
                lista.eliminar(indice);
                guardar();
                return true;
            }
            indice++;
        }
        return false;
    }

    @Override
    public List<Refresco> listar() {
        List<Refresco> copia = new ArrayList<>();
        for (Refresco refresco : lista) {
            copia.add(refresco);
        }
        return copia;
    }

    @Override
    public List<Refresco> ranking() {
        List<Refresco> copia = listar();
        copia.sort(Comparator.comparingInt(Refresco::getFrio).reversed()
                .thenComparing(Refresco::getCodigo));
        return copia;
    }

    @Override
    public Map<String, Double> promedioPorCategoria() {
        Map<String, Integer> sumas = new TreeMap<>();
        Map<String, Integer> cantidades = new TreeMap<>();
        for (Refresco refresco : lista) {
            sumas.merge(refresco.getCategoria(), refresco.getFrio(), Integer::sum);
            cantidades.merge(refresco.getCategoria(), 1, Integer::sum);
        }
        Map<String, Double> promedios = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entrada : sumas.entrySet()) {
            String categoria = entrada.getKey();
            promedios.put(categoria, (double) entrada.getValue() / cantidades.get(categoria));
        }
        return promedios;
    }

    @Override
    public int total() {
        return lista.tamano();
    }

    @Override
    public Refresco actual() {
        return lista.actual();
    }

    @Override
    public Refresco siguiente() {
        if (lista.estaVacia()) {
            return null;
        }
        lista.avanzar();
        return lista.actual();
    }

    @Override
    public Refresco anterior() {
        if (lista.estaVacia()) {
            return null;
        }
        lista.retroceder();
        return lista.actual();
    }

    /** Guarda en el repositorio el estado actual de la lista. */
    private void guardar() {
        try {
            repositorio.guardar(listar());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}