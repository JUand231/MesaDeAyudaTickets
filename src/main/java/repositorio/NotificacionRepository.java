package repositorio;

import modelo.Notificacion;

import java.util.List;

import java.util.Optional;

public interface NotificacionRepository {

    Notificacion guardar(Notificacion notificacion);

    Optional<Notificacion> buscarPorId(int idNotificacion);

    List<Notificacion> listarPorUsuario(int idUsuario);

    List<Notificacion> listarNoLeidas(int idUsuario);

    int contarNoLeidas(int idUsuario);

    void marcarComoLeida(int idNotificacion);

    void marcarTodasComoLeidas(int idUsuario);
}
