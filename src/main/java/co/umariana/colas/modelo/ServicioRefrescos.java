package co.umariana.colas.modelo;

import java.util.List;
import java.util.Map;

/**
 * Lo que la aplicación web necesita del modelo. NO se modifica.
 * {@link GestorRefrescos} es la implementación (tarea de MODELO).
 */
public interface ServicioRefrescos {

    /** Valida el refresco, rechaza códigos repetidos y guarda el cambio en el archivo. */
    void agregar(Refresco refresco);

    /** Devuelve el refresco con ese código, o {@code null} si no existe. */
    Refresco buscar(String codigo);

    /** Elimina el refresco con ese código y guarda el cambio. Devuelve {@code false} si no existía. */
    boolean eliminar(String codigo);

    /** Todos los refrescos, en el orden de la lista. */
    List<Refresco> listar();

    /** Lista NUEVA ordenada de mayor a menor frío (empate: por código). No altera el orden de la lista. */
    List<Refresco> ranking();

    /** Promedio de frío por categoría, ordenado alfabéticamente por categoría. */
    Map<String, Double> promedioPorCategoria();

    /** Cantidad de refrescos guardados. */
    int total();

    /** Refresco donde está el cursor (el visor), o {@code null} si no hay refrescos. */
    Refresco actual();

    /** Mueve el cursor al siguiente refresco y lo devuelve ({@code null} si no hay refrescos). */
    Refresco siguiente();

    /** Mueve el cursor al refresco anterior y lo devuelve ({@code null} si no hay refrescos). */
    Refresco anterior();
}
