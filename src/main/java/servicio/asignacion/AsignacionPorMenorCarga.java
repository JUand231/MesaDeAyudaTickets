package servicio.asignacion;

import modelo.Ticket;
import modelo.Usuario;
import java.util.List;

public class AsignacionPorMenorCarga implements EstrategiaAsignacion {

    @Override
    public int asignarAgente(Ticket ticket, List<Usuario> agentesDisponibles, List<Ticket> ticketsExistentes) {
        if (agentesDisponibles == null || agentesDisponibles.isEmpty()) {
            throw new IllegalStateException("No hay agentes disponibles para asignar");
        }

        Usuario agenteConMenorCarga = agentesDisponibles.get(0);
        int menorCarga = contarCargaActiva(agenteConMenorCarga.getIdUsuario(), ticketsExistentes);

        for (Usuario agente : agentesDisponibles) {
            int carga = contarCargaActiva(agente.getIdUsuario(), ticketsExistentes);
            if (carga < menorCarga) {
                menorCarga = carga;
                agenteConMenorCarga = agente;
            }
        }
        return agenteConMenorCarga.getIdUsuario();
    }

    private int contarCargaActiva(int idAgente, List<Ticket> ticketsExistentes) {
        int total = 0;
        for (Ticket ticket : ticketsExistentes) {
            boolean esDeEsteAgente = ticket.getIdAgente() != null && ticket.getIdAgente() == idAgente;
            boolean estaActivo = !esEstadoFinal(ticket.getEstadoNombre());
            if (esDeEsteAgente && estaActivo) {
                total++;
            }
        }
        return total;
    }

    private boolean esEstadoFinal(String estado) {
        return estado.equals("RESUELTO") || estado.equals("CERRADO") || estado.equals("CANCELADO");
    }
}
