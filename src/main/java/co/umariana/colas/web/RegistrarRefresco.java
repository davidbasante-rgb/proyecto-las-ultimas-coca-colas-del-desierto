package co.umariana.colas.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import co.umariana.colas.modelo.Refresco;

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
        req.getRequestDispatcher("/WEB-INF/vistas/formulario.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String codigo = req.getParameter("codigo");
        String titulo = req.getParameter("titulo");
        String categoria = req.getParameter("categoria");
        String frioStr = req.getParameter("frio");
        String litrosStr = req.getParameter("litros");
        String descripcion = req.getParameter("descripcion");
        String archivo = req.getParameter("archivo");

        try {
            int frio = Integer.parseInt(frioStr);
            double litros = Double.parseDouble(litrosStr);

            Refresco refresco = new Refresco(codigo, titulo, categoria, frio, litros, descripcion, archivo);
            Contexto.servicio().agregar(refresco);

            resp.sendRedirect("listar");
        } catch (IllegalArgumentException e) {
            //No se aplica NumberFormatException ya que IllegalArgumentException es la superclase de NumberFormatException,
            // por lo que se puede capturar solo IllegalArgumentException
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/vistas/formulario.jsp").forward(req, resp);
        }
    }
}
