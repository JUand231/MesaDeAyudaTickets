package servicio.notificacion;

import modelo.Ticket;
import modelo.Usuario;
import java.util.List;

public class NotificadorCompuesto implements Notificador {

    private final List<Notificador> canales;

    public NotificadorCompuesto(List<Notificador> canales) {
        this.canales = canales;
    }

    @Override
    public void notificar(
            Usuario destinatario,
            Ticket ticket,
            String mensaje) {

        for (Notificador canal : canales) {

            try {

                canal.notificar(destinatario, ticket, mensaje);

            } catch (Exception e) {

                System.err.println(
                        "Fallo el canal de notificacion "
                        + canal.getClass().getSimpleName()
                        + ": "
                        + e.getMessage()
                );
            }
        }
    }
}
