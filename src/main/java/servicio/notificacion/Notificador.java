package servicio.notificacion;

import modelo.Ticket;
import modelo.Usuario;

public interface Notificador {

    void notificar(Usuario destinatario, Ticket ticket, String mensaje);
}
