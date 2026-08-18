package WebServlet;

import modelo.Categoria;
import repositorio.CategoriaRepository;
import modelo.Ticket;
import modelo.Prioridad;
import modelo.Usuario;
import repositorio.PrioridadRepository;
import repositorio.UsuarioRepository;
import servicio.TicketService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/dashboardAgente")
public class DashboardAgenteServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null
                || session.getAttribute("idUsuario") == null) {

            response.sendRedirect(
                    request.getContextPath() + "/login");

            return;
        }

        int idUsuario = (Integer) session.getAttribute("idUsuario");
        int idRol = (Integer) session.getAttribute("idRol");

        if (idRol != 2) {

            response.sendRedirect(
                    request.getContextPath() + "/login");

            return;
        }

        CategoriaRepository categoriaRepository
                = (CategoriaRepository) getServletContext()
                        .getAttribute(
                                AppContextListener.CATEGORIA_REPOSITORY);

        PrioridadRepository prioridadRepository
                = (PrioridadRepository) getServletContext()
                        .getAttribute(
                                AppContextListener.PRIORIDAD_REPOSITORY);

        UsuarioRepository usuarioRepository
                = (UsuarioRepository) getServletContext()
                        .getAttribute(
                                AppContextListener.USUARIO_REPOSITORY);

        TicketService ticketService
                = (TicketService) getServletContext()
                        .getAttribute(
                                AppContextListener.TICKET_SERVICE);

        List<Ticket> tickets
                = ticketService.listarPorAgente(idUsuario);

        Map<Integer, String> nombresCategorias = new HashMap<>();
        Map<Integer, String> nombresPrioridades = new HashMap<>();
        Map<Integer, String> nombresUsuarios = new HashMap<>();

        // =========================================================
        // CATEGORÍAS
        // =========================================================
        for (Ticket ticket : tickets) {

            int idCategoria = ticket.getIdCategoria();

            if (!nombresCategorias.containsKey(idCategoria)) {

                String nombre = "Sin categoría";

                nombre = categoriaRepository
                        .buscarPorId(idCategoria)
                        .map(Categoria::getNombreCategoria)
                        .orElse("Sin categoría");

                nombresCategorias.put(idCategoria, nombre);
            }
        }

        // =========================================================
        // PRIORIDADES
        // =========================================================
        for (Ticket ticket : tickets) {

            int idPrioridad = ticket.getIdPrioridad();

            if (!nombresPrioridades.containsKey(idPrioridad)) {

                String nombre = "Sin prioridad";


                    nombre = prioridadRepository
                            .buscarPorId(idPrioridad)
                            .map(Prioridad::getTipo)
                            .orElse("Sin prioridad");

                nombresPrioridades.put(idPrioridad, nombre);
            }
        }

        // =========================================================
        // SOLICITANTES
        // =========================================================
        for (Ticket ticket : tickets) {

            int idSolicitante = ticket.getIdSolicitante();

            if (!nombresUsuarios.containsKey(idSolicitante)) {

                String nombre = "Sin solicitante";

                nombre = usuarioRepository
                        .buscarPorId(idSolicitante)
                        .map(Usuario::getNombre)
                        .orElse("Sin solicitante");

                nombresUsuarios.put(idSolicitante, nombre);
            }
        }

        // =========================================================
        // ESTADÍSTICAS
        // =========================================================
        int totalTickets = tickets.size();

        int pendientes = 0;
        int enProceso = 0;
        int resueltos = 0;

        for (Ticket ticket : tickets) {

            String estado = ticket.getEstadoNombre();

            if (estado == null) {
                continue;
            }

            estado = estado.trim();

            if (estado.equalsIgnoreCase("NUEVO")
                    || estado.equalsIgnoreCase("ASIGNADO")
                    || estado.equalsIgnoreCase("PENDIENTE")) {

                pendientes++;

            } else if (estado.equalsIgnoreCase("EN_PROCESO")) {

                enProceso++;

            } else if (estado.equalsIgnoreCase("RESUELTO")) {

                resueltos++;
            }
        }

        // =========================================================
        // ENVIAR DATOS AL JSP
        // =========================================================
        request.setAttribute(
                "tickets",
                tickets
        );

        request.setAttribute(
                "nombresCategorias",
                nombresCategorias
        );

        request.setAttribute(
                "nombresPrioridades",
                nombresPrioridades
        );

        request.setAttribute(
                "nombresUsuarios",
                nombresUsuarios
        );

        request.setAttribute(
                "totalTickets",
                totalTickets
        );

        request.setAttribute(
                "pendientes",
                pendientes
        );

        request.setAttribute(
                "enProceso",
                enProceso
        );

        request.setAttribute(
                "resueltos",
                resueltos
        );

        // =========================================================
        // MOSTRAR DASHBOARD
        // =========================================================
        request.getRequestDispatcher(
                "/WEB-INF/jsp/Agente/dashboardAgente.jsp"
        ).forward(request, response);
    }
}
