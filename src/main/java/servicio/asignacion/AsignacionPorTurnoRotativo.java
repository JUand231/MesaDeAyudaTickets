package servicio.asignacion;

import modelo.Ticket;
import modelo.Usuario;
import java.util.List;

public class AsignacionPorTurnoRotativo implements EstrategiaAsignacion {

    @Override
    public int asignarAgente(Ticket ticket, List<Usuario> agentesDisponibles, List<Ticket> ticketsExistentes) {
        if (agentesDisponibles == null || agentesDisponibles.isEmpty()) {
            throw new IllegalStateException("No hay agentes disponibles para asignar");
        }
        int posicion = ticket.getIdTicket() % agentesDisponibles.size();
        return agentesDisponibles.get(posicion).getIdUsuario();
    }
}
