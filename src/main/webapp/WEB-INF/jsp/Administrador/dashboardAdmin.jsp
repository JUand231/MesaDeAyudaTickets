<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Dashboard | Mesa de Ayuda</title>
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
                <a href="${pageContext.request.contextPath}/dashboardAdmin" class="menu-item activo">
                    <span>⌂</span><label>Dashboard</label>
                </a>

                <a href="${pageContext.request.contextPath}/tickets" class="menu-item">
                    <span>▣</span><label>Tickets</label>
                </a>
            </nav>

            <div class="menu-titulo">ADMINISTRACIÓN</div>

            <nav class="menu">
                <a href="${pageContext.request.contextPath}/agentes" class="menu-item">
                    <span>♟</span><label>Agentes</label>
                </a>

                <a href="${pageContext.request.contextPath}/categorias" class="menu-item">
                    <span>◇</span><label>Categorías</label>
                </a>

                <a href="${pageContext.request.contextPath}/reportes" class="menu-item">
                    <span>▥</span><label>Reportes</label>
                </a>

                <a href="${pageContext.request.contextPath}/usuarios" class="menu-item">
                    <span>●️</span><label>Comentarios</label>
                </a>
            </nav>

            <div class="menu-titulo">SISTEMA</div>

            <nav class="menu">
                <a href="${pageContext.request.contextPath}/perfil" class="menu-item">
                    <span>⚙</span><label>Mi Perfil</label>
                </a>

                <a href="${pageContext.request.contextPath}/CerrarSesion" class="menu-item">
                    <span>↪</span><label>Cerrar sesión</label>
                </a>
            </nav>

            <div class="usuario-sidebar">
                <div class="usuario-avatar">A</div>
                <div>
                    <strong>Administrador</strong>
                    <small>Administrador</small>
                </div>
            </div>

        </aside>

        <!-- CONTENIDO -->
        <main class="contenido">

            <header class="barra-superior">
                <div>
                    <h1>Dashboard</h1>
                    <p>Bienvenido al centro de gestión de soporte.</p>
                </div>

                <div class="barra-acciones">
                    <button class="btn-notificacion">
                        ♧<span></span>
                    </button>
                </div>
            </header>

            <!-- ESTADÍSTICAS -->
            <section class="estadisticas">

                <div class="estadistica">
                    <div class="estadistica-icono azul">▣</div>
                    <div>
                        <span>TOTAL DE TICKETS</span>
                        <strong>${totalTickets}</strong>
                        <small>Todos los registrados</small>
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
                    <div class="estadistica-icono verde">✓</div>
                    <div>
                        <span>RESUELTOS</span>
                        <strong>${resueltos}</strong>
                        <small class="positivo">${porcentajeResolucion}% de resolución</small>
                    </div>
                </div>

                <div class="estadistica">
                    <div class="estadistica-icono rojo">!</div>
                    <div>
                        <span>CRÍTICOS</span>
                        <strong>${criticos}</strong>
                        <small class="negativo">Atención inmediata</small>
                    </div>
                </div>

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
                                    <th>PRIORIDAD</th>
                                    <th>ESTADO</th>
                                    <th>SOLICITANTE</th>
                                    <th>AGENTE</th>
                                </tr>
                            </thead>

                            <tbody>

                                <c:if test="${empty ticketsRecientes}">
                                    <tr>
                                        <td colspan="5">No hay tickets recientes.</td>
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
                                                <c:when test="${ticket.nombrePrioridad == 'CRITICA'}">
                                                    <span class="badge prioridad-critica">CRÍTICA</span>
                                                </c:when>

                                                <c:when test="${ticket.nombrePrioridad == 'ALTA'}">
                                                    <span class="badge prioridad-alta">ALTA</span>
                                                </c:when>

                                                <c:when test="${ticket.nombrePrioridad == 'MEDIA'}">
                                                    <span class="badge prioridad-media">MEDIA</span>
                                                </c:when>

                                                <c:otherwise>
                                                    <span class="badge prioridad-baja">BAJA</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>

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
                                        
                                        <td>${ticket.nombreSolicitante}</td>
                                        <td>${ticket.nombreAgente}</td>
                                    </tr>
                                </c:forEach>

                            </tbody>

                        </table>

                    </div>

                </div>

                <!-- ACTIVIDAD -->
                <div class="panel">

                    <div class="panel-header">
                        <div>
                            <h2>Actividad reciente</h2>
                            <p>Últimos movimientos</p>
                        </div>
                    </div>

                    <div class="actividad">

                        <div class="actividad-item">
                            <div class="actividad-icon azul">+</div>
                            <div>
                                <strong>Nuevo ticket</strong>
                                <p>Sofia creó el ticket #TK-0248</p>
                                <small>Hace 5 minutos</small>
                            </div>
                        </div>

                        <div class="actividad-item">
                            <div class="actividad-icon verde">✓</div>
                            <div>
                                <strong>Ticket resuelto</strong>
                                <p>#TK-0246 fue resuelto</p>
                                <small>Hace 18 minutos</small>
                            </div>
                        </div>

                        <div class="actividad-item">
                            <div class="actividad-icon naranja">→</div>
                            <div>
                                <strong>Ticket asignado</strong>
                                <p>#TK-0248 → Carlos M.</p>
                                <small>Hace 32 minutos</small>
                            </div>
                        </div>

                        <div class="actividad-item">
                            <div class="actividad-icon rojo">!</div>
                            <div>
                                <strong>Atención requerida</strong>
                                <p>Ticket crítico pendiente</p>
                                <small>Hace 1 hora</small>
                            </div>
                        </div>

                    </div>

                </div>

            </section>
        </main>

    </body>
</html>