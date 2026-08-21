<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="es">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Categorías | Mesa de Ayuda</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilo.css">
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
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

                <a href="${pageContext.request.contextPath}/categorias" class="menu-item activo">
                    <span>▥</span><label>Categorías</label>
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
                    <button class="btn-guardar" onclick="abrirModalCategoria()">
                        + Nueva Categoría
                    </button>
                </div>
            </header>

            <!-- ==================================================
                 RESUMEN DE ESTADÍSTICAS
            ================================================== -->

            <section class="estadisticas-categorias">

                <div class="card-categoria-resumen">
                    <div class="icono-resumen azul">⚐</div>
                    <div>
                        <span>Total categorías</span>
                        <strong>${totalCategorias != null ? totalCategorias : 0}</strong>
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

                                        <div class="categoria-icono azul">🖳</div>
                                    </div>

                                    <div class="categoria-info">
                                        <h3>${cat.nombreCategoria}</h3>
                                    </div>

                                    <div class="categoria-footer">
                                        <div class="categoria-acciones">
                                            <button class="accion-categoria editar"
                                                    title="Editar"
                                                    onclick="editarCategoria('${cat.idCategoria}', '${cat.nombreCategoria}')">
                                                ✎
                                            </button>

                                            <button class="accion-categoria eliminar"
                                                    title="Eliminar"
                                                    onclick="eliminarCategoria('${cat.idCategoria}')">
                                                ×
                                            </button>
                                        </div>
                                    </div>
                                </article>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <p style="padding: 20px; color: #888;">No hay categorías registradas todavía.</p>
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
             MODAL EDITAR CATEGORÍA
        ================================================== -->

        <div class="modal-overlay" id="modalEditarCategoria">
            <div class="modal-categoria">
                <button class="modal-cerrar" onclick="cerrarModalEditar()">×</button>

                <div class="modal-icono">✎</div>

                <h2>Editar categoría</h2>
                <p>Actualiza el nombre de la categoría.</p>

                <form action="${pageContext.request.contextPath}/categorias" method="post">
                    <input type="hidden" name="accion" value="editar">
                    <input type="hidden" name="idCategoria" id="editIdCategoria">

                    <div class="campo-modal">
                        <label for="editNombreCategoria">Nombre de la categoría</label>
                        <input type="text"
                               id="editNombreCategoria"
                               name="nombreCategoria"
                               maxlength="80"
                               required>
                    </div>

                    <div class="modal-botones">
                        <button type="button" class="btn-cancelar" onclick="cerrarModalEditar()">
                            Cancelar
                        </button>

                        <button type="submit" class="btn-guardar">
                            Guardar cambios
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

            function editarCategoria(id, nombre) {
                document.getElementById("editIdCategoria").value = id;
                document.getElementById("editNombreCategoria").value = nombre;
                document.getElementById("modalEditarCategoria").classList.add("mostrar");
            }

            function cerrarModalEditar() {
                document.getElementById("modalEditarCategoria").classList.remove("mostrar");
            }

            function filtrarCategorias() {
                const texto = document.getElementById("buscarCategoria").value.toLowerCase();
                const categorias = document.querySelectorAll(".categoria-card");

                categorias.forEach(function (categoria) {
                    const nombre = categoria.querySelector("h3").textContent.toLowerCase();

                    if (nombre.includes(texto)) {
                        categoria.style.display = "";
                    } else {
                        categoria.style.display = "none";
                    }
                });
            }

            function eliminarCategoria(id) {
                Swal.fire({
                    title: "¿Eliminar esta categoría?",
                    text: "Los tickets que ya la usan no se verán afectados, "
                            + "pero no podrás elegirla para tickets nuevos. "
                            + "Esta acción no se puede deshacer.",
                    icon: "warning",
                    showCancelButton: true,
                    confirmButtonText: "Sí, eliminar",
                    cancelButtonText: "Cancelar",
                    confirmButtonColor: "#dc2626",
                    cancelButtonColor: "#94a3b8"
                }).then(function (resultado) {
                    if (resultado.isConfirmed) {
                        window.location.href = "${pageContext.request.contextPath}/categorias?accion=eliminar&id=" + id;
                    }
                });
            }

            document.getElementById("modalCategoria").addEventListener("click", function (e) {
                if (e.target === this) {
                    cerrarModalCategoria();
                }
            });

            document.getElementById("modalEditarCategoria").addEventListener("click", function (e) {
                if (e.target === this) {
                    cerrarModalEditar();
                }
            });
        </script>
    </body>
</html>