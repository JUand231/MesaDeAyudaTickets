package servicio.asignacion;

import modelo.Ticket;
import modelo.Usuario;
import java.util.List;

public interface EstrategiaAsignacion {

    int asignarAgente(Ticket ticket, List<Usuario> agentesDisponibles, List<Ticket> ticketsExistentes);
}
