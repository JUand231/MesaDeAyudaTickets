package mapper;
import dto.TicketDTO;
import modelo.Ticket;
import java.time.format.DateTimeFormatter;

/**
 * Convierte Ticket <-> TicketDTO. Es el unico lugar del sistema que conoce
 * ambos mundos (entidad y DTO);
 */
public class TicketMapper {

    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private TicketMapper() {

    }

    public static TicketDTO aDTO(Ticket ticket) {
        TicketDTO dto = new TicketDTO();
        dto.setIdTicket(ticket.getIdTicket());
        dto.setTitulo(ticket.getTitulo());
        dto.setDescripcion(ticket.getDescripcion());
        dto.setIdCategoria(ticket.getIdCategoria());
        dto.setIdPrioridad(ticket.getIdPrioridad());
        dto.setIdSolicitante(ticket.getIdSolicitante());
        dto.setIdAgente(ticket.getIdAgente());
        dto.setEstado(ticket.getEstadoNombre());
        dto.setFechaCreacion(ticket.getFechaCreacion().format(FORMATO_FECHA));
        dto.setTotalComentarios(ticket.getComentarios().size());
        return dto;
    }

    public static TicketDTO aDTO(Ticket ticket, String nombreCategoria, String nombrePrioridad,
            String nombreSolicitante, String nombreAgente) {
        TicketDTO dto = aDTO(ticket);
        dto.setNombreCategoria(nombreCategoria);
        dto.setNombrePrioridad(nombrePrioridad);
        dto.setNombreSolicitante(nombreSolicitante);
        dto.setNombreAgente(nombreAgente != null ? nombreAgente : "Sin asignar");
        return dto;
    }
}
