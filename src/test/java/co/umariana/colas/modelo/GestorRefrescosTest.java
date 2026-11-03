package co.umariana.colas.modelo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.UncheckedIOException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/** Prueba el GestorRefrescos con una lista y un repositorio de mentira: no depende del resto del equipo. */
class GestorRefrescosTest {

    private ListaFalsa<Refresco> lista;
    private RepositorioFalso repo;
    private GestorRefrescos gestor;

    private static Refresco l(String codigo, String categoria, int frio) {
        return new Refresco(codigo, "Titulo " + codigo, categoria, frio, 2.5, "desc", codigo + ".png");
    }

    @BeforeEach
    void preparar() {
        lista = new ListaFalsa<>();
        repo = new RepositorioFalso();
        gestor = new GestorRefrescos(lista, repo);
    }

    @Test
    @DisplayName("al crearse carga los refrescos que ya estaban guardados")
    void cargaAlCrearse() {
        repo.guardados.add(l("A", "Gaseosa", 3));
        repo.guardados.add(l("B", "Tráfico", 6));
        GestorRefrescos otro = new GestorRefrescos(new ListaFalsa<>(), repo);
        assertEquals(2, otro.total());
        assertEquals("A", otro.listar().get(0).getCodigo());
        assertEquals("B", otro.listar().get(1).getCodigo());
    }

    @Test
    @DisplayName("si el repositorio falla al cargar, lanza UncheckedIOException")
    void falloAlCargar() {
        repo.fallar = true;
        assertThrows(UncheckedIOException.class, () -> new GestorRefrescos(new ListaFalsa<>(), repo));
    }

    @Test
    @DisplayName("agregar guarda el refresco en la lista y en el repositorio")
    void agregar() {
        gestor.agregar(l("A", "Gaseosa", 3));
        assertEquals(1, gestor.total());
        assertEquals(1, lista.tamano(), "debe guardarlo en la Lista que recibió");
        assertEquals(1, repo.guardados.size());
        assertEquals("A", repo.guardados.get(0).getCodigo());
    }

    @Test
    @DisplayName("agregar mantiene el orden de llegada")
    void agregarMantieneOrden() {
        gestor.agregar(l("C", "x", 1));
        gestor.agregar(l("A", "x", 2));
        gestor.agregar(l("B", "x", 3));
        assertEquals(List.of("C", "A", "B"), gestor.listar().stream().map(Refresco::getCodigo).toList());
        assertEquals(List.of("C", "A", "B"), repo.guardados.stream().map(Refresco::getCodigo).toList());
    }

    @Test
    @DisplayName("agregar rechaza un código repetido y no guarda nada")
    void agregarRepetido() {
        gestor.agregar(l("A", "x", 3));
        int guardadas = repo.vecesGuardado;
        assertThrows(IllegalArgumentException.class, () -> gestor.agregar(l("A", "otra", 5)));
        assertEquals(1, gestor.total());
        assertEquals(guardadas, repo.vecesGuardado);
    }

    @Test
    @DisplayName("agregar rechaza un refresco inválido y no guarda nada")
    void agregarInvalido() {
        assertThrows(IllegalArgumentException.class, () -> gestor.agregar(l("A", "x", 11)));
        assertEquals(0, gestor.total());
        assertEquals(0, repo.vecesGuardado);
    }

    @Test
    @DisplayName("buscar devuelve el refresco o null")
    void buscar() {
        gestor.agregar(l("A", "x", 3));
        gestor.agregar(l("B", "x", 4));
        assertEquals("B", gestor.buscar("B").getCodigo());
        assertNull(gestor.buscar("Z"));
        assertNull(gestor.buscar(null));
    }

    @Test
    @DisplayName("eliminar quita el refresco y guarda el cambio")
    void eliminar() {
        gestor.agregar(l("A", "x", 3));
        gestor.agregar(l("B", "x", 4));
        gestor.agregar(l("C", "x", 5));
        assertTrue(gestor.eliminar("B"));
        assertEquals(List.of("A", "C"), gestor.listar().stream().map(Refresco::getCodigo).toList());
        assertEquals(2, repo.guardados.size());
        assertNull(gestor.buscar("B"));
    }

    @Test
    @DisplayName("eliminar un código que no existe devuelve false y no guarda")
    void eliminarInexistente() {
        gestor.agregar(l("A", "x", 3));
        int guardadas = repo.vecesGuardado;
        assertFalse(gestor.eliminar("Z"));
        assertEquals(1, gestor.total());
        assertEquals(guardadas, repo.vecesGuardado);
    }

    @Test
    @DisplayName("ranking ordena de mayor a menor frío, desempata por código y no altera la lista")
    void ranking() {
        gestor.agregar(l("D", "x", 4));
        gestor.agregar(l("B", "x", 9));
        gestor.agregar(l("A", "x", 4));
        gestor.agregar(l("C", "x", 10));
        assertEquals(List.of("C", "B", "A", "D"), gestor.ranking().stream().map(Refresco::getCodigo).toList());
        assertEquals(List.of("D", "B", "A", "C"), gestor.listar().stream().map(Refresco::getCodigo).toList(),
                "el ranking no debe reordenar la lista");
    }

    @Test
    @DisplayName("promedioPorCategoria calcula el promedio y ordena por categoría")
    void promedios() {
        gestor.agregar(l("A", "Tráfico", 4));
        gestor.agregar(l("B", "Gaseosa", 10));
        gestor.agregar(l("C", "Tráfico", 7));
        gestor.agregar(l("D", "Gaseosa", 2));
        gestor.agregar(l("E", "Fútbol", 5));
        Map<String, Double> p = gestor.promedioPorCategoria();
        assertEquals(List.of("Fútbol", "Gaseosa", "Tráfico"), List.copyOf(p.keySet()));
        assertEquals(5.0, p.get("Fútbol"), 0.001);
        assertEquals(6.0, p.get("Gaseosa"), 0.001);
        assertEquals(5.5, p.get("Tráfico"), 0.001);
    }

    @Test
    @DisplayName("sin refrescos: listas vacías, promedios vacíos, total 0")
    void vacio() {
        assertEquals(0, gestor.total());
        assertTrue(gestor.listar().isEmpty());
        assertTrue(gestor.ranking().isEmpty());
        assertTrue(gestor.promedioPorCategoria().isEmpty());
    }

    @Test
    @DisplayName("el cursor: actual, siguiente y anterior recorren la lista")
    void cursor() {
        gestor.agregar(l("A", "x", 1));
        gestor.agregar(l("B", "x", 2));
        gestor.agregar(l("C", "x", 3));
        assertEquals("A", gestor.actual().getCodigo());
        assertEquals("B", gestor.siguiente().getCodigo());
        assertEquals("B", gestor.actual().getCodigo());
        assertEquals("C", gestor.siguiente().getCodigo());
        assertEquals("B", gestor.anterior().getCodigo());
        assertEquals("A", gestor.anterior().getCodigo());
    }

    @Test
    @DisplayName("el cursor devuelve null cuando no hay refrescos")
    void cursorVacio() {
        assertNull(gestor.actual());
        assertNull(gestor.siguiente());
        assertNull(gestor.anterior());
    }

    @Test
    @DisplayName("listar devuelve una copia: cambiarla no afecta al gestor")
    void listarEsCopia() {
        gestor.agregar(l("A", "x", 1));
        List<Refresco> copia = gestor.listar();
        try {
            copia.clear();
        } catch (UnsupportedOperationException ignorada) {
            // también es válido devolver una lista de solo lectura
        }
        assertEquals(1, gestor.total());
    }
}
