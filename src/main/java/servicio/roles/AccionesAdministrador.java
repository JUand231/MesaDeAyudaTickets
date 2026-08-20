package servicio.roles;

import modelo.Ticket;
import modelo.Usuario;
import java.util.List;

/**
 * Todo lo que puede hacer un ADMINISTRADOR con un ticket. No incluye iniciar
 * atención ni resolver: eso es del agente, no del admin (ISP-01).
 */
public interface AccionesAdministrador extends ConsultaTicket, AccionesComentario {

    List<Ticket> listarTodos();

    /**
     * NUEVO -> ASIGNADO, elegido a mano por el admin.
     */
    Ticket asignarAgente(
            int idTicket,
            int idAgente);

    /**
     * NUEVO -> ASIGNADO, elegido por la Strategy de asignación configurada
     * (turno rotativo, menor carga, etc.).
     */
    Ticket asignarAgenteAutomatico(
            int idTicket,
            List<Usuario> agentesDisponibles);

    /**
     * RF-10: el admin reasigna un ticket ya asignado a otro agente.
     */
    Ticket reasignarAgente(
            int idTicket,
            int nuevoIdAgente,
            Usuario solicitante);

    /**
     * Cualquier estado no cerrado -> CANCELADO, solo por el admin.
     */
    Ticket cancelar(
            int idTicket,
            Usuario solicitante);
}
