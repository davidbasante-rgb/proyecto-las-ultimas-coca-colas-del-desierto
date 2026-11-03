package co.umariana.colas.estructura;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class ListaCircularTest {

    private static ListaCircular<String> con(String... elementos) {
        ListaCircular<String> lista = new ListaCircular<>();
        for (String e : elementos) {
            lista.agregar(e);
        }
        return lista;
    }

    private static List<String> contenido(Lista<String> lista) {
        List<String> r = new ArrayList<>();
        for (String s : lista) {
            r.add(s);
        }
        return r;
    }

    // ---------- básico ----------

    @Test
    @DisplayName("Una lista nueva está vacía")
    void nuevaEstaVacia() {
        ListaCircular<String> lista = new ListaCircular<>();
        assertTrue(lista.estaVacia());
        assertEquals(0, lista.tamano());
        assertNull(lista.actual());
    }

    @Test
    @DisplayName("agregar pone los elementos al final, en orden")
    void agregarAlFinal() {
        ListaCircular<String> lista = con("A", "B", "C");
        assertEquals(3, lista.tamano());
        assertFalse(lista.estaVacia());
        assertEquals("A", lista.obtener(0));
        assertEquals("B", lista.obtener(1));
        assertEquals("C", lista.obtener(2));
    }

    @Test
    @DisplayName("insertar al inicio, en medio y al final")
    void insertar() {
        ListaCircular<String> lista = con("B", "D");
        lista.insertar(0, "A");
        lista.insertar(2, "C");
        lista.insertar(4, "E");
        assertEquals(List.of("A", "B", "C", "D", "E"), contenido(lista));
    }

    @Test
    @DisplayName("insertar en una lista vacía")
    void insertarEnVacia() {
        ListaCircular<String> lista = new ListaCircular<>();
        lista.insertar(0, "A");
        assertEquals(List.of("A"), contenido(lista));
        assertEquals("A", lista.actual());
    }

    @Test
    @DisplayName("insertar y obtener fuera de rango lanzan IndexOutOfBoundsException")
    void indicesInvalidos() {
        ListaCircular<String> lista = con("A", "B");
        assertThrows(IndexOutOfBoundsException.class, () -> lista.insertar(-1, "X"));
        assertThrows(IndexOutOfBoundsException.class, () -> lista.insertar(3, "X"));
        assertThrows(IndexOutOfBoundsException.class, () -> lista.obtener(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> lista.obtener(2));
        assertThrows(IndexOutOfBoundsException.class, () -> lista.eliminar(2));
        assertThrows(IndexOutOfBoundsException.class, () -> new ListaCircular<String>().eliminar(0));
        assertEquals(List.of("A", "B"), contenido(lista), "los intentos fallidos no deben cambiar la lista");
    }

    @Test
    @DisplayName("eliminar devuelve el elemento: primero, medio y último")
    void eliminar() {
        ListaCircular<String> lista = con("A", "B", "C", "D", "E");
        assertEquals("A", lista.eliminar(0));
        assertEquals("C", lista.eliminar(1));
        assertEquals("E", lista.eliminar(2));
        assertEquals(List.of("B", "D"), contenido(lista));
        assertEquals(2, lista.tamano());
    }

    @Test
    @DisplayName("eliminar el único elemento deja la lista vacía y reutilizable")
    void eliminarUnico() {
        ListaCircular<String> lista = con("A");
        assertEquals("A", lista.eliminar(0));
        assertTrue(lista.estaVacia());
        assertNull(lista.actual());
        lista.agregar("B");
        assertEquals(List.of("B"), contenido(lista));
        assertEquals("B", lista.actual());
    }

    @Test
    @DisplayName("vaciar elimina todo")
    void vaciar() {
        ListaCircular<String> lista = con("A", "B", "C");
        lista.vaciar();
        assertTrue(lista.estaVacia());
        assertEquals(0, lista.tamano());
        assertNull(lista.actual());
        assertFalse(lista.iterator().hasNext());
    }

    @Test
    @DisplayName("acepta cientos de elementos")
    void muchosElementos() {
        ListaCircular<Integer> lista = new ListaCircular<>();
        for (int i = 0; i < 1000; i++) {
            lista.agregar(i);
        }
        assertEquals(1000, lista.tamano());
        assertEquals(999, lista.obtener(999));
        assertEquals(500, lista.obtener(500));
        for (int i = 0; i < 500; i++) {
            lista.eliminar(0);
        }
        assertEquals(500, lista.tamano());
        assertEquals(500, lista.obtener(0));
    }

    // ---------- iterador ----------

    @Test
    @DisplayName("el iterador recorre cada elemento UNA vez y termina")
    void iteradorTermina() {
        ListaCircular<String> lista = con("A", "B", "C");
        Iterator<String> it = lista.iterator();
        assertTrue(it.hasNext());
        assertEquals("A", it.next());
        assertEquals("B", it.next());
        assertEquals("C", it.next());
        assertFalse(it.hasNext(), "una lista circular no puede iterar para siempre");
        assertThrows(NoSuchElementException.class, it::next);
    }

    @Test
    @DisplayName("el iterador de una lista vacía no tiene elementos")
    void iteradorVacia() {
        assertFalse(new ListaCircular<String>().iterator().hasNext());
    }

    // ---------- cursor circular ----------

    @Test
    @DisplayName("el cursor empieza en el primer elemento")
    void cursorInicial() {
        assertEquals("A", con("A", "B", "C").actual());
    }

    @Test
    @DisplayName("avanzar recorre los elementos y vuelve al primero (circular)")
    void avanzarDaLaVuelta() {
        ListaCircular<String> lista = con("A", "B", "C");
        assertTrue(lista.avanzar());
        assertEquals("B", lista.actual());
        assertTrue(lista.avanzar());
        assertEquals("C", lista.actual());
        assertTrue(lista.avanzar(), "desde el último, avanzar vuelve al primero");
        assertEquals("A", lista.actual());
    }

    @Test
    @DisplayName("retroceder desde el primero va al último (circular)")
    void retrocederDaLaVuelta() {
        ListaCircular<String> lista = con("A", "B", "C");
        assertTrue(lista.retroceder());
        assertEquals("C", lista.actual());
        assertTrue(lista.retroceder());
        assertEquals("B", lista.actual());
        assertTrue(lista.retroceder());
        assertEquals("A", lista.actual());
    }

    @Test
    @DisplayName("con un solo elemento el cursor se queda en él")
    void cursorUnElemento() {
        ListaCircular<String> lista = con("A");
        assertTrue(lista.avanzar());
        assertEquals("A", lista.actual());
        assertTrue(lista.retroceder());
        assertEquals("A", lista.actual());
    }

    @Test
    @DisplayName("en una lista vacía el cursor no se mueve")
    void cursorVacia() {
        ListaCircular<String> lista = new ListaCircular<>();
        assertFalse(lista.avanzar());
        assertFalse(lista.retroceder());
        assertNull(lista.actual());
    }

    @Test
    @DisplayName("dar tamano() vueltas completas deja el cursor donde estaba")
    void vueltaCompleta() {
        ListaCircular<String> lista = con("A", "B", "C", "D");
        lista.avanzar();
        for (int i = 0; i < 4; i++) {
            lista.avanzar();
        }
        assertEquals("B", lista.actual());
        for (int i = 0; i < 8; i++) {
            lista.retroceder();
        }
        assertEquals("B", lista.actual());
    }

    @Test
    @DisplayName("la lista sigue siendo circular después de agregar e insertar")
    void siguesiendoCircularTrasModificar() {
        ListaCircular<String> lista = con("A", "B");
        lista.agregar("C");
        lista.insertar(0, "Z");
        // Z A B C -> el cursor sigue en A (insertar antes no lo mueve)
        assertEquals("A", lista.actual());
        lista.avanzar();
        lista.avanzar(); // C
        assertEquals("C", lista.actual());
        lista.avanzar();
        assertEquals("Z", lista.actual(), "tras el último debe venir el primero");
        lista.retroceder();
        assertEquals("C", lista.actual());
    }

    @Test
    @DisplayName("eliminar el elemento actual deja el cursor en el siguiente")
    void eliminarElActual() {
        ListaCircular<String> lista = con("A", "B", "C");
        lista.avanzar(); // B
        lista.eliminar(1);
        assertEquals("C", lista.actual());
        assertEquals(List.of("A", "C"), contenido(lista));
    }

    @Test
    @DisplayName("eliminar el actual cuando es el último deja el cursor en el primero")
    void eliminarElActualUltimo() {
        ListaCircular<String> lista = con("A", "B", "C");
        lista.retroceder(); // C
        lista.eliminar(2);
        assertEquals("A", lista.actual());
        lista.avanzar();
        lista.avanzar();
        assertEquals("A", lista.actual(), "con A y B, dos pasos dan la vuelta");
    }

    @Test
    @DisplayName("eliminar otro elemento no mueve el cursor")
    void eliminarOtroNoMueveElCursor() {
        ListaCircular<String> lista = con("A", "B", "C");
        lista.avanzar(); // B
        lista.eliminar(0);
        assertEquals("B", lista.actual());
        lista.eliminar(1); // C
        assertEquals("B", lista.actual());
    }

    @Test
    @DisplayName("tras vaciar, el cursor vuelve a empezar en el primer elemento nuevo")
    void cursorTrasVaciar() {
        ListaCircular<String> lista = con("A", "B");
        lista.avanzar();
        lista.vaciar();
        lista.agregar("X");
        lista.agregar("Y");
        assertEquals("X", lista.actual());
    }

    // ---------- reglas del proyecto ----------

    @Test
    @DisplayName("la lista se implementa con nodos propios (sin ArrayList, LinkedList, etc.)")
    void sinColeccionesDeJava() throws IOException {
        Path fuente = Path.of("src/main/java/co/umariana/colas/estructura/ListaCircular.java");
        // Se ignoran los comentarios: solo importa el código.
        String codigo = Files.readString(fuente).replaceAll("(?s)/\\*.*?\\*/", "").replaceAll("//[^\\n]*", "");
        for (String prohibida : List.of("ArrayList", "LinkedList", "Vector", "Deque", "Stack", "HashMap",
                "TreeMap", "HashSet", "TreeSet", "java.util.List;", "java.util.*", "Arrays", "Collections")) {
            assertFalse(codigo.contains(prohibida),
                    "ListaCircular no debe usar '" + prohibida + "': implemente los nodos usted mismo");
        }
        assertTrue(codigo.contains("class Nodo"), "debe declarar su propia clase Nodo");
    }
}
