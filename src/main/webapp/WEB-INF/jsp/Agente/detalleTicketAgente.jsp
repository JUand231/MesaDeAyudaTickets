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

        <aside class="sidebar">

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

                <a href="${pageContext.request.contextPath}/dashboardAgente"
                   class="menu-item">

                    <span>⌂</span>

                    <label>Dashboard</label>

                </a>


                <a href="${pageContext.request.contextPath}/tickets"
                   class="menu-item activo">

                    <span>▣</span>

                    <label>Mis tickets</label>

                </a>

            </nav>


            <div class="menu-titulo">
                GESTIÓN
            </div>


            <nav class="menu">

                <a href="${pageContext.request.contextPath}/tickets?estado=NUEVO"
                   class="menu-item">

                    <span>●</span>

                    <label>Pendientes</label>

                </a>


                <a href="${pageContext.request.contextPath}/tickets?estado=EN_PROCESO"
                   class="menu-item">

                    <span>◷</span>

                    <label>En proceso</label>

                </a>


                <a href="${pageContext.request.contextPath}/tickets?estado=RESUELTO"
                   class="menu-item">

                    <span>✓</span>

                    <label>Resueltos</label>

                </a>

            </nav>


            <div class="menu-titulo">
                SISTEMA
            </div>


            <nav class="menu">

                <a href="${pageContext.request.contextPath}/perfil"
                   class="menu-item">

                    <span>⚙</span>

                    <label>Mi Perfil</label>

                </a>


                <a href="${pageContext.request.contextPath}/CerrarSesion"
                   class="menu-item">

                    <span>↪</span>

                    <label>Cerrar sesión</label>

                </a>

            </nav>


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
                        Consulta la información y gestiona la solicitud.
                    </p>

                </div>


                <div class="barra-acciones">

                    <a href="${pageContext.request.contextPath}/tickets"
                       class="btn-principal">

                        ← Volver a mis tickets

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

                    </div>

                </div>


                <!-- ==================================================
                     DATOS
                     ================================================== -->

                <div style="
                     padding: 25px;
                     ">


                    <div style="
                         display:grid;
                         grid-template-columns: repeat(2, 1fr);
                         gap:20px;
                         margin-bottom:25px;
                         ">


                        <div>

                            <strong>
                                Categoría
                            </strong>

                            <p>
                                ${ticket.nombreCategoria}
                            </p>

                        </div>


                        <div>

                            <strong>
                                Prioridad
                            </strong>

                            <p>
                                ${ticket.nombrePrioridad}
                            </p>

                        </div>


                        <div>

                            <strong>
                                Solicitante
                            </strong>

                            <p>
                                ${ticket.nombreSolicitante}
                            </p>

                        </div>


                        <div>

                            <strong>
                                Agente asignado
                            </strong>

                            <p>
                                ${ticket.nombreAgente}
                            </p>

                        </div>


                        <div>

                            <strong>
                                Fecha de creación
                            </strong>

                            <p>
                                ${ticket.fechaCreacion}
                            </p>

                        </div>
                        <div>

                            <strong>
                                Tiempo máximo de atención
                            </strong>

                            <p>
                                ${horasSLA} horas
                            </p>

                        </div>


                        <div>

                            <strong>
                                Fecha límite
                            </strong>

                            <p>
                                ${fechaLimiteSLA}
                            </p>

                        </div>


                        <div>

                            <strong>
                                Estado
                            </strong>

                            <p>

                                <c:choose>

                                    <c:when test="${slaVencido}">
                                        VENCIDO
                                    </c:when>

                                    <c:otherwise>
                                        DENTRO DEL TIEMPO MAXIMO
                                    </c:otherwise>

                                </c:choose>

                            </p>

                        </div>

                    </div>


                    <!-- DESCRIPCIÓN -->

                    <div>

                        <strong>
                            Descripción
                        </strong>

                        <div style="
                             margin-top:10px;
                             padding:15px;
                             background:#f8fafc;
                             border-radius:10px;
                             border:1px solid #e2e8f0;
                             ">

                            ${ticket.descripcion}

                        </div>

                    </div>

                </div>

            </section>


            <!-- ==================================================
                 ACCIONES DEL AGENTE
                 ================================================== -->

            <section class="panel">


                <div class="panel-header">

                    <div>

                        <h2>
                            Gestión del ticket
                        </h2>

                        <p>
                            Acciones disponibles para el agente.
                        </p>

                    </div>

                </div>


                <div style="
                     padding:25px;
                     display:flex;
                     gap:12px;
                     flex-wrap:wrap;
                     ">


                    <!-- ==========================================
                         INICIAR ATENCIÓN
                         ASIGNADO -> EN_PROCESO
                         ========================================== -->

                    <c:if test="${ticket.estado == 'NUEVO' || ticket.estado == 'ASIGNADO'}">

                        <form method="post"
                              action="${pageContext.request.contextPath}/detalleTicket">

                            <input type="hidden"
                                   name="id"
                                   value="${ticket.idTicket}">

                            <input type="hidden"
                                   name="accion"
                                   value="iniciar">

                            <button type="submit"
                                    class="btn-principal"
                                    style="border:none;cursor:pointer;">

                                Iniciar atención

                            </button>

                        </form>

                    </c:if>


                    <!-- ==========================================
                         RESOLVER
                         EN_PROCESO -> RESUELTO
                         ========================================== -->

                    <c:if test="${ticket.estado == 'EN_PROCESO'}">

                        <form method="post"
                              action="${pageContext.request.contextPath}/detalleTicket">

                            <input type="hidden"
                                   name="id"
                                   value="${ticket.idTicket}">

                            <input type="hidden"
                                   name="accion"
                                   value="resolver">

                            <button type="submit"
                                    class="btn-principal"
                                    style="border:none;cursor:pointer;">

                                ✓ Resolver ticket

                            </button>

                        </form>

                    </c:if>


                    <!-- ==========================================
                         INFORMACIÓN CUANDO YA ESTÁ RESUELTO
                         ========================================== -->

                    <c:if test="${ticket.estado == 'RESUELTO'}">

                        <div style="
                             padding:12px 16px;
                             background:#f0fff4;
                             border:1px solid #c6f6d5;
                             border-radius:8px;
                             ">

                            El ticket fue resuelto.
                            El solicitante puede confirmarlo
                            o reabrirlo.

                        </div>

                    </c:if>


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
                            Comunicación entre el agente y el solicitante.
                        </p>

                    </div>

                </div>


                <div style="padding:25px;">


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

                        <div style="
                             padding:16px;
                             margin-bottom:15px;
                             border:1px solid #e2e8f0;
                             border-radius:10px;
                             background:#ffffff;
                             ">

                            <div style="
                                 display:flex;
                                 justify-content:space-between;
                                 align-items:center;
                                 margin-bottom:8px;
                                 ">

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


                                <small style="color:#718096;">

                                    ${comentario.fecha}

                                </small>

                            </div>


                            <p style="
                               margin:0;
                               color:#334155;
                               ">

                                ${comentario.texto}

                            </p>

                        </div>

                    </c:forEach>


                    <!-- ==========================================
                         NUEVO COMENTARIO
                         ========================================== -->

                    <div style="
                         margin-top:25px;
                         ">

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
                                placeholder="Escribe un comentario para el solicitante..."
                                style="
                                width:100%;
                                box-sizing:border-box;
                                padding:12px;
                                border:1px solid #d9e0e8;
                                border-radius:8px;
                                resize:vertical;
                                font-family:inherit;
                                margin-bottom:10px;
                                "></textarea>


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