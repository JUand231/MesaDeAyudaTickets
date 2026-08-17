package repositorio;

import modelo.Comentario;

import java.util.List;
import java.util.Optional;

public interface ComentarioRepository {

    Comentario guardar(Comentario comentario);

    Optional<Comentario> buscarPorId(int idComentario);

    List<Comentario> listarPorTicket(int idTicket);

    List<Comentario> listarPorSolicitante(int idUsuario);

    boolean ticketPerteneceAUsuario(int idTicket, int idUsuario);
}
