package co.umariana.colas.web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * GET /visor  (tarea de SERVLETS)
 *
 * <p>Contrato (las pruebas lo revisan):
 * <ul>
 *   <li>Parámetro {@code accion}: {@code "siguiente"} usa {@code Contexto.servicio().siguiente()},
 *       {@code "anterior"} usa {@code anterior()}; sin parámetro (o con otro valor) usa {@code actual()}.</li>
 *   <li>Pone el refresco resultante (puede ser {@code null}) en el atributo {@code "refresco"} y la cantidad
 *       total en {@code "total"}.</li>
 *   <li>Reenvía a {@code /WEB-INF/vistas/visor.jsp}, que debe mostrar el refresco, un visor
 *       {@code <img>} (los archivos están en {@code media/}) y los botones Anterior / Siguiente.</li>
 * </ul>
 */
@WebServlet("/visor")
public class Visor extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String accion = req.getParameter("accion");

        req.setAttribute("total", Contexto.servicio().total());

        if ("siguiente".equals(accion)) {
            req.setAttribute("refresco", Contexto.servicio().siguiente());
        } else if ("anterior".equals(accion)) {
            req.setAttribute("refresco", Contexto.servicio().anterior());
        } else {
            req.setAttribute("refresco", Contexto.servicio().actual());
        }
        
        req.getRequestDispatcher("/WEB-INF/vistas/visor.jsp").forward(req, resp);
    }
}
