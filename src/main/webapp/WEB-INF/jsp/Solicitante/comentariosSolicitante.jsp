<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="es">

    <head>

        <meta charset="UTF-8">

        <meta name="viewport"
              content="width=device-width, initial-scale=1.0">

        <title>Comentarios | Mesa de Ayuda</title>

        <link rel="stylesheet"
              href="${pageContext.request.contextPath}/css/estilo.css">

    </head>

    <body>

        <!-- SIDEBAR -->

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

                <!-- DASHBOARD -->

                <a href="${pageContext.request.contextPath}/dashboardSolicitante"
                   class="menu-item">

                    <span>⌂</span>

                    <label>Dashboard</label>

                </a>


                <!-- MIS TICKETS -->

                <a href="${pageContext.request.contextPath}/tickets"
                   class="menu-item">

                    <span>▣</span>

                    <label>Mis tickets</label>

                </a>


                <!-- COMENTARIOS -->

                <a href="${pageContext.request.contextPath}/comentarios"
                   class="menu-item activo">

                    <span>●</span>

                    <label>Comentarios</label>

                </a>


                <!-- ESTADO -->

                <a href="${pageContext.request.contextPath}/tickets?estado=En%20Proceso"
                   class="menu-item">

                    <span>◷</span>

                    <label>Estado</label>

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
                    S
                </div>

                <div>

                    <strong>Solicitante</strong>

                    <small>Usuario solicitante</small>

                </div>

            </div>

        </aside>


        <!-- CONTENIDO -->

        <main class="contenido">


            <!-- BARRA SUPERIOR -->

            <header class="barra-superior">

                <div>

                    <h1>Comentarios</h1>

                    <p>
                        Consulta y agrega comentarios a tus tickets.
                    </p>

                </div>


                <div class="barra-acciones">

                    <a href="${pageContext.request.contextPath}/ticket/nuevo"
                       class="btn-principal">

                        ＋ Nuevo ticket

                    </a>

                </div>

            </header>


            <!-- PANEL -->

            <section class="panel">


                <div class="panel-header">

                    <div>

                        <h2>Mis comentarios</h2>

                        <p>
                            Comentarios relacionados con tus solicitudes.
                        </p>

                    </div>

                </div>


                <!-- LISTADO -->

                <div style="padding: 20px;">

                    <c:if test="${empty comentarios}">

                        <div style="
                             padding: 30px;
                             text-align: center;
                             color: #718096;
                             ">

                            No tienes comentarios registrados.

                        </div>

                    </c:if>


                    <c:forEach
                        var="comentario"
                        items="${comentarios}">

                        <div style="
                             padding: 18px;
                             margin-bottom: 15px;
                             border: 1px solid #e3e8ef;
                             border-radius: 12px;
                             background: white;
                             ">

                            <!-- CABECERA -->

                            <div style="
                                 display: flex;
                                 justify-content: space-between;
                                 align-items: center;
                                 margin-bottom: 10px;
                                 ">

                                <strong>

                                    #TK-${comentario.idTicket}

                                </strong>


                                <small style="color:#718096;">

                                    ${comentario.fecha}

                                </small>

                            </div>


                            <!-- TEXTO -->

                            <p style="
                               margin: 0 0 15px 0;
                               color: #334155;
                               ">

                                ${comentario.texto}

                            </p>


                            <!-- AGREGAR OTRO -->

                            <form
                                method="post"
                                action="${pageContext.request.contextPath}/comentarios">

                                <input
                                    type="hidden"
                                    name="idTicket"
                                    value="${comentario.idTicket}">


                                <textarea
                                    name="texto"
                                    rows="2"
                                    placeholder="Escribe un comentario..."
                                    required
                                    style="
                                    width: 100%;
                                    box-sizing: border-box;
                                    padding: 10px;
                                    border: 1px solid #d9e0e8;
                                    border-radius: 8px;
                                    resize: vertical;
                                    font-family: inherit;
                                    margin-bottom: 8px;
                                    "></textarea>


                                <button
                                    type="submit"
                                    class="btn-principal"
                                    style="border: none; cursor: pointer;">

                                    Agregar comentario

                                </button>

                            </form>

                        </div>

                    </c:forEach>

                </div>

            </section>

        </main>

    </body>

</html>