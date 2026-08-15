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
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/tickets")
public class TicketsAdminServlet extends HttpServlet {

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

        // ==================================================
        // FILTROS
        // ==================================================

        String buscar = request.getParameter("buscar");
        String estado = request.getParameter("estado");
        String prioridad = request.getParameter("prioridad");
        String categoria = request.getParameter("categoria");

        List<TicketDTO> ticketsFiltrados = new ArrayList<>();

        for (TicketDTO dto : ticketsDTO) {

            if (buscar != null && !buscar.trim().isEmpty()) {
                String textoBusqueda = buscar.trim().toLowerCase();
                String titulo = dto.getTitulo() != null ? dto.getTitulo().toLowerCase() : "";
                String descripcion = dto.getDescripcion() != null ? dto.getDescripcion().toLowerCase() : "";
                String idTexto = "tk-" + dto.getIdTicket();

                boolean coincide = titulo.contains(textoBusqueda)
                        || descripcion.contains(textoBusqueda)
                        || idTexto.contains(textoBusqueda)
                        || String.valueOf(dto.getIdTicket()).contains(textoBusqueda);

                if (!coincide) {
                    continue;
                }
            }

            if (estado != null && !estado.trim().isEmpty()
                    && !normalizar(dto.getEstado()).equals(normalizar(estado))) {
                continue;
            }

            if (prioridad != null && !prioridad.trim().isEmpty()
                    && !normalizar(dto.getNombrePrioridad()).equals(normalizar(prioridad))) {
                continue;
            }

            if (categoria != null && !categoria.trim().isEmpty()
                    && !normalizar(dto.getNombreCategoria()).equals(normalizar(categoria))) {
                continue;
            }

            ticketsFiltrados.add(dto);
        }

        request.setAttribute("tickets", ticketsFiltrados);
        request.setAttribute("totalTickets", ticketsFiltrados.size());

        // Para que el formulario recuerde lo que el usuario filtró
        request.setAttribute("filtroBuscar", buscar);
        request.setAttribute("filtroEstado", estado);
        request.setAttribute("filtroPrioridad", prioridad);
        request.setAttribute("filtroCategoria", categoria);

        request.getRequestDispatcher("/WEB-INF/jsp/Administrador/ticketsAdmin.jsp")
                .forward(request, response);
    }

    /**
     * Normaliza texto para poder comparar valores que vienen del formulario
     * (con tildes y espacios) contra los que están guardados en base de
     * datos (en mayúsculas, sin tildes y con guion bajo).
     * Ej: "En proceso" -> "EN_PROCESO", "Crítica" -> "CRITICA"
     */
    private String normalizar(String texto) {
        if (texto == null) {
            return "";
        }
        String sinAcentos = Normalizer.normalize(texto, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");
        return sinAcentos.trim().toUpperCase().replace(" ", "_");
    }
}