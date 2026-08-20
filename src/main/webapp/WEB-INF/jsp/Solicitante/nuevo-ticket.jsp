<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="es">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <title>Nuevo Ticket | Mesa de Ayuda</title>

        <link rel="stylesheet"
              href="${pageContext.request.contextPath}/css/estilo.css">
    </head>

    <body class="pagina-nuevo-ticket">

        <main class="contenido">

            <div class="nuevo-ticket-contenedor">

                <!-- ENCABEZADO -->
                <div class="nuevo-ticket-header">

                    <h1>Nuevo Ticket</h1>

                    <p>
                        Describe el problema que necesitas reportar.
                    </p>

                </div>


                <!-- FORMULARIO -->
                <div class="formulario-ticket">

                    <!-- MENSAJE DE ERROR -->
                    <c:if test="${not empty error}">

                        <div class="error-ticket">
                            ${error}
                        </div>

                    </c:if>


                    <form method="post"
                          action="${pageContext.request.contextPath}/ticket/nuevo">


                        <!-- TÍTULO -->
                        <div class="campo-ticket">

                            <label for="titulo">
                                Título
                            </label>

                            <input
                                type="text"
                                id="titulo"
                                name="titulo"
                                required
                                maxlength="150"
                                placeholder="Ej. No hay conexión a Internet"
                                >

                        </div>


                        <!-- DESCRIPCIÓN -->
                        <div class="campo-ticket">

                            <label for="descripcion">
                                Descripción
                            </label>

                            <textarea
                                id="descripcion"
                                name="descripcion"
                                required
                                placeholder="Describe detalladamente el problema que estás presentando..."
                                ></textarea>

                        </div>


                        <!-- CATEGORÍA -->
                        <div class="campo-ticket">

                            <label for="idCategoria">
                                Categoría
                            </label>

                            <select
                                id="idCategoria"
                                name="idCategoria"
                                required
                                >

                                <option value="">
                                    Selecciona una categoría
                                </option>

                                <c:forEach
                                    var="categoria"
                                    items="${categorias}"
                                    >

                                    <option value="${categoria.idCategoria}">
                                        ${categoria.nombreCategoria}
                                    </option>

                                </c:forEach>

                            </select>

                        </div>


                        <p style="
                           color:#718096;
                           font-size:0.9rem;
                           margin-top:-10px;
                           ">

                            La prioridad se calcula automáticamente según la
                            categoría y la descripción del problema.

                        </p>
                </div>


                <!-- BOTONES -->
                <div class="acciones-ticket">

                    <button
                        type="submit"
                        class="btn-crear-ticket"
                        >
                        Crear ticket
                    </button>


                    <a
                        href="${pageContext.request.contextPath}/dashboardSolicitante"
                        class="btn-secundario"
                        >
                        Cancelar
                    </a>

                </div>

                </form>

            </div>

        </div>

    </main>

</body>

</html>