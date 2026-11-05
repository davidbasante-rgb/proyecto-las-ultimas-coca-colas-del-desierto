package co.umariana.colas.web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import co.umariana.colas.modelo.GestorRefrescos;

import java.io.IOException;

/**
 * POST /eliminar  (tarea de SERVLETS)
 *
 * <p>Contrato: toma el parámetro {@code codigo}, llama a {@code Contexto.servicio().eliminar(codigo)}
 * y redirige a {@code "listar"}.
 */
@WebServlet("/eliminar")
public class EliminarRefresco extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // TODO (SERVLETS)
        String codigo = req.getParameter("codigo");
        GestorRefrescos gestor = (GestorRefrescos) req.getServletContext().getAttribute("gestorRefrescos");
        gestor.eliminar(codigo);
        resp.sendRedirect("listar");
    }
}
