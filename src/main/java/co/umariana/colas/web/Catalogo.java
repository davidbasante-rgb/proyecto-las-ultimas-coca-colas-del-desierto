package co.umariana.colas.web;

import co.umariana.colas.modelo.Refresco;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/** Lee el catálogo de imágenes que vienen en el proyecto (catalogo.txt). NO se modifica. */
public final class Catalogo {

    /** Una imagen del catálogo: el refresco que se agregaría y de dónde salió. */
    public record Entrada(Refresco refresco, String autor) {
    }

    private static final List<Entrada> ENTRADAS = leer();

    private Catalogo() {
    }

    public static List<Entrada> entradas() {
        return ENTRADAS;
    }

    public static Entrada buscar(String codigo) {
        for (Entrada e : ENTRADAS) {
            if (e.refresco().getCodigo().equals(codigo)) {
                return e;
            }
        }
        return null;
    }

    private static List<Entrada> leer() {
        List<Entrada> entradas = new ArrayList<>();
        try (InputStream in = Catalogo.class.getResourceAsStream("/catalogo.txt")) {
            if (in == null) {
                return entradas;
            }
            BufferedReader lector = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            String linea;
            while ((linea = lector.readLine()) != null) {
                if (linea.isBlank() || linea.startsWith("#")) {
                    continue;
                }
                String[] c = linea.split(";", -1);
                Refresco l = new Refresco(c[0], c[1], c[2], Integer.parseInt(c[3]),
                        Double.parseDouble(c[4]), c[5], c[6]);
                entradas.add(new Entrada(l, c[7]));
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        return entradas;
    }
}
