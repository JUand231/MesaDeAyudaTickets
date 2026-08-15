<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="es">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Mi Perfil | Mesa de Ayuda</title>
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

            <c:choose>

                <c:when test="${usuario.idRol == 1}">
                    <!-- SOLICITANTE -->

                    <div class="menu-titulo">PRINCIPAL</div>

                    <nav class="menu">
                        <a href="${pageContext.request.contextPath}/dashboardSolicitante" class="menu-item">
                            <span>⌂</span><label>Dashboard</label>
                        </a>

                        <a href="${pageContext.request.contextPath}/tickets" class="menu-item">
                            <span>▣</span><label>Mis tickets</label>
                        </a>

                        <a href="${pageContext.request.contextPath}/tickets?estado=Nuevo" class="menu-item">
                            <span>●</span><label>Comentarios</label>
                        </a>

                        <a href="${pageContext.request.contextPath}/tickets?estado=En%20Proceso" class="menu-item">
                            <span>◷</span><label>Estado</label>
                        </a>
                    </nav>

                    <div class="menu-titulo">SISTEMA</div>

                    <nav class="menu">
                        <a href="${pageContext.request.contextPath}/perfil" class="menu-item activo">
                            <span>⚙</span><label>Mi Perfil</label>
                        </a>

                        <a href="${pageContext.request.contextPath}/CerrarSesion" class="menu-item">
                            <span>↪</span><label>Cerrar sesión</label>
                        </a>
                    </nav>

                    <div class="usuario-sidebar">
                        <div class="usuario-avatar">S</div>
                        <div>
                            <strong>${usuario.nombre}</strong>
                            <small>Usuario solicitante</small>
                        </div>
                    </div>

                </c:when>

                <c:when test="${usuario.idRol == 2}">
                    <!-- AGENTE -->

                    <div class="menu-titulo">PRINCIPAL</div>

                    <nav class="menu">
                        <a href="${pageContext.request.contextPath}/dashboardAgente" class="menu-item">
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
                        <a href="${pageContext.request.contextPath}/perfil" class="menu-item activo">
                            <span>⚙</span><label>Mi Perfil</label>
                        </a>

                        <a href="${pageContext.request.contextPath}/CerrarSesion" class="menu-item">
                            <span>↪</span><label>Cerrar sesión</label>
                        </a>
                    </nav>

                    <div class="usuario-sidebar">
                        <div class="usuario-avatar">A</div>
                        <div>
                            <strong>${usuario.nombre}</strong>
                            <small>Agente de soporte</small>
                        </div>
                    </div>

                </c:when>

                <c:otherwise>
                    <!-- ADMINISTRADOR -->

                    <div class="menu-titulo">PRINCIPAL</div>

                    <nav class="menu">
                        <a href="${pageContext.request.contextPath}/dashboard" class="menu-item">
                            <span>⌂</span><label>Dashboard</label>
                        </a>

                        <a href="${pageContext.request.contextPath}/tickets" class="menu-item">
                            <span>▣</span><label>Tickets</label>
                        </a>
                    </nav>

                    <div class="menu-titulo">ADMINISTRACIÓN</div>

                    <nav class="menu">
                        <a href="${pageContext.request.contextPath}/agentes" class="menu-item">
                            <span>♟</span><label>Agentes</label>
                        </a>

                        <a href="${pageContext.request.contextPath}/categorias" class="menu-item">
                            <span>◇</span><label>Categorías</label>
                        </a>

                        <a href="${pageContext.request.contextPath}/reportes" class="menu-item">
                            <span>▥</span><label>Reportes</label>
                        </a>

                        <a href="${pageContext.request.contextPath}/usuarios" class="menu-item">
                            <span>●️</span><label>Comentarios</label>
                        </a>
                    </nav>

                    <div class="menu-titulo">SISTEMA</div>

                    <nav class="menu">
                        <a href="${pageContext.request.contextPath}/perfil" class="menu-item activo">
                            <span>⚙</span><label>Mi Perfil</label>
                        </a>

                        <a href="${pageContext.request.contextPath}/CerrarSesion" class="menu-item">
                            <span>↪</span><label>Cerrar sesión</label>
                        </a>
                    </nav>

                    <div class="usuario-sidebar">
                        <div class="usuario-avatar">A</div>
                        <div>
                            <strong>${usuario.nombre}</strong>
                            <small>Administrador</small>
                        </div>
                    </div>

                </c:otherwise>

            </c:choose>
        </aside>

        <!-- CONTENIDO -->
        <main class="contenido">

            <header class="barra-superior">
                <div>
                    <h1>Mi Perfil</h1>
                    <p>Consulta tu información y actualiza tu correo o contraseña.</p>
                </div>
            </header>

            <section class="panel" style="max-width: 560px;">

                <div class="panel-header">
                    <div>
                        <h2>Información de la cuenta</h2>
                        <p>El nombre y el rol solo puede modificarlos un administrador.</p>
                    </div>
                </div>

                <c:if test="${not empty exito}">
                    <p style="color: #2e7d32; font-weight: 600; margin: 0 0 16px;">${exito}</p>
                </c:if>

                <c:if test="${not empty error}">
                    <p style="color: #c62828; font-weight: 600; margin: 0 0 16px;">${error}</p>
                </c:if>

                <form method="post" action="${pageContext.request.contextPath}/perfil">

                    <div class="campo-modal">
                        <label>Nombre</label>
                        <input type="text" value="${usuario.nombre}" disabled>
                    </div>

                    <div class="campo-modal">
                        <label>Rol</label>
                        <input type="text" value="${nombreRol}" disabled>
                    </div>

                    <div class="campo-modal">
                        <label>Correo</label>
                        <input type="email" name="correo" value="${usuario.correo}" required>
                    </div>

                    <div class="campo-modal">
                        <label>Nueva contraseña</label>
                        <input type="password" name="nuevaContrasena" placeholder="Déjalo vacío si no la vas a cambiar">
                    </div>

                    <div class="campo-modal">
                        <label>Confirmar nueva contraseña</label>
                        <input type="password" name="confirmarContrasena" placeholder="Repite la nueva contraseña">
                    </div>

                    <div class="modal-botones">
                        <button type="submit" class="btn-guardar">Guardar cambios</button>
                    </div>

                </form>

            </section>

        </main>

    </body>
</html>