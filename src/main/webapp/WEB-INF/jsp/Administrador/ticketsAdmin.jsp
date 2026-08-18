<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Tickets | Mesa de Ayuda</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilo.css">
    </head>
    <body>

        <!-- ==================================================
             SIDEBAR
        ================================================== -->
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
                <a href="${pageContext.request.contextPath}/dashboardAdmin" class="menu-item">
                    <span>⌂</span><label>Dashboard</label>
                </a>
                <a href="${pageContext.request.contextPath}/tickets" class="menu-item activo">
                    <span>🏷</span><label>Tickets</label>
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
                    <span>🖂️</span><label>Comentarios</label>
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

        <!-- ==================================================
             CONTENIDO PRINCIPAL
        ================================================== -->
        <main class="contenido">

            <!-- ENCABEZADO -->
            <header class="barra-superior">
                <div>
                    <h1>Tickets</h1>
                    <p>Gestiona y consulta todas las solicitudes de soporte.</p>
                </div>
                <div class="barra-acciones">
                    <button class="btn-notificacion">
                        ♧
                        <span></span>
                    </button>
                </div>
            </header>

            <!-- ==================================================
                 PANEL DE FILTROS
            ================================================== -->
            <section class="panel filtros-panel">
                <div class="panel-header">
                    <div>
                        <h2>Buscar tickets</h2>
                        <p>Utiliza los filtros para encontrar una solicitud.</p>
                    </div>
                </div>

                <form class="filtros-form" method="get" action="${pageContext.request.contextPath}/tickets">
                    <div class="campo-filtro">
                        <label>Buscar</label>
                        <div class="input-busqueda">
                            <span>⌕</span>
                            <input type="text" name="buscar" placeholder="Título, descripción o ID...">
                        </div>
                    </div>

                    <div class="campo-filtro">
                        <label>Estado</label>
                        <select name="estado">
                            <option value="">Todos los estados</option>
                            <option value="Nuevo">Nuevo</option>
                            <option value="En proceso">En proceso</option>
                            <option value="Resuelto">Resuelto</option>
                            <option value="Cerrado">Cerrado</option>
                        </select>
                    </div>

                    <div class="campo-filtro">
                        <label>Prioridad</label>
                        <select name="prioridad">
                            <option value="">Todas</option>
                            <option value="Crítica">Crítica</option>
                            <option value="Alta">Alta</option>
                            <option value="Media">Media</option>
                            <option value="Baja">Baja</option>
                        </select>
                    </div>

                    <div class="campo-filtro">
                        <label>Categoría</label>
                        <select name="categoria">
                            <option value="">Todas</option>
                            <option value="Hardware">Hardware</option>
                            <option value="Software">Software</option>
                            <option value="Red">Red</option>
                            <option value="Mantenimiento">Mantenimiento</option>
                        </select>
                    </div>

                    <button type="submit" class="btn-filtrar">Filtrar</button>
                </form>
            </section>

     <!-- ==================================================
         TABLA DE TICKETS
        ================================================== -->
            <section class="panel tickets-panel">
                <div class="panel-header">
                    <div>
                        <h2>Todos los tickets</h2>
                        <p>${totalTickets} solicitudes encontradas</p>
                    </div>
                    <div class="tabla-controles">
                        <button class="btn-exportar">↓ Exportar</button>
                    </div>
                </div>

                <div class="tabla-contenedor">
                    <table class="tabla tabla-tickets">
                        <thead>
                            <tr>
                                <th>TICKET</th>
                                <th>TÍTULO</th>
                                <th>CATEGORÍA</th>
                                <th>PRIORIDAD</th>
                                <th>ESTADO</th>
                                <th>FECHA</th>
                                <th>SOLICITANTE</th>
                                <th>AGENTE</th>
                                <th>ACCIONES</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:if test="${empty tickets}">
                                <tr>
                                    <td colspan="9" style="text-align:center; padding:24px;">
                                        No hay tickets para mostrar.
                                    </td>
                                </tr>
                            </c:if>

                            <c:forEach var="ticket" items="${tickets}">
                                <tr>
                                    <td><strong class="ticket-id">#TK-${ticket.idTicket}</strong></td>

                                    <td>
                                        <span class="ticket-titulo">${ticket.titulo}</span>
                                        <small class="ticket-descripcion">${ticket.descripcion}</small>
                                    </td>

                                    <td><span class="categoria-texto">${ticket.nombreCategoria}</span></td>

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

                                    <td>${ticket.fechaCreacion}</td>

                                    <td>
                                        <div class="persona"><span>${ticket.nombreSolicitante}</span></div>
                                    </td>

                                    <td>
                                        <div class="persona"><span>${ticket.nombreAgente}</span></div>
                                    </td>

                                    <td>
                                        <div class="acciones-ticket">
                                            <a href="${pageContext.request.contextPath}/tickets?id=${ticket.idTicket}" class="accion ver" title="Ver ticket">+</a>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </section>
            <!-- ==================================================
                 PAGINACIÓN
            ================================================== -->
            <div class="paginacion">
                <span class="paginacion-info">Mostrando 1–5 de 248 tickets</span>
                <div class="paginas">
                    <button class="pagina disabled">‹</button>
                    <button class="pagina activa">1</button>
                    <button class="pagina">2</button>
                    <button class="pagina">3</button>
                    <span class="puntos">...</span>
                    <button class="pagina">50</button>
                    <button class="pagina">›</button>
                </div>
            </div>
        </section>

    </main>

</body>
</html>