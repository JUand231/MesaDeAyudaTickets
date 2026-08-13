<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>

<html lang="es">

    <head>

        <meta charset="UTF-8">

        <meta name="viewport"
              content="width=device-width, initial-scale=1.0">

        <title>Tickets | Mesa de Ayuda</title>

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

                    <strong>
                        MESA DE AYUDA
                    </strong>

                    <small>
                        CIMM · SENA
                    </small>

                </div>

            </div>


            <div class="menu-titulo">
                PRINCIPAL
            </div>


            <nav class="menu">

                <a href="${pageContext.request.contextPath}/dashboard"
                   class="menu-item">

                    <span>⌂</span>

                    <label>
                        Dashboard
                    </label>

                </a>


                <a href="${pageContext.request.contextPath}/tickets"
                   class="menu-item activo">

                    <span>▣</span>

                    <label>
                        Tickets
                    </label>

                </a>


                <a href="${pageContext.request.contextPath}/ticket/nuevo"
                   class="menu-item">

                    <span>＋</span>

                    <label>
                        Nuevo ticket
                    </label>

                </a>

            </nav>


            <div class="menu-titulo">
                ADMINISTRACIÓN
            </div>


            <nav class="menu">

                <a href="${pageContext.request.contextPath}/usuarios"
                   class="menu-item">

                    <span>♙</span>

                    <label>
                        Usuarios
                    </label>

                </a>


                <a href="${pageContext.request.contextPath}/agentes"
                   class="menu-item">

                    <span>♟</span>

                    <label>
                        Agentes
                    </label>

                </a>


                <a href="${pageContext.request.contextPath}/categorias"
                   class="menu-item">

                    <span>◇</span>

                    <label>
                        Categorías
                    </label>

                </a>


                <a href="${pageContext.request.contextPath}/reportes"
                   class="menu-item">

                    <span>▥</span>

                    <label>
                        Reportes
                    </label>

                </a>

            </nav>


            <div class="menu-titulo">
                SISTEMA
            </div>


            <nav class="menu">

                <a href="#" class="menu-item">

                    <span>⚙</span>

                    <label>
                        Configuración
                    </label>

                </a>


                <a href="#" class="menu-item">

                    <span>↪</span>

                    <label>
                        Cerrar sesión
                    </label>

                </a>

            </nav>


            <div class="usuario-sidebar">

                <div class="usuario-avatar">
                    A
                </div>

                <div>

                    <strong>
                        Administrador
                    </strong>

                    <small>
                        Administrador
                    </small>

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

                    <h1>
                        Tickets
                    </h1>

                    <p>
                        Gestiona y consulta todas las solicitudes de soporte.
                    </p>

                </div>


                <div class="barra-acciones">

                    <button class="btn-notificacion">

                        ♧

                        <span></span>

                    </button>


                    <a href="${pageContext.request.contextPath}/ticket/nuevo"
                       class="btn-principal">

                        ＋ Nuevo ticket

                    </a>

                </div>

            </header>



            <!-- ==================================================
                 PANEL DE FILTROS
            ================================================== -->

            <section class="panel filtros-panel">


                <div class="panel-header">

                    <div>

                        <h2>
                            Buscar tickets
                        </h2>

                        <p>
                            Utiliza los filtros para encontrar una solicitud.
                        </p>

                    </div>

                </div>


                <form class="filtros-form"
                      method="get"
                      action="${pageContext.request.contextPath}/tickets">


                    <div class="campo-filtro">

                        <label>
                            Buscar
                        </label>

                        <div class="input-busqueda">

                            <span>
                                ⌕
                            </span>

                            <input type="text"
                                   name="buscar"
                                   placeholder="Título, descripción o ID...">

                        </div>

                    </div>


                    <div class="campo-filtro">

                        <label>
                            Estado
                        </label>

                        <select name="estado">

                            <option value="">
                                Todos los estados
                            </option>

                            <option value="Nuevo">
                                Nuevo
                            </option>

                            <option value="En proceso">
                                En proceso
                            </option>

                            <option value="Resuelto">
                                Resuelto
                            </option>

                            <option value="Cerrado">
                                Cerrado
                            </option>

                        </select>

                    </div>


                    <div class="campo-filtro">

                        <label>
                            Prioridad
                        </label>

                        <select name="prioridad">

                            <option value="">
                                Todas
                            </option>

                            <option value="Crítica">
                                Crítica
                            </option>

                            <option value="Alta">
                                Alta
                            </option>

                            <option value="Media">
                                Media
                            </option>

                            <option value="Baja">
                                Baja
                            </option>

                        </select>

                    </div>


                    <div class="campo-filtro">

                        <label>
                            Categoría
                        </label>

                        <select name="categoria">

                            <option value="">
                                Todas
                            </option>

                            <option value="Hardware">
                                Hardware
                            </option>

                            <option value="Software">
                                Software
                            </option>

                            <option value="Red">
                                Red
                            </option>

                            <option value="Mantenimiento">
                                Mantenimiento
                            </option>

                        </select>

                    </div>


                    <button type="submit"
                            class="btn-filtrar">

                        Filtrar

                    </button>


                </form>

            </section>



            <!-- ==================================================
                 TABLA DE TICKETS
            ================================================== -->

            <section class="panel tickets-panel">


                <div class="panel-header">

                    <div>

                        <h2>
                            Todos los tickets
                        </h2>

                        <p>
                            248 solicitudes registradas
                        </p>

                    </div>


                    <div class="tabla-controles">

                        <button class="btn-exportar">
                            ↓ Exportar
                        </button>

                    </div>

                </div>


                <div class="tabla-contenedor">


                    <table class="tabla tabla-tickets">


                        <thead>

                            <tr>

                                <th>
                                    TICKET
                                </th>

                                <th>
                                    TÍTULO
                                </th>

                                <th>
                                    CATEGORÍA
                                </th>

                                <th>
                                    PRIORIDAD
                                </th>

                                <th>
                                    SOLICITANTE
                                </th>

                                <th>
                                    AGENTE
                                </th>

                                <th>
                                    ESTADO
                                </th>

                                <th>
                                    FECHA
                                </th>

                                <th>
                                    ACCIONES
                                </th>

                            </tr>

                        </thead>


                        <tbody>


                            <!-- TICKET 1 -->

                            <tr>

                                <td>

                                    <strong class="ticket-id">
                                        #TK-0248
                                    </strong>

                                </td>


                                <td>

                                    <span class="ticket-titulo">
                                        Problema con conexión de red
                                    </span>

                                    <small class="ticket-descripcion">
                                        No hay acceso a Internet.
                                    </small>

                                </td>


                                <td>
                                    <span class="categoria-texto">
                                        Red
                                    </span>
                                </td>


                                <td>

                                    <span class="badge prioridad-critica">
                                        CRÍTICA
                                    </span>

                                </td>


                                <td>

                                    <div class="persona">

                                        <div class="persona-avatar">
                                            S
                                        </div>

                                        <span>
                                            Sofia Lara
                                        </span>

                                    </div>

                                </td>


                                <td>

                                    <div class="persona">

                                        <div class="persona-avatar agente">
                                            C
                                        </div>

                                        <span>
                                            Carlos M.
                                        </span>

                                    </div>

                                </td>


                                <td>

                                    <span class="badge estado-proceso">
                                        EN PROCESO
                                    </span>

                                </td>


                                <td>
                                    11/08/2026
                                </td>


                                <td>

                                    <div class="acciones-ticket">

                                        <a href="#"
                                           class="accion ver"
                                           title="Ver ticket">

                                            👁

                                        </a>


                                        <a href="#"
                                           class="accion editar"
                                           title="Editar ticket">

                                            ✎

                                        </a>

                                    </div>

                                </td>

                            </tr>



                            <!-- TICKET 2 -->

                            <tr>

                                <td>

                                    <strong class="ticket-id">
                                        #TK-0247
                                    </strong>

                                </td>


                                <td>

                                    <span class="ticket-titulo">
                                        Equipo no enciende
                                    </span>

                                    <small class="ticket-descripcion">
                                        Computador no responde.
                                    </small>

                                </td>


                                <td>
                                    <span class="categoria-texto">
                                        Hardware
                                    </span>
                                </td>


                                <td>

                                    <span class="badge prioridad-alta">
                                        ALTA
                                    </span>

                                </td>


                                <td>

                                    <div class="persona">

                                        <div class="persona-avatar">
                                            J
                                        </div>

                                        <span>
                                            Juan Pérez
                                        </span>

                                    </div>

                                </td>


                                <td>

                                    <div class="persona">

                                        <div class="persona-avatar agente">
                                            L
                                        </div>

                                        <span>
                                            Laura P.
                                        </span>

                                    </div>

                                </td>


                                <td>

                                    <span class="badge estado-nuevo">
                                        NUEVO
                                    </span>

                                </td>


                                <td>
                                    11/08/2026
                                </td>


                                <td>

                                    <div class="acciones-ticket">

                                        <a href="#"
                                           class="accion ver">
                                            👁
                                        </a>

                                        <a href="#"
                                           class="accion editar">
                                            ✎
                                        </a>

                                    </div>

                                </td>

                            </tr>



                            <!-- TICKET 3 -->

                            <tr>

                                <td>

                                    <strong class="ticket-id">
                                        #TK-0246
                                    </strong>

                                </td>


                                <td>

                                    <span class="ticket-titulo">
                                        Instalación de software
                                    </span>

                                    <small class="ticket-descripcion">
                                        Solicitud de instalación.
                                    </small>

                                </td>


                                <td>
                                    <span class="categoria-texto">
                                        Software
                                    </span>
                                </td>


                                <td>

                                    <span class="badge prioridad-media">
                                        MEDIA
                                    </span>

                                </td>


                                <td>

                                    <div class="persona">

                                        <div class="persona-avatar">
                                            M
                                        </div>

                                        <span>
                                            María Gómez
                                        </span>

                                    </div>

                                </td>


                                <td>

                                    <div class="persona">

                                        <div class="persona-avatar agente">
                                            A
                                        </div>

                                        <span>
                                            Andrés R.
                                        </span>

                                    </div>

                                </td>


                                <td>

                                    <span class="badge estado-resuelto">
                                        RESUELTO
                                    </span>

                                </td>


                                <td>
                                    10/08/2026
                                </td>


                                <td>

                                    <div class="acciones-ticket">

                                        <a href="#"
                                           class="accion ver">
                                            👁
                                        </a>

                                        <a href="#"
                                           class="accion editar">
                                            ✎
                                        </a>

                                    </div>

                                </td>

                            </tr>



                            <!-- TICKET 4 -->

                            <tr>

                                <td>

                                    <strong class="ticket-id">
                                        #TK-0245
                                    </strong>

                                </td>


                                <td>

                                    <span class="ticket-titulo">
                                        Mantenimiento de equipo
                                    </span>

                                    <small class="ticket-descripcion">
                                        Mantenimiento preventivo.
                                    </small>

                                </td>


                                <td>
                                    <span class="categoria-texto">
                                        Mantenimiento
                                    </span>
                                </td>


                                <td>

                                    <span class="badge prioridad-baja">
                                        BAJA
                                    </span>

                                </td>


                                <td>

                                    <div class="persona">

                                        <div class="persona-avatar">
                                            D
                                        </div>

                                        <span>
                                            David Ruiz
                                        </span>

                                    </div>

                                </td>


                                <td>

                                    <div class="persona">

                                        <div class="persona-avatar agente">
                                            C
                                        </div>

                                        <span>
                                            Carlos M.
                                        </span>

                                    </div>

                                </td>


                                <td>

                                    <span class="badge estado-resuelto">
                                        RESUELTO
                                    </span>

                                </td>


                                <td>
                                    10/08/2026
                                </td>


                                <td>

                                    <div class="acciones-ticket">

                                        <a href="#"
                                           class="accion ver">
                                            👁
                                        </a>

                                        <a href="#"
                                           class="accion editar">
                                            ✎
                                        </a>

                                    </div>

                                </td>

                            </tr>



                            <!-- TICKET 5 -->

                            <tr>

                                <td>

                                    <strong class="ticket-id">
                                        #TK-0244
                                    </strong>

                                </td>


                                <td>

                                    <span class="ticket-titulo">
                                        Error en sistema académico
                                    </span>

                                    <small class="ticket-descripcion">
                                        No permite iniciar sesión.
                                    </small>

                                </td>


                                <td>
                                    <span class="categoria-texto">
                                        Software
                                    </span>
                                </td>


                                <td>

                                    <span class="badge prioridad-alta">
                                        ALTA
                                    </span>

                                </td>


                                <td>

                                    <div class="persona">

                                        <div class="persona-avatar">
                                            L
                                        </div>

                                        <span>
                                            Laura Torres
                                        </span>

                                    </div>

                                </td>


                                <td>

                                    <div class="persona">

                                        <div class="persona-avatar agente">
                                            A
                                        </div>

                                        <span>
                                            Andrés R.
                                        </span>

                                    </div>

                                </td>


                                <td>

                                    <span class="badge estado-proceso">
                                        EN PROCESO
                                    </span>

                                </td>


                                <td>
                                    09/08/2026
                                </td>


                                <td>

                                    <div class="acciones-ticket">

                                        <a href="#"
                                           class="accion ver">
                                            👁
                                        </a>

                                        <a href="#"
                                           class="accion editar">
                                            ✎
                                        </a>

                                    </div>

                                </td>

                            </tr>


                        </tbody>

                    </table>

                </div>



                <!-- ==================================================
                     PAGINACIÓN
                ================================================== -->

                <div class="paginacion">


                    <span class="paginacion-info">
                        Mostrando 1–5 de 248 tickets
                    </span>


                    <div class="paginas">

                        <button class="pagina disabled">
                            ‹
                        </button>

                        <button class="pagina activa">
                            1
                        </button>

                        <button class="pagina">
                            2
                        </button>

                        <button class="pagina">
                            3
                        </button>

                        <span class="puntos">
                            ...
                        </span>

                        <button class="pagina">
                            50
                        </button>

                        <button class="pagina">
                            ›
                        </button>

                    </div>


                </div>


            </section>


        </main>


    </body>

</html>