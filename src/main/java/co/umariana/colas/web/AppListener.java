package co.umariana.colas.web;

import co.umariana.colas.estructura.ListaCircular;
import co.umariana.colas.modelo.GestorRefrescos;
import co.umariana.colas.modelo.Refresco;
import co.umariana.colas.persistencia.RepositorioTxt;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.nio.file.Path;

/** Arma el modelo al arrancar el servidor (lista + archivo + gestor). NO se modifica. */
@WebListener
public class AppListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent evento) {
        Path archivo = Path.of(System.getProperty("refrescos.archivo", "refrescos.txt"));
        try {
            Contexto.usar(new GestorRefrescos(new ListaCircular<Refresco>(), new RepositorioTxt(archivo)));
            evento.getServletContext().log("Oasis listo. Datos en: " + archivo.toAbsolutePath());
        } catch (Throwable e) {
            // Mientras el equipo no termine sus clases, la aplicación arranca igual pero sin modelo.
            evento.getServletContext().log("El modelo todavía no está disponible: " + e);
            Contexto.usar(null);
        }
    }
}
