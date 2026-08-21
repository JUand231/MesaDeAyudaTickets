<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="es">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <title>Mis Tickets | Mesa de Ayuda</title>

        <link rel="stylesheet"
              href="${pageContext.request.contextPath}/css/estilo.css">
    </head>

    <body>

        <!-- SIDEBAR -->
        <aside class="sidebar" id="sidebar">

            <div class="marca">
                <div class="marca-icono"><span>🌐</span></div>
                <div class="marca-texto">
                    <strong>MESA DE AYUDA</strong>
                    <small>CIMM · SENA</small>
                </div>
            </div>

            <div class="menu-titulo">PRINCIPAL</div>

            <nav class="menu">

                <a href="${pageContext.request.contextPath}/dashboardSolicitante" class="menu-item ">
                    <span>⌂</span><label>Dashboard</label>
                </a>

                <a href="${pageContext.request.contextPath}/tickets" class="menu-item activo">
                    <span>🏷</span><label>Mis tickets</label>
                </a>

                <a href="${pageContext.request.contextPath}/tickets?estado=En%20Proceso" class="menu-item">
                    <span>◷</span><label>Estado</label>
                </a>

            </nav>

            <div class="menu-titulo">SISTEMA</div>

            <nav class="menu">

                <a href="${pageContext.request.contextPath}/perfil" class="menu-item">
                    <span>⚙</span><label>Mi Perfil</label>
                </a>

                <a href="${pageContext.request.contextPath}/CerrarSesion" class="menu-item">
                    <span>↪</span>
                    <label> Cerrar sesión </label>
                </a>

            </nav>

            <div class="usuario-sidebar">
                <div class="usuario-avatar">S</div>
                <div>
                    <strong>Solicitante</strong>
                    <small>Usuario solicitante</small>
                </div>
            </div>

        </aside>


        <!-- CONTENIDO PRINCIPAL -->
        <main class="contenido">

            <!-- BARRA SUPERIOR -->
            <header class="barra-superior">

                <div>

                    <h1>Mis Tickets</h1>

                    <p>
                        Consulta y realiza seguimiento a tus solicitudes de soporte.
                    </p>

                </div>

                <div class="barra-acciones">

                    <!-- NUEVO TICKET -->
                    <a href="${pageContext.request.contextPath}/ticket/nuevo"
                       class="btn-principal">

                        ＋ Nuevo ticket

                    </a>

                </div>

            </header>


            <!-- PANEL DE TICKETS -->
            <section class="panel">

                <div class="panel-header">

                    <div>

                        <h2>Mis solicitudes</h2>

                        <p>
                            Tickets creados por ti.
                        </p>

                    </div>

                    <div>

                        <strong>
                            ${totalTickets}
                        </strong>

                        <small>
                            tickets
                        </small>

                    </div>

                </div>


                <!-- TABLA -->
                <div class="tabla-contenedor">

                    <table class="tabla">

                        <thead>

                            <tr>

                                <th>TICKET</th>

                                <th>CATEGORÍA</th>

                                <th>PRIORIDAD</th>

                                <th>ESTADO</th>

                                <th>AGENTE</th>

                            </tr>

                        </thead>


                        <tbody>

                            <!-- CUANDO NO HAY TICKETS -->
                            <c:if test="${empty tickets}">

                                <tr>

                                    <td colspan="5">

                                        No tienes tickets registrados.

                                    </td>

                                </tr>

                            </c:if>


                            <!-- LISTADO DE TICKETS -->
                            <c:forEach var="ticket"
                                       items="${tickets}">

                                <tr onclick="window.location.href = '${pageContext.request.contextPath}/detalleTicket?id=${ticket.idTicket}'"
                                    style="cursor:pointer;">

                                    <td>

                                        <strong class="ticket-id">
                                            #TK-${ticket.idTicket}
                                        </strong>

                                        <span class="ticket-titulo">
                                            ${ticket.titulo}
                                        </span>

                                    </td>

                                    <td>
                                        ${ticket.nombreCategoria}
                                    </td>

                                    <td>
                                        ${ticket.nombrePrioridad}
                                    </td>

                                    <td>

                                        <c:choose>

                                            <c:when test="${ticket.estado == 'NUEVO'}">
                                                <span class="badge estado-nuevo">
                                                    NUEVO
                                                </span>
                                            </c:when>

                                            <c:when test="${ticket.estado == 'ASIGNADO'}">
                                                <span class="badge estado-nuevo">
                                                    ASIGNADO
                                                </span>
                                            </c:when>

                                            <c:when test="${ticket.estado == 'EN_PROCESO'}">
                                                <span class="badge estado-proceso">
                                                    EN PROCESO
                                                </span>
                                            </c:when>

                                            <c:when test="${ticket.estado == 'RESUELTO'}">
                                                <span class="badge estado-resuelto">
                                                    RESUELTO
                                                </span>
                                            </c:when>

                                            <c:when test="${ticket.estado == 'CANCELADO'}">
                                                <span class="badge estado-cancelado">
                                                    CANCELADO
                                                </span>
                                            </c:when>

                                            <c:otherwise>
                                                <span class="badge">
                                                    ${ticket.estado}
                                                </span>
                                            </c:otherwise>

                                        </c:choose>

                                    </td>

                                    <td>

                                        <c:choose>

                                            <c:when test="${not empty ticket.nombreAgente}">
                                                ${ticket.nombreAgente}
                                            </c:when>

                                            <c:otherwise>
                                                Sin asignar
                                            </c:otherwise>

                                        </c:choose>

                                    </td>

                                </tr>

                            </c:forEach>

                        </tbody>

                    </table>

                </div>

            </section>

        </main>

    </body>

</html>