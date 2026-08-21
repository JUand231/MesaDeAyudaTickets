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
import servicio.roles.AccionesSolicitante;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/dashboardSolicitante")
public class DashboardSolicitanteServlet extends HttpServlet {

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

        int idUsuario
                = (Integer) session.getAttribute("idUsuario");

        int idRol
                = (Integer) session.getAttribute("idRol");

        if (idRol != 1) {

            response.sendRedirect(
                    request.getContextPath() + "/login");

            return;
        }

        AccionesSolicitante ticketService
                = (AccionesSolicitante) getServletContext().getAttribute(
                        AppContextListener.TICKET_SERVICE);

        CategoriaRepository categoriaRepository
                = (CategoriaRepository) getServletContext().getAttribute(
                        AppContextListener.CATEGORIA_REPOSITORY);

        PrioridadRepository prioridadRepository
                = (PrioridadRepository) getServletContext().getAttribute(
                        AppContextListener.PRIORIDAD_REPOSITORY);

        UsuarioRepository usuarioRepository
                = (UsuarioRepository) getServletContext().getAttribute(
                        AppContextListener.USUARIO_REPOSITORY);

        NotificacionRepository notificacionRepository
                = (NotificacionRepository) getServletContext().getAttribute(
                        AppContextListener.NOTIFICACION_REPOSITORY);

        List<Ticket> tickets
                = ticketService.listarPorSolicitante(idUsuario);

        List<TicketDTO> ticketsDTO
                = new ArrayList<>();

        for (Ticket ticket : tickets) {

            String nombreCategoria
                    = categoriaRepository
                            .buscarPorId(ticket.getIdCategoria())
                            .map(Categoria::getNombreCategoria)
                            .orElse(
                                    "Categoria #"
                                    + ticket.getIdCategoria());

            String nombrePrioridad
                    = prioridadRepository
                            .buscarPorId(ticket.getIdPrioridad())
                            .map(Prioridad::getTipo)
                            .orElse(
                                    "Prioridad #"
                                    + ticket.getIdPrioridad());

            String nombreSolicitante
                    = usuarioRepository
                            .buscarPorId(
                                    ticket.getIdSolicitante())
                            .map(Usuario::getNombre)
                            .orElse(
                                    "Usuario #"
                                    + ticket.getIdSolicitante());

            String nombreAgente = null;

            if (ticket.getIdAgente() != null) {

                nombreAgente
                        = usuarioRepository
                                .buscarPorId(
                                        ticket.getIdAgente())
                                .map(Usuario::getNombre)
                                .orElse(
                                        "Usuario #"
                                        + ticket.getIdAgente());
            }

            TicketDTO dto
                    = TicketMapper.aDTO(
                            ticket,
                            nombreCategoria,
                            nombrePrioridad,
                            nombreSolicitante,
                            nombreAgente);

            ticketsDTO.add(dto);
        }

        // ==========================================
        // ESTADÍSTICAS
        // ==========================================
        int totalTickets
                = ticketsDTO.size();

        int pendientes = 0;
        int enProceso = 0;
        int resueltos = 0;

        for (TicketDTO ticket : ticketsDTO) {

            String estado
                    = ticket.getEstado();

            if (estado == null) {
                continue;
            }

            if (estado.equalsIgnoreCase("NUEVO")
                    || estado.equalsIgnoreCase("PENDIENTE")
                    || estado.equalsIgnoreCase("ASIGNADO")) {

                pendientes++;

            } else if (estado.equalsIgnoreCase("EN_PROCESO")
                    || estado.equalsIgnoreCase("EN PROCESO")) {

                enProceso++;

            } else if (estado.equalsIgnoreCase("RESUELTO")) {

                resueltos++;
            }
        }

        // ==========================================
        // NOTIFICACIONES
        // ==========================================
        int notificacionesNoLeidas
                = notificacionRepository.contarNoLeidas(idUsuario);

        request.setAttribute(
                "ticketsRecientes",
                ticketsDTO);

        request.setAttribute(
                "totalTickets",
                totalTickets);

        request.setAttribute(
                "pendientes",
                pendientes);

        request.setAttribute(
                "enProceso",
                enProceso);

        request.setAttribute(
                "resueltos",
                resueltos);

        request.setAttribute(
                "notificacionesNoLeidas",
                notificacionesNoLeidas);

        request.getRequestDispatcher(
                "/WEB-INF/jsp/Solicitante/dashboardSolicitante.jsp")
                .forward(request, response);
    }
}
