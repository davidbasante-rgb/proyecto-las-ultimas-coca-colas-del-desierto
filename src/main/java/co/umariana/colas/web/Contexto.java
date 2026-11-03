package co.umariana.colas.web;

import co.umariana.colas.modelo.ServicioRefrescos;

/**
 * Punto único de acceso al modelo desde los servlets. NO se modifica.
 * Los servlets hacen {@code Contexto.servicio().listar()}, etc.
 */
public final class Contexto {

    private static volatile ServicioRefrescos servicio;

    private Contexto() {
    }

    public static ServicioRefrescos servicio() {
        ServicioRefrescos s = servicio;
        if (s == null) {
            throw new IllegalStateException("El modelo no está disponible todavía. Revise la consola del servidor: "
                    + "probablemente falta implementar alguna clase (GestorRefrescos, ListaCircular o RepositorioTxt).");
        }
        return s;
    }

    /** Lo usa {@link AppListener} al arrancar y las pruebas para poner un servicio de mentira. */
    public static void usar(ServicioRefrescos nuevo) {
        servicio = nuevo;
    }
}
