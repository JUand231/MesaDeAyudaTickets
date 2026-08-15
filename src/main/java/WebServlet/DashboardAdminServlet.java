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
import servicio.TicketService;
import java.io.IOException;
import java.sql.SQLException;
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
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("idUsuario") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        int idUsuario = (Integer) session.getAttribute("idUsuario");
        int idRol = (Integer) session.getAttribute("idRol");

        TicketService ticketService = (TicketService) getServletContext().getAttribute(AppContextListener.TICKET_SERVICE);
        CategoriaRepository categoriaRepository = (CategoriaRepository) getServletContext().getAttribute(AppContextListener.CATEGORIA_REPOSITORY);
        PrioridadRepository prioridadRepository = (PrioridadRepository) getServletContext().getAttribute(AppContextListener.PRIORIDAD_REPOSITORY);
        UsuarioRepository usuarioRepository = (UsuarioRepository) getServletContext().getAttribute(AppContextListener.USUARIO_REPOSITORY);

        List<Ticket> tickets;
        switch (idRol) {
            case 1:
                tickets = ticketService.listarPorSolicitante(idUsuario);
                break;
            case 2:
                tickets = ticketService.listarPorAgente(idUsuario);
                break;
            default:
                tickets = ticketService.listarTodos();
                break;
        }

        try {
            int total = tickets.size();
            int pendientes = 0;
            int resueltos = 0;
            int criticos = 0;

            for (Ticket ticket : tickets) {
                String estado = ticket.getEstadoNombre();
                boolean esFinal = estado.equals("RESUELTO") || estado.equals("CERRADO")
                        || estado.equals("CANCELADO");

                if (!esFinal) {
                    pendientes++;
                }
                if (estado.equals("RESUELTO") || estado.equals("CERRADO")) {
                    resueltos++;
                }

                Prioridad prioridad = prioridadRepository.buscarPorId(ticket.getIdPrioridad()).orElse(null);
                if (prioridad != null && "CRITICA".equals(prioridad.getTipo()) && !esFinal) {
                    criticos++;
                }
            }

            int porcentajeResolucion = total == 0 ? 0 : (resueltos * 100) / total;

            // Los 4 tickets mas recientes, ordenados por fecha de creacion descendente
            List<Ticket> masRecientes = new ArrayList<>(tickets);
            masRecientes.sort(Comparator.comparing(Ticket::getFechaCreacion).reversed());
            if (masRecientes.size() > 4) {
                masRecientes = masRecientes.subList(0, 4);
            }

            List<TicketDTO> ticketsRecientes = new ArrayList<>();
            for (Ticket ticket : masRecientes) {
                String nombreCategoria = categoriaRepository.buscarPorId(ticket.getIdCategoria())
                        .map(Categoria::getNombreCategoria)
                        .orElse("Categoria #" + ticket.getIdCategoria());

                String nombrePrioridad = prioridadRepository.buscarPorId(ticket.getIdPrioridad())
                        .map(Prioridad::getTipo)
                        .orElse("Prioridad #" + ticket.getIdPrioridad());

                String nombreSolicitante = usuarioRepository.buscarPorId(ticket.getIdSolicitante())
                        .map(Usuario::getNombre)
                        .orElse("Usuario #" + ticket.getIdSolicitante());

                String nombreAgente = null;
                if (ticket.getIdAgente() != null) {
                    nombreAgente = usuarioRepository.buscarPorId(ticket.getIdAgente())
                            .map(Usuario::getNombre)
                            .orElse("Usuario #" + ticket.getIdAgente());
                }

                ticketsRecientes.add(TicketMapper.aDTO(ticket, nombreCategoria, nombrePrioridad,
                        nombreSolicitante, nombreAgente));
            }

            request.setAttribute("totalTickets", total);
            request.setAttribute("pendientes", pendientes);
            request.setAttribute("resueltos", resueltos);
            request.setAttribute("criticos", criticos);
            request.setAttribute("porcentajeResolucion", porcentajeResolucion);
            request.setAttribute("ticketsRecientes", ticketsRecientes);

        } catch (SQLException e) {
            throw new ServletException("Error calculando estadisticas del dashboard", e);
        }

        request.getRequestDispatcher("/WEB-INF/jsp/Administrador/dashboardAdmin.jsp")
                .forward(request, response);

    }
}
