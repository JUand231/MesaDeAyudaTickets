<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>

<html lang="es">

    <head>

        <meta charset="UTF-8">

        <meta name="viewport"
              content="width=device-width, initial-scale=1.0">

        <title>
            Tickets | Mesa de Ayuda
        </title>

        <link rel="stylesheet"
              href="${pageContext.request.contextPath}/css/estilo.css">


        <!-- ==========================================================
             CSS ESPECÍFICO DE ESTA PÁGINA
             ========================================================== -->

        <style>

            /* ==========================================================
               ACCIONES DEL TICKET
               ========================================================== */

            .acciones-ticket {
                position: relative;
                display: flex;
                justify-content: center;
                align-items: center;
            }


            /* ==========================================================
               BOTÓN +
               ========================================================== */

            .btn-accion-ticket,
            .acciones-ticket .accion.ver {
                width: 34px;
                height: 34px;

                display: flex;
                align-items: center;
                justify-content: center;

                border: 1px solid #cfd6df;
                border-radius: 7px;

                background: #f8fafc;
                color: #2563eb;

                font-size: 20px;
                line-height: 1;

                cursor: pointer;

                transition: all 0.2s ease;
            }


            .btn-accion-ticket:hover,
            .acciones-ticket .accion.ver:hover {
                background: #2563eb;
                color: white;
                border-color: #2563eb;
            }


            /* ==========================================================
               MENÚ DE ASIGNACIÓN
               
               IMPORTANTE:
               Se utiliza position: fixed para evitar que la tabla
               lo recorte.
               ========================================================== */

            .menu-asignar {

                display: none;

                position: fixed;

                width: 240px;
                box-sizing: border-box;

                padding: 16px;

                background: #ffffff;

                border: 1px solid #d9e0e8;
                border-radius: 12px;

                box-shadow:
                    0 10px 30px rgba(15, 23, 42, 0.15);

                z-index: 999999;
            }


            /* ==========================================================
               MENÚ VISIBLE
               ========================================================== */

            .menu-asignar.mostrar {
                display: block;
            }


            /* ==========================================================
               TÍTULO DEL MENÚ
               ========================================================== */

            .menu-asignar h4 {

                margin: 0 0 12px 0;

                font-size: 15px;
                font-weight: 600;

                color: #475569;
            }


            /* ==========================================================
               FORMULARIO
               ========================================================== */

            .menu-asignar form {

                display: flex !important;

                flex-direction: column !important;

                align-items: stretch !important;

                gap: 10px !important;

                width: 100% !important;

                margin: 0 !important;
                padding: 0 !important;
            }


            /* ==========================================================
               SELECT
               ========================================================== */

            .menu-asignar select {

                display: block;

                width: 100% !important;

                height: 40px;

                box-sizing: border-box;

                padding: 8px 10px;

                border: 1px solid #cfd6df;

                border-radius: 7px;

                background: #ffffff;

                color: #334155;

                font-size: 13px;

                outline: none;

                cursor: pointer;
            }


            .menu-asignar select:focus {

                border-color: #2563eb;

                box-shadow:
                    0 0 0 2px rgba(37, 99, 235, 0.10);
            }


            /* ==========================================================
               BOTÓN ASIGNAR
               ========================================================== */

            .menu-asignar .btn-asignar {

                display: flex !important;

                align-items: center;
                justify-content: center;

                width: 100% !important;

                min-width: 100% !important;

                height: 38px;

                box-sizing: border-box;

                padding: 8px 10px;

                margin: 0 !important;

                border: none !important;

                border-radius: 7px;

                background: #2563eb;

                color: #ffffff;

                font-size: 13px;

                font-weight: 600;

                cursor: pointer;

                transition: background 0.2s ease;
            }


            .menu-asignar .btn-asignar:hover {

                background: #1d4ed8;
            }


            /* ==========================================================
               TEXTO CUANDO NO HAY AGENTE
               ========================================================== */

            .sin-agente {

                color: #94a3b8;
            }

        </style>

    </head>


    <body>


        <!-- ==========================================================
             SIDEBAR
             ========================================================== -->

        <aside class="sidebar" id="sidebar">


            <div class="marca">

                <div class="marca-icono">
                    <span>🌐</span>
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

                <a href="${pageContext.request.contextPath}/dashboardAdmin"
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

            </nav>


            <div class="menu-titulo">
                ADMINISTRACIÓN
            </div>


            <nav class="menu">

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


                <a href="${pageContext.request.contextPath}/usuarios"
                   class="menu-item">

                    <span>●</span>

                    <label>
                        Comentarios
                    </label>

                </a>

            </nav>


            <div class="menu-titulo">
                SISTEMA
            </div>


            <nav class="menu">

                <a href="${pageContext.request.contextPath}/perfil"
                   class="menu-item">

                    <span>⚙</span>

                    <label>
                        Mi Perfil
                    </label>

                </a>


                <a href="${pageContext.request.contextPath}/CerrarSesion"
                   class="menu-item">

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



        <!-- ==========================================================
             CONTENIDO PRINCIPAL
             ========================================================== -->

        <main class="contenido">


            <!-- ======================================================
                 ENCABEZADO
                 ====================================================== -->

            <header class="barra-superior">

                <div>

                    <h1>
                        Tickets
                    </h1>

                    <p>
                        Gestiona y consulta todas las solicitudes de soporte.
                    </p>

                </div>

            </header>



            <!-- ======================================================
                 FILTROS
                 ====================================================== -->

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


                    <!-- BUSCAR -->

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
                                   value="${filtroBuscar}"
                                   placeholder="Título, descripción o ID...">

                        </div>

                    </div>



                    <!-- ESTADO -->

                    <div class="campo-filtro">

                        <label>
                            Estado
                        </label>

                        <select name="estado">

                            <option value="">
                                Todos los estados
                            </option>

                            <option value="Nuevo"
                                    ${filtroEstado == 'Nuevo' ? 'selected' : ''}>
                                Nuevo
                            </option>

                            <option value="Asignado"
                                    ${filtroEstado == 'Asignado' ? 'selected' : ''}>
                                Asignado
                            </option>

                            <option value="En proceso"
                                    ${filtroEstado == 'En proceso' ? 'selected' : ''}>
                                En proceso
                            </option>

                            <option value="Resuelto"
                                    ${filtroEstado == 'Resuelto' ? 'selected' : ''}>
                                Resuelto
                            </option>

                            <option value="Cerrado"
                                    ${filtroEstado == 'Cerrado' ? 'selected' : ''}>
                                Cerrado
                            </option>

                        </select>

                    </div>



                    <!-- PRIORIDAD -->

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



                    <!-- CATEGORÍA -->

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



            <!-- ======================================================
                 TABLA DE TICKETS
                 ====================================================== -->

            <section class="panel tickets-panel">


                <div class="panel-header">

                    <div>

                        <h2>
                            Todos los tickets
                        </h2>

                        <p>
                            ${totalTickets} solicitudes encontradas
                        </p>

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
                                    ESTADO
                                </th>

                                <th>
                                    FECHA
                                </th>

                                <th>
                                    SOLICITANTE
                                </th>

                                <th>
                                    AGENTE
                                </th>

                                <th>
                                    ACCIONES
                                </th>

                            </tr>

                        </thead>



                        <tbody>


                            <!-- ==================================================
                                 SIN TICKETS
                                 ================================================== -->

                            <c:if test="${empty tickets}">

                                <tr>

                                    <td colspan="9"
                                        style="text-align:center; padding:24px;">

                                        No hay tickets para mostrar.

                                    </td>

                                </tr>

                            </c:if>



                            <!-- ==================================================
                                 TICKETS
                                 ================================================== -->

                            <c:forEach var="ticket"
                                       items="${tickets}">


                                <tr>


                                    <!-- ID -->

                                    <td>

                                        <strong class="ticket-id">

                                            #TK-${ticket.idTicket}

                                        </strong>

                                    </td>



                                    <!-- TÍTULO -->

                                    <td>

                                        <span class="ticket-titulo">

                                            ${ticket.titulo}

                                        </span>

                                        <small class="ticket-descripcion">

                                            ${ticket.descripcion}

                                        </small>

                                    </td>



                                    <!-- CATEGORÍA -->

                                    <td>

                                        <span class="categoria-texto">

                                            ${ticket.nombreCategoria}

                                        </span>

                                    </td>



                                    <!-- PRIORIDAD -->

                                    <td>


                                        <c:choose>


                                            <c:when test="${ticket.nombrePrioridad == 'CRITICA'}">

                                                <span class="badge prioridad-critica">

                                                    CRÍTICA

                                                </span>

                                            </c:when>


                                            <c:when test="${ticket.nombrePrioridad == 'ALTA'}">

                                                <span class="badge prioridad-alta">

                                                    ALTA

                                                </span>

                                            </c:when>


                                            <c:when test="${ticket.nombrePrioridad == 'MEDIA'}">

                                                <span class="badge prioridad-media">

                                                    MEDIA

                                                </span>

                                            </c:when>


                                            <c:otherwise>

                                                <span class="badge prioridad-baja">

                                                    BAJA

                                                </span>

                                            </c:otherwise>


                                        </c:choose>


                                    </td>



                                    <!-- ESTADO -->

                                    <td>


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


                                            <c:otherwise>

                                                <span class="badge">

                                                    ${ticket.estado}

                                                </span>

                                            </c:otherwise>


                                        </c:choose>


                                    </td>



                                    <!-- FECHA -->

                                    <td>

                                        ${ticket.fechaCreacion}

                                    </td>



                                    <!-- SOLICITANTE -->

                                    <td>

                                        <div class="persona">

                                            <span>

                                                ${ticket.nombreSolicitante}

                                            </span>

                                        </div>

                                    </td>



                                    <!-- AGENTE -->

                                    <td>

                                        <div class="persona">


                                            <c:choose>


                                                <c:when test="${not empty ticket.nombreAgente}">

                                                    <span>

                                                        ${ticket.nombreAgente}

                                                    </span>

                                                </c:when>


                                                <c:otherwise>

                                                    <span class="sin-agente">

                                                        Sin asignar

                                                    </span>

                                                </c:otherwise>


                                            </c:choose>


                                        </div>

                                    </td>



                                    <!-- ==================================================
                                         ACCIONES
                                         ================================================== -->

                                    <td>

                                        <div class="acciones-ticket">


                                            <!-- BOTÓN + -->

                                            <c:if test="${ticket.estado == 'NUEVO'}">

                                                <button type="button"
                                                        class="accion ver"
                                                        onclick="mostrarAsignacion(${ticket.idTicket}, this)"
                                                        title="Asignar agente">

                                                    +

                                                </button>


                                                <!-- ==================================================
                                                     MENÚ DE ASIGNACIÓN
                                                     ================================================== -->

                                                <div id="menu-${ticket.idTicket}"
                                                     class="menu-asignar">


                                                    <h4>
                                                        Asignar agente
                                                    </h4>


                                                    <form method="post"
                                                          action="${pageContext.request.contextPath}/tickets">


                                                        <!-- ID DEL TICKET -->

                                                        <input type="hidden"
                                                               name="idTicket"
                                                               value="${ticket.idTicket}">


                                                        <!-- SELECTOR DE AGENTE -->

                                                        <select name="idAgente"
                                                                required>

                                                            <option value="">
                                                                Seleccionar agente
                                                            </option>


                                                            <c:forEach var="agente"
                                                                       items="${agentes}">

                                                                <option value="${agente.idUsuario}">

                                                                    ${agente.nombre}

                                                                </option>

                                                            </c:forEach>

                                                        </select>


                                                        <!-- BOTÓN ASIGNAR -->

                                                        <button type="submit"
                                                                class="btn-asignar">

                                                            Asignar

                                                        </button>


                                                    </form>


                                                </div>

                                            </c:if>


                                            <!-- ==================================================
                                                 SI YA ESTÁ ASIGNADO
                                                 ================================================== -->

                                            <c:if test="${ticket.estado != 'NUEVO'}">

                                                <span
                                                    style="
                                                    color:#94a3b8;
                                                    font-size:14px;
                                                    ">

                                                    —

                                                </span>

                                            </c:if>


                                        </div>

                                    </td>


                                </tr>


                            </c:forEach>


                        </tbody>


                    </table>


                </div>


            </section>



            <!-- ======================================================
                 PAGINACIÓN
                 ====================================================== -->

            <div class="paginacion">


                <span class="paginacion-info">

                    Mostrando ${totalTickets} tickets

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



        <!-- ==========================================================
             JAVASCRIPT
             ========================================================== -->

        <script>

            /*
             * ==========================================================
             * MOSTRAR / OCULTAR MENÚ DE ASIGNACIÓN
             * ==========================================================
             */

            function mostrarAsignacion(idTicket, boton) {

                const menu = document.getElementById(
                        "menu-" + idTicket
                        );


                /*
                 * Si por alguna razón no existe el menú,
                 * no hacemos nada.
                 */

                if (!menu) {
                    return;
                }


                /*
                 * Cerrar todos los demás menús.
                 */

                document.querySelectorAll(".menu-asignar").forEach(
                        function (elemento) {

                            if (elemento !== menu) {

                                elemento.classList.remove("mostrar");

                            }

                        }
                );


                /*
                 * Si ya estaba abierto, simplemente lo cerramos.
                 */

                if (menu.classList.contains("mostrar")) {

                    menu.classList.remove("mostrar");

                    return;
                }


                /*
                 * ======================================================
                 * CALCULAR POSICIÓN
                 *
                 * Usamos position: fixed para que el menú no quede
                 * escondido por el contenedor de la tabla.
                 * ======================================================
                 */

                const rect = boton.getBoundingClientRect();


                const anchoMenu = 240;

                const altoMenu = 150;

                const margen = 8;


                /*
                 * Posición horizontal:
                 *
                 * Alineamos el lado derecho del menú con el botón.
                 */

                let izquierda =
                        rect.right - anchoMenu;


                /*
                 * Evitar que se salga por la izquierda.
                 */

                if (izquierda < margen) {

                    izquierda = margen;

                }


                /*
                 * Evitar que se salga por la derecha.
                 */

                if (izquierda + anchoMenu
                        > window.innerWidth - margen) {

                    izquierda =
                            window.innerWidth
                            - anchoMenu
                            - margen;

                }


                /*
                 * Por defecto aparece debajo del botón.
                 */

                let arriba =
                        rect.bottom + margen;


                /*
                 * Si no hay suficiente espacio debajo,
                 * aparece encima del botón.
                 */

                if (arriba + altoMenu
                        > window.innerHeight - margen) {

                    arriba =
                            rect.top
                            - altoMenu
                            - margen;

                }


                /*
                 * Aplicar posición.
                 */

                menu.style.left =
                        izquierda + "px";

                menu.style.top =
                        arriba + "px";


                /*
                 * Mostrar menú.
                 */

                menu.classList.add("mostrar");

            }



            /*
             * ==========================================================
             * CERRAR AL HACER CLIC FUERA
             * ==========================================================
             */

            document.addEventListener(
                    "click",
                    function (event) {


                        /*
                         * Si el clic fue dentro de las acciones
                         * no cerramos inmediatamente.
                         */

                        if (event.target.closest(".acciones-ticket")) {

                            return;

                        }


                        /*
                         * Cerrar todos los menús.
                         */

                        document.querySelectorAll(
                                ".menu-asignar"
                                ).forEach(
                                function (menu) {

                                    menu.classList.remove(
                                            "mostrar"
                                            );

                                }
                        );

                    }
            );



            /*
             * ==========================================================
             * CERRAR AL HACER SCROLL
             * ==========================================================
             */

            window.addEventListener(
                    "scroll",
                    function () {

                        document.querySelectorAll(
                                ".menu-asignar"
                                ).forEach(
                                function (menu) {

                                    menu.classList.remove(
                                            "mostrar"
                                            );

                                }
                        );

                    },
                    true
                    );



            /*
             * ==========================================================
             * CERRAR AL CAMBIAR EL TAMAÑO DE LA VENTANA
             * ==========================================================
             */

            window.addEventListener(
                    "resize",
                    function () {

                        document.querySelectorAll(
                                ".menu-asignar"
                                ).forEach(
                                function (menu) {

                                    menu.classList.remove(
                                            "mostrar"
                                            );

                                }
                        );

                    }
            );

        </script>


    </body>

</html>

