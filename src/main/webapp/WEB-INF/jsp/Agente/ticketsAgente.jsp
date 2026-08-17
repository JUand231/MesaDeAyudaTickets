<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

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

                <div class="marca-icono">
                    <span>⌁</span>
                </div>

                <div class="marca-texto">
                    <strong>MESA DE AYUDA</strong>
                    <small>CIMM · SENA</small>
                </div>

            </div>


            <div class="menu-titulo">
                PRINCIPAL
            </div>


            <nav class="menu">

                <!-- DASHBOARD -->
                <a href="${pageContext.request.contextPath}/dashboardAgente"
                   class="menu-item">

                    <span>⌂</span>
                    <label>Dashboard</label>

                </a>


                <!-- MIS TICKETS -->
                <a href="${pageContext.request.contextPath}/tickets"
                   class="menu-item ${empty param.estado ? 'activo' : ''}">

                    <span>▣</span>
                    <label>Mis tickets</label>

                </a>

            </nav>


            <div class="menu-titulo">
                GESTIÓN
            </div>


            <nav class="menu">

                <!-- PENDIENTES -->
                <a href="${pageContext.request.contextPath}/tickets?estado=NUEVO"
                   class="menu-item ${param.estado == 'NUEVO' ? 'activo' : ''}">
                    <span>●</span>
                    <label>Pendientes</label>
                </a>

                <!-- EN PROCESO -->
                <a href="${pageContext.request.contextPath}/tickets?estado=EN_PROCESO"
                   class="menu-item ${param.estado == 'EN_PROCESO' ? 'activo' : ''}">
                    <span>◷</span>
                    <label>En proceso</label>
                </a>


                <!-- RESUELTOS -->
                <a href="${pageContext.request.contextPath}/tickets?estado=RESUELTO"
                   class="menu-item ${param.estado == 'RESUELTO' ? 'activo' : ''}">

                    <span>✓</span>
                    <label>Resueltos</label>

                </a>

            </nav>


            <div class="menu-titulo">
                SISTEMA
            </div>


            <nav class="menu">

                <!-- PERFIL -->
                <a href="${pageContext.request.contextPath}/perfil"
                   class="menu-item">

                    <span>⚙</span>
                    <label>Mi Perfil</label>

                </a>


                <!-- CERRAR SESIÓN -->
                <a href="${pageContext.request.contextPath}/CerrarSesion"
                   class="menu-item">

                    <span>↪</span>
                    <label>Cerrar sesión</label>

                </a>

            </nav>


            <!-- USUARIO -->
            <div class="usuario-sidebar">

                <div class="usuario-avatar">
                    A
                </div>

                <div>

                    <strong>Agente</strong>

                    <small>
                        Agente de soporte
                    </small>

                </div>

            </div>

        </aside>


        <!-- CONTENIDO -->
        <main class="contenido">


            <!-- CABECERA -->
            <header class="barra-superior">

                <div>

                    <h1>Mis Tickets</h1>

                    <p>
                        Consulta y gestiona los tickets asignados a ti.
                    </p>

                </div>

            </header>


            <!-- PANEL -->
            <section class="panel">

                <div class="panel-header">

                    <div>

                        <h2>Tickets asignados</h2>

                        <p>
                            Solicitudes que requieren tu atención.
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

                                <th>SOLICITANTE</th>

                            </tr>

                        </thead>


                        <tbody>


                            <!-- SIN TICKETS -->
                            <c:if test="${empty tickets}">

                                <tr>

                                    <td colspan="5">

                                        No tienes tickets asignados.

                                    </td>

                                </tr>

                            </c:if>


                            <!-- TICKETS -->
                            <c:forEach var="ticket"
                                       items="${tickets}">

                                <tr>


                                    <!-- TICKET -->
                                    <td>

                                        <a href="${pageContext.request.contextPath}/detalleTicket?id=${ticket.idTicket}"
                                           style="text-decoration:none;">

                                            <strong class="ticket-id">
                                                #TK-${ticket.idTicket}
                                            </strong>

                                            <span class="ticket-titulo">
                                                ${ticket.titulo}
                                            </span>

                                        </a>

                                    </td>


                                    <!-- CATEGORÍA -->
                                    <td>

                                        ${ticket.nombreCategoria}

                                    </td>


                                    <!-- PRIORIDAD -->
                                    <td>

                                        ${ticket.nombrePrioridad}

                                    </td>


                                    <!-- ESTADO -->
                                    <td>

                                        <c:choose>

                                            <c:when test="${ticket.estado == 'NUEVO'}">

                                                <span class="badge estado-nuevo">
                                                    NUEVO
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


                                    <!-- SOLICITANTE -->
                                    <td>

                                        <c:choose>

                                            <c:when test="${not empty ticket.nombreSolicitante}">

                                                ${ticket.nombreSolicitante}

                                            </c:when>

                                            <c:otherwise>

                                                Sin información

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