package servicio.roles;

import modelo.Ticket;
import modelo.Usuario;
import java.util.List;

/**
 * Todo lo que puede hacer un ADMINISTRADOR con un ticket.
 */
public interface AccionesAdministrador extends ConsultaTicket, AccionesComentario {

    List<Ticket> listarTodos();

    Ticket asignarAgente(
            int idTicket,
            int idAgente);

    Ticket asignarAgenteAutomatico(
            int idTicket,
            List<Usuario> agentesDisponibles);

    Ticket cancelar(
            int idTicket,
            Usuario solicitante);
}
