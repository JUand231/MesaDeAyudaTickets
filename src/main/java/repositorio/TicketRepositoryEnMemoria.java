package repositorio;

import modelo.Ticket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class TicketRepositoryEnMemoria implements TicketRepository {

    private final Map<Integer, Ticket> tickets = new HashMap<>();

    @Override
    public Ticket guardar(Ticket ticket) {
        tickets.put(ticket.getIdTicket(), ticket);
        return ticket;
    }

    @Override
    public Optional<Ticket> buscarPorId(int idTicket) {
        return Optional.ofNullable(tickets.get(idTicket));
    }

    @Override
    public List<Ticket> listarTodos() {
        return new ArrayList<>(tickets.values());
    }

    @Override
    public List<Ticket> listarPorSolicitante(int idSolicitante) {
        List<Ticket> resultado = new ArrayList<>();

        for (Ticket ticket : tickets.values()) {
            if (ticket.getIdSolicitante() == idSolicitante) {
                resultado.add(ticket);
            }
        }

        return resultado;
    }

    @Override
    public List<Ticket> listarPorAgente(int idAgente) {
        List<Ticket> resultado = new ArrayList<>();

        for (Ticket ticket : tickets.values()) {
            if (ticket.getIdAgente() != null
                    && ticket.getIdAgente() == idAgente) {
                resultado.add(ticket);
            }
        }

        return resultado;
    }

    @Override
    public Ticket actualizar(Ticket ticket) {
        tickets.put(ticket.getIdTicket(), ticket);
        return ticket;
    }

    @Override
    public void eliminar(int idTicket) {
        tickets.remove(idTicket);
    }
}
