package servicio.notificacion;

import modelo.Ticket;
import modelo.Usuario;

public class NotificadorSMS implements Notificador {

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

        System.out.println(
                "[Simulando envio de SMS a "
                + destinatario.getCorreo()
                + "] Ticket #"
                + ticket.getIdTicket()
                + ": "
                + mensaje
        );
    }
}