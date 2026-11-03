package co.umariana.colas.persistencia;

import co.umariana.colas.modelo.Refresco;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

/**
 * Guarda los refrescos en un archivo de texto (tarea de PERSISTENCIA).
 *
 * <p>Formato: UNA línea por refresco, con los campos separados por {@code ;} y en este orden:
 * <pre>codigo;titulo;categoria;frio;litros;descripcion;archivo</pre>
 * Ejemplo:
 * <pre>L001;Clásica;Gaseosa;2;1.32;Refresco de ejemplo de todos los días;classic.png</pre>
 * <ul>
 *   <li>Codificación UTF-8 (para las tildes y la ñ).</li>
 *   <li>{@code litros} se escribe con punto decimal.</li>
 *   <li>{@code cargar}: si el archivo no existe devuelve una lista vacía; ignora las líneas en
 *       blanco y las líneas dañadas (cantidad de campos distinta de 7 o números inválidos).</li>
 *   <li>{@code guardar}: reemplaza todo el contenido del archivo y crea las carpetas que falten.
 *       Una lista vacía deja el archivo vacío.</li>
 * </ul>
 */
public class RepositorioTxt implements Repositorio {

    private final Path archivo;

    public RepositorioTxt(Path archivo) {
        this.archivo = archivo;
    }

    @Override
    public List<Refresco> cargar() throws IOException {
        // TODO (PERSISTENCIA)
        throw new UnsupportedOperationException("TODO: cargar()");
    }

    @Override
    public void guardar(List<Refresco> refrescos) throws IOException {
        // TODO (PERSISTENCIA)
        throw new UnsupportedOperationException("TODO: guardar()");
    }
}
