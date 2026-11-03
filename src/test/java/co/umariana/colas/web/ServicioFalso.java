package co.umariana.colas.web;

import co.umariana.colas.modelo.Refresco;
import co.umariana.colas.modelo.ServicioRefrescos;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/** Servicio de mentira que anota lo que le piden: permite probar los servlets sin el resto del equipo. */
class ServicioFalso implements ServicioRefrescos {

    final List<Refresco> datos = new ArrayList<>();
    final List<Refresco> ranking = new ArrayList<>();
    final Map<String, Double> promedios = new TreeMap<>();
    final List<String> llamadas = new ArrayList<>();
    RuntimeException fallarAlAgregar = null;
    Refresco cursorActual = null;
    Refresco cursorSiguiente = null;
    Refresco cursorAnterior = null;

    @Override public void agregar(Refresco l) {
        llamadas.add("agregar");
        if (fallarAlAgregar != null) throw fallarAlAgregar;
        datos.add(l);
    }
    @Override public Refresco buscar(String codigo) { llamadas.add("buscar"); return null; }
    @Override public boolean eliminar(String codigo) { llamadas.add("eliminar:" + codigo); return true; }
    @Override public List<Refresco> listar() { llamadas.add("listar"); return datos; }
    @Override public List<Refresco> ranking() { llamadas.add("ranking"); return ranking; }
    @Override public Map<String, Double> promedioPorCategoria() { llamadas.add("promedios"); return promedios; }
    @Override public int total() { return datos.size(); }
    @Override public Refresco actual() { llamadas.add("actual"); return cursorActual; }
    @Override public Refresco siguiente() { llamadas.add("siguiente"); return cursorSiguiente; }
    @Override public Refresco anterior() { llamadas.add("anterior"); return cursorAnterior; }
}
