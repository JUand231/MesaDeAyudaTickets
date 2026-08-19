<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>

<html lang="es">

    <head>

        <meta charset="UTF-8">

        <meta name="viewport"
              content="width=device-width, initial-scale=1.0">

        <title>
            Detalle Ticket | Mesa de Ayuda
        </title>

        <link rel="stylesheet"
              href="${pageContext.request.contextPath}/css/estilo.css">

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


        <!-- ==================================================
             CONTENIDO
             ================================================== -->

        <main class="contenido">


            <!-- CABECERA -->

            <header class="barra-superior">

                <div>

                    <h1>
                        Detalle del ticket
                    </h1>

                    <p>
                        Consulta la información y la conversación del ticket.
                    </p>

                </div>


                <div class="barra-acciones">

                    <a href="${pageContext.request.contextPath}/comentariosAdmin"
                       class="btn-principal">

                        ← Volver a comentarios

                    </a>

                </div>

            </header>


            <!-- ==================================================
                 INFORMACIÓN DEL TICKET
                 ================================================== -->

            <section class="panel">


                <div class="panel-header">

                    <div>

                        <h2>
                            #TK-${ticket.idTicket}
                        </h2>

                        <p>
                            ${ticket.titulo}
                        </p>

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


                <!-- ==================================================
                     DATOS
                     ================================================== -->

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


                    <!-- DESCRIPCIÓN -->

                    <div>

                        <strong>Descripción</strong>

                        <div class="descripcion-box">
                            ${ticket.descripcion}
                        </div>

                    </div>

                </div>

            </section>


            <!-- ==================================================
                 COMENTARIOS
                 ================================================== -->

            <section class="panel">


                <div class="panel-header">

                    <div>

                        <h2>
                            Comentarios
                        </h2>

                        <p>
                            Conversación completa entre solicitante y agente.
                        </p>

                    </div>

                </div>


                <div class="panel-body">

                    <!-- ==========================================
                         COMENTARIOS EXISTENTES
                         ========================================== -->

                    <c:if test="${empty comentarios}">

                        <div style="
                             padding:20px;
                             text-align:center;
                             color:#718096;
                             ">

                            Todavía no hay comentarios en este ticket.

                        </div>

                    </c:if>


                    <c:forEach var="comentario"
                               items="${comentarios}">

                        <div class="comentario-card">

                            <div class="comentario-header">
                                <div style="display:flex; align-items:center; gap:8px;">

                                    <strong>
                                        ${comentario.nombreUsuario}
                                    </strong>

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


                                <small>
                                    ${comentario.fecha}
                                </small>

                            </div>


                            <p class="comentario-texto">
                                ${comentario.texto}
                            </p>

                        </div>

                    </c:forEach>


                    <!-- ==========================================
                         NUEVO COMENTARIO
                         ========================================== -->

                    <div style="margin-top:25px;">

                        <h3>
                            Agregar comentario
                        </h3>


                        <form method="post"
                              action="${pageContext.request.contextPath}/detalleTicket">

                            <input type="hidden"
                                   name="id"
                                   value="${ticket.idTicket}">

                            <input type="hidden"
                                   name="accion"
                                   value="comentar">


                            <textarea
                                name="texto"
                                rows="4"
                                required
                                placeholder="Escribe un comentario como administrador..."
                                class="textarea-comentario"></textarea>


                            <button type="submit"
                                    class="btn-principal"
                                    style="border:none;cursor:pointer;">

                                Agregar comentario

                            </button>

                        </form>

                    </div>

                </div>

            </section>


        </main>

    </body>

</html>
