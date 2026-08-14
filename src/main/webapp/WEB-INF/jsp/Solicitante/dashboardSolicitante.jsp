<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Dashboard | Solicitante - Mesa de Ayuda</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilo.css">
    </head>

    <body>

        <!-- SIDEBAR -->
        <aside class="sidebar" id="sidebar">

            <div class="marca">
                <div class="marca-icono"><span>⌁</span></div>
                <div class="marca-texto">
                    <strong>MESA DE AYUDA</strong>
                    <small>CIMM · SENA</small>
                </div>
            </div>

            <div class="menu-titulo">PRINCIPAL</div>

            <nav class="menu">

                <a href="${pageContext.request.contextPath}/dashboardSolicitante" class="menu-item activo">
                    <span>⌂</span><label>Dashboard</label>
                </a>

                <a href="${pageContext.request.contextPath}/tickets" class="menu-item">
                    <span>▣</span><label>Mis tickets</label>
                </a>

                <a href="${pageContext.request.contextPath}/ticket/nuevo" class="menu-item">
                    <span>＋</span><label>Nuevo ticket</label>
                </a>

            </nav>

            <div class="menu-titulo">MIS TICKETS</div>

            <nav class="menu">

                <a href="${pageContext.request.contextPath}/tickets?estado=Nuevo" class="menu-item">
                    <span>●</span><label>Nuevos</label>
                </a>

                <a href="${pageContext.request.contextPath}/tickets?estado=En%20Proceso" class="menu-item">
                    <span>◷</span><label>En proceso</label>
                </a>

                <a href="${pageContext.request.contextPath}/tickets?estado=Resuelto" class="menu-item">
                    <span>✓</span><label>Resueltos</label>
                </a>

            </nav>

            <div class="menu-titulo">SISTEMA</div>

            <nav class="menu">

                <a href="#" class="menu-item">
                    <span>⚙</span><label>Configuración</label>
                </a>

                <a href="${pageContext.request.contextPath}/logout" class="menu-item">
                    <span>↪</span><label>Cerrar sesión</label>
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
                    <h1>Dashboard</h1>
                    <p>Consulta y gestiona tus solicitudes de soporte.</p>
                </div>

                <div class="barra-acciones">

                    <button class="btn-notificacion">
                        ♧<span></span>
                    </button>

                    <a href="${pageContext.request.contextPath}/ticket/nuevo" class="btn-principal">
                        ＋ Nuevo ticket
                    </a>

                </div>

            </header>


            <!-- ESTADÍSTICAS -->
            <section class="estadisticas">

                <div class="estadistica">
                    <div class="estadistica-icono azul">▣</div>
                    <div>
                        <span>MIS TICKETS</span>
                        <strong>12</strong>
                        <small>Solicitudes realizadas</small>
                    </div>
                </div>

                <div class="estadistica">
                    <div class="estadistica-icono naranja">◷</div>
                    <div>
                        <span>EN PROCESO</span>
                        <strong>4</strong>
                        <small>Siendo atendidos</small>
                    </div>
                </div>

                <div class="estadistica">
                    <div class="estadistica-icono verde">✓</div>
                    <div>
                        <span>RESUELTOS</span>
                        <strong>7</strong>
                        <small>Solicitudes solucionadas</small>
                    </div>
                </div>

                <div class="estadistica">
                    <div class="estadistica-icono rojo">!</div>
                    <div>
                        <span>PENDIENTES</span>
                        <strong>1</strong>
                        <small>Esperando atención</small>
                    </div>
                </div>

            </section>


            <!-- GRID PRINCIPAL -->
            <section class="dashboard-grid">

                <!-- MIS TICKETS -->
                <div class="panel">

                    <div class="panel-header">

                        <div>
                            <h2>Mis tickets</h2>
                            <p>Estado de tus solicitudes de soporte</p>
                        </div>

                        <a href="${pageContext.request.contextPath}/tickets">
                            Ver todos →
                        </a>

                    </div>

                    <div class="tabla-contenedor">

                        <table class="tabla">

                            <thead>
                                <tr>
                                    <th>TICKET</th>
                                    <th>CATEGORÍA</th>
                                    <th>PRIORIDAD</th>
                                    <th>ESTADO</th>
                                    <th>AGENTE</th>
                                </tr>
                            </thead>

                            <tbody>

                                <tr>
                                    <td>
                                        <strong class="ticket-id">#TK-0248</strong>
                                        <span class="ticket-titulo">Problema con conexión de red</span>
                                    </td>
                                    <td>Red</td>
                                    <td>
                                        <span class="badge prioridad-critica">CRÍTICA</span>
                                    </td>
                                    <td>
                                        <span class="badge estado-proceso">EN PROCESO</span>
                                    </td>
                                    <td>Carlos M.</td>
                                </tr>

                                <tr>
                                    <td>
                                        <strong class="ticket-id">#TK-0244</strong>
                                        <span class="ticket-titulo">No puedo acceder al sistema</span>
                                    </td>
                                    <td>Software</td>
                                    <td>
                                        <span class="badge prioridad-alta">ALTA</span>
                                    </td>
                                    <td>
                                        <span class="badge estado-nuevo">NUEVO</span>
                                    </td>
                                    <td>Sin asignar</td>
                                </tr>

                                <tr>
                                    <td>
                                        <strong class="ticket-id">#TK-0239</strong>
                                        <span class="ticket-titulo">Problema con impresora</span>
                                    </td>
                                    <td>Hardware</td>
                                    <td>
                                        <span class="badge prioridad-media">MEDIA</span>
                                    </td>
                                    <td>
                                        <span class="badge estado-resuelto">RESUELTO</span>
                                    </td>
                                    <td>Laura P.</td>
                                </tr>

                                <tr>
                                    <td>
                                        <strong class="ticket-id">#TK-0235</strong>
                                        <span class="ticket-titulo">Solicitud de instalación</span>
                                    </td>
                                    <td>Software</td>
                                    <td>
                                        <span class="badge prioridad-baja">BAJA</span>
                                    </td>
                                    <td>
                                        <span class="badge estado-resuelto">RESUELTO</span>
                                    </td>
                                    <td>Andrés R.</td>
                                </tr>

                            </tbody>

                        </table>

                    </div>

                </div>


                <!-- ACTIVIDAD -->
                <div class="panel">

                    <div class="panel-header">

                        <div>
                            <h2>Mi actividad</h2>
                            <p>Últimos movimientos de tus tickets</p>
                        </div>

                    </div>

                    <div class="actividad">

                        <div class="actividad-item">
                            <div class="actividad-icon azul">+</div>
                            <div>
                                <strong>Nuevo ticket</strong>
                                <p>Creaste el ticket #TK-0248</p>
                                <small>Hace 5 minutos</small>
                            </div>
                        </div>

                        <div class="actividad-item">
                            <div class="actividad-icon verde">✓</div>
                            <div>
                                <strong>Ticket resuelto</strong>
                                <p>El ticket #TK-0239 fue resuelto</p>
                                <small>Hace 18 minutos</small>
                            </div>
                        </div>

                        <div class="actividad-item">
                            <div class="actividad-icon naranja">→</div>
                            <div>
                                <strong>Estado actualizado</strong>
                                <p>#TK-0248 pasó a En Proceso</p>
                                <small>Hace 32 minutos</small>
                            </div>
                        </div>

                        <div class="actividad-item">
                            <div class="actividad-icon rojo">!</div>
                            <div>
                                <strong>Nuevo comentario</strong>
                                <p>El agente respondió al ticket #TK-0248</p>
                                <small>Hace 1 hora</small>
                            </div>
                        </div>

                    </div>

                </div>

            </section>


            <!-- ACCESOS RÁPIDOS -->
            <section class="panel accesos">

                <div class="panel-header">

                    <div>
                        <h2>Accesos rápidos</h2>
                        <p>Gestiona tus solicitudes de soporte</p>
                    </div>

                </div>

                <div class="accesos-grid">

                    <a href="${pageContext.request.contextPath}/ticket/nuevo" class="acceso">
                        <span>＋</span>
                        <strong>Nuevo ticket</strong>
                        <small>Crear una solicitud</small>
                    </a>

                    <a href="${pageContext.request.contextPath}/tickets" class="acceso">
                        <span>▣</span>
                        <strong>Mis tickets</strong>
                        <small>Consultar tus solicitudes</small>
                    </a>

                    <a href="${pageContext.request.contextPath}/tickets?estado=En%20Proceso" class="acceso">
                        <span>◷</span>
                        <strong>En proceso</strong>
                        <small>Consultar tickets activos</small>
                    </a>

                    <a href="${pageContext.request.contextPath}/tickets?estado=Resuelto" class="acceso">
                        <span>✓</span>
                        <strong>Resueltos</strong>
                        <small>Ver solicitudes solucionadas</small>
                    </a>

                </div>

            </section>

        </main>

    </body>
</html>