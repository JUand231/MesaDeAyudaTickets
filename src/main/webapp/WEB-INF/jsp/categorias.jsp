<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>

<html lang="es">

    <head>

        <meta charset="UTF-8">

        <meta name="viewport"
              content="width=device-width, initial-scale=1.0">

        <title>Categorías | Mesa de Ayuda</title>

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
                   class="menu-item activo">

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
                        Categorías
                    </h1>

                    <p>
                        Organiza y administra las categorías de los tickets.
                    </p>

                </div>


                <div class="barra-acciones">

                    <button class="btn-notificacion">

                        ♧

                        <span></span>

                    </button>


                    <button class="btn-principal"
                            onclick="abrirModalCategoria()">

                        ＋ Nueva categoría

                    </button>

                </div>

            </header>



            <!-- ==================================================
                 RESUMEN
            ================================================== -->

            <section class="estadisticas-categorias">


                <div class="card-categoria-resumen">

                    <div class="icono-resumen azul">
                        ◇
                    </div>

                    <div>

                        <span>
                            Total categorías
                        </span>

                        <strong>
                            6
                        </strong>

                    </div>

                </div>


                <div class="card-categoria-resumen">

                    <div class="icono-resumen verde">
                        ✓
                    </div>

                    <div>

                        <span>
                            Categorías activas
                        </span>

                        <strong>
                            6
                        </strong>

                    </div>

                </div>


                <div class="card-categoria-resumen">

                    <div class="icono-resumen morado">
                        ▣
                    </div>

                    <div>

                        <span>
                            Tickets clasificados
                        </span>

                        <strong>
                            248
                        </strong>

                    </div>

                </div>


            </section>



            <!-- ==================================================
                 PANEL DE CATEGORÍAS
            ================================================== -->

            <section class="panel categorias-panel">


                <div class="panel-header">

                    <div>

                        <h2>
                            Todas las categorías
                        </h2>

                        <p>
                            Categorías disponibles para clasificar tickets.
                        </p>

                    </div>


                    <div class="buscador-categoria">

                        <span>
                            ⌕
                        </span>

                        <input type="text"
                               id="buscarCategoria"
                               placeholder="Buscar categoría..."
                               onkeyup="filtrarCategorias()">

                    </div>

                </div>



                <!-- ==================================================
                     GRID DE CATEGORÍAS
                ================================================== -->

                <div class="categorias-grid"
                     id="listaCategorias">


                    <!-- CATEGORÍA 1 -->

                    <article class="categoria-card">

                        <div class="categoria-card-superior">

                            <div class="categoria-icono azul">
                                ◈
                            </div>


                            <span class="estado-categoria activa">
                                Activa
                            </span>

                        </div>


                        <div class="categoria-info">

                            <h3>
                                Hardware
                            </h3>

                            <p>
                                Problemas relacionados con equipos,
                                computadores y dispositivos físicos.
                            </p>

                        </div>


                        <div class="categoria-footer">

                            <span>
                                <strong>58</strong> tickets
                            </span>


                            <div class="categoria-acciones">

                                <button class="accion-categoria editar"
                                        title="Editar">

                                    ✎

                                </button>


                                <button class="accion-categoria eliminar"
                                        title="Eliminar">

                                    ×

                                </button>

                            </div>

                        </div>

                    </article>



                    <!-- CATEGORÍA 2 -->

                    <article class="categoria-card">

                        <div class="categoria-card-superior">

                            <div class="categoria-icono morado">
                                ◇
                            </div>


                            <span class="estado-categoria activa">
                                Activa
                            </span>

                        </div>


                        <div class="categoria-info">

                            <h3>
                                Software
                            </h3>

                            <p>
                                Errores, instalaciones y problemas
                                relacionados con aplicaciones.
                            </p>

                        </div>


                        <div class="categoria-footer">

                            <span>
                                <strong>72</strong> tickets
                            </span>


                            <div class="categoria-acciones">

                                <button class="accion-categoria editar"
                                        title="Editar">

                                    ✎

                                </button>


                                <button class="accion-categoria eliminar"
                                        title="Eliminar">

                                    ×

                                </button>

                            </div>

                        </div>

                    </article>



                    <!-- CATEGORÍA 3 -->

                    <article class="categoria-card">

                        <div class="categoria-card-superior">

                            <div class="categoria-icono verde">
                                ⌁
                            </div>


                            <span class="estado-categoria activa">
                                Activa
                            </span>

                        </div>


                        <div class="categoria-info">

                            <h3>
                                Red
                            </h3>

                            <p>
                                Conectividad, Internet, servidores
                                y problemas de red.
                            </p>

                        </div>


                        <div class="categoria-footer">

                            <span>
                                <strong>46</strong> tickets
                            </span>


                            <div class="categoria-acciones">

                                <button class="accion-categoria editar"
                                        title="Editar">

                                    ✎

                                </button>


                                <button class="accion-categoria eliminar"
                                        title="Eliminar">

                                    ×

                                </button>

                            </div>

                        </div>

                    </article>



                    <!-- CATEGORÍA 4 -->

                    <article class="categoria-card">

                        <div class="categoria-card-superior">

                            <div class="categoria-icono naranja">
                                ⚙
                            </div>


                            <span class="estado-categoria activa">
                                Activa
                            </span>

                        </div>


                        <div class="categoria-info">

                            <h3>
                                Mantenimiento
                            </h3>

                            <p>
                                Mantenimiento preventivo y correctivo
                                de equipos tecnológicos.
                            </p>

                        </div>


                        <div class="categoria-footer">

                            <span>
                                <strong>31</strong> tickets
                            </span>


                            <div class="categoria-acciones">

                                <button class="accion-categoria editar"
                                        title="Editar">

                                    ✎

                                </button>


                                <button class="accion-categoria eliminar"
                                        title="Eliminar">

                                    ×

                                </button>

                            </div>

                        </div>

                    </article>



                    <!-- CATEGORÍA 5 -->

                    <article class="categoria-card">

                        <div class="categoria-card-superior">

                            <div class="categoria-icono rojo">
                                ⚠
                            </div>


                            <span class="estado-categoria activa">
                                Activa
                            </span>

                        </div>


                        <div class="categoria-info">

                            <h3>
                                Seguridad
                            </h3>

                            <p>
                                Incidentes relacionados con seguridad
                                y acceso a sistemas.
                            </p>

                        </div>


                        <div class="categoria-footer">

                            <span>
                                <strong>24</strong> tickets
                            </span>


                            <div class="categoria-acciones">

                                <button class="accion-categoria editar"
                                        title="Editar">

                                    ✎

                                </button>


                                <button class="accion-categoria eliminar"
                                        title="Eliminar">

                                    ×

                                </button>

                            </div>

                        </div>

                    </article>



                    <!-- CATEGORÍA 6 -->

                    <article class="categoria-card">

                        <div class="categoria-card-superior">

                            <div class="categoria-icono cyan">
                                ▣
                            </div>


                            <span class="estado-categoria activa">
                                Activa
                            </span>

                        </div>


                        <div class="categoria-info">

                            <h3>
                                Acceso y cuentas
                            </h3>

                            <p>
                                Contraseñas, usuarios, permisos y
                                acceso a plataformas.
                            </p>

                        </div>


                        <div class="categoria-footer">

                            <span>
                                <strong>17</strong> tickets
                            </span>


                            <div class="categoria-acciones">

                                <button class="accion-categoria editar"
                                        title="Editar">

                                    ✎

                                </button>


                                <button class="accion-categoria eliminar"
                                        title="Eliminar">

                                    ×

                                </button>

                            </div>

                        </div>

                    </article>


                </div>


            </section>



        </main>



        <!-- ==================================================
             MODAL NUEVA CATEGORÍA
        ================================================== -->

        <div class="modal-overlay"
             id="modalCategoria">

            <div class="modal-categoria">


                <button class="modal-cerrar"
                        onclick="cerrarModalCategoria()">

                    ×

                </button>


                <div class="modal-icono">
                    ◇
                </div>


                <h2>
                    Nueva categoría
                </h2>


                <p>
                    Registra una nueva categoría para organizar
                    los tickets de soporte.
                </p>


                <form action="${pageContext.request.contextPath}/categorias"
                      method="post">


                    <div class="campo-modal">

                        <label>
                            Nombre de la categoría
                        </label>

                        <input type="text"
                               name="nombreCategoria"
                               placeholder="Ej. Telefonía"
                               maxlength="80"
                               required>

                    </div>


                    <div class="modal-botones">

                        <button type="button"
                                class="btn-cancelar"
                                onclick="cerrarModalCategoria()">

                            Cancelar

                        </button>


                        <button type="submit"
                                class="btn-guardar">

                            Guardar categoría

                        </button>

                    </div>


                </form>


            </div>

        </div>



        <script>

            function abrirModalCategoria() {

                document
                        .getElementById("modalCategoria")
                        .classList.add("mostrar");

            }


            function cerrarModalCategoria() {

                document
                        .getElementById("modalCategoria")
                        .classList.remove("mostrar");

            }


            function filtrarCategorias() {

                const texto =
                        document
                        .getElementById("buscarCategoria")
                        .value
                        .toLowerCase();


                const categorias =
                        document
                        .querySelectorAll(".categoria-card");


                categorias.forEach(function (categoria) {

                    const nombre =
                            categoria
                            .querySelector("h3")
                            .textContent
                            .toLowerCase();


                    if (nombre.includes(texto)) {

                        categoria.style.display = "";

                    } else {

                        categoria.style.display = "none";

                    }

                });

            }


            document
                    .getElementById("modalCategoria")
                    .addEventListener("click", function (e) {

                        if (e.target === this) {

                            cerrarModalCategoria();

                        }

                    });

        </script>


    </body>

</html>