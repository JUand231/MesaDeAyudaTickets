package servicio.notificacion;

import modelo.Ticket;
import modelo.Usuario;

public class NotificadorEnAplicacion implements Notificador {

    @Override
    public void notificar(Usuario destinatario, Ticket ticket, String mensaje) {
        System.out.println("[Notificacion para " + destinatario.getNombre() + "] "
                + "Ticket #" + ticket.getIdTicket() + ": " + mensaje);
    }
}
