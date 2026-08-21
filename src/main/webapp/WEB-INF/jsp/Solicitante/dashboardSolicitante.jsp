<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="es">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Dashboard | Solicitante - Mesa de Ayuda</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilo.css">
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

                <a href="${pageContext.request.contextPath}/dashboardSolicitante" class="menu-item activo">
                    <span>⌂</span><label>Dashboard</label>
                </a>

                <a href="${pageContext.request.contextPath}/tickets" class="menu-item">
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

        <!-- CONTENIDO -->
        <main class="contenido">

            <header class="barra-superior">

                <div>
                    <h1>Dashboard</h1>
                    <p>Consulta y gestiona tus solicitudes de soporte.</p>
                </div>

                <div class="barra-acciones">

                    <a href="${pageContext.request.contextPath}/notificaciones"
                       class="btn-notificacion"
                       title="Notificaciones">

                        🔔

                        <c:if test="${notificacionesNoLeidas > 0}">
                            <span class="contador-notificaciones">
                                ${notificacionesNoLeidas}
                            </span>
                        </c:if>

                    </a>

                    <a href="${pageContext.request.contextPath}/ticket/nuevo" class="btn-principal">
                        ＋ Nuevo ticket
                    </a>

                </div>

            </header>

            <!-- ACCESOS RÁPIDOS -->
            <section class="estadisticas">

                <a href="${pageContext.request.contextPath}/ticket/nuevo" class="estadistica acceso">
                    <div class="estadistica-icono azul">＋</div>
                    <div>
                        <strong>Nuevo ticket</strong>
                        <small>Crear una solicitud</small>
                    </div>
                </a>

                <a href="${pageContext.request.contextPath}/tickets" class="estadistica acceso">
                    <div class="estadistica-icono naranja">▣</div>
                    <div>
                        <strong>Mis tickets</strong>
                        <small>Consultar tus solicitudes</small>
                    </div>
                </a>

                <a href="${pageContext.request.contextPath}/tickets?estado=En%20Proceso" class="estadistica acceso">
                    <div class="estadistica-icono verde">◷</div>
                    <div>
                        <strong>En proceso</strong>
                        <small>Consultar tickets activos</small>
                    </div>
                </a>

                <a href="${pageContext.request.contextPath}/tickets?estado=Resuelto" class="estadistica acceso">
                    <div class="estadistica-icono rojo">✓</div>
                    <div>
                        <strong>Resueltos</strong>
                        <small>Ver solicitudes solucionadas</small>
                    </div>
                </a>

            </section>

            <!-- GRID PRINCIPAL -->
            <section class="dashboard-grid">

                <!-- TICKETS -->
                <div class="panel">

                    <div class="panel-header">
                        <div>
                            <h2>Tickets recientes</h2>
                            <p>Últimas solicitudes registradas</p>
                        </div>

                        <a href="${pageContext.request.contextPath}/tickets">Ver todos →</a>
                    </div>

                    <div class="tabla-contenedor">

                        <table class="tabla">

                            <thead>
                                <tr>
                                    <th>TICKET</th>
                                    <th>CATEGORÍA</th>
                                    <th>ESTADO</th>
                                    <th>AGENTE</th>
                                </tr>
                            </thead>

                            <tbody>

                                <c:if test="${empty ticketsRecientes}">
                                    <tr>
                                        <td colspan="4">No hay tickets recientes.</td>
                                    </tr>
                                </c:if>

                                <c:forEach var="ticket" items="${ticketsRecientes}">
                                    <tr>

                                        <td>
                                            <strong class="ticket-id">#TK-${ticket.idTicket}</strong>
                                            <span class="ticket-titulo">${ticket.titulo}</span>
                                        </td>

                                        <td>${ticket.nombreCategoria}</td>

                                        <td>
                                            <c:choose>
                                                <c:when test="${ticket.estado == 'NUEVO'}">
                                                    <span class="badge estado-nuevo">NUEVO</span>
                                                </c:when>

                                                <c:when test="${ticket.estado == 'EN_PROCESO'}">
                                                    <span class="badge estado-proceso">EN PROCESO</span>
                                                </c:when>

                                                <c:when test="${ticket.estado == 'RESUELTO'}">
                                                    <span class="badge estado-resuelto">RESUELTO</span>
                                                </c:when>

                                                <c:otherwise>
                                                    <span class="badge">${ticket.estado}</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>

                                        <td>${ticket.nombreAgente}</td>

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