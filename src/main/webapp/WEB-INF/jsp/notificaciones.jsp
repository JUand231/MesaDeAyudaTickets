<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="modelo.Notificacion" %>

<!DOCTYPE html>
<html lang="es">

    <head>

        <meta charset="UTF-8">

        <title>Notificaciones</title>

        <style>

            body {
                font-family: Arial, sans-serif;
                background: #f4f6f8;
                margin: 0;
                padding: 30px;
            }

            .contenedor {
                max-width: 800px;
                margin: auto;
            }

            .cabecera {
                display: flex;
                justify-content: space-between;
                align-items: center;
                margin-bottom: 20px;
            }

            .notificacion {
                background: white;
                padding: 18px;
                margin-bottom: 12px;
                border-radius: 8px;
                border-left: 5px solid #ccc;
            }

            .no-leida {
                border-left-color: #007bff;
                background: #eef6ff;
            }

            .mensaje {
                font-size: 16px;
                margin-bottom: 8px;
            }

            .fecha {
                color: #777;
                font-size: 13px;
            }

            button {
                background: #007bff;
                color: white;
                border: none;
                padding: 8px 14px;
                border-radius: 5px;
                cursor: pointer;
            }

            .vacio {
                background: white;
                padding: 30px;
                text-align: center;
                border-radius: 8px;
            }

            a {
                text-decoration: none;
                color: #007bff;
            }

        </style>

    </head>

    <body>

        <div class="contenedor">

            <div class="cabecera">

                <div>
                    <h1>Notificaciones</h1>

                    <p>
                        No leídas:
                        <strong>${noLeidas}</strong>
                    </p>
                </div>

                <div>

                    <form method="post"
                          action="${pageContext.request.contextPath}/notificaciones">

                        <input type="hidden"
                               name="accion"
                               value="leerTodas">

                        <button type="submit">
                            Marcar todas como leídas
                        </button>

                    </form>

                </div>

            </div>


            <%
                List<Notificacion> notificaciones
                        = (List<Notificacion>) request.getAttribute("notificaciones");

                if (notificaciones == null || notificaciones.isEmpty()) {
            %>

            <div class="vacio">
                No tienes notificaciones.
            </div>

            <%
            } else {

                for (Notificacion n : notificaciones) {
            %>

            <div class="notificacion <%= !n.isLeida() ? "no-leida" : ""%>">

                <div class="mensaje">

                    <%= n.getMensaje()%>

                </div>

                <div class="fecha">

                    Ticket #<%= n.getIdTicket()%>
                    -
                    <%= n.getFecha()%>

                </div>

                <% if (!n.isLeida()) {%>

                <form method="post"
                      action="${pageContext.request.contextPath}/notificaciones"
                      style="margin-top:10px;">

                    <input type="hidden"
                           name="accion"
                           value="leer">

                    <input type="hidden"
                           name="idNotificacion"
                           value="<%= n.getIdNotificacion()%>">

                    <button type="submit">
                        Marcar como leída
                    </button>

                </form>

                <% } else { %>

                <span style="color:green;">
                    Leída
                </span>

                <% } %>

            </div>

            <%
                    }
                }
            %>

            <br>

            <a href="${pageContext.request.contextPath}${dashboard}">
                ← Volver
            </a>

        </div>

    </body>

</html>