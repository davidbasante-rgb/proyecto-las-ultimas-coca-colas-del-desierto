package co.umariana.colas.web;

import co.umariana.colas.modelo.Refresco;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Página del catálogo: muestra los imágenes que vienen en el proyecto y permite agregarlos al
 * Oasis con un botón. Ya está hecha: es la forma de tener datos para probar la aplicación.
 * NO se modifica.
 */
@WebServlet("/catalogo")
public class CatalogoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=UTF-8");
        String ok = req.getParameter("ok");
        String error = req.getParameter("error");
        PrintWriter out = resp.getWriter();
        out.println("<!DOCTYPE html><html lang=\"es\"><head><meta charset=\"UTF-8\">");
        out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">");
        out.println("<title>Catálogo de refrescos</title></head><body>");
        out.println("<header><h1>🥤 Oasis</h1><p>Catálogo de imágenes</p></header>");
        out.println("<nav><a href=\"index.html\">Inicio</a></nav><main>");
        out.println("<h2>Imágenes disponibles</h2>");
        out.println("<p>Pulse <strong>Agregar</strong> para guardar una imagen en el Oasis (usa su <code>GestorRefrescos</code>).</p>");
        if (ok != null) {
            out.println("<p>✔ Se agregó " + Html.esc(ok) + ".</p>");
        }
        if (error != null) {
            out.println("<p>✖ " + Html.esc(error) + "</p>");
        }
        for (Catalogo.Entrada e : Catalogo.entradas()) {
            Refresco l = e.refresco();
            boolean agregado = yaAgregado(l.getCodigo());
            out.println("<article>");
            out.println("<h3>" + Html.esc(l.getTitulo()) + " <small>" + Html.esc(l.getCodigo()) + "</small></h3>");
            out.println("<p><span>" + Html.esc(l.getCategoria()) + "</span>"
                    + "<span>Frío " + l.getFrio() + "/10</span>"
                    + "<span>" + l.getLitros() + " L</span></p>");
            out.println("<p>" + Html.esc(l.getDescripcion()) + "</p>");
            out.println("<p>Fuente: " + Html.esc(e.autor()) + "</p>");
            out.println("<img width=\"200\" alt=\"" + Html.esc(l.getTitulo()) + "\" src=\"media/" + Html.esc(l.getArchivo()) + "\">");
            out.println("<form method=\"post\" action=\"catalogo\"><input type=\"hidden\" name=\"codigo\" value=\""
                    + Html.esc(l.getCodigo()) + "\">"
                    + (agregado ? "<button disabled>Ya agregado</button>" : "<button>Agregar</button>") + "</form>");
            out.println("</article>");
        }
        out.println("</main><footer>Proyecto final · Estructuras de Datos I</footer></body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Catalogo.Entrada entrada = Catalogo.buscar(req.getParameter("codigo"));
        if (entrada == null) {
            resp.sendRedirect("catalogo?error=" + codificar("Esa imagen no está en el catálogo."));
            return;
        }
        try {
            Contexto.servicio().agregar(entrada.refresco());
            resp.sendRedirect("catalogo?ok=" + codificar(entrada.refresco().getTitulo()));
        } catch (UnsupportedOperationException | IllegalStateException e) {
            resp.sendRedirect("catalogo?error=" + codificar("Todavía falta implementar una parte: " + e.getMessage()));
        } catch (IllegalArgumentException e) {
            resp.sendRedirect("catalogo?error=" + codificar(e.getMessage()));
        }
    }

    private static boolean yaAgregado(String codigo) {
        try {
            return Contexto.servicio().buscar(codigo) != null;
        } catch (RuntimeException e) {
            return false; // el modelo aún no está listo: se deja el botón activo
        }
    }

    private static String codificar(String texto) {
        return URLEncoder.encode(String.valueOf(texto), StandardCharsets.UTF_8);
    }
}
