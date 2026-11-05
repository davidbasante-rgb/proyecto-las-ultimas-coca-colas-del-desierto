package co.umariana.colas.persistencia;

import co.umariana.colas.modelo.Refresco;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class RepositorioTxt implements Repositorio {

    private final Path archivo;

    public RepositorioTxt(Path archivo) {
        this.archivo = archivo;
    }

    @Override
    public List<Refresco> cargar() throws IOException {
        List<Refresco> lista = new ArrayList<>();
        
        // Si el archivo no existe, devuelve una lista vacía[cite: 5]
        if (!Files.exists(archivo)) {
            return lista;
        }

        // Lee todas las líneas con codificación UTF-8[cite: 5]
        List<String> lineas = Files.readAllLines(archivo, StandardCharsets.UTF_8);

        for (String linea : lineas) {
            // Ignora las líneas en blanco[cite: 5]
            if (linea == null || linea.trim().isEmpty()) {
                continue;
            }

            String[] campos = linea.split(";");
            
            // Ignora cantidad de campos distinta de 7[cite: 5]
            if (campos.length != 7) {
                continue;
            }

            try {
                String codigo = campos[0];
                String titulo = campos[1];
                String categoria = campos[2];
                int frio = Integer.parseInt(campos[3]); // Según el ejemplo "2"[cite: 5]
                double litros = Double.parseDouble(campos[4]); // Parsea el decimal[cite: 5]
                String descripcion = campos[5];
                String archivoRefresco = campos[6];

                // IMPORTANTE: Asegúrate de que el orden de estas variables coincida con el constructor de tu clase Refresco
                Refresco refresco = new Refresco(codigo, titulo, categoria, frio, litros, descripcion, archivoRefresco);
                lista.add(refresco);
            } catch (NumberFormatException e) {
                // Ignora las líneas con números inválidos[cite: 5]
            }
        }
        return lista;
    }

    @Override
    public void guardar(List<Refresco> refrescos) throws IOException {
        // Crea las carpetas que falten[cite: 5]
        if (archivo.getParent() != null) {
            Files.createDirectories(archivo.getParent());
        }

        List<String> lineas = new ArrayList<>();
        for (Refresco r : refrescos) {
            // Formato: codigo;titulo;categoria;frio;litros;descripcion;archivo[cite: 5]
            // IMPORTANTE: Revisa que los nombres de los métodos "get" coincidan con los de tu clase Refresco
            String linea = r.getCodigo() + ";" +
                           r.getTitulo() + ";" +
                           r.getCategoria() + ";" +
                           r.getFrio() + ";" +
                           String.valueOf(r.getLitros()) + ";" + // String.valueOf garantiza el uso del punto decimal[cite: 5]
                           r.getDescripcion() + ";" +
                           r.getArchivo();
            lineas.add(linea);
        }

        // Reemplaza todo el contenido escribiendo en UTF-8 (una lista vacía deja el archivo vacío)[cite: 5]
        Files.write(archivo, lineas, StandardCharsets.UTF_8);
    }
}
