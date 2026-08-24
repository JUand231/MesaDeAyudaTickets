<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="es">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <title>Mesa de Ayuda CIMM</title>

        <link rel="stylesheet"
              href="${pageContext.request.contextPath}/css/estilo.css">
    </head>

    <body class="portal-body">

        <main class="portal">


            <!-- CONTENIDO PRINCIPAL -->
            <section class="portal-contenido">

                <!-- LOGO -->
                <div class="portal-logo">

                    <div class="portal-logo-icono">
                        <span>🌐</span>
                    </div>

                    <div class="portal-logo-texto">
                        <strong>MESA DE AYUDA</strong>
                        <small>CIMM · SENA</small>
                    </div>

                </div>


                <!-- ETIQUETA -->
                <div class="portal-etiqueta">
                    <span></span>
                    CENTRO DE SOPORTE CIMM
                </div>


                <!-- TITULO -->
                <h1>
                    Tu soporte,
                    <span>más fácil.</span>
                </h1>


                <p class="portal-descripcion">
                    Un espacio centralizado para registrar, consultar
                    y gestionar tus solicitudes de soporte de manera
                    rápida, organizada y segura.
                </p>


                <!-- BOTÓN -->
                <a href="${pageContext.request.contextPath}/login"
                   class="portal-boton">
                    <span>Login</span>
                </a>


                <!-- CARACTERISTICAS -->
                <div class="portal-caracteristicas">

                    <div class="portal-caracteristica">

                        <div class="caracteristica-icono">
                            🎫
                        </div>

                        <div>
                            <strong>Solicitudes</strong>
                            <span>
                                Registra tus tickets
                            </span>
                        </div>

                    </div>


                    <div class="portal-caracteristica">

                        <div class="caracteristica-icono">
                            🔍
                        </div>

                        <div>
                            <strong>Seguimiento</strong>
                            <span>
                                Consulta el estado
                            </span>
                        </div>

                    </div>


                    <div class="portal-caracteristica">

                        <div class="caracteristica-icono">
                            💡
                        </div>

                        <div>
                            <strong>Atención</strong>
                            <span>
                                Soporte organizado
                            </span>
                        </div>

                    </div>

                </div>

            </section>


            <!-- ILUSTRACIÓN DERECHA -->
            <section class="portal-visual">

                <div class="visual-circulo grande"></div>
                <div class="visual-circulo mediano"></div>

                <div class="visual-card">

                    <div class="visual-icono">
                        🌐
                    </div>

                    <div class="visual-linea"></div>

                    <div class="visual-texto">
                        <strong>Soporte conectado</strong>
                        <span>
                            Todo en un solo lugar
                        </span>
                    </div>

                </div>

            </section>

        </main>

    </body>

</html>