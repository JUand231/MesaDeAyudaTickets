package servicio.roles;

import modelo.Ticket;
import modelo.Usuario;
import java.util.List;

/**
 * Todo lo que puede hacer un SOLICITANTE con un ticket.
 */
public interface AccionesSolicitante extends ConsultaTicket, AccionesComentario {

    Ticket crearTicket(
            String titulo,
            String descripcion,
            int idCategoria,
            int idSolicitante);

    List<Ticket> listarPorSolicitante(int idSolicitante);

    Ticket cerrar(
            int idTicket,
            Usuario solicitante);

    Ticket reabrir(
            int idTicket,
            Usuario solicitante);
}
