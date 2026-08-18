<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="es">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Comentarios | Mesa de Ayuda</title>
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
                <a href="${pageContext.request.contextPath}/dashboardAdmin" class="menu-item ">
                    <span>⌂</span><label>Dashboard</label>
                </a>

                <a href="${pageContext.request.contextPath}/tickets" class="menu-item">
                    <span>🏷</span><label>Tickets</label>
                </a>
            </nav>

            <div class="menu-titulo">ADMINISTRACIÓN</div>

            <nav class="menu">
                <a href="${pageContext.request.contextPath}/admin/agentes" class="menu-item">
                    <span>♟</span><label>Agentes</label>
                </a>

                <a href="${pageContext.request.contextPath}/categorias" class="menu-item">
                    <span>▥</span><label>Categorías</label>
                </a>

                <a href="${pageContext.request.contextPath}/comentariosAdmin" class="menu-item activo">
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

        <!-- CONTENIDO -->
        <main class="contenido">

            <!-- ENCABEZADO -->
            <header class="barra-superior">
                <div>
                    <h1>Comentarios</h1>
                    <p>Consulta y gestiona las conversaciones de los tickets.</p>
                </div>
                <div class="barra-acciones">
                </div>
            </header>

            <!-- ESTADÍSTICAS -->
            <section class="estadisticas">
                <!-- TOTAL -->
                <div class="estadistica">
                    <div class="estadistica-icono azul">🖂</div>
                    <div>
                        <span>TOTAL COMENTARIOS</span>
                        <strong>${totalComentarios}</strong>
                        <small>Comentarios registrados</small>
                    </div>
                </div>

                <!-- HOY -->
                <div class="estadistica">
                    <div class="estadistica-icono naranja">◷</div>
                    <div>
                        <span>COMENTARIOS HOY</span>
                        <strong>${comentariosHoy}</strong>
                        <small>Actividad del día</small>
                    </div>
                </div>

                <!-- AGENTES -->
                <div class="estadistica">
                    <div class="estadistica-icono verde">♟</div>
                    <div>
                        <span>AGENTES</span>
                        <strong>${comentariosAgentes}</strong>
                        <small>Comentarios realizados</small>
                    </div>
                </div>

                <!-- SOLICITANTES -->
                <div class="estadistica">
                    <div class="estadistica-icono rojo">●</div>
                    <div>
                        <span>SOLICITANTES</span>
                        <strong>${comentariosSolicitantes}</strong>
                        <small>Comentarios realizados</small>
                    </div>
                </div>
            </section>

            <!-- FILTROS -->
            <section class="panel filtros-panel">
                <div class="panel-header">
                    <div>
                        <h2>Buscar comentarios</h2>
                        <p>Utiliza los filtros para encontrar una conversación.</p>
                    </div>
                </div>

                <form class="filtros-form"
                      method="get"
                      action="${pageContext.request.contextPath}/comentariosAdmin">

                    <div class="campo-filtro">
                        <label>Buscar</label>
                        <div class="input-busqueda">
                            <span>⌕</span>
                            <input type="text"
                                   name="buscar"
                                   placeholder="Comentario, usuario o ID del ticket..."
                                   value="${param.buscar}">
                        </div>
                    </div>

                    <div class="campo-filtro">
                        <label>Rol</label>
                        <select name="rol">
                            <option value="">Todos</option>
                            <option value="AGENTE">Agente</option>
                            <option value="SOLICITANTE">Solicitante</option>
                        </select>
                    </div>

                    <button type="submit" class="btn-filtrar">
                        Filtrar
                    </button>

                </form>
            </section>

            <!-- TABLA DE COMENTARIOS -->
            <section class="panel">
                <div class="panel-header">
                    <div>
                        <h2>Comentarios recientes</h2>
                        <p>Últimas conversaciones registradas.</p>
                    </div>
                </div>

                <div class="tabla-contenedor">
                    <table class="tabla">
                        <thead>
                            <tr>
                                <th>TICKET</th>
                                <th>USUARIO</th>
                                <th>ROL</th>
                                <th>COMENTARIO</th>
                                <th>FECHA</th>
                                <th>ACCIÓN</th>
                            </tr>
                        </thead>
                        <tbody>
                            <!-- SIN RESULTADOS -->
                            <c:if test="${empty comentarios}">
                                <tr>
                                    <td colspan="6" style="text-align:center; padding:30px;">
                                        No hay comentarios para mostrar.
                                    </td>
                                </tr>
                            </c:if>

                            <!-- COMENTARIOS -->
                            <c:forEach var="comentario" items="${comentarios}">
                                <tr>
                                    <td><strong class="ticket-id">#TK-${comentario.idTicket}</strong></td>
                                    <td>
                                        <div class="persona">
                                            <span>${comentario.nombreUsuario}</span>
                                        </div>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${comentario.nombreRol == 'ADMINISTRADOR'}">
                                                <span class="badge estado-resuelto">ADMINISTRADOR</span>
                                            </c:when>
                                            <c:when test="${comentario.nombreRol == 'AGENTE'}">
                                                <span class="badge estado-proceso">AGENTE</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge estado-nuevo">SOLICITANTE</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td><span class="ticket-titulo">${comentario.texto}</span></td>
                                    <td>${comentario.fecha}</td>
                                    <td>
                                        <div class="acciones-ticket">
                                            <a href="${pageContext.request.contextPath}/tickets?id=${comentario.idTicket}" class="accion ver" title="Ver ticket">+</a>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
                <!-- PAGINACIÓN -->
                <div class="paginacion">
                    <span class="paginacion-info">Mostrando comentarios recientes</span>
                    <div class="paginas">
                        <button class="pagina disabled">‹</button>
                        <button class="pagina activa">1</button>
                        <button class="pagina">2</button>
                        <button class="pagina">3</button>
                        <span class="puntos">...</span>
                        <button class="pagina">›</button>
                    </div>
                </div>
            </section>


            <!-- NUEVO COMENTARIO -->
            <section class="panel">
                <div class="panel-header">
                    <div>
                        <h2>Agregar comentario</h2>
                        <p>Escribe un comentario en un ticket.</p>
                    </div>
                </div>

                <form method="post" action="${pageContext.request.contextPath}/comentarios">
                    <!-- TICKET -->
                    <div class="campo-filtro" style="margin-bottom:20px;">
                        <label>Ticket</label>
                        <select name="idTicket" required>
                            <option value="">Selecciona un ticket</option>
                            <c:forEach var="ticket" items="${tickets}">
                                <option value="${ticket.idTicket}">#TK-${ticket.idTicket} - ${ticket.titulo}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <!-- COMENTARIO -->
                    <div class="campo-filtro" style="margin-bottom:20px;">
                        <label>Comentario</label>
                        <textarea name="contenido" rows="5" maxlength="1000" required placeholder="Escribe tu comentario..."></textarea>
                    </div>

                    <!-- BOTÓN -->
                    <button type="submit" class="btn-filtrar">✎ Publicar comentario</button>
                </form>
            </section>

        </main>

    </body>

</html>