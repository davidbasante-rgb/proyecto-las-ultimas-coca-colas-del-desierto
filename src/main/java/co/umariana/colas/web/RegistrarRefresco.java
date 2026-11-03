package co.umariana.colas.web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * /registrar  (tarea de SERVLETS)
 *
 * <p>Contrato (las pruebas lo revisan):
 * <ul>
 *   <li><b>GET:</b> reenvía a {@code /WEB-INF/vistas/formulario.jsp}.</li>
 *   <li><b>POST:</b> lee los parámetros {@code codigo, titulo, categoria, frio, litros,
 *       descripcion, archivo}; crea un {@code Refresco} (el frío es un entero y el volumen un
 *       decimal) y llama a {@code Contexto.servicio().agregar(...)}. Si todo sale bien, redirige a
 *       {@code "listar"} ({@code resp.sendRedirect("listar")}).</li>
 *   <li><b>Si algo falla</b> ({@link NumberFormatException} o {@link IllegalArgumentException}), NO redirige:
 *       pone el mensaje de la excepción en el atributo {@code "error"} y reenvía de nuevo a
 *       {@code formulario.jsp}, que debe mostrar ese error.</li>
 * </ul>
 */
@WebServlet("/registrar")
public class RegistrarRefresco extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // TODO (SERVLETS)
        throw new UnsupportedOperationException("TODO: RegistrarRefresco.doGet()");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // TODO (SERVLETS)
        throw new UnsupportedOperationException("TODO: RegistrarRefresco.doPost()");
    }
}
