package co.umariana.colas.modelo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RefrescoTest {

    private static Refresco refresco(String codigo, String titulo, String categoria, int frio,
                                   double litros, String descripcion, String archivo) {
        return new Refresco(codigo, titulo, categoria, frio, litros, descripcion, archivo);
    }

    private static Refresco valido() {
        return refresco("L001", "Clásica", "Gaseosa", 2, 1.32, "Refresco de ejemplo", "classic.png");
    }

    @Test
    @DisplayName("un refresco correcto es válido")
    void valido_noLanza() {
        assertDoesNotThrow(() -> valido().validar());
    }

    @Test
    @DisplayName("la descripción puede estar vacía")
    void descripcionVaciaEsValida() {
        assertDoesNotThrow(() -> refresco("L1", "T", "C", 5, 1, "", "a.png").validar());
    }

    @Test
    @DisplayName("los límites del frío (1 y 10) son válidos")
    void limitesValidos() {
        assertDoesNotThrow(() -> refresco("L1", "T", "C", 1, 1, "", "a.png").validar());
        assertDoesNotThrow(() -> refresco("L1", "T", "C", 10, 1, "", "a.png").validar());
    }

    @Test
    @DisplayName("el frío fuera de 1..10 es inválida")
    void frioFueraDeRango() {
        assertThrows(IllegalArgumentException.class, () -> refresco("L1", "T", "C", 0, 1, "", "a.png").validar());
        assertThrows(IllegalArgumentException.class, () -> refresco("L1", "T", "C", 11, 1, "", "a.png").validar());
        assertThrows(IllegalArgumentException.class, () -> refresco("L1", "T", "C", -3, 1, "", "a.png").validar());
    }

    @Test
    @DisplayName("el volumen debe ser mayor que 0")
    void litrosInvalida() {
        assertThrows(IllegalArgumentException.class, () -> refresco("L1", "T", "C", 5, 0, "", "a.png").validar());
        assertThrows(IllegalArgumentException.class, () -> refresco("L1", "T", "C", 5, -1.5, "", "a.png").validar());
    }

    @Test
    @DisplayName("código, título, categoría y archivo no pueden ser nulos ni estar en blanco")
    void textosObligatorios() {
        assertThrows(IllegalArgumentException.class, () -> refresco(null, "T", "C", 5, 1, "", "a.png").validar());
        assertThrows(IllegalArgumentException.class, () -> refresco("  ", "T", "C", 5, 1, "", "a.png").validar());
        assertThrows(IllegalArgumentException.class, () -> refresco("L1", "", "C", 5, 1, "", "a.png").validar());
        assertThrows(IllegalArgumentException.class, () -> refresco("L1", null, "C", 5, 1, "", "a.png").validar());
        assertThrows(IllegalArgumentException.class, () -> refresco("L1", "T", " ", 5, 1, "", "a.png").validar());
        assertThrows(IllegalArgumentException.class, () -> refresco("L1", "T", "C", 5, 1, "", "").validar());
        assertThrows(IllegalArgumentException.class, () -> refresco("L1", "T", "C", 5, 1, "", null).validar());
    }

    @Test
    @DisplayName("la descripción no puede ser nula")
    void descripcionNula() {
        assertThrows(IllegalArgumentException.class, () -> refresco("L1", "T", "C", 5, 1, null, "a.png").validar());
    }

    @Test
    @DisplayName("ningún texto puede contener ';' (separa los campos del .txt)")
    void sinPuntoYComa() {
        assertThrows(IllegalArgumentException.class, () -> refresco("L;1", "T", "C", 5, 1, "", "a.png").validar());
        assertThrows(IllegalArgumentException.class, () -> refresco("L1", "T;x", "C", 5, 1, "", "a.png").validar());
        assertThrows(IllegalArgumentException.class, () -> refresco("L1", "T", "C;x", 5, 1, "", "a.png").validar());
        assertThrows(IllegalArgumentException.class, () -> refresco("L1", "T", "C", 5, 1, "a;b", "a.png").validar());
        assertThrows(IllegalArgumentException.class, () -> refresco("L1", "T", "C", 5, 1, "", "a;.png").validar());
    }

    @Test
    @DisplayName("ningún texto puede contener saltos de línea")
    void sinSaltosDeLinea() {
        assertThrows(IllegalArgumentException.class, () -> refresco("L1", "T\nx", "C", 5, 1, "", "a.png").validar());
        assertThrows(IllegalArgumentException.class, () -> refresco("L1", "T", "C", 5, 1, "uno\r\ndos", "a.png").validar());
    }

    @Test
    @DisplayName("el mensaje de error dice qué está mal")
    void mensajeDelError() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> refresco("L1", "T", "C", 11, 1, "", "a.png").validar());
        assertNotNull(e.getMessage());
        assertFalse(e.getMessage().isBlank());
    }
}
