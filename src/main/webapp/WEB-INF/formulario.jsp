<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Registrar Refresco - Oasis</title>
    <style>
        :root {
            --primary-color: #007BFF;
            --bg-gradient-1: #e0c3fc;
            --bg-gradient-2: #8ec5fc;
        }
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, var(--bg-gradient-1) 0%, var(--bg-gradient-2) 100%);
            min-height: 100vh;
            margin: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 20px;
        }
        .container {
            background: white;
            max-width: 500px;
            width: 100%;
            padding: 30px;
            border-radius: 12px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.15);
        }
        h2 {
            text-align: center;
            color: #333;
            margin-bottom: 20px;
        }
        .form-group {
            margin-bottom: 15px;
        }
        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
            color: #555;
            font-size: 0.9rem;
        }
        input[type="text"],
        input[type="number"],
        textarea,
        select {
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 6px;
            box-sizing: border-box;
            font-size: 1rem;
        }
        textarea {
            resize: vertical;
            height: 80px;
        }
        .error-message {
            background-color: #f8d7da;
            color: #721c24;
            padding: 12px;
            border-radius: 6px;
            margin-bottom: 20px;
            border: 1px solid #f5c6cb;
            font-size: 0.9rem;
        }
        .button-group {
            display: flex;
            gap: 10px;
            margin-top: 20px;
        }
        .btn {
            flex: 1;
            padding: 12px;
            border: none;
            border-radius: 6px;
            font-size: 1rem;
            font-weight: bold;
            cursor: pointer;
            text-align: center;
            text-decoration: none;
            transition: opacity 0.3s;
        }
        .btn:hover { opacity: 0.9; }
        .btn-submit { background-color: var(--primary-color); color: white; }
        .btn-cancel { background-color: #6c757d; color: white; }
    </style>
</head>
<body>

    <div class="container">
        <h2>➕ Registrar Refresco</h2>

        <!-- Bloque para mostrar el mensaje de error si el Servlet lo envía -->
        <c:if test="${not empty error}">
            <div class="error-message">
                <strong>⚠️ Error al registrar:</strong> ${error}
            </div>
        </c:if>

        <!-- Formulario: la acción "registrar" y el método "post" coinciden con tu Servlet -->
        <form action="registrar" method="post">
            <div class="form-group">
                <label for="codigo">Código del Refresco:</label>
                <input type="text" id="codigo" name="codigo" required placeholder="Ej: REF-001">
            </div>

            <div class="form-group">
                <label for="titulo">Título / Nombre:</label>
                <input type="text" id="titulo" name="titulo" required placeholder="Ej: Coca-Cola Clásica">
            </div>

            <div class="form-group">
                <label for="categoria">Categoría:</label>
                <input type="text" id="categoria" name="categoria" required placeholder="Ej: Cola, Cítrico, etc.">
            </div>

            <div class="form-group">
                <label for="frio">Nivel de Frío (Número entero):</label>
                <input type="number" id="frio" name="frio" required placeholder="Ej: 5">
            </div>

            <div class="form-group">
                <label for="litros">Litros (Decimal):</label>
                <input type="number" id="litros" name="litros" step="0.1" required placeholder="Ej: 1.5">
            </div>

            <div class="form-group">
                <label for="descripcion">Descripción:</label>
                <textarea id="descripcion" name="descripcion" required placeholder="Detalles del refresco..."></textarea>
            </div>

            <div class="form-group">
                <label for="archivo">Nombre del Archivo de Imagen:</label>
                <!-- Según tu carpeta media, las imágenes son classic.png, six-pack.png, zero.png -->
                <select id="archivo" name="archivo" required>
                    <option value="" disabled selected>Selecciona una imagen...</option>
                    <option value="classic.png">classic.png</option>
                    <option value="six-pack.png">six-pack.png</option>
                    <option value="zero.png">zero.png</option>
                </select>
                <small style="color: #666; font-size: 0.8rem; margin-top: 4px; display: block;">
                    *Selecciona una imagen existente en la carpeta media.
                </small>
            </div>

            <div class="button-group">
                <a href="${pageContext.request.contextPath}/" class="btn btn-cancel">Cancelar</a>
                <button type="submit" class="btn btn-submit">Guardar</button>
            </div>
        </form>
    </div>

</body>
</html>