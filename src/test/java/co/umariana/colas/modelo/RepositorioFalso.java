package co.umariana.colas.modelo;

import co.umariana.colas.persistencia.Repositorio;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/** Repositorio de mentira (en memoria) para probar el modelo sin depender del archivo .txt. */
class RepositorioFalso implements Repositorio {

    List<Refresco> guardados = new ArrayList<>();
    int vecesGuardado = 0;
    boolean fallar = false;

    @Override
    public List<Refresco> cargar() throws IOException {
        if (fallar) throw new IOException("fallo simulado");
        return new ArrayList<>(guardados);
    }

    @Override
    public void guardar(List<Refresco> refrescos) throws IOException {
        if (fallar) throw new IOException("fallo simulado");
        guardados = new ArrayList<>(refrescos);
        vecesGuardado++;
    }
}
