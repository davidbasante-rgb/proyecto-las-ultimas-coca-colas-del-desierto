"""Valida el título y la descripción de un Pull Request.

Lee TITULO y DESCRIPCION de las variables de entorno (nunca se insertan en el script:
los escribe el estudiante). Termina con código 1 si algo falta.
"""
import os
import re
import sys

MINIMO_DESCRIPCION = 20  # caracteres (sin espacios) de texto propio

titulo = os.environ.get("TITULO", "")
cuerpo = (os.environ.get("DESCRIPCION") or "").replace("\r", "")
errores = []

# 1) Título: debe empezar con el ID de la tarea (CCD-1 ... CCD-5). Ej.: "CCD-1: estructura de Juan Pérez"
if not re.fullmatch(r"CCD-[1-5]([\s:-].*)?", titulo):
    errores.append((
        "Título inválido",
        "El título debe empezar con el ID de la tarea (`CCD-1` a `CCD-5`), por ejemplo "
        "`CCD-1: estructura de datos de Juan Pérez`. "
        f"Título actual: `{titulo}`."))

# 2) Nombre: una línea "Nombre: <algo>" (no vacío)
if not re.search(r"(?im)^[ \t]*Nombre:[ \t]*\S", cuerpo):
    errores.append((
        "Falta el nombre",
        "La descripción debe incluir una línea `Nombre: Su Nombre Completo`."))

# 3) Descripción: texto propio, sin contar comentarios, imágenes, títulos ni la línea del nombre
texto = re.sub(r"<!--.*?-->", "", cuerpo, flags=re.DOTALL)
texto = re.sub(r"!\[[^\]]*\]\([^)]*\)", "", texto)
texto = re.sub(r"<img[^>]*>", "", texto, flags=re.IGNORECASE)
texto = re.sub(r"(?im)^[ \t]*Nombre:.*$", "", texto)
texto = re.sub(r"(?m)^[ \t]*#+.*$", "", texto)
if len(re.sub(r"\s+", "", texto)) < MINIMO_DESCRIPCION:
    errores.append((
        "Falta la descripción",
        "Escriba con sus palabras qué hizo (qué tarea hizo y cómo la probó)."))

resumen = []
if errores:
    resumen.append("## ❌ El PR no cumple el formato\n")
    for titulo_error, detalle in errores:
        print(f"::error title={titulo_error}::{detalle}")
        resumen.append(f"- ❌ **{titulo_error}.** {detalle}")
    resumen.append("\nCorrija el título o la descripción (botón **Edit** del PR): "
                   "la validación se vuelve a ejecutar sola.")
else:
    print("OK  título, nombre y descripción")
    resumen.append("## ✅ El PR cumple el formato")

archivo = os.environ.get("GITHUB_STEP_SUMMARY")
if archivo:
    with open(archivo, "a", encoding="utf-8") as f:
        f.write("\n".join(resumen) + "\n")

sys.exit(1 if errores else 0)
