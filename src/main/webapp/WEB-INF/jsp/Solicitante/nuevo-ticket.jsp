<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Nuevo Ticket | Mesa de Ayuda</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilo.css">
    </head>
    <body>

        <main style="max-width:600px; margin:40px auto; padding:24px;">

            <h1>Nuevo Ticket</h1>
            <p>Describe el problema que necesitas reportar.</p>

            <c:if test="${not empty error}">
                <p style="color:red;">${error}</p>
            </c:if>

            <form method="post" action="${pageContext.request.contextPath}/ticket/nuevo">

                <div style="margin-bottom:16px;">
                    <label for="titulo">Titulo</label><br>
                    <input type="text" id="titulo" name="titulo" required
                           style="width:100%; padding:8px;">
                </div>

                <div style="margin-bottom:16px;">
                    <label for="descripcion">Descripcion</label><br>
                    <textarea id="descripcion" name="descripcion" rows="4" required
                              style="width:100%; padding:8px;"></textarea>
                </div>

                <div style="margin-bottom:16px;">
                    <label for="idCategoria">Categoria</label><br>
                    <select id="idCategoria" name="idCategoria" required style="width:100%; padding:8px;">
                        <c:forEach var="categoria" items="${categorias}">
                            <option value="${categoria.idCategoria}">${categoria.nombreCategoria}</option>
                        </c:forEach>
                    </select>
                </div>

                <div style="margin-bottom:16px;">
                    <label for="idPrioridad">Prioridad</label><br>
                    <select id="idPrioridad" name="idPrioridad" required style="width:100%; padding:8px;">
                        <c:forEach var="prioridad" items="${prioridades}">
                            <option value="${prioridad.idPrioridad}">${prioridad.tipo}</option>
                        </c:forEach>
                    </select>
                </div>

                <button type="submit" style="padding:10px 20px;">Crear ticket</button>
                <a href="${pageContext.request.contextPath}/dashboard" style="margin-left:12px;">Cancelar</a>

            </form>

        </main>

    </body>
</html>