package servicio.roles;

import modelo.Prioridad;
import modelo.Ticket;
import java.time.LocalDateTime;

/**
 * Consultas de solo lectura sobre un ticket que necesitan los TRES roles
 * (solicitante, agente y administrador) para ver el detalle de un ticket y su
 * SLA.
 */
public interface ConsultaTicket {

    Ticket buscarPorId(int idTicket);

    LocalDateTime calcularFechaLimiteSLA(
            int idTicket,
            Prioridad prioridad);

    boolean estaVencido(
            int idTicket,
            Prioridad prioridad);
}
