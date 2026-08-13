<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="es">

    <head>

        <meta charset="UTF-8">

        <meta name="viewport"
              content="width=device-width, initial-scale=1.0">

        <title>Acceso | Mesa de Ayuda CIMM</title>

        <link rel="stylesheet"
              href="${pageContext.request.contextPath}/css/estilo.css">

    </head>

    <body class="login-body">

        <main class="login-page">

            <section class="login-formulario">

                <div class="login-card">

                    <div class="login-card-header">

                        <div class="login-card-icono">
                            <span>🌐</span>
                        </div>

                        <h2>Bienvenido</h2>

                        <p>
                            Ingresa a tu cuenta para continuar.
                        </p>

                    </div>

                    <%
                        String error = request.getParameter("error");

                        if ("1".equals(error)) {
                    %>

                    <div class="login-alerta error">
                        <span>!</span>
                        Correo o contraseña incorrectos.
                    </div>

                    <%
                        }
                    %>

                    <form action="${pageContext.request.contextPath}/login"
                          method="post"
                          class="login-form">

                        <div class="login-campo">

                            <label for="correo">
                                Correo electrónico
                            </label>

                            <div class="input-icono">

                                <span>✉</span>

                                <input type="email"
                                       id="correo"
                                       name="correo"
                                       placeholder="correo@cimm.edu.co"
                                       autocomplete="username"
                                       required>

                            </div>

                        </div>


                        <div class="login-campo">

                            <div class="label-password">

                                <label for="contrasena">
                                    Contraseña
                                </label>

                                <a href="#">
                                    ¿Olvidaste tu contraseña?
                                </a>

                            </div>


                            <div class="input-icono">

                                <span>⌑</span>

                                <input type="password"
                                       id="contrasena"
                                       name="contrasena"
                                       placeholder="Ingresa tu contraseña"
                                       autocomplete="current-password"
                                       required>

                            </div>

                        </div>


                        <div class="login-opciones">

                            <label class="recordarme">

                                <input type="checkbox"
                                       name="recordarme">

                                <span></span>

                                Recordarme

                            </label>

                        </div>


                        <button type="submit"
                                class="btn-login">

                            <span>
                                Iniciar sesión
                            </span>

                            <strong>
                                →
                            </strong>

                        </button>

                    </form>


                    <div class="login-separador">
                        <span>ACCESO SEGURO</span>
                    </div>


                    <div class="login-seguridad">

                        <span class="seguridad-icono">
                            ✓
                        </span>

                        <div>

                            <strong>
                                Acceso protegido
                            </strong>

                            <small>
                                Tu información se mantiene segura.
                            </small>

                        </div>

                    </div>


                    <p class="login-ayuda">
                        ¿Necesitas ayuda para ingresar?
                        <a href="#">
                            Contacta al administrador
                        </a>
                    </p>

                </div>


                <div class="login-footer">

                    <span>
                        © 2026 Mesa de Ayuda CIMM
                    </span>

                    <span>
                        Sistema de gestión de soporte
                    </span>

                </div>

            </section>

        </main>


        <script>

            function mostrarPassword() {

                const campo =
                        document.getElementById("contrasena");

                const boton =
                        document.querySelector(".mostrar-password");

                if (campo.type === "password") {

                    campo.type = "text";
                    boton.textContent = "◉";

                } else {

                    campo.type = "password";
                    boton.textContent = "◉";

                }

            }

        </script>

    </body>