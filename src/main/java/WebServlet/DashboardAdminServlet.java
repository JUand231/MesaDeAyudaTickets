package WebServlet;

import dto.TicketDTO;
import mapper.TicketMapper;
import modelo.Categoria;
import modelo.Prioridad;
import modelo.Ticket;
import modelo.Usuario;
import repositorio.CategoriaRepository;
import repositorio.PrioridadRepository;
import repositorio.UsuarioRepository;
import repositorio.NotificacionRepository;
import servicio.roles.AccionesAdministrador;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/dashboardAdmin")
public class DashboardAdminServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        // ==========================================
        // VERIFICAR SESIÓN
        // ==========================================
        HttpSession session = request.getSession(false);

        if (session == null
                || session.getAttribute("idUsuario") == null) {

            response.sendRedirect(
                    request.getContextPath() + "/login");

            return;
        }

        int idUsuario
                = (Integer) session.getAttribute("idUsuario");

        int idRol
                = (Integer) session.getAttribute("idRol");

        // ==========================================
        // VERIFICAR QUE SEA ADMINISTRADOR
        // ==========================================
        if (idRol != 3) {

            response.sendRedirect(
                    request.getContextPath() + "/login");

            return;
        }

        // ==========================================
        // OBTENER SERVICIOS Y REPOSITORIOS
        // ==========================================
        AccionesAdministrador ticketService
                = (AccionesAdministrador) getServletContext()
                        .getAttribute(
                                AppContextListener.TICKET_SERVICE);

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

        NotificacionRepository notificacionRepository
                = (NotificacionRepository) getServletContext()
                        .getAttribute(
                                AppContextListener.NOTIFICACION_REPOSITORY);

        // ==========================================
        // TODOS LOS TICKETS
        // ==========================================
        List<Ticket> tickets
                = ticketService.listarTodos();

        // ==========================================
        // ESTADÍSTICAS
        // ==========================================
        int total = tickets.size();

        int pendientes = 0;

        int resueltos = 0;

        int criticos = 0;

        for (Ticket ticket : tickets) {

            String estado
                    = ticket.getEstadoNombre();

            if (estado == null) {
                continue;
            }

            estado = estado.trim();

            boolean esFinal
                    = estado.equalsIgnoreCase("RESUELTO")
                    || estado.equalsIgnoreCase("CERRADO")
                    || estado.equalsIgnoreCase("CANCELADO");

            // Tickets que todavía requieren atención
            if (!esFinal) {
                pendientes++;
            }

            // Tickets resueltos o cerrados
            if (estado.equalsIgnoreCase("RESUELTO")
                    || estado.equalsIgnoreCase("CERRADO")) {

                resueltos++;
            }

            // ======================================
            // TICKETS CRÍTICOS
            // ======================================
            Prioridad prioridad
                    = prioridadRepository
                            .buscarPorId(
                                    ticket.getIdPrioridad())
                            .orElse(null);

            if (prioridad != null
                    && "CRITICA".equalsIgnoreCase(
                            prioridad.getTipo())
                    && !esFinal) {

                criticos++;
            }
        }

        // ==========================================
        // PORCENTAJE DE RESOLUCIÓN
        // ==========================================
        int porcentajeResolucion
                = total == 0
                        ? 0
                        : (resueltos * 100) / total;

        // ==========================================
        // TICKETS MÁS RECIENTES
        // ==========================================
        List<Ticket> masRecientes
                = new ArrayList<>(tickets);

        masRecientes.sort(
                Comparator.comparing(
                        Ticket::getFechaCreacion)
                        .reversed()
        );

        if (masRecientes.size() > 4) {

            masRecientes
                    = new ArrayList<>(
                            masRecientes.subList(0, 4)
                    );
        }

        // ==========================================
        // CONVERTIR A DTO
        // ==========================================
        List<TicketDTO> ticketsRecientes
                = new ArrayList<>();

        for (Ticket ticket : masRecientes) {

            String nombreCategoria
                    = categoriaRepository
                            .buscarPorId(
                                    ticket.getIdCategoria())
                            .map(
                                    Categoria::getNombreCategoria)
                            .orElse(
                                    "Categoria #"
                                    + ticket.getIdCategoria()
                            );

            String nombrePrioridad
                    = prioridadRepository
                            .buscarPorId(
                                    ticket.getIdPrioridad())
                            .map(
                                    Prioridad::getTipo)
                            .orElse(
                                    "Prioridad #"
                                    + ticket.getIdPrioridad()
                            );

            String nombreSolicitante
                    = usuarioRepository
                            .buscarPorId(
                                    ticket.getIdSolicitante())
                            .map(
                                    Usuario::getNombre)
                            .orElse(
                                    "Usuario #"
                                    + ticket.getIdSolicitante()
                            );

            String nombreAgente = null;

            if (ticket.getIdAgente() != null) {

                nombreAgente
                        = usuarioRepository
                                .buscarPorId(
                                        ticket.getIdAgente())
                                .map(
                                        Usuario::getNombre)
                                .orElse(
                                        "Usuario #"
                                        + ticket.getIdAgente()
                                );
            }

            ticketsRecientes.add(
                    TicketMapper.aDTO(
                            ticket,
                            nombreCategoria,
                            nombrePrioridad,
                            nombreSolicitante,
                            nombreAgente
                    )
            );
        }

        // ==========================================
        // NOTIFICACIONES DEL ADMIN
        // ==========================================
        int notificacionesNoLeidas
                = notificacionRepository
                        .contarNoLeidas(idUsuario);

        // ==========================================
        // ENVIAR DATOS AL JSP
        // ==========================================
        request.setAttribute(
                "totalTickets",
                total);

        request.setAttribute(
                "pendientes",
                pendientes);

        request.setAttribute(
                "resueltos",
                resueltos);

        request.setAttribute(
                "criticos",
                criticos);

        request.setAttribute(
                "porcentajeResolucion",
                porcentajeResolucion);

        request.setAttribute(
                "ticketsRecientes",
                ticketsRecientes);

        request.setAttribute(
                "notificacionesNoLeidas",
                notificacionesNoLeidas);

        // ==========================================
        // MOSTRAR DASHBOARD
        // ==========================================
        request.getRequestDispatcher(
                "/WEB-INF/jsp/Administrador/dashboardAdmin.jsp")
                .forward(request, response);
    }
}
