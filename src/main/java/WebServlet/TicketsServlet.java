package WebServlet;

import dto.TicketDTO;
import mapper.TicketMapper;
import modelo.Categoria;
import modelo.Prioridad;
import modelo.Ticket;
import modelo.Usuario;
import WebServlet.AppContextListener;
import repositorio.CategoriaRepository;
import repositorio.PrioridadRepository;
import repositorio.UsuarioRepository;
import servicio.TicketService;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/tickets")
public class TicketsServlet extends HttpServlet {

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
            case 1: // solicitante: solo los suyos
                tickets = ticketService.listarPorSolicitante(idUsuario);
                break;
            case 2: // agente: solo los asignados a el
                tickets = ticketService.listarPorAgente(idUsuario);
                break;
            default: // administrador: todos
                tickets = ticketService.listarTodos();
                break;
        }

        List<TicketDTO> ticketsDTO = new ArrayList<>();
        try {
            for (Ticket ticket : tickets) {

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

                ticketsDTO.add(TicketMapper.aDTO(ticket, nombreCategoria, nombrePrioridad,
                        nombreSolicitante, nombreAgente));
            }
        } catch (SQLException e) {
            throw new ServletException("Error consultando datos relacionados del ticket", e);
        }

        request.setAttribute("tickets", ticketsDTO);
        request.getRequestDispatcher("/WEB-INF/jsp/Administrador/ticketsAdmin.jsp")
                .forward(request, response);
    }
}
