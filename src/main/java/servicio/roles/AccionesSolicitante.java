package servicio.roles;

import modelo.Ticket;
import modelo.Usuario;
import java.util.List;

/**
 * Todo lo que puede hacer un SOLICITANTE con un ticket. No incluye asignar
 * agentes, iniciar atención ni cancelar: eso no es parte de su rol, así que ni
 * siquiera aparece aquí (ISP-01).
 */
public interface AccionesSolicitante extends ConsultaTicket, AccionesComentario {

    Ticket crearTicket(
            String titulo,
            String descripcion,
            int idCategoria,
            int idSolicitante);

    List<Ticket> listarPorSolicitante(int idSolicitante);

    /**
     * RESUELTO -> CERRADO: el solicitante confirma que su problema quedó
     * solucionado.
     */
    Ticket cerrar(
            int idTicket,
            Usuario solicitante);

    /**
     * RESUELTO -> EN_PROCESO: el solicitante no quedó conforme y reabre el
     * ticket.
     */
    Ticket reabrir(
            int idTicket,
            Usuario solicitante);
}