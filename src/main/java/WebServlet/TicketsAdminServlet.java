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

        TicketService ticketService
                = (TicketService) getServletContext().getAttribute(
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

        if (ticketService == null
                || categoriaRepository == null
                || prioridadRepository == null
                || usuarioRepository == null) {

            throw new ServletException(
                    "Los componentes necesarios no están configurados.");
        }

        List<Ticket> tickets;

        switch (idRol) {

            case 1:
                // SOLICITANTE
                tickets
                        = ticketService.listarPorSolicitante(
                                idUsuario);
                break;

            case 2:
                // AGENTE
                tickets
                        = ticketService.listarPorAgente(
                                idUsuario);
                break;

            default:
                // ADMINISTRADOR
                tickets
                        = ticketService.listarTodos();
                break;
        }

        List<TicketDTO> ticketsDTO
                = new ArrayList<>();

        for (Ticket ticket : tickets) {

            String nombreCategoria
                    = categoriaRepository
                            .buscarPorId(
                                    ticket.getIdCategoria())
                            .map(
                                    Categoria::getNombreCategoria)
                            .orElse(
                                    "Categoria #"
                                    + ticket.getIdCategoria());

            String nombrePrioridad
                    = prioridadRepository
                            .buscarPorId(
                                    ticket.getIdPrioridad())
                            .map(
                                    Prioridad::getTipo)
                            .orElse(
                                    "Prioridad #"
                                    + ticket.getIdPrioridad());

            String nombreSolicitante
                    = usuarioRepository
                            .buscarPorId(
                                    ticket.getIdSolicitante())
                            .map(
                                    Usuario::getNombre)
                            .orElse(
                                    "Usuario #"
                                    + ticket.getIdSolicitante());

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
                                        + ticket.getIdAgente());
            }

            ticketsDTO.add(
                    TicketMapper.aDTO(
                            ticket,
                            nombreCategoria,
                            nombrePrioridad,
                            nombreSolicitante,
                            nombreAgente));
        }

        String buscar
                = request.getParameter("buscar");

        String estado
                = request.getParameter("estado");

        String prioridad
                = request.getParameter("prioridad");

        String categoria
                = request.getParameter("categoria");

        List<TicketDTO> ticketsFiltrados
                = new ArrayList<>();

        for (TicketDTO dto : ticketsDTO) {

            if (buscar != null
                    && !buscar.trim().isEmpty()) {

                String textoBusqueda
                        = buscar.trim().toLowerCase();

                String titulo
                        = dto.getTitulo() != null
                        ? dto.getTitulo().toLowerCase()
                        : "";

                String descripcion
                        = dto.getDescripcion() != null
                        ? dto.getDescripcion().toLowerCase()
                        : "";

                String idTexto
                        = "tk-" + dto.getIdTicket();

                boolean coincide
                        = titulo.contains(textoBusqueda)
                        || descripcion.contains(textoBusqueda)
                        || idTexto.contains(textoBusqueda)
                        || String.valueOf(
                                dto.getIdTicket())
                                .contains(textoBusqueda);

                if (!coincide) {
                    continue;
                }
            }

            if (estado != null
                    && !estado.trim().isEmpty()
                    && !normalizar(
                            dto.getEstado())
                            .equals(
                                    normalizar(estado))) {

                continue;
            }

            if (prioridad != null
                    && !prioridad.trim().isEmpty()
                    && !normalizar(
                            dto.getNombrePrioridad())
                            .equals(
                                    normalizar(prioridad))) {

                continue;
            }

            if (categoria != null
                    && !categoria.trim().isEmpty()
                    && !normalizar(
                            dto.getNombreCategoria())
                            .equals(
                                    normalizar(categoria))) {

                continue;
            }

            ticketsFiltrados.add(dto);
        }

        request.setAttribute(
                "tickets",
                ticketsFiltrados);

        request.setAttribute(
                "totalTickets",
                ticketsFiltrados.size());

        request.setAttribute(
                "filtroBuscar",
                buscar);

        request.setAttribute(
                "filtroEstado",
                estado);

        request.setAttribute(
                "filtroPrioridad",
                prioridad);

        request.setAttribute(
                "filtroCategoria",
                categoria);

        if (idRol != 1 && idRol != 2) {

            try {

                List<Usuario> agentes
                        = usuarioRepository.listarAgentes();

                request.setAttribute(
                        "agentes",
                        agentes);

            } catch (SQLException e) {

                throw new ServletException(
                        "Error consultando los agentes.",
                        e);
            }
        }

        if (idRol == 1) {

            request.getRequestDispatcher(
                    "/WEB-INF/jsp/Solicitante/ticketsSolicitante.jsp")
                    .forward(
                            request,
                            response);

        } else if (idRol == 2) {

            request.getRequestDispatcher(
                    "/WEB-INF/jsp/Agente/ticketsAgente.jsp")
                    .forward(
                            request,
                            response);

        } else {

            request.getRequestDispatcher(
                    "/WEB-INF/jsp/Administrador/ticketsAdmin.jsp")
                    .forward(
                            request,
                            response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session
                = request.getSession(false);

        if (session == null
                || session.getAttribute("idUsuario") == null) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/login");

            return;
        }

        int idRol
                = (Integer) session.getAttribute("idRol");

        if (idRol == 1 || idRol == 2) {

            response.sendError(
                    HttpServletResponse.SC_FORBIDDEN,
                    "No tienes permisos para asignar agentes.");

            return;
        }

        String idTicketStr
                = request.getParameter("idTicket");

        String idAgenteStr
                = request.getParameter("idAgente");

        String tipoAsignacion
                = request.getParameter("tipoAsignacion");

        String accion
                = request.getParameter("accion");

        if (idTicketStr == null
                || idTicketStr.trim().isEmpty()) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/tickets");

            return;
        }

        int idTicket;

        try {

            idTicket = Integer.parseInt(idTicketStr);

        } catch (NumberFormatException e) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "El ID del ticket no es válido.");

            return;
        }

        TicketService ticketService
                = (TicketService) getServletContext()
                        .getAttribute(
                                AppContextListener.TICKET_SERVICE);

        UsuarioRepository usuarioRepository
                = (UsuarioRepository) getServletContext()
                        .getAttribute(
                                AppContextListener.USUARIO_REPOSITORY);

        if (ticketService == null
                || usuarioRepository == null) {

            throw new ServletException(
                    "TicketService o UsuarioRepository no están configurados.");
        }

        try {

            Ticket ticket
                    = ticketService.buscarPorId(idTicket);

            if ("cancelar".equalsIgnoreCase(accion)) {

                // No permitir cancelar un ticket cerrado
                if ("CERRADO".equals(ticket.getEstadoNombre())) {

                    response.sendError(
                            HttpServletResponse.SC_BAD_REQUEST,
                            "Un ticket cerrado no puede ser cancelado."
                    );

                    return;
                }

                int idAdministrador
                        = (Integer) session.getAttribute("idUsuario");

                Usuario administrador
                        = usuarioRepository
                                .buscarPorId(idAdministrador)
                                .orElseThrow(
                                        () -> new IllegalArgumentException(
                                                "No existe el administrador."
                                        )
                                );

                ticketService.cancelar(
                        idTicket,
                        administrador
                );

                response.sendRedirect(
                        request.getContextPath()
                        + "/tickets"
                );

                return;
            }

            if (!"NUEVO".equals(ticket.getEstadoNombre())
                    && !"EN_PROCESO".equals(ticket.getEstadoNombre())
                    && !"ASIGNADO".equals(ticket.getEstadoNombre())) {

                response.sendError(
                        HttpServletResponse.SC_BAD_REQUEST,
                        "Solo se pueden asignar agentes a tickets NUEVO, EN_PROCESO o ASIGNADO.");

                return;
            }

            if ("automatico".equalsIgnoreCase(tipoAsignacion)) {

                List<Usuario> agentesDisponibles
                        = usuarioRepository.listarAgentes();

                if (agentesDisponibles == null
                        || agentesDisponibles.isEmpty()) {

                    response.sendError(
                            HttpServletResponse.SC_BAD_REQUEST,
                            "No hay agentes disponibles para asignar.");

                    return;
                }

                ticketService.asignarAgenteAutomatico(
                        idTicket,
                        agentesDisponibles);

            } else {

                if (idAgenteStr == null
                        || idAgenteStr.trim().isEmpty()) {

                    response.sendError(
                            HttpServletResponse.SC_BAD_REQUEST,
                            "Debes seleccionar un agente.");

                    return;
                }

                int idAgente;

                try {

                    idAgente = Integer.parseInt(idAgenteStr);

                } catch (NumberFormatException e) {

                    response.sendError(
                            HttpServletResponse.SC_BAD_REQUEST,
                            "El ID del agente no es válido.");

                    return;
                }

                Usuario agente
                        = usuarioRepository
                                .buscarPorId(idAgente)
                                .orElseThrow(
                                        ()
                                        -> new IllegalArgumentException(
                                                "No existe el agente seleccionado."));

                if (agente.getIdRol() != 2) {

                    response.sendError(
                            HttpServletResponse.SC_BAD_REQUEST,
                            "El usuario seleccionado no es un agente.");

                    return;
                }

                ticketService.asignarAgente(
                        idTicket,
                        agente.getIdUsuario());
            }
            response.sendRedirect(
                    request.getContextPath()
                    + "/tickets");

        } catch (IllegalArgumentException e) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    e.getMessage());
        } catch (SQLException ex) {
            System.getLogger(TicketsAdminServlet.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }

    }

    private String normalizar(String texto) {

        if (texto == null) {
            return "";
        }

        String sinAcentos
                = Normalizer.normalize(texto, Normalizer.Form.NFD).replaceAll("\\p{M}", "");

        return sinAcentos.trim().toUpperCase().replace(" ", "_");
    }
}
