<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Visor de Refrescos - Oasis</title>
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
            box-sizing: border-box;
        }
        .container {
            background: white;
            max-width: 600px;
            width: 100%;
            padding: 30px;
            border-radius: 12px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.15);
        }
        .header-visor {
            text-align: center;
            margin-bottom: 20px;
            border-bottom: 2px solid #f0f0f0;
            padding-bottom: 15px;
        }
        .badge {
            background: #e9ecef;
            color: #495057;
            padding: 5px 12px;
            border-radius: 20px;
            font-size: 0.9em;
            font-weight: bold;
        }
        .visor-content {
            text-align: center;
        }
        .img-container {
            height: 250px;
            display: flex;
            justify-content: center;
            align-items: center;
            margin-bottom: 20px;
            background: #f8f9fa;
            border-radius: 8px;
            padding: 10px;
        }
        .img-container img {
            max-width: 100%;
            max-height: 100%;
            object-fit: contain;
            filter: drop-shadow(0 5px 15px rgba(0,0,0,0.2));
        }
        .info-box {
            background: #f8f9fa;
            padding: 15px;
            border-radius: 8px;
            text-align: left;
            margin-bottom: 25px;
        }
        .info-box p {
            margin: 8px 0;
            color: #444;
        }
        .controls {
            display: flex;
            justify-content: space-between;
            align-items: center;
            gap: 10px;
        }
        .btn {
            padding: 10px 20px;
            border-radius: 6px;
            text-decoration: none;
            color: white;
            background-color: var(--primary-color);
            font-weight: bold;
            text-align: center;
            transition: opacity 0.3s;
            flex: 1;
        }
        .btn:hover { opacity: 0.9; }
        .btn-home { background-color: #6c757d; flex: 0.5; }
        
        .empty-state {
            text-align: center;
            padding: 40px 20px;
            color: #6c757d;
        }
    </style>
</head>
<body>

    <div class="container">
        <div class="header-visor">
            <h2 style="margin: 0 0 10px 0;">▶️ Visor de Refrescos</h2>
            <span class="badge">Total en inventario: <c:out value="${total != null ? total : 0}" /></span>
        </div>

        <c:choose>
            <c:when test="${not empty refresco}">
                <div class="visor-content">
                    
                    <div class="img-container">
                        <img src="${pageContext.request.contextPath}/media/${refresco.archivo}" alt="${refresco.titulo}">
                    </div>

                    <h3 style="color: var(--primary-color); margin-bottom: 15px;">${refresco.titulo}</h3>

                    <div class="info-box">
                        <p><strong>Código:</strong> ${refresco.codigo}</p>
                        <p><strong>Categoría:</strong> ${refresco.categoria}</p>
                        <p><strong>Nivel de Frío:</strong> ${refresco.frio}</p>
                        <p><strong>Litros:</strong> ${refresco.litros} L</p>
                        <p><strong>Descripción:</strong> ${refresco.descripcion}</p>
                    </div>

                    <div class="controls">
                        <a href="visor?accion=anterior" class="btn">⬅️ Anterior</a>
                        <a href="${pageContext.request.contextPath}/" class="btn btn-home">🏠 Inicio</a>
                        <a href="visor?accion=siguiente" class="btn">Siguiente ➡️</a>
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <div class="empty-state">
                    <h3>¡El inventario está vacío!</h3>
                    <p>No hay gaseosas para mostrar en el visor en este momento.</p>
                    <a href="${pageContext.request.contextPath}/" class="btn btn-home" style="display: inline-block; margin-top: 15px;">Volver al Inicio</a>
                </div>
            </c:otherwise>
        </c:choose>
    </div>

</body>
</html>