<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="es">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Categorías | Mesa de Ayuda</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilo.css">
    </head>
    <body>
        <!-- ==================================================
             SIDEBAR
        ================================================== -->
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
                <a href="${pageContext.request.contextPath}/dashboardAdmin" class="menu-item">
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

                <a href="${pageContext.request.contextPath}/categorias" class="menu-item activo">
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

        <!-- ==================================================
             CONTENIDO PRINCIPAL
        ================================================== -->

        <main class="contenido">

            <!-- ENCABEZADO -->
            <header class="barra-superior">
                <div>
                    <h1>Categorías</h1>
                    <p>Organiza y administra las categorías de los tickets.</p>
                </div>

                <div class="barra-acciones">
                    <!-- BOTÓN AGREGADO PARA ABRIR MODAL -->
                    <button class="btn-guardar" onclick="abrirModalCategoria()">
                        + Nueva Categoría
                    </button>

                    <button class="btn-notificacion">
                        ♧
                        <span></span>
                    </button>
                </div>
            </header>

            <!-- ==================================================
                 RESUMEN DE ESTADÍSTICAS
            ================================================== -->

            <section class="estadisticas-categorias">

                <div class="card-categoria-resumen">
                    <div class="icono-resumen azul">◇</div>
                    <div>
                        <span>Total categorías</span>
                        <strong>${totalCategorias != null ? totalCategorias : 6}</strong>
                    </div>
                </div>

                <div class="card-categoria-resumen">
                    <div class="icono-resumen verde">✓</div>
                    <div>
                        <span>Categorías activas</span>
                        <strong>${categoriasActivas != null ? categoriasActivas : 6}</strong>
                    </div>
                </div>

                <div class="card-categoria-resumen">
                    <div class="icono-resumen morado">▣</div>
                    <div>
                        <span>Tickets clasificados</span>
                        <strong>${totalTicketsClasificados != null ? totalTicketsClasificados : 248}</strong>
                    </div>
                </div>

            </section>

            <!-- ==================================================
                 PANEL DE CATEGORÍAS
            ================================================== -->

            <section class="panel categorias-panel">

                <div class="panel-header">
                    <div>
                        <h2>Todas las categorías</h2>
                        <p>Categorías disponibles para clasificar tickets.</p>
                    </div>

                    <div class="buscador-categoria">
                        <span>⌕</span>
                        <input type="text"
                               id="buscarCategoria"
                               placeholder="Buscar categoría..."
                               onkeyup="filtrarCategorias()">
                    </div>
                </div>

                <!-- GRID DE CATEGORÍAS (DINÁMICO CON JSTL) -->
                <div class="categorias-grid" id="listaCategorias">
                    <c:choose>
                        <c:when test="${not empty listaCategorias}">
                            <c:forEach var="cat" items="${listaCategorias}">
                                <article class="categoria-card">
                                    <div class="categoria-card-superior">
                                        <div class="categoria-icono azul">◈</div>
                                        <span class="estado-categoria ${cat.estado ? 'activa' : 'inactiva'}">
                                            ${cat.estado ? 'Activa' : 'Inactiva'}
                                        </span>
                                    </div>

                                    <div class="categoria-info">
                                        <h3>${cat.nombre}</h3>
                                        <p>${cat.descripcion}</p>
                                    </div>

                                    <div class="categoria-footer">
                                        <span><strong>${cat.totalTickets}</strong> tickets</span>

                                        <div class="categoria-acciones">
                                            <button class="accion-categoria editar" 
                                                    title="Editar" 
                                                    onclick="editarCategoria('${cat.id}', '${cat.nombre}')">
                                                ✎
                                            </button>

                                            <button class="accion-categoria eliminar" 
                                                    title="Eliminar" 
                                                    onclick="eliminarCategoria('${cat.id}')">
                                                ×
                                            </button>
                                        </div>
                                    </div>
                                </article>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <!-- VISTA POR DEFECTO / FALLBACK EN CASO DE NO HABER ATRIBUTOS DE SESIÓN -->
                            <article class="categoria-card">
                                <div class="categoria-card-superior">
                                    <div class="categoria-icono azul">◈</div>
                                    <span class="estado-categoria activa">Activa</span>
                                </div>
                                <div class="categoria-info">
                                    <h3>Hardware</h3>
                                    <p>Problemas relacionados con equipos, computadores y dispositivos físicos.</p>
                                </div>
                                <div class="categoria-footer">
                                    <span><strong>58</strong> tickets</span>
                                    <div class="categoria-acciones">
                                        <button class="accion-categoria editar" title="Editar">✎</button>
                                        <button class="accion-categoria eliminar" title="Eliminar">×</button>
                                    </div>
                                </div>
                            </article>
                        </c:otherwise>
                    </c:choose>
                </div>

            </section>

        </main>

        <!-- ==================================================
             MODAL NUEVA CATEGORÍA
        ================================================== -->

        <div class="modal-overlay" id="modalCategoria">
            <div class="modal-categoria">
                <button class="modal-cerrar" onclick="cerrarModalCategoria()">×</button>

                <div class="modal-icono">◇</div>

                <h2>Nueva categoría</h2>
                <p>Registra una nueva categoría para organizar los tickets de soporte.</p>

                <form action="${pageContext.request.contextPath}/categorias" method="post">
                    <input type="hidden" name="accion" value="crear">

                    <div class="campo-modal">
                        <label for="nombreCategoria">Nombre de la categoría</label>
                        <input type="text"
                               id="nombreCategoria"
                               name="nombreCategoria"
                               placeholder="Ej. Telefonía"
                               maxlength="80"
                               required>
                    </div>

                    <div class="modal-botones">
                        <button type="button" class="btn-cancelar" onclick="cerrarModalCategoria()">
                            Cancelar
                        </button>

                        <button type="submit" class="btn-guardar">
                            Guardar categoría
                        </button>
                    </div>
                </form>
            </div>
        </div>

        <!-- ==================================================
             SCRIPTS
        ================================================== -->
        <script>
            function abrirModalCategoria() {
                document.getElementById("modalCategoria").classList.add("mostrar");
            }

            function cerrarModalCategoria() {
                document.getElementById("modalCategoria").classList.remove("mostrar");
            }

            function filtrarCategorias() {
                const texto = document.getElementById("buscarCategoria").value.toLowerCase();
                const categorias = document.querySelectorAll(".categoria-card");

                categorias.forEach(function (categoria) {
                    const nombre = categoria.querySelector("h3").textContent.toLowerCase();
                    const descripcion = categoria.querySelector("p") ? categoria.querySelector("p").textContent.toLowerCase() : "";

                    if (nombre.includes(texto) || descripcion.includes(texto)) {
                        categoria.style.display = "";
                    } else {
                        categoria.style.display = "none";
                    }
                });
            }

            // Carga modal de edición o confirmación de borrado
            function editarCategoria(id, nombre) {
                console.log("Editar categoría:", id, nombre);
                // Lógica de apertura de modal con datos pre-cargados
            }

            function eliminarCategoria(id) {
                if (confirm("¿Estás seguro de eliminar esta categoría?")) {
                    window.location.href = "${pageContext.request.contextPath}/categorias?accion=eliminar&id=" + id;
                }
            }

            // Cerrar al hacer clic en el backdrop
            document.getElementById("modalCategoria").addEventListener("click", function (e) {
                if (e.target === this) {
                    cerrarModalCategoria();
                }
            });
        </script>
    </body>
</html>