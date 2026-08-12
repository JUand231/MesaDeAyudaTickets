<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>

<html lang="es">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Dashboard | Mesa de Ayuda</title>

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
           class="menu-item activo">

            <span>⌂</span>

            <label>
                Dashboard
            </label>

        </a>


        <a href="${pageContext.request.contextPath}/tickets"
           class="menu-item">

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
     CONTENIDO
================================================== -->

<main class="contenido">


    <header class="barra-superior">

        <div>

            <h1>
                Dashboard
            </h1>

            <p>
                Bienvenido al centro de gestión de soporte.
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



    <!-- ==============================================
         ESTADÍSTICAS
    =============================================== -->

    <section class="estadisticas">


        <div class="estadistica">

            <div class="estadistica-icono azul">
                ▣
            </div>

            <div>

                <span>
                    TOTAL DE TICKETS
                </span>

                <strong>
                    248
                </strong>

                <small class="positivo">
                    ↑ 12% este mes
                </small>

            </div>

        </div>



        <div class="estadistica">

            <div class="estadistica-icono naranja">
                ◷
            </div>

            <div>

                <span>
                    PENDIENTES
                </span>

                <strong>
                    36
                </strong>

                <small>
                    Requieren atención
                </small>

            </div>

        </div>



        <div class="estadistica">

            <div class="estadistica-icono verde">
                ✓
            </div>

            <div>

                <span>
                    RESUELTOS
                </span>

                <strong>
                    184
                </strong>

                <small class="positivo">
                    74% de resolución
                </small>

            </div>

        </div>



        <div class="estadistica">

            <div class="estadistica-icono rojo">
                !
            </div>

            <div>

                <span>
                    CRÍTICOS
                </span>

                <strong>
                    8
                </strong>

                <small class="negativo">
                    Atención inmediata
                </small>

            </div>

        </div>


    </section>



    <!-- ==============================================
         GRID PRINCIPAL
    =============================================== -->

    <section class="dashboard-grid">


        <!-- TICKETS -->

        <div class="panel">

            <div class="panel-header">

                <div>

                    <h2>
                        Tickets recientes
                    </h2>

                    <p>
                        Últimas solicitudes registradas
                    </p>

                </div>


                <a href="${pageContext.request.contextPath}/tickets">
                    Ver todos →
                </a>

            </div>


            <div class="tabla-contenedor">

                <table class="tabla">

                    <thead>

                        <tr>

                            <th>
                                TICKET
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
                                AGENTE
                            </th>

                        </tr>

                    </thead>


                    <tbody>


                        <tr>

                            <td>

                                <strong class="ticket-id">
                                    #TK-0248
                                </strong>

                                <span class="ticket-titulo">
                                    Problema con conexión de red
                                </span>

                            </td>

                            <td>
                                Red
                            </td>

                            <td>
                                <span class="badge prioridad-critica">
                                    CRÍTICA
                                </span>
                            </td>

                            <td>
                                <span class="badge estado-proceso">
                                    EN PROCESO
                                </span>
                            </td>

                            <td>
                                Carlos M.
                            </td>

                        </tr>



                        <tr>

                            <td>

                                <strong class="ticket-id">
                                    #TK-0247
                                </strong>

                                <span class="ticket-titulo">
                                    Equipo no enciende
                                </span>

                            </td>

                            <td>
                                Hardware
                            </td>

                            <td>
                                <span class="badge prioridad-alta">
                                    ALTA
                                </span>
                            </td>

                            <td>
                                <span class="badge estado-nuevo">
                                    NUEVO
                                </span>
                            </td>

                            <td>
                                Laura P.
                            </td>

                        </tr>



                        <tr>

                            <td>

                                <strong class="ticket-id">
                                    #TK-0246
                                </strong>

                                <span class="ticket-titulo">
                                    Instalación de software
                                </span>

                            </td>

                            <td>
                                Software
                            </td>

                            <td>
                                <span class="badge prioridad-media">
                                    MEDIA
                                </span>
                            </td>

                            <td>
                                <span class="badge estado-resuelto">
                                    RESUELTO
                                </span>
                            </td>

                            <td>
                                Andrés R.
                            </td>

                        </tr>



                        <tr>

                            <td>

                                <strong class="ticket-id">
                                    #TK-0245
                                </strong>

                                <span class="ticket-titulo">
                                    Mantenimiento de equipo
                                </span>

                            </td>

                            <td>
                                Mantenimiento
                            </td>

                            <td>
                                <span class="badge prioridad-baja">
                                    BAJA
                                </span>
                            </td>

                            <td>
                                <span class="badge estado-resuelto">
                                    RESUELTO
                                </span>
                            </td>

                            <td>
                                Carlos M.
                            </td>

                        </tr>


                    </tbody>

                </table>

            </div>

        </div>



        <!-- ACTIVIDAD -->

        <div class="panel">

            <div class="panel-header">

                <div>

                    <h2>
                        Actividad reciente
                    </h2>

                    <p>
                        Últimos movimientos
                    </p>

                </div>

            </div>


            <div class="actividad">


                <div class="actividad-item">

                    <div class="actividad-icon azul">
                        +
                    </div>

                    <div>

                        <strong>
                            Nuevo ticket
                        </strong>

                        <p>
                            Sofia creó el ticket #TK-0248
                        </p>

                        <small>
                            Hace 5 minutos
                        </small>

                    </div>

                </div>



                <div class="actividad-item">

                    <div class="actividad-icon verde">
                        ✓
                    </div>

                    <div>

                        <strong>
                            Ticket resuelto
                        </strong>

                        <p>
                            #TK-0246 fue resuelto
                        </p>

                        <small>
                            Hace 18 minutos
                        </small>

                    </div>

                </div>



                <div class="actividad-item">

                    <div class="actividad-icon naranja">
                        →
                    </div>

                    <div>

                        <strong>
                            Ticket asignado
                        </strong>

                        <p>
                            #TK-0248 → Carlos M.
                        </p>

                        <small>
                            Hace 32 minutos
                        </small>

                    </div>

                </div>



                <div class="actividad-item">

                    <div class="actividad-icon rojo">
                        !
                    </div>

                    <div>

                        <strong>
                            Atención requerida
                        </strong>

                        <p>
                            Ticket crítico pendiente
                        </p>

                        <small>
                            Hace 1 hora
                        </small>

                    </div>

                </div>


            </div>

        </div>


    </section>



    <!-- ==============================================
         ACCESOS
    =============================================== -->

    <section class="panel accesos">

        <div class="panel-header">

            <div>

                <h2>
                    Accesos rápidos
                </h2>

                <p>
                    Administración del sistema
                </p>

            </div>

        </div>


        <div class="accesos-grid">


            <a href="${pageContext.request.contextPath}/ticket/nuevo"
               class="acceso">

                <span>＋</span>

                <strong>
                    Crear ticket
                </strong>

                <small>
                    Registrar una solicitud
                </small>

            </a>


            <a href="${pageContext.request.contextPath}/usuarios"
               class="acceso">

                <span>♙</span>

                <strong>
                    Usuarios
                </strong>

                <small>
                    Gestionar usuarios
                </small>

            </a>


            <a href="${pageContext.request.contextPath}/categorias"
               class="acceso">

                <span>◇</span>

                <strong>
                    Categorías
                </strong>

                <small>
                    Tipos de soporte
                </small>

            </a>


            <a href="${pageContext.request.contextPath}/reportes"
               class="acceso">

                <span>▥</span>

                <strong>
                    Reportes
                </strong>

                <small>
                    Consultar estadísticas
                </small>

            </a>


        </div>

    </section>


</main>

</body>

</html>