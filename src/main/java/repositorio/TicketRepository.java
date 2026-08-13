package repositorio;

import java.util.List;
import java.util.Optional;
import modelo.Ticket;

public interface TicketRepository {

    Ticket guardar(Ticket ticket);

    Optional<Ticket> buscarPorId(int idTicket);

    List<Ticket> listarTodos();

    List<Ticket> listarPorSolicitante(int idSolicitante);

    List<Ticket> listarPorAgente(int idAgente);

    Ticket actualizar(Ticket ticket);

    void eliminar(int idTicket);
}
