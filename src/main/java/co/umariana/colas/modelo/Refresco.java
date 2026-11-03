package co.umariana.colas.modelo;

/**
 * Un refresco del Oasis: una imagen con su título, categoría y nivel de frío.
 *
 * <p>La clase ya tiene sus atributos, constructor y getters. Lo que falta es {@link #validar()}
 * (tarea de MODELO).
 */
public class Refresco {

    private final String codigo;
    private final String titulo;
    private final String categoria;
    private final int frio;
    private final double litros;
    private final String descripcion;
    private final String archivo;

    public Refresco(String codigo, String titulo, String categoria, int frio,
                   double litros, String descripcion, String archivo) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.categoria = categoria;
        this.frio = frio;
        this.litros = litros;
        this.descripcion = descripcion;
        this.archivo = archivo;
    }

    /**
     * Revisa que el refresco sea válido. Si no lo es, lanza {@link IllegalArgumentException}
     * con un mensaje que explique el problema. Reglas:
     * <ul>
     *   <li>{@code codigo}, {@code titulo}, {@code categoria} y {@code archivo} no pueden ser nulos ni estar en blanco.</li>
     *   <li>{@code frio} debe estar entre 1 y 10 (incluidos).</li>
     *   <li>{@code litros} debe ser mayor que 0.</li>
     *   <li>{@code descripcion} puede estar vacía, pero no ser nula.</li>
     *   <li>Ningún texto puede contener el carácter {@code ;} (separa los campos del archivo .txt)
     *       ni saltos de línea.</li>
     * </ul>
     */
    public void validar() {
        // TODO (MODELO): implementar las reglas descritas arriba.
        throw new UnsupportedOperationException("TODO: validar()");
    }

    public String getCodigo() { return codigo; }
    public String getTitulo() { return titulo; }
    public String getCategoria() { return categoria; }
    public int getFrio() { return frio; }
    public double getLitros() { return litros; }
    public String getDescripcion() { return descripcion; }
    public String getArchivo() { return archivo; }

    @Override
    public String toString() {
        return codigo + " - " + titulo + " (" + categoria + ", frío " + frio + ")";
    }
}
