<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="es">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Mi Perfil | Mesa de Ayuda</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilo.css">

        <!-- ==========================================================
             CSS ESPECÍFICO DE ESTA PÁGINA
             ========================================================== -->
        <style>
            /* Layout Principal */
            .contenido-futurista {
                max-width: 600px;
                margin: 0 auto;
                padding: 20px;
                color: #e0f2fe;
                font-family: 'Segoe UI', Roboto, sans-serif;
            }

            .header-perfil {
                text-align: center;
                margin-bottom: 25px;
            }

            .header-perfil h1 {
                font-size: 2rem;
                font-weight: 700;
                color: #38bdf8;
                text-shadow: 0 0 12px rgba(56, 189, 248, 0.4);
                margin: 0 0 6px 0;
            }

            .header-perfil p {
                color: #94a3b8;
                font-size: 0.95rem;
                margin: 0;
            }

            /* Avatar de Silueta Neón */
            .perfil-avatar-wrapper {
                display: flex;
                flex-direction: column;
                align-items: center;
                margin-bottom: 30px;
            }

            .avatar-glow {
                width: 110px;
                height: 110px;
                border-radius: 50%;
                background: radial-gradient(circle, rgba(14, 165, 233, 0.2) 0%, rgba(3, 7, 18, 0.8) 100%);
                border: 2px solid #38bdf8;
                box-shadow: 0 0 20px rgba(56, 189, 248, 0.5), inset 0 0 15px rgba(56, 189, 248, 0.3);
                display: flex;
                align-items: center;
                justify-content: center;
                margin-bottom: 12px;
            }

            .avatar-icon {
                width: 65px;
                height: 65px;
                fill: #38bdf8;
                filter: drop-shadow(0 0 8px #0284c7);
            }

            .avatar-info {
                text-align: center;
            }

            .avatar-info p {
                margin: 3px 0;
                font-size: 0.95rem;
                color: #f1f5f9;
            }

            .avatar-info .label {
                color: #0284c7;
                font-weight: 600;
            }

            /* Contenedor tipo Tarjeta Neón */
            .panel-futurista {
                background: rgba(15, 23, 42, 0.75);
                backdrop-filter: blur(12px);
                border: 1px solid rgba(56, 189, 248, 0.3);
                border-radius: 12px;
                padding: 28px;
                box-shadow: 0 8px 32px rgba(0, 0, 0, 0.5), 0 0 15px rgba(14, 165, 233, 0.15);
            }

            .panel-header {
                border-bottom: 1px solid rgba(56, 189, 248, 0.15);
                padding-bottom: 14px;
                margin-bottom: 22px;
            }

            .panel-header h2 {
                font-size: 1.15rem;
                color: #f8fafc;
                margin: 0 0 4px 0;
            }

            .panel-header p {
                font-size: 0.85rem;
                color: #64748b;
                margin: 0;
            }

            /* Campos de Formulario */
            .campo-cyber {
                margin-bottom: 18px;
                display: flex;
                flex-direction: column;
            }

            .campo-cyber label {
                font-size: 0.88rem;
                color: #94a3b8;
                margin-bottom: 6px;
                font-weight: 500;
            }

            .campo-cyber input {
                background: rgba(2, 6, 23, 0.6);
                border: 1px solid rgba(56, 189, 248, 0.25);
                border-radius: 6px;
                padding: 10px 14px;
                color: #f1f5f9;
                font-size: 0.95rem;
                outline: none;
                transition: all 0.3s ease;
            }

            .campo-cyber input:focus {
                border-color: #38bdf8;
                box-shadow: 0 0 10px rgba(56, 189, 248, 0.3);
            }

            .campo-cyber input.input-readonly {
                background: rgba(15, 23, 42, 0.4);
                border-color: rgba(148, 163, 184, 0.15);
                color: #64748b;
                cursor: not-allowed;
            }

            /* Botón Futurista */
            .panel-acciones {
                display: flex;
                justify-content: flex-end;
                margin-top: 25px;
            }

            .btn-cyber {
                background: linear-gradient(135deg, #0284c7 0%, #0369a1 100%);
                color: #ffffff;
                border: 1px solid #38bdf8;
                padding: 10px 24px;
                border-radius: 6px;
                font-weight: 600;
                font-size: 0.95rem;
                cursor: pointer;
                box-shadow: 0 0 12px rgba(2, 132, 199, 0.4);
                transition: all 0.3s ease;
            }

            .btn-cyber:hover {
                background: linear-gradient(135deg, #0369a1 0%, #0284c7 100%);
                box-shadow: 0 0 20px rgba(56, 189, 248, 0.7);
                transform: translateY(-1px);
            }

            /* Alertas Futuristas */
            .alerta-cyber {
                padding: 10px 14px;
                border-radius: 6px;
                font-size: 0.9rem;
                margin-bottom: 20px;
                border: 1px solid;
            }

            .alerta-exito {
                background: rgba(22, 101, 52, 0.2);
                border-color: #22c55e;
                color: #4ade80;
                box-shadow: 0 0 10px rgba(34, 197, 94, 0.2);
            }

            .alerta-error {
                background: rgba(153, 27, 27, 0.2);
                border-color: #ef4444;
                color: #f87171;
                box-shadow: 0 0 10px rgba(239, 68, 68, 0.2);
            }
            /* 1. Fondo general oscuro (Aplica a la vista principal) */
            body, .contenido-futurista {
                background-color: #0b1120; /* Fondo azul noche muy oscuro */
                color: #e0f2fe;
            }

            /* 2. Corrección de contraste en textos superiores */
            .header-perfil p {
                color: #94a3b8; /* Gris claro muy legible sobre fondo oscuro */
            }

            .avatar-info p {
                color: #ffffff; /* Texto principal en blanco puro */
                font-size: 0.95rem;
            }

            .avatar-info .label {
                color: #38bdf8; /* Azul cyan neón brillante */
                font-weight: 600;
            }

            /* 3. Corrección dentro del Panel Oscuro */
            .panel-futurista {
                background: #1e293b; /* Azul pizarra oscuro sólido */
                border: 1px solid rgba(56, 189, 248, 0.3);
                box-shadow: 0 0 25px rgba(14, 165, 233, 0.15);
            }

            .panel-header p {
                color: #cbd5e1; /* Aumenta la visibilidad del subtítulo */
            }

            .campo-cyber label {
                color: #e2e8f0; /* Etiquetas claramente visibles */
            }

            /* Placeholders visibles en los inputs */
            .campo-cyber input::placeholder {
                color: #64748b;
            }
        </style>
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

            <c:choose>

                <c:when test="${usuario.idRol == 1}">
                    <!-- SOLICITANTE -->

                    <div class="menu-titulo">PRINCIPAL</div>

                    <nav class="menu">

                        <a href="${pageContext.request.contextPath}/dashboardSolicitante" class="menu-item ">
                            <span>⌂</span><label>Dashboard</label>
                        </a>

                        <a href="${pageContext.request.contextPath}/tickets" class="menu-item">
                            <span>🏷</span><label>Mis tickets</label>
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
                            <span>↪</span>
                            <label> Cerrar sesión </label>
                        </a>

                    </nav>

                    <div class="usuario-sidebar">
                        <div class="usuario-avatar">S</div>
                        <div>
                            <strong>Solicitante</strong>
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
                            <span>🏷</span><label>Mis tickets</label>
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
                        <a href="${pageContext.request.contextPath}/dashboardAdmin" class="menu-item">
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
                            <span>▥</span><label>Categorías</label>
                        </a>

                        <a href="${pageContext.request.contextPath}/comentariosAdmin" class="menu-item">
                            <span>🖂️</span><label>Comentarios</label>
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
                            <strong>Administrador</strong>
                            <small>Administrador</small>
                        </div>
                    </div>

                </c:otherwise>

            </c:choose>
        </aside>

        <!-- CONTENIDO PRINCIPAL -->
        <main class="contenido-futurista">

            <!-- Header de la sección -->
            <header class="header-perfil">
                <h1>Mi Perfil</h1>
            </header>

            <!-- Avatar e Identificador Futurista -->
            <div class="perfil-avatar-wrapper">
                <div class="avatar-glow">
                    <svg class="avatar-icon" viewBox="0 0 24 24" fill="currentColor">
                    <path d="M12 12c2.21 0 4-1.79 4-4s-1.79-4-4-4-4 1.79-4 4 1.79 4 4 4zm0 2c-2.67 0-8 1.34-8 4v2h16v-2c0-2.66-5.33-4-8-4z"/>
                    </svg>
                </div>
                <div class="avatar-info">
                    <p>Consulta tu información y actualiza tu correo o contraseña.</p>
                </div>
            </div>

            <!-- Panel de Formulario -->
            <section class="panel-futurista">
                <div class="panel-header">
                    <h2>Información de la cuenta</h2>
                </div>

                <!-- Alertas de estado -->
                <c:if test="${not empty exito}">
                    <div class="alerta-cyber alerta-exito" role="alert">
                        ${exito}
                    </div>
                </c:if>
                <c:if test="${not empty error}">
                    <div class="alerta-cyber alerta-error" role="alert">
                        ${error}
                    </div>
                </c:if>

                <form method="post" action="${pageContext.request.contextPath}/perfil" autocomplete="off">
                    <div class="campo-cyber">
                        <label for="nombre">Nombre</label>
                        <input type="text" id="nombre" value="${usuario.nombre}" readonly class="input-readonly">
                    </div>

                    <div class="campo-cyber">
                        <label for="rol">Rol</label>
                        <input type="text" id="rol" value="${nombreRol}" readonly class="input-readonly">
                    </div>

                    <div class="campo-cyber">
                        <label for="correo">Correo electrónico</label>
                        <input type="email" id="correo" name="correo" value="${usuario.correo}" required>
                    </div>

                    <div class="campo-cyber">
                        <label for="nuevaContrasena">Nueva contraseña</label>
                        <input type="password" id="nuevaContrasena" name="nuevaContrasena" 
                               placeholder="Déjala en blanco si no deseas cambiarla" 
                               autocomplete="new-password" minlength="8">
                    </div>

                    <div class="campo-cyber">
                        <label for="confirmarContrasena">Confirmar nueva contraseña</label>
                        <input type="password" id="confirmarContrasena" name="confirmarContrasena" 
                               placeholder="Repite la nueva contraseña" 
                               autocomplete="new-password">
                    </div>

                    <div class="panel-acciones">
                        <button type="submit" class="btn-cyber">Guardar cambios</button>
                    </div>
                </form>
            </section>
        </main>

    </body>
</html>