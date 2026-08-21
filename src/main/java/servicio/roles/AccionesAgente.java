package servicio.roles;

import modelo.Ticket;
import modelo.Usuario;
import java.util.List;

/**
 * Todo lo que puede hacer un AGENTE con un ticket.
 */
public interface AccionesAgente extends ConsultaTicket, AccionesComentario {

    List<Ticket> listarPorAgente(int idAgente);

    Ticket iniciarAtencion(
            int idTicket,
            Usuario solicitante);

    Ticket resolver(
            int idTicket,
            Usuario solicitante);
}
