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
        validarTexto(codigo, "El código", true);
        validarTexto(titulo, "El título", true);
        validarTexto(categoria, "La categoría", true);
        validarTexto(archivo, "El archivo", true);
        validarTexto(descripcion, "La descripción", false);

        if (frio < 1 || frio > 10) {
            throw new IllegalArgumentException("El frío debe estar entre 1 y 10.");
        }
        if (litros <= 0) {
            throw new IllegalArgumentException("Los litros deben ser mayores que 0.");
        }
    }

    /** Revisa un campo de texto: obligatorio (si se pide) y sin ';' ni saltos de línea. */
    private static void validarTexto(String valor, String nombre, boolean obligatorio) {
        if (valor == null) {
            throw new IllegalArgumentException(nombre + " no puede ser nulo.");
        }
        if (obligatorio && valor.isBlank()) {
            throw new IllegalArgumentException(nombre + " no puede estar en blanco.");
        }
        if (valor.indexOf(';') >= 0) {
            throw new IllegalArgumentException(nombre + " no puede contener el carácter ';'.");
        }
        if (valor.indexOf('\n') >= 0 || valor.indexOf('\r') >= 0) {
            throw new IllegalArgumentException(nombre + " no puede tener saltos de línea.");
        }
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