package co.umariana.colas.modelo;

import co.umariana.colas.estructura.Lista;
import co.umariana.colas.persistencia.Repositorio;

import java.util.List;
import java.util.Map;

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
        // TODO (MODELO): cargar los refrescos del repositorio y agregarlos a la lista.
    }

    @Override
    public void agregar(Refresco refresco) {
        // TODO (MODELO): validar (refresco.validar()), rechazar código repetido
        //  (IllegalArgumentException), agregar al final de la lista y guardar con el repositorio.
        throw new UnsupportedOperationException("TODO: agregar()");
    }

    @Override
    public Refresco buscar(String codigo) {
        // TODO (MODELO)
        throw new UnsupportedOperationException("TODO: buscar()");
    }

    @Override
    public boolean eliminar(String codigo) {
        // TODO (MODELO): si existe, eliminarlo de la lista, guardar y devolver true.
        throw new UnsupportedOperationException("TODO: eliminar()");
    }

    @Override
    public List<Refresco> listar() {
        // TODO (MODELO): devolver los refrescos en el orden de la lista.
        throw new UnsupportedOperationException("TODO: listar()");
    }

    @Override
    public List<Refresco> ranking() {
        // TODO (MODELO)
        throw new UnsupportedOperationException("TODO: ranking()");
    }

    @Override
    public Map<String, Double> promedioPorCategoria() {
        // TODO (MODELO)
        throw new UnsupportedOperationException("TODO: promedioPorCategoria()");
    }

    @Override
    public int total() {
        // TODO (MODELO)
        throw new UnsupportedOperationException("TODO: total()");
    }

    @Override
    public Refresco actual() {
        // TODO (MODELO): usar el cursor de la lista.
        throw new UnsupportedOperationException("TODO: actual()");
    }

    @Override
    public Refresco siguiente() {
        // TODO (MODELO)
        throw new UnsupportedOperationException("TODO: siguiente()");
    }

    @Override
    public Refresco anterior() {
        // TODO (MODELO)
        throw new UnsupportedOperationException("TODO: anterior()");
    }
}
