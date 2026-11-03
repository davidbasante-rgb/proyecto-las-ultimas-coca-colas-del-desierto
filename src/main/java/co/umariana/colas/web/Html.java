package co.umariana.colas.web;

/** Utilidad para mostrar texto en HTML sin que se interprete como etiquetas. Se puede usar en los JSP. */
public final class Html {

    private Html() {
    }

    public static String esc(String texto) {
        if (texto == null) {
            return "";
        }
        return texto.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;");
    }
}
