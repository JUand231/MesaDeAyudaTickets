<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Detalle Ticket | Mesa de Ayuda</title>
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

        <!-- CONTENIDO -->
        <main class="contenido">

            <header class="barra-superior">
                <div>
                    <h1>Detalle del ticket</h1>
                    <p>Consulta la información de tu solicitud.</p>
                </div>
                <div class="barra-acciones">
                    <a href="${pageContext.request.contextPath}/tickets" class="btn-principal">
                        ← Volver a mis tickets
                    </a>
                </div>
            </header>

            <!-- INFORMACIÓN DEL TICKET -->
            <section class="panel">

                <div class="panel-header">
                    <div>
                        <h2>#TK-${ticket.idTicket}</h2>
                        <p>${ticket.titulo}</p>
                    </div>
                    <div>
                        <c:choose>
                            <c:when test="${ticket.estado == 'NUEVO'}">
                                <span class="badge estado-nuevo">NUEVO</span>
                            </c:when>
                            <c:when test="${ticket.estado == 'ASIGNADO'}">
                                <span class="badge estado-nuevo">ASIGNADO</span>
                            </c:when>
                            <c:when test="${ticket.estado == 'EN_PROCESO'}">
                                <span class="badge estado-proceso">EN PROCESO</span>
                            </c:when>
                            <c:when test="${ticket.estado == 'RESUELTO'}">
                                <span class="badge estado-resuelto">RESUELTO</span>
                            </c:when>
                            <c:when test="${ticket.estado == 'CANCELADO'}">
                                <span class="badge estado-cancelado">CANCELADO</span>
                            </c:when>
                            <c:otherwise>
                                <span class="badge">${ticket.estado}</span>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>

                <div class="panel-body">

                    <div class="info-grid">

                        <div>
                            <strong>Categoría</strong>
                            <p>${ticket.nombreCategoria}</p>
                        </div>

                        <div>
                            <strong>Prioridad</strong>
                            <p>${ticket.nombrePrioridad}</p>
                        </div>

                        <div>
                            <strong>Solicitante</strong>
                            <p>${ticket.nombreSolicitante}</p>
                        </div>

                        <div>
                            <strong>Agente asignado</strong>
                            <p>${ticket.nombreAgente}</p>
                        </div>

                        <div>
                            <strong>Fecha de creación</strong>
                            <p>${ticket.fechaCreacion}</p>
                        </div>

                        <div>
                            <strong>SLA</strong>
                            <p>${horasSLA} horas</p>
                        </div>

                        <div>
                            <strong>Fecha límite SLA</strong>
                            <p>${fechaLimiteSLA}</p>
                        </div>

                        <div>
                            <strong>Estado SLA</strong>
                            <p>
                                <c:choose>
                                    <c:when test="${slaVencido}">VENCIDO</c:when>
                                    <c:otherwise>DENTRO DEL SLA</c:otherwise>
                                </c:choose>
                            </p>
                        </div>

                    </div>

                    <div>
                        <strong>Descripción</strong>
                        <div class="descripcion-box">
                            ${ticket.descripcion}
                        </div>
                    </div>

                </div>

            </section>

            <!-- ESTADO -->
            <section class="panel">

                <div class="panel-header">
                    <div>
                        <h2>Estado de la solicitud</h2>
                        <p>Consulta el estado actual de tu ticket.</p>
                    </div>
                </div>

                <div class="panel-body">

                    <c:choose>

                        <c:when test="${ticket.estado == 'NUEVO'}">
                            <div class="estado-box">
                                Tu solicitud fue registrada y está pendiente de atención.
                            </div>
                        </c:when>

                        <c:when test="${ticket.estado == 'ASIGNADO'}">
                            <div class="estado-box">
                                Tu solicitud fue asignada a un agente de soporte.
                            </div>
                        </c:when>

                        <c:when test="${ticket.estado == 'EN_PROCESO'}">
                            <div class="estado-box">
                                Un agente está trabajando actualmente en tu solicitud.
                            </div>
                        </c:when>

                        <c:when test="${ticket.estado == 'RESUELTO'}">
                            <div class="estado-box resuelto">
                                Tu solicitud ha sido marcada como resuelta.
                            </div>
                        </c:when>

                        <c:when test="${ticket.estado == 'CANCELADO'}">
                            <div class="estado-box cancelado">
                                Esta solicitud fue cancelada.
                            </div>
                        </c:when>

                        <c:otherwise>
                            <div class="estado-box">
                                Estado actual: <strong>${ticket.estado}</strong>
                            </div>
                        </c:otherwise>

                    </c:choose>

                </div>

            </section>

            <!-- COMENTARIOS -->
            <section class="panel">

                <div class="panel-header">
                    <div>
                        <h2>Comentarios</h2>
                        <p>Comunicación con el equipo de soporte.</p>
                    </div>
                </div>

                <div class="panel-body">

                    <c:if test="${empty comentarios}">
                        <div style="padding:20px; text-align:center; color:#718096;">
                            Todavía no hay comentarios en este ticket.
                        </div>
                    </c:if>

                    <c:forEach var="comentario" items="${comentarios}">

                        <div class="comentario-card">

                            <div class="comentario-header">
                                <div style="display:flex; align-items:center; gap:8px;">

                                    <strong>${comentario.nombreUsuario}</strong>

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

                                </div>

                                <small>${comentario.fecha}</small>

                            </div>

                            <p class="comentario-texto">
                                ${comentario.texto}
                            </p>

                        </div>

                    </c:forEach>

                    <div style="margin-top:25px;">

                        <h3>Agregar comentario</h3>

                        <form method="post" action="${pageContext.request.contextPath}/detalleTicket">
                            <input type="hidden" name="id" value="${ticket.idTicket}">
                            <input type="hidden" name="accion" value="comentar">

                            <textarea
                                name="texto"
                                rows="4"
                                required
                                placeholder="Escribe un comentario para el equipo de soporte..."
                                class="textarea-comentario"></textarea>

                            <button type="submit" class="btn-principal" style="border:none;cursor:pointer;">
                                Agregar comentario
                            </button>

                        </form>

                    </div>

                </div>

            </section>

        </main>

    </body>
</html>