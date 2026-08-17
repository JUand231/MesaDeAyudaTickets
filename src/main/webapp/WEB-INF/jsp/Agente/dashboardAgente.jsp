<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Dashboard | Agente - Mesa de Ayuda</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilo.css">
    </head>

    <body>

        <!-- SIDEBAR -->
        <aside class="sidebar" id="sidebar">


            <div class="marca">
                <div class="marca-icono"><span>⌁</span></div>
                <div class="marca-texto">
                    <strong>MESA DE AYUDA</strong>
                    <small>CIMM · SENA</small>
                </div>
            </div>

            <div class="menu-titulo">PRINCIPAL</div>

            <nav class="menu">
                <a href="${pageContext.request.contextPath}/dashboardAgente" class="menu-item activo">
                    <span>⌂</span><label>Dashboard</label>
                </a>

                <a href="${pageContext.request.contextPath}/tickets" class="menu-item">
                    <span>▣</span>
                    <label>Mis tickets</label>
                </a>
            </nav>

            <div class="menu-titulo">GESTIÓN</div>

            <nav class="menu">
                <!-- PENDIENTES -->
                <a href="${pageContext.request.contextPath}/tickets?estado=NUEVO"
                   class="menu-item">
                    <span>●</span>
                    <label>Pendientes</label>
                </a>

                <!-- EN PROCESO -->
                <a href="${pageContext.request.contextPath}/tickets?estado=EN_PROCESO"
                   class="menu-item">
                    <span>◷</span>
                    <label>En proceso</label>
                </a>

                <!-- RESUELTOS -->
                <a href="${pageContext.request.contextPath}/tickets?estado=RESUELTO"
                   class="menu-item">
                    <span>✓</span>
                    <label>Resueltos</label>
                </a>

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
                    <div class="usuario-avatar">A</div>
                    <div>
                        <strong>Agente</strong>
                        <small>Agente de soporte</small>
                    </div>
                </div>

        </aside>
        <main class="contenido">

            <!-- ESTADÍSTICAS -->
            <section class="estadisticas">

                <div class="estadistica">
                    <div class="estadistica-icono azul">▣</div>
                    <div>
                        <span>MIS TICKETS</span>
                        <strong>${totalTickets}</strong>
                        <small>Tickets asignados</small>
                    </div>
                </div>

                <div class="estadistica">
                    <div class="estadistica-icono naranja">◷</div>
                    <div>
                        <span>PENDIENTES</span>
                        <strong>${pendientes}</strong>
                        <small>Requieren atención</small>
                    </div>
                </div>

                <div class="estadistica">
                    <div class="estadistica-icono verde">→</div>
                    <div>
                        <span>EN PROCESO</span>
                        <strong>${enProceso}</strong>
                        <small>Actualmente atendiendo</small>
                    </div>
                </div>

                <div class="estadistica">
                    <div class="estadistica-icono rojo">✓</div>
                    <div>
                        <span>RESUELTOS</span>
                        <strong>${resueltos}</strong>
                        <small class="positivo">Tickets solucionados</small>
                    </div>
                </div>

            </section>

            <!-- GRID PRINCIPAL -->
            <section class="dashboard-grid">

                <!-- MIS TICKETS -->
                <div class="panel">

                    <div class="panel-header">
                        <div>
                            <h2>Mis tickets</h2>
                            <p>Tickets asignados para atención</p>
                        </div>
                        <a href="${pageContext.request.contextPath}/tickets">Ver todos →</a>
                    </div>

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

                                <c:forEach var="ticket" items="${tickets}">

                                    <tr>
                                        <td>
                                            <strong class="ticket-id">
                                                #TK-${ticket.idTicket}
                                            </strong>

                                            <span class="ticket-titulo">
                                                ${ticket.titulo}
                                            </span>
                                        </td>

                                        <td>
                                            ${nombresCategorias[ticket.idCategoria]}
                                        </td>

                                        <td>
                                            ${nombresPrioridades[ticket.idPrioridad]}
                                        </td>

                                        <td>
                                            <c:choose>

                                                <c:when test="${ticket.estadoNombre == 'NUEVO' || ticket.estadoNombre == 'ASIGNADO'}">
                                                    <span class="badge estado-nuevo">
                                                        ${ticket.estadoNombre}
                                                    </span>
                                                </c:when>

                                                <c:when test="${ticket.estadoNombre == 'EN_PROCESO'}">
                                                    <span class="badge estado-proceso">
                                                        EN PROCESO
                                                    </span>
                                                </c:when>

                                                <c:when test="${ticket.estadoNombre == 'RESUELTO'}">
                                                    <span class="badge estado-resuelto">
                                                        RESUELTO
                                                    </span>
                                                </c:when>

                                                <c:otherwise>
                                                    <span class="badge">
                                                        ${ticket.estadoNombre}
                                                    </span>
                                                </c:otherwise>

                                            </c:choose>
                                        </td>

                                        <td>
                                            ${nombresUsuarios[ticket.idSolicitante]}
                                        </td>
                                    </tr>

                                </c:forEach>

                            </tbody>
                        </table>
                    </div>

                </div>
            </section>

        </main>

    </body>
</html>