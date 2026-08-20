package servicio.roles;

import modelo.Ticket;
import modelo.Usuario;
import java.util.List;

/**
 * Todo lo que puede hacer un AGENTE con un ticket. No incluye crear tickets,
 * cancelarlos ni reasignarlos a otro agente: eso no es parte de su rol
 * (ISP-01).
 */
public interface AccionesAgente extends ConsultaTicket, AccionesComentario {

    List<Ticket> listarPorAgente(int idAgente);

    /**
     * ASIGNADO -> EN_PROCESO: el agente inicia la atención.
     */
    Ticket iniciarAtencion(
            int idTicket,
            Usuario solicitante);

    /**
     * EN_PROCESO -> RESUELTO: el agente resuelve el ticket.
     */
    Ticket resolver(
            int idTicket,
            Usuario solicitante);
}
