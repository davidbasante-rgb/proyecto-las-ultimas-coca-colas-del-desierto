package co.umariana.colas.persistencia;

import co.umariana.colas.modelo.Refresco;

import java.io.IOException;
import java.util.List;

/** Contrato de la persistencia. NO se modifica. {@link RepositorioTxt} es la implementación. */
public interface Repositorio {

    /** Lee todos los refrescos guardados. Si todavía no hay nada guardado, devuelve una lista vacía. */
    List<Refresco> cargar() throws IOException;

    /** Guarda TODOS los refrescos, reemplazando lo que hubiera antes. */
    void guardar(List<Refresco> refrescos) throws IOException;
}
