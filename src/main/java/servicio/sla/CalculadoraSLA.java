package servicio.sla;

import modelo.Prioridad;
import modelo.Ticket;
import java.time.LocalDateTime;

public interface CalculadoraSLA {
    
    LocalDateTime calcularFechaLimite(Ticket ticket, Prioridad prioridad);

    boolean estaVencido(Ticket ticket, Prioridad prioridad);
}
