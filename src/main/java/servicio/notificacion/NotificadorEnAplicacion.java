package servicio.notificacion;

import modelo.Notificacion;
import modelo.Ticket;
import modelo.Usuario;

import repositorio.NotificacionRepository;

public class NotificadorEnAplicacion implements Notificador {

    private final NotificacionRepository notificacionRepository;

    public NotificadorEnAplicacion(
            NotificacionRepository notificacionRepository) {

        this.notificacionRepository = notificacionRepository;
    }

    @Override
    public void notificar(
            Usuario destinatario,
            Ticket ticket,
            String mensaje) {

        if (destinatario == null) {
            throw new IllegalArgumentException(
                    "El destinatario de la notificacion es obligatorio"
            );
        }

        if (ticket == null) {
            throw new IllegalArgumentException(
                    "El ticket de la notificacion es obligatorio"
            );
        }

        if (mensaje == null || mensaje.isBlank()) {
            throw new IllegalArgumentException(
                    "El mensaje de la notificacion es obligatorio"
            );
        }

        Notificacion notificacion = new Notificacion(
                destinatario.getIdUsuario(),
                ticket.getIdTicket(),
                mensaje.trim()
        );

        notificacionRepository.guardar(notificacion);
    }
}
