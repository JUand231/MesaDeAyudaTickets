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

    // ==========================================================
    // GET
    // LISTAR TICKETS
    // ==========================================================
    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        // ==========================================================
        // VALIDAR SESIÓN
        // ==========================================================
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

        // ==========================================================
        // OBTENER SERVICIOS Y REPOSITORIOS
        // ==========================================================
        TicketService ticketService
                = (TicketService) getServletContext()
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

        if (ticketService == null
                || categoriaRepository == null
                || prioridadRepository == null
                || usuarioRepository == null) {

            throw new ServletException(
                    "Los componentes necesarios no están configurados.");
        }

        // ==========================================================
        // OBTENER TICKETS SEGÚN EL ROL
        // ==========================================================
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

        // ==========================================================
        // CONVERTIR TICKETS A DTO
        // ==========================================================
        List<TicketDTO> ticketsDTO
                = new ArrayList<>();

        try {

            for (Ticket ticket : tickets) {

                // --------------------------------------------------
                // CATEGORÍA
                // --------------------------------------------------
                String nombreCategoria
                        = categoriaRepository
                                .buscarPorId(
                                        ticket.getIdCategoria())
                                .map(
                                        Categoria::getNombreCategoria)
                                .orElse(
                                        "Categoria #"
                                        + ticket.getIdCategoria());

                // --------------------------------------------------
                // PRIORIDAD
                // --------------------------------------------------
                String nombrePrioridad
                        = prioridadRepository
                                .buscarPorId(
                                        ticket.getIdPrioridad())
                                .map(
                                        Prioridad::getTipo)
                                .orElse(
                                        "Prioridad #"
                                        + ticket.getIdPrioridad());

                // --------------------------------------------------
                // SOLICITANTE
                // --------------------------------------------------
                String nombreSolicitante
                        = usuarioRepository
                                .buscarPorId(
                                        ticket.getIdSolicitante())
                                .map(
                                        Usuario::getNombre)
                                .orElse(
                                        "Usuario #"
                                        + ticket.getIdSolicitante());

                // --------------------------------------------------
                // AGENTE
                // --------------------------------------------------
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

                // --------------------------------------------------
                // CREAR DTO
                // --------------------------------------------------
                ticketsDTO.add(
                        TicketMapper.aDTO(
                                ticket,
                                nombreCategoria,
                                nombrePrioridad,
                                nombreSolicitante,
                                nombreAgente));
            }

        } catch (SQLException e) {

            throw new ServletException(
                    "Error consultando datos relacionados del ticket.",
                    e);
        }

        // ==========================================================
        // FILTROS
        // ==========================================================
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

            // ------------------------------------------------------
            // BUSCAR
            // ------------------------------------------------------
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

            // ------------------------------------------------------
            // ESTADO
            // ------------------------------------------------------
            if (estado != null
                    && !estado.trim().isEmpty()
                    && !normalizar(
                            dto.getEstado())
                            .equals(
                                    normalizar(estado))) {

                continue;
            }

            // ------------------------------------------------------
            // PRIORIDAD
            // ------------------------------------------------------
            if (prioridad != null
                    && !prioridad.trim().isEmpty()
                    && !normalizar(
                            dto.getNombrePrioridad())
                            .equals(
                                    normalizar(prioridad))) {

                continue;
            }

            // ------------------------------------------------------
            // CATEGORÍA
            // ------------------------------------------------------
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

        // ==========================================================
        // ENVIAR TICKETS AL JSP
        // ==========================================================
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

        // ==========================================================
        // CARGAR AGENTES DISPONIBLES
        // SOLO ADMINISTRADOR
        // ==========================================================
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

        // ==========================================================
        // SELECCIONAR JSP SEGÚN ROL
        // ==========================================================
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

    // ==========================================================
    // POST
    // ASIGNAR AGENTE
    // NUEVO -> ASIGNADO
    // ==========================================================
    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        // ==========================================================
        // VALIDAR SESIÓN
        // ==========================================================
        HttpSession session
                = request.getSession(false);

        if (session == null
                || session.getAttribute("idUsuario") == null) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/login");

            return;
        }

        // ==========================================================
        // OBTENER ROL
        // ==========================================================
        int idRol
                = (Integer) session.getAttribute("idRol");

        // ==========================================================
        // SOLO ADMINISTRADOR
        // ==========================================================
        if (idRol == 1 || idRol == 2) {

            response.sendError(
                    HttpServletResponse.SC_FORBIDDEN,
                    "No tienes permisos para asignar agentes.");

            return;
        }

        // ==========================================================
        // DATOS DEL FORMULARIO
        // ==========================================================
        String idTicketStr
                = request.getParameter("idTicket");

        String idAgenteStr
                = request.getParameter("idAgente");

        if (idTicketStr == null
                || idAgenteStr == null
                || idTicketStr.trim().isEmpty()
                || idAgenteStr.trim().isEmpty()) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/tickets");

            return;
        }

        int idTicket;
        int idAgente;

        try {

            idTicket
                    = Integer.parseInt(
                            idTicketStr);

            idAgente
                    = Integer.parseInt(
                            idAgenteStr);

        } catch (NumberFormatException e) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "El ID del ticket o del agente no es válido.");

            return;
        }

        // ==========================================================
        // OBTENER SERVICIOS
        // ==========================================================
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

            // ======================================================
            // BUSCAR TICKET
            // ======================================================
            Ticket ticket
                    = ticketService.buscarPorId(
                            idTicket);

            // ======================================================
            // VALIDAR ESTADO
            // SOLO NUEVO PUEDE SER ASIGNADO
            // ======================================================
            if (!"NUEVO".equals(
                    ticket.getEstadoNombre())) {

                response.sendError(
                        HttpServletResponse.SC_BAD_REQUEST,
                        "Solo se pueden asignar agentes a tickets NUEVO.");

                return;
            }

            // ======================================================
            // BUSCAR AGENTE
            // ======================================================
            Usuario agente
                    = usuarioRepository
                            .buscarPorId(
                                    idAgente)
                            .orElseThrow(
                                    ()
                                    -> new IllegalArgumentException(
                                            "No existe el agente seleccionado."));

            // ======================================================
            // VALIDAR QUE REALMENTE SEA AGENTE
            // ======================================================
            if (agente.getIdRol() != 2) {

                response.sendError(
                        HttpServletResponse.SC_BAD_REQUEST,
                        "El usuario seleccionado no es un agente.");

                return;
            }

            // ======================================================
            // BUSCAR SOLICITANTE
            // ======================================================
            Usuario solicitante
                    = usuarioRepository
                            .buscarPorId(
                                    ticket.getIdSolicitante())
                            .orElseThrow(
                                    ()
                                    -> new IllegalArgumentException(
                                            "No se encontró el solicitante del ticket."));

            // ======================================================
            // ASIGNAR AGENTE
            //
            // El State Pattern ejecutará:
            //
            // NUEVO
            //   ↓
            // ASIGNADO
            // ======================================================
            ticketService.asignarAgente(
                    idTicket,
                    agente.getIdUsuario(),
                    solicitante);

            // ======================================================
            // VOLVER A LA LISTA
            // ======================================================
            response.sendRedirect(
                    request.getContextPath()
                    + "/tickets");

        } catch (SQLException e) {

            throw new ServletException(
                    "Error consultando usuarios para asignar el ticket.",
                    e);

        } catch (IllegalArgumentException e) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    e.getMessage());
        }
    }

    // ==========================================================
    // NORMALIZAR
    // ==========================================================
    private String normalizar(String texto) {

        if (texto == null) {
            return "";
        }

        String sinAcentos
                = Normalizer.normalize(
                        texto,
                        Normalizer.Form.NFD)
                        .replaceAll(
                                "\\p{M}",
                                "");

        return sinAcentos
                .trim()
                .toUpperCase()
                .replace(
                        " ",
                        "_");
    }
}
