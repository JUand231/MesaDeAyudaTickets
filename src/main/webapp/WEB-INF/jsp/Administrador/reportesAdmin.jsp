<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Reportes | Mesa de Ayuda</title>
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
                <a href="${pageContext.request.contextPath}/dashboardAdmin" class="menu-item ">
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
                    <span>◇</span><label>Categorías</label>
                </a>

                <a href="${pageContext.request.contextPath}/reportes" class="menu-item activo">
                    <span>▥</span><label>Reportes</label>
                </a>

                <a href="${pageContext.request.contextPath}/comentariosAdmin" class="menu-item">
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

        <!-- GRÁFICAS -->

        <section class="reportes-grid">

            <div class="panel reporte-grafica">

                <div class="panel-header">
                    <div>
                        <h2>Tickets por estado</h2>
                        <p>Distribución actual de las solicitudes.</p>
                    </div>
                </div>

                <div class="grafica-barras">

                    <div class="barra-item">
                        <div class="barra-info">
                            <span>Nuevo</span>
                            <strong>28</strong>
                        </div>
                        <div class="barra-base">
                            <div class="barra azul-barra" style="width:35%"></div>
                        </div>
                    </div>

                    <div class="barra-item">
                        <div class="barra-info">
                            <span>En proceso</span>
                            <strong>54</strong>
                        </div>
                        <div class="barra-base">
                            <div class="barra morado-barra" style="width:60%"></div>
                        </div>
                    </div>

                    <div class="barra-item">
                        <div class="barra-info">
                            <span>Resuelto</span>
                            <strong>119</strong>
                        </div>
                        <div class="barra-base">
                            <div class="barra verde-barra" style="width:88%"></div>
                        </div>
                    </div>

                    <div class="barra-item">
                        <div class="barra-info">
                            <span>Cerrado</span>
                            <strong>47</strong>
                        </div>
                        <div class="barra-base">
                            <div class="barra gris-barra" style="width:48%"></div>
                        </div>
                    </div>

                </div>
            </div>


            <div class="panel reporte-grafica">

                <div class="panel-header">
                    <div>
                        <h2>Tickets por prioridad</h2>
                        <p>Nivel de urgencia de las solicitudes.</p>
                    </div>
                </div>

                <div class="prioridades-reporte">

                    <div class="prioridad-reporte">
                        <div class="prioridad-circulo critica">18</div>
                        <div>
                            <strong>Crítica</strong>
                            <span>7.3%</span>
                        </div>
                    </div>

                    <div class="prioridad-reporte">
                        <div class="prioridad-circulo alta">67</div>
                        <div>
                            <strong>Alta</strong>
                            <span>27.0%</span>
                        </div>
                    </div>

                    <div class="prioridad-reporte">
                        <div class="prioridad-circulo media">109</div>
                        <div>
                            <strong>Media</strong>
                            <span>44.0%</span>
                        </div>
                    </div>

                    <div class="prioridad-reporte">
                        <div class="prioridad-circulo baja">54</div>
                        <div>
                            <strong>Baja</strong>
                            <span>21.7%</span>
                        </div>
                    </div>

                </div>
            </div>

        </section>


        <!-- CATEGORÍAS -->

        <section class="panel reporte-categorias">

            <div class="panel-header">
                <div>
                    <h2>Tickets por categoría</h2>
                    <p>Áreas que concentran la mayor cantidad de solicitudes.</p>
                </div>
            </div>

            <div class="categorias-reporte">

                <div class="categoria-reporte">
                    <div class="categoria-reporte-icono azul">◈</div>
                    <div class="categoria-reporte-info">
                        <strong>Software</strong>
                        <span>72 tickets</span>
                    </div>
                    <div class="categoria-progreso">
                        <div style="width:85%"></div>
                    </div>
                    <strong>29%</strong>
                </div>

                <div class="categoria-reporte">
                    <div class="categoria-reporte-icono verde">⌁</div>
                    <div class="categoria-reporte-info">
                        <strong>Red</strong>
                        <span>46 tickets</span>
                    </div>
                    <div class="categoria-progreso">
                        <div style="width:62%"></div>
                    </div>
                    <strong>19%</strong>
                </div>

                <div class="categoria-reporte">
                    <div class="categoria-reporte-icono naranja">⚙</div>
                    <div class="categoria-reporte-info">
                        <strong>Mantenimiento</strong>
                        <span>31 tickets</span>
                    </div>
                    <div class="categoria-progreso">
                        <div style="width:43%"></div>
                    </div>
                    <strong>12%</strong>
                </div>

                <div class="categoria-reporte">
                    <div class="categoria-reporte-icono morado">◇</div>
                    <div class="categoria-reporte-info">
                        <strong>Hardware</strong>
                        <span>58 tickets</span>
                    </div>
                    <div class="categoria-progreso">
                        <div style="width:70%"></div>
                    </div>
                    <strong>23%</strong>
                </div>

            </div>
        </section>


        <!-- AGENTES -->

        <section class="panel agentes-reporte">

            <div class="panel-header">
                <div>
                    <h2>Rendimiento de agentes</h2>
                    <p>Resumen de tickets gestionados durante el periodo.</p>
                </div>
            </div>

            <div class="tabla-contenedor">

                <table class="tabla">

                    <thead>
                        <tr>
                            <th>AGENTE</th>
                            <th>ASIGNADOS</th>
                            <th>RESUELTOS</th>
                            <th>PENDIENTES</th>
                            <th>EFECTIVIDAD</th>
                        </tr>
                    </thead>

                    <tbody>

                        <tr>
                            <td>
                                <div class="persona">
                                    <div class="persona-avatar agente">C</div>
                                    <span>Carlos Martínez</span>
                                </div>
                            </td>
                            <td>68</td>
                            <td>61</td>
                            <td>7</td>
                            <td>
                                <span class="efectividad alta-efectividad">89.7%</span>
                            </td>
                        </tr>

                        <tr>
                            <td>
                                <div class="persona">
                                    <div class="persona-avatar agente">L</div>
                                    <span>Laura Pérez</span>
                                </div>
                            </td>
                            <td>57</td>
                            <td>51</td>
                            <td>6</td>
                            <td>
                                <span class="efectividad alta-efectividad">89.4%</span>
                            </td>
                        </tr>

                        <tr>
                            <td>
                                <div class="persona">
                                    <div class="persona-avatar agente">A</div>
                                    <span>Andrés Rodríguez</span>
                                </div>
                            </td>
                            <td>63</td>
                            <td>48</td>
                            <td>15</td>
                            <td>
                                <span class="efectividad media-efectividad">76.2%</span>
                            </td>
                        </tr>

                    </tbody>

                </table>

            </div>

        </section>

    </main>

</body>
</html>