package co.umariana.colas.estructura;

/**
 * Contrato de la lista del equipo. NO se modifica.
 *
 * <p>Además de las operaciones de siempre, la lista tiene un CURSOR: una posición "actual" que el
 * visor mueve hacia adelante y hacia atrás. El cursor empieza en el primer elemento.
 */
public interface Lista<T> extends Iterable<T> {

    /** Agrega al final. */
    void agregar(T elemento);

    /** Inserta en la posición {@code indice} (0 = al inicio, {@code tamano()} = al final). */
    void insertar(int indice, T elemento);

    /** Devuelve el elemento de la posición {@code indice}. */
    T obtener(int indice);

    /** Elimina y devuelve el elemento de la posición {@code indice}. */
    T eliminar(int indice);

    int tamano();

    boolean estaVacia();

    /** Elimina todos los elementos. */
    void vaciar();

    /** Elemento donde está el cursor, o {@code null} si la lista está vacía. */
    T actual();

    /** Mueve el cursor al siguiente elemento. Devuelve {@code false} si no se pudo mover. */
    boolean avanzar();

    /** Mueve el cursor al elemento anterior. Devuelve {@code false} si no se pudo mover. */
    boolean retroceder();
}
