<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Lista de Refrescos - Oasis</title>
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
            padding: 20px;
            color: #333;
        }
        .container {
            background: white;
            max-width: 1000px;
            margin: 0 auto;
            padding: 30px;
            border-radius: 12px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.15);
        }
        .header-actions {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 20px;
            padding-bottom: 10px;
            border-bottom: 2px solid #f0f0f0;
        }
        .btn {
            padding: 10px 15px;
            border-radius: 6px;
            text-decoration: none;
            color: white;
            background-color: var(--primary-color);
            border: none;
            font-weight: bold;
            cursor: pointer;
            transition: background 0.3s;
        }
        .btn:hover { background-color: #0056b3; }
        .btn-danger { background-color: #dc3545; padding: 6px 12px; font-size: 0.9em; }
        .btn-danger:hover { background-color: #c82333; }
        .btn-ranking { background-color: #ffc107; color: #212529; }
        .btn-ranking:hover { background-color: #e0a800; }
        
        .promedios-card {
            background: #e9f5ff;
            padding: 15px;
            border-radius: 8px;
            border-left: 4px solid var(--primary-color);
            margin-bottom: 20px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        th, td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        th {
            background-color: var(--primary-color);
            color: white;
        }
        tr:hover { background-color: #f8f9fa; }
    </style>
</head>
<body>
    <div class="container">
        <!-- Navegación y Botones de Ordenamiento -->
        <div class="header-actions">
            <h2>📋 Inventario de Refrescos</h2>
            <div>
                <!-- Enlace al index base -->
                <a href="${pageContext.request.contextPath}/" class="btn" style="background: #6c757d;">🏠 Inicio</a>
                <!-- Enlaces que conectan con los parámetros del ListarRefrescos.java -->
                <a href="listar" class="btn">📋 Lista Normal</a>
                <a href="listar?orden=ranking" class="btn btn-ranking">⭐ Ver Ranking</a>
            </div>
        </div>

        <!-- Tarjeta de Promedios -->
        <div class="promedios-card">
            <strong>📊 Promedios por categoría:</strong>
            <p style="margin: 5px 0 0 0;">${promedios}</p>
        </div>

        <!-- Tabla de Datos -->
        <table>
            <thead>
                <tr>
                    <th>Código</th>
                    <th>Título</th>
                    <th>Categoría</th>
                    <th>Frío (Nivel)</th>
                    <th>Litros</th>
                    <th>Descripción</th>
                    <th>Acción</th>
                </tr>
            </thead>
            <tbody>
                <!-- Iteramos sobre la lista de refrescos que manda el Servlet -->
                <c:forEach var="refresco" items="${refrescos}">
                    <tr>
                        <td>${refresco.codigo}</td>
                        <td>${refresco.titulo}</td>
                        <td>${refresco.categoria}</td>
                        <td>${refresco.frio}</td>
                        <td>${refresco.litros} L</td>
                        <td>${refresco.descripcion}</td>
                        <td>
                            <!-- Formulario de eliminación que conecta por POST con EliminarRefresco.java -->
                            <form action="eliminar" method="post" style="margin: 0;">
                                <input type="hidden" name="codigo" value="${refresco.codigo}">
                                <button type="submit" class="btn btn-danger">🗑️ Eliminar</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                
                <!-- Si la lista está vacía -->
                <c:if test="${empty refrescos}">
                    <tr>
                        <td colspan="7" style="text-align: center; color: #777;">No hay refrescos en el sistema.</td>
                    </tr>
                </c:if>
            </tbody>
        </table>
    </div>
</body>
</html>