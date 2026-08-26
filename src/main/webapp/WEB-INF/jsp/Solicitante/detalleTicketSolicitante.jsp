<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>

<html lang="es">

    <head>

        <meta charset="UTF-8">

        <meta name="viewport"
              content="width=device-width, initial-scale=1.0">

        <title>
            Detalle Ticket | Mesa de Ayuda
        </title>

        <link rel="stylesheet"
              href="${pageContext.request.contextPath}/css/estilo.css">

        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

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

                    <strong>MESA DE AYUDA</strong>

                    <small>CIMM · SENA</small>

                </div>

            </div>

            <div class="menu-titulo">
                PRINCIPAL
            </div>

            <nav class="menu">

                <a href="${pageContext.request.contextPath}/dashboardSolicitante"
                   class="menu-item">

                    <span>⌂</span>

                    <label>Dashboard</label>

                </a>

                <a href="${pageContext.request.contextPath}/tickets"
                   class="menu-item activo">

                    <span>▣</span>

                    <label>Mis tickets</label>

                </a>

            </nav>

            <div class="menu-titulo">
                SISTEMA
            </div>

            <nav class="menu">

                <a href="${pageContext.request.contextPath}/perfil"
                   class="menu-item">

                    <span>⚙</span>

                    <label>Mi Perfil</label>

                </a>

                <a href="${pageContext.request.contextPath}/CerrarSesion"
                   class="menu-item">

                    <span>↪</span>

                    <label>Cerrar sesión</label>

                </a>

            </nav>

            <div class="usuario-sidebar">

                <div class="usuario-avatar">
                    S
                </div>

                <div>

                    <strong>Solicitante</strong>

                    <small>
                        Usuario
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
                        Detalle del ticket
                    </h1>

                    <p>
                        Consulta la información de tu solicitud.
                    </p>

                </div>

                <div class="barra-acciones">

                    <a href="${pageContext.request.contextPath}/tickets"
                       class="btn-principal">

                        ← Volver a mis tickets

                    </a>

                </div>

            </header>


            <!-- ==================================================
                 INFORMACIÓN DEL TICKET
                 ================================================== -->

            <section class="panel">

                <div class="panel-header">

                    <div>

                        <h2>
                            #TK-${ticket.idTicket}
                        </h2>

                        <p>
                            ${ticket.titulo}
                        </p>

                    </div>

                    <div>

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

                            <c:when test="${ticket.estado == 'CANCELADO'}">

                                <span class="badge estado-cancelado">
                                    CANCELADO
                                </span>

                            </c:when>

                            <c:otherwise>

                                <span class="badge">
                                    ${ticket.estado}
                                </span>

                            </c:otherwise>

                        </c:choose>

                    </div>

                </div>


                <!-- ==================================================
                     DATOS DEL TICKET
                     ================================================== -->

                <div style="
                     padding:25px;
                     ">

                    <div style="
                         display:grid;
                         grid-template-columns:repeat(2, 1fr);
                         gap:20px;
                         margin-bottom:25px;
                         ">

                        <div>

                            <strong>
                                Categoría
                            </strong>

                            <p>
                                ${ticket.nombreCategoria}
                            </p>

                        </div>


                        <div>

                            <strong>
                                Prioridad
                            </strong>

                            <p>
                                ${ticket.nombrePrioridad}
                            </p>

                        </div>


                        <div>

                            <strong>
                                Solicitante
                            </strong>

                            <p>
                                ${ticket.nombreSolicitante}
                            </p>

                        </div>


                        <div>

                            <strong>
                                Agente asignado
                            </strong>

                            <p>
                                ${ticket.nombreAgente}
                            </p>

                        </div>


                        <div>

                            <strong>
                                Fecha de creación
                            </strong>

                            <p>
                                ${ticket.fechaCreacion}
                            </p>

                        </div>


                        <div>

                            <strong>
                                Tiempo máximo de atención
                            </strong>

                            <p>
                                ${horasSLA} horas
                            </p>

                        </div>


                        <div>

                            <strong>
                                Fecha límite
                            </strong>

                            <p>
                                ${fechaLimiteSLA}
                            </p>

                        </div>


                        <div>

                            <strong>
                                Estado
                            </strong>

                            <p>

                                <c:choose>

                                    <c:when test="${slaVencido}">
                                        VENCIDO
                                    </c:when>

                                    <c:otherwise>
                                        DENTRO DEL TIEMPO MAXIMO
                                    </c:otherwise>

                                </c:choose>

                            </p>

                        </div>

                    </div>


                    <!-- ==================================================
                         DESCRIPCIÓN
                         ================================================== -->

                    <div>

                        <strong>
                            Descripción
                        </strong>

                        <div style="
                             margin-top:10px;
                             padding:15px;
                             background:#f8fafc;
                             border-radius:10px;
                             border:1px solid #e2e8f0;
                             ">

                            ${ticket.descripcion}

                        </div>

                    </div>

                </div>

            </section>


            <!-- ==================================================
                 ESTADO
                 ================================================== -->

            <section class="panel">

                <div class="panel-header">

                    <div>

                        <h2>
                            Estado de la solicitud
                        </h2>

                        <p>
                            Consulta el estado actual de tu ticket.
                        </p>

                    </div>

                </div>

                <div style="
                     padding:25px;
                     ">

                    <c:choose>

                        <c:when test="${ticket.estado == 'NUEVO'}">

                            <div style="
                                 padding:15px;
                                 background:#f8fafc;
                                 border:1px solid #e2e8f0;
                                 border-radius:10px;
                                 ">

                                Tu solicitud fue registrada y está
                                pendiente de atención.

                            </div>

                        </c:when>


                        <c:when test="${ticket.estado == 'ASIGNADO'}">

                            <div style="
                                 padding:15px;
                                 background:#f8fafc;
                                 border:1px solid #e2e8f0;
                                 border-radius:10px;
                                 ">

                                Tu solicitud fue asignada a un agente
                                de soporte.

                            </div>

                        </c:when>


                        <c:when test="${ticket.estado == 'EN_PROCESO'}">

                            <div style="
                                 padding:15px;
                                 background:#f8fafc;
                                 border:1px solid #e2e8f0;
                                 border-radius:10px;
                                 ">

                                Un agente está trabajando actualmente
                                en tu solicitud.

                            </div>

                        </c:when>


                        <c:when test="${ticket.estado == 'RESUELTO'}">

                            <div style="
                                 padding:15px;
                                 background:#f0fff4;
                                 border:1px solid #c6f6d5;
                                 border-radius:10px;
                                 margin-bottom:15px;
                                 ">

                                Tu solicitud ha sido marcada como
                                resuelta. Confirma si quedó bien o,
                                si el problema sigue, reábrela.

                            </div>


                            <div style="
                                 display:flex;
                                 gap:12px;
                                 flex-wrap:wrap;
                                 ">

                                <c:choose>

                                    <c:when test="${!otpPendienteCierre}">

                                        <form method="post"
                                              id="formSolicitarOtp"
                                              action="${pageContext.request.contextPath}/detalleTicket">

                                            <input type="hidden"
                                                   name="id"
                                                   value="${ticket.idTicket}">

                                            <input type="hidden"
                                                   name="accion"
                                                   value="solicitarCierreOtp">

                                            <button type="button"
                                                    class="btn-principal"
                                                    style="border:none; cursor:pointer;"
                                                    onclick="confirmarSolicitarOtp();">

                                                ✓ Confirmar y cerrar

                                            </button>

                                        </form>

                                    </c:when>

                                    <c:otherwise>

                                        <div style="
                                             padding:12px 15px;
                                             background:#fffbea;
                                             border:1px solid #fde68a;
                                             border-radius:10px;
                                             width:100%;
                                             ">

                                            Te enviamos un código de 6 dígitos
                                            a tus notificaciones. Ingrésalo
                                            para confirmar el cierre (vence
                                            en 10 minutos).

                                        </div>


                                        <form method="post"
                                              id="formCerrarTicket"
                                              action="${pageContext.request.contextPath}/detalleTicket">

                                            <input type="hidden"
                                                   name="id"
                                                   value="${ticket.idTicket}">

                                            <input type="hidden"
                                                   name="accion"
                                                   value="cerrar">

                                            <input type="hidden"
                                                   name="otp"
                                                   id="otpIngresado">

                                            <button type="button"
                                                    class="btn-principal"
                                                    style="border:none; cursor:pointer;"
                                                    onclick="pedirCodigoCierre();">

                                                ✓ Ingresar código y cerrar

                                            </button>

                                        </form>


                                        <form method="post"
                                              id="formReenviarOtp"
                                              action="${pageContext.request.contextPath}/detalleTicket">

                                            <input type="hidden"
                                                   name="id"
                                                   value="${ticket.idTicket}">

                                            <input type="hidden"
                                                   name="accion"
                                                   value="solicitarCierreOtp">

                                            <button type="button"
                                                    style="
                                                    padding:10px 18px;
                                                    border-radius:8px;
                                                    border:1px solid #d9e0e8;
                                                    background:#ffffff;
                                                    color:#334155;
                                                    cursor:pointer;
                                                    font-weight:600;
                                                    "
                                                    onclick="document.getElementById('formReenviarOtp').submit();">

                                                ↻ Reenviar código

                                            </button>

                                        </form>

                                    </c:otherwise>

                                </c:choose>


                                <!-- REABRIR -->
                                <form method="post"
                                      id="formReabrirTicket"
                                      action="${pageContext.request.contextPath}/detalleTicket">

                                    <input type="hidden"
                                           name="id"
                                           value="${ticket.idTicket}">

                                    <input type="hidden"
                                           name="accion"
                                           value="reabrir">

                                    <button type="button"
                                            style="
                                            padding:10px 18px;
                                            border-radius:8px;
                                            border:1px solid #d9e0e8;
                                            background:#ffffff;
                                            color:#334155;
                                            cursor:pointer;
                                            font-weight:600;
                                            "
                                            onclick="confirmarReabrirTicket();">

                                        ↺ No quedó resuelto, reabrir

                                    </button>

                                </form>

                            </div>

                        </c:when>


                        <c:when test="${ticket.estado == 'CANCELADO'}">

                            <div style="
                                 padding:15px;
                                 background:#fff5f5;
                                 border:1px solid #fed7d7;
                                 border-radius:10px;
                                 ">

                                Esta solicitud fue cancelada.

                            </div>

                        </c:when>


                        <c:when test="${ticket.estado == 'CERRADO'}">

                            <div style="
                                 padding:15px;
                                 background:#f8fafc;
                                 border:1px solid #e2e8f0;
                                 border-radius:10px;
                                 color:#718096;
                                 ">

                                Este ticket está cerrado. Ya quedó
                                como quedó: no admite más comentarios
                                ni cambios.

                            </div>

                        </c:when>


                        <c:otherwise>

                            <div style="
                                 padding:15px;
                                 background:#f8fafc;
                                 border:1px solid #e2e8f0;
                                 border-radius:10px;
                                 ">

                                Estado actual:
                                <strong>${ticket.estado}</strong>

                            </div>

                        </c:otherwise>

                    </c:choose>

                </div>

            </section>


            <!-- ==================================================
                 COMENTARIOS
                 ================================================== -->

            <section class="panel">

                <div class="panel-header">

                    <div>

                        <h2>
                            Comentarios
                        </h2>

                        <p>
                            Comunicación con el equipo de soporte.
                        </p>

                    </div>

                </div>


                <div style="
                     padding:25px;
                     ">


                    <c:if test="${empty comentarios}">

                        <div style="
                             padding:20px;
                             text-align:center;
                             color:#718096;
                             ">

                            Todavía no hay comentarios
                            en este ticket.

                        </div>

                    </c:if>


                    <c:forEach var="comentario"
                               items="${comentarios}">

                        <div style="
                             padding:16px;
                             margin-bottom:15px;
                             border:1px solid #e2e8f0;
                             border-radius:10px;
                             background:#ffffff;
                             ">

                            <div style="
                                 display:flex;
                                 justify-content:space-between;
                                 align-items:center;
                                 margin-bottom:8px;
                                 ">

                                <div style="display:flex; align-items:center; gap:8px;">

                                    <strong>
                                        ${comentario.nombreUsuario}
                                    </strong>

                                    <c:choose>

                                        <c:when test="${comentario.nombreRol == 'ADMINISTRADOR'}">
                                            <span class="badge estado-resuelto">ADMINISTRADOR</span>
                                        </c:when>

                                        <c:when test="${comentario.nombreRol == 'AGENTE'}">
                                            <span class="badge estado-proceso">AGENTE</span>
                                        </c:when>

                                        <c:otherwise>
                                            <span class="badge estado-nuevo">SOLICITANTE</span>
                                        </c:otherwise>

                                    </c:choose>

                                </div>

                                <small style="
                                       color:#718096;
                                       ">

                                    ${comentario.fecha}

                                </small>

                            </div>


                            <p style="
                               margin:0;
                               color:#334155;
                               ">

                                ${comentario.texto}

                            </p>

                        </div>

                    </c:forEach>


                    <!-- ==================================================
                         NUEVO COMENTARIO
                         ================================================== -->

                    <c:if test="${ticket.estado != 'CERRADO'}">

                        <div style="
                             margin-top:25px;
                             ">

                            <h3>
                                Agregar comentario
                            </h3>


                            <form method="post"
                                  action="${pageContext.request.contextPath}/detalleTicket">

                                <input type="hidden"
                                       name="id"
                                       value="${ticket.idTicket}">

                                <input type="hidden"
                                       name="accion"
                                       value="comentar">


                                <textarea
                                    name="texto"
                                    rows="4"
                                    required
                                    placeholder="Escribe un comentario para el equipo de soporte..."
                                    style="
                                    width:100%;
                                    box-sizing:border-box;
                                    padding:12px;
                                    border:1px solid #d9e0e8;
                                    border-radius:8px;
                                    resize:vertical;
                                    font-family:inherit;
                                    margin-bottom:10px;
                                    "></textarea>


                                <button type="submit"
                                        class="btn-principal"
                                        style="
                                        border:none;
                                        cursor:pointer;
                                        ">

                                    Agregar comentario

                                </button>

                            </form>

                        </div>

                    </c:if>

                    <c:if test="${ticket.estado == 'CERRADO'}">

                        <div style="
                             margin-top:25px;
                             padding:15px;
                             text-align:center;
                             color:#718096;
                             background:#f8fafc;
                             border:1px solid #e2e8f0;
                             border-radius:10px;
                             ">

                            Este ticket está cerrado, ya no se pueden
                            agregar más comentarios.

                        </div>

                    </c:if>

                </div>

            </section>

        </main>

        <script>

            function confirmarSolicitarOtp() {

                Swal.fire({
                    title: "¿Confirmar y cerrar el ticket?",
                    text: "Te enviaremos un código de 6 dígitos a tus "
                            + "notificaciones para confirmar el cierre. "
                            + "Una vez cerrado, el ticket no se podrá "
                            + "volver a comentar ni modificar.",
                    icon: "question",
                    showCancelButton: true,
                    confirmButtonText: "Sí, enviarme el código",
                    cancelButtonText: "Cancelar",
                    confirmButtonColor: "#2f855a",
                    cancelButtonColor: "#94a3b8"
                }).then(function (resultado) {

                    if (resultado.isConfirmed) {
                        document.getElementById("formSolicitarOtp").submit();
                    }
                });
            }

            function pedirCodigoCierre() {

                Swal.fire({
                    title: "Ingresa el código de cierre",
                    text: "Revisa tus notificaciones, te enviamos un "
                            + "código de 6 dígitos.",
                    icon: "question",
                    input: "text",
                    inputPlaceholder: "Ej: 123456",
                    inputAttributes: {
                        maxlength: 6,
                        autocapitalize: "off",
                        autocorrect: "off"
                    },
                    showCancelButton: true,
                    confirmButtonText: "Confirmar cierre",
                    cancelButtonText: "Cancelar",
                    confirmButtonColor: "#2f855a",
                    cancelButtonColor: "#94a3b8",
                    inputValidator: function (valor) {
                        if (!valor || valor.trim().length !== 6) {
                            return "El código debe tener 6 dígitos.";
                        }
                    }
                }).then(function (resultado) {

                    if (resultado.isConfirmed) {
                        document.getElementById("otpIngresado").value = resultado.value.trim();
                        document.getElementById("formCerrarTicket").submit();
                    }
                });
            }

            function confirmarReabrirTicket() {

                Swal.fire({
                    title: "¿Reabrir este ticket?",
                    text: "El ticket volverá a estado EN PROCESO y se le "
                            + "avisará al agente encargado.",
                    icon: "warning",
                    showCancelButton: true,
                    confirmButtonText: "Sí, reabrir",
                    cancelButtonText: "Cancelar",
                    confirmButtonColor: "#dd6b20",
                    cancelButtonColor: "#94a3b8"
                }).then(function (resultado) {

                    if (resultado.isConfirmed) {
                        document.getElementById("formReabrirTicket").submit();
                    }
                });
            }

        </script>

    </body>

</html>