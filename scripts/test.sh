#!/usr/bin/env bash
# Ejecuta las pruebas del proyecto.
#
#   ./scripts/test.sh                  todas las pruebas
#   ./scripts/test.sh estructura       solo las de la estructura de datos
#   ./scripts/test.sh modelo           solo las del modelo
#   ./scripts/test.sh persistencia     solo las de la persistencia (.txt)
#   ./scripts/test.sh web              solo las de los servlets
#
# Termina con código distinto de 0 si alguna prueba falla.
set -uo pipefail
source "$(dirname "${BASH_SOURCE[0]}")/_comun.sh"
exigir_java

filtro=()
case "${1:-todas}" in
    todas) ;;
    estructura|modelo|persistencia|web)
        filtro=("-Dtest=co/umariana/colas/$1/*Test")
        shift ;;
    *)
        echo "ERROR: área desconocida '$1'. Use: estructura, modelo, persistencia, web (o nada para todas)." >&2
        exit 2 ;;
esac

echo "Ejecutando pruebas (la primera vez tarda unos minutos mientras se descargan las dependencias)..."
if ./mvnw -B test ${filtro[@]+"${filtro[@]}"} "$@"; then
    echo
    echo "OK: todas las pruebas pasaron."
else
    codigo=$?
    echo
    echo "FALLO: hay pruebas que no pasan. Revise el detalle más arriba." >&2
    exit "$codigo"
fi
