<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Dashboard | Agente - Mesa de Ayuda</title>
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
                <a href="${pageContext.request.contextPath}/dashboardAgente" class="menu-item activo">
                    <span>⌂</span><label>Dashboard</label>
                </a>

                <a href="${pageContext.request.contextPath}/tickets" class="menu-item">
                    <span>▣</span><label>Mis tickets</label>
                </a>
            </nav>

            <div class="menu-titulo">GESTIÓN</div>

            <nav class="menu">
                <a href="${pageContext.request.contextPath}/tickets?estado=En%20Proceso" class="menu-item">
                    <span>◷</span><label>En proceso</label>
                </a>

                <a href="${pageContext.request.contextPath}/tickets?estado=Pendiente" class="menu-item">
                    <span>!</span><label>Pendientes</label>
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
                <div class="usuario-avatar">A</div>
                <div>
                    <strong>Agente</strong>
                    <small>Agente de soporte</small>
                </div>
            </div>

        </aside>

        <!-- CONTENIDO -->
        <main class="contenido">

            <header class="barra-superior">
                <div>
                    <h1>Dashboard</h1>
                    <p>Bienvenido al centro de atención y soporte.</p>
                </div>

                <div class="barra-acciones">
                    <button class="btn-notificacion">♧<span></span></button>
                    <a href="${pageContext.request.contextPath}/tickets" class="btn-principal">▣ Mis tickets</a>
                </div>
            </header>

            <!-- ESTADÍSTICAS -->
            <section class="estadisticas">

                <div class="estadistica">
                    <div class="estadistica-icono azul">▣</div>
                    <div>
                        <span>MIS TICKETS</span>
                        <strong>24</strong>
                        <small>Tickets asignados</small>
                    </div>
                </div>

                <div class="estadistica">
                    <div class="estadistica-icono naranja">◷</div>
                    <div>
                        <span>PENDIENTES</span>
                        <strong>8</strong>
                        <small>Requieren atención</small>
                    </div>
                </div>

                <div class="estadistica">
                    <div class="estadistica-icono verde">→</div>
                    <div>
                        <span>EN PROCESO</span>
                        <strong>10</strong>
                        <small>Actualmente atendiendo</small>
                    </div>
                </div>

                <div class="estadistica">
                    <div class="estadistica-icono rojo">✓</div>
                    <div>
                        <span>RESUELTOS</span>
                        <strong>6</strong>
                        <small class="positivo">Tickets solucionados</small>
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
                            <p>Tickets asignados para atención</p>
                        </div>
                        <a href="${pageContext.request.contextPath}/tickets">Ver todos →</a>
                    </div>

                    <div class="tabla-contenedor">
                        <table class="tabla">
                            <thead>
                                <tr>
                                    <th>TICKET</th>
                                    <th>CATEGORÍA</th>
                                    <th>PRIORIDAD</th>
                                    <th>ESTADO</th>
                                    <th>SOLICITANTE</th>
                                </tr>
                            </thead>

                            <tbody>

                                <tr>
                                    <td>
                                        <strong class="ticket-id">#TK-0248</strong>
                                        <span class="ticket-titulo">Problema con conexión de red</span>
                                    </td>
                                    <td>Red</td>
                                    <td><span class="badge prioridad-critica">CRÍTICA</span></td>
                                    <td><span class="badge estado-proceso">EN PROCESO</span></td>
                                    <td>Sofia L.</td>
                                </tr>

                                <tr>
                                    <td>
                                        <strong class="ticket-id">#TK-0247</strong>
                                        <span class="ticket-titulo">Equipo no enciende</span>
                                    </td>
                                    <td>Hardware</td>
                                    <td><span class="badge prioridad-alta">ALTA</span></td>
                                    <td><span class="badge estado-nuevo">NUEVO</span></td>
                                    <td>Carlos M.</td>
                                </tr>

                                <tr>
                                    <td>
                                        <strong class="ticket-id">#TK-0246</strong>
                                        <span class="ticket-titulo">Instalación de software</span>
                                    </td>
                                    <td>Software</td>
                                    <td><span class="badge prioridad-media">MEDIA</span></td>
                                    <td><span class="badge estado-proceso">EN PROCESO</span></td>
                                    <td>Laura P.</td>
                                </tr>

                                <tr>
                                    <td>
                                        <strong class="ticket-id">#TK-0245</strong>
                                        <span class="ticket-titulo">Mantenimiento de equipo</span>
                                    </td>
                                    <td>Mantenimiento</td>
                                    <td><span class="badge prioridad-baja">BAJA</span></td>
                                    <td><span class="badge estado-resuelto">RESUELTO</span></td>
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
                            <p>Últimos movimientos realizados</p>
                        </div>
                    </div>

                    <div class="actividad">

                        <div class="actividad-item">
                            <div class="actividad-icon azul">→</div>
                            <div>
                                <strong>Ticket atendido</strong>
                                <p>Comenzaste a atender el ticket #TK-0248</p>
                                <small>Hace 5 minutos</small>
                            </div>
                        </div>

                        <div class="actividad-item">
                            <div class="actividad-icon verde">✓</div>
                            <div>
                                <strong>Ticket resuelto</strong>
                                <p>#TK-0245 fue marcado como resuelto</p>
                                <small>Hace 18 minutos</small>
                            </div>
                        </div>

                        <div class="actividad-item">
                            <div class="actividad-icon naranja">→</div>
                            <div>
                                <strong>Estado actualizado</strong>
                                <p>#TK-0246 cambió a En Proceso</p>
                                <small>Hace 32 minutos</small>
                            </div>
                        </div>

                        <div class="actividad-item">
                            <div class="actividad-icon rojo">!</div>
                            <div>
                                <strong>Atención requerida</strong>
                                <p>El ticket #TK-0248 tiene prioridad crítica</p>
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
                        <p>Gestión de tus tickets asignados</p>
                    </div>
                </div>

                <div class="accesos-grid">

                    <a href="${pageContext.request.contextPath}/tickets" class="acceso">
                        <span>▣</span>
                        <strong>Mis tickets</strong>
                        <small>Ver tickets asignados</small>
                    </a>

                    <a href="${pageContext.request.contextPath}/tickets?estado=Pendiente" class="acceso">
                        <span>◷</span>
                        <strong>Pendientes</strong>
                        <small>Tickets por atender</small>
                    </a>

                    <a href="${pageContext.request.contextPath}/tickets?estado=En%20Proceso" class="acceso">
                        <span>→</span>
                        <strong>En proceso</strong>
                        <small>Tickets que estás atendiendo</small>
                    </a>

                    <a href="${pageContext.request.contextPath}/tickets?estado=Resuelto" class="acceso">
                        <span>✓</span>
                        <strong>Resueltos</strong>
                        <small>Tickets solucionados</small>
                    </a>

                </div>

            </section>

        </main>

    </body>
</html>