package servicio.sla;

import modelo.Prioridad;
import modelo.Ticket;
import java.time.LocalDateTime;

public class CalculadoraSLAPorPrioridad implements CalculadoraSLA {

    @Override
    public LocalDateTime calcularFechaLimite(Ticket ticket, Prioridad prioridad) {
        return ticket.getFechaCreacion().plusHours(prioridad.getHorasSLA());
    }

    @Override
    public boolean estaVencido(Ticket ticket, Prioridad prioridad) {
        String estado = ticket.getEstadoNombre();
        if (estado.equals("RESUELTO") || estado.equals("CERRADO") || estado.equals("CANCELADO")) {
            return false;
        }
        LocalDateTime fechaLimite = calcularFechaLimite(ticket, prioridad);
        return LocalDateTime.now().isAfter(fechaLimite);
    }
}
