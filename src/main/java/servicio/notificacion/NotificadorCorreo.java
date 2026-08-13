package servicio.notificacion;

import modelo.Ticket;
import modelo.Usuario;

public class NotificadorCorreo implements Notificador {

    @Override
    public void notificar(Usuario destinatario, Ticket ticket, String mensaje) {
        System.out.println("[Simulando envio de correo a " + destinatario.getCorreo() + "] "
                + "Ticket #" + ticket.getIdTicket() + ": " + mensaje);
    }
}
