<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <title>Agentes | Mesa de Ayuda</title>

        <link rel="stylesheet"
              href="${pageContext.request.contextPath}/css/estilo.css">
    </head>

    <body>

        <!-- ==================================================
             SIDEBAR
        ================================================== -->
        <aside class="sidebar" id="sidebar">

            <div class="marca">
                <div class="marca-icono">
                    <span>🌐</span>
                </div>

                <div class="marca-texto">
                    <strong>MESA DE AYUDA</strong>
                    <small>CIMM · SENA</small>
                </div>
            </div>

            <div class="menu-titulo">PRINCIPAL</div>

            <nav class="menu">

                <a href="${pageContext.request.contextPath}/dashboardAdmin"
                   class="menu-item">
                    <span>⌂</span>
                    <label>Dashboard</label>
                </a>

                <a href="${pageContext.request.contextPath}/tickets"
                   class="menu-item">
                    <span>🏷</span>
                    <label>Tickets</label>
                </a>

            </nav>


            <div class="menu-titulo">ADMINISTRACIÓN</div>

            <nav class="menu">

                <a href="${pageContext.request.contextPath}/admin/agentes"
                   class="menu-item activo">
                    <span>♟</span>
                    <label>Agentes</label>
                </a>

                <a href="${pageContext.request.contextPath}/categorias"
                   class="menu-item">
                    <span>◇</span>
                    <label>Categorías</label>
                </a>

                <a href="${pageContext.request.contextPath}/reportes"
                   class="menu-item">
                    <span>▥</span>
                    <label>Reportes</label>
                </a>

                <a href="${pageContext.request.contextPath}/usuarios"
                   class="menu-item">
                    <span>🖂️</span>
                    <label>Comentarios</label>
                </a>

            </nav>


            <div class="menu-titulo">SISTEMA</div>

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

                    <h1>Agentes</h1>

                    <p>
                        Gestiona y consulta los agentes de la mesa de ayuda.
                    </p>

                </div>

                <div class="barra-acciones">

                    <button class="btn-notificacion">
                        ♧
                        <span></span>
                    </button>

                </div>

            </header>


            <!-- ==================================================
                 PANEL DE BÚSQUEDA
            ================================================== -->

            <section class="panel filtros-panel">

                <div class="panel-header">

                    <div>

                        <h2>Buscar agentes</h2>

                        <p>
                            Busca un agente por nombre, correo o ID.
                        </p>

                    </div>

                </div>


                <form class="filtros-form"
                      method="get"
                      action="${pageContext.request.contextPath}/admin/agentes">

                    <div class="campo-filtro">

                        <label>Buscar</label>

                        <div class="input-busqueda">

                            <span>⌕</span>

                            <input type="text"
                                   name="buscar"
                                   placeholder="Nombre, correo o ID...">

                        </div>

                    </div>


                    <button type="submit"
                            class="btn-filtrar">

                        Buscar

                    </button>

                </form>

            </section>


            <!-- ==================================================
                 TABLA DE AGENTES
            ================================================== -->

            <section class="panel tickets-panel">

                <div class="panel-header">

                    <div>

                        <h2>Agentes registrados</h2>

                        <p>
                            ${totalAgentes} agentes encontrados
                        </p>

                    </div>
                </div>


                <div class="tabla-contenedor">

                    <table class="tabla tabla-tickets">

                        <thead>

                            <tr>

                                <th>ID</th>

                                <th>AGENTE</th>

                                <th>CORREO</th>

                                <th>ROL</th>

                                <th>ACCIONES</th>

                            </tr>

                        </thead>


                        <tbody>

                            <c:if test="${empty agentes}">

                                <tr>

                                    <td colspan="5"
                                        style="text-align:center; padding:24px;">

                                        No hay agentes registrados.

                                    </td>

                                </tr>

                            </c:if>


                            <c:forEach var="agente"
                                       items="${agentes}">

                                <tr>

                                    <!-- ID -->

                                    <td>

                                        <strong class="ticket-id">
                                            #AG-${agente.idUsuario}
                                        </strong>

                                    </td>


                                    <!-- NOMBRE -->

                                    <td>

                                        <div class="persona">

                                            <span>
                                                ${agente.nombre}
                                            </span>

                                        </div>

                                    </td>


                                    <!-- CORREO -->

                                    <td>

                                        ${agente.correo}

                                    </td>


                                    <!-- ROL -->

                                    <td>

                                        <span class="badge estado-proceso">
                                            Agente
                                        </span>

                                    </td>


                                    <!-- ACCIONES -->

                                    <td>

                                        <div class="acciones-ticket">

                                            <a href="${pageContext.request.contextPath}/admin/agentes/ver?id=${agente.idUsuario}"
                                               class="accion ver"
                                               title="Ver agente">

                                                +

                                            </a>

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

                <span class="paginacion-info">

                    Mostrando ${totalAgentes} agentes

                </span>

                <div class="paginas">

                    <button class="pagina disabled">
                        ‹
                    </button>

                    <button class="pagina activa">
                        1
                    </button>

                    <button class="pagina disabled">
                        ›
                    </button>

                </div>

            </div>

        </main>

    </body>
</html>