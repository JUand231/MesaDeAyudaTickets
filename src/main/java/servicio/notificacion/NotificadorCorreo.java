package servicio.notificacion;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import modelo.Ticket;
import modelo.Usuario;

public class NotificadorCorreo implements Notificador {

    private final ConfiguracionCorreo configuracion;

    public NotificadorCorreo() {
        this.configuracion = new ConfiguracionCorreo();
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

        String correoDestinatario = destinatario.getCorreo();

        if (correoDestinatario == null || correoDestinatario.isBlank()) {
            throw new IllegalArgumentException(
                    "El destinatario no tiene un correo valido"
            );
        }

        Properties propiedades = new Properties();

        propiedades.put(
                "mail.smtp.auth",
                "true"
        );

        propiedades.put(
                "mail.smtp.starttls.enable",
                "true"
        );

        propiedades.put(
                "mail.smtp.host",
                configuracion.getSmtpHost()
        );

        propiedades.put(
                "mail.smtp.port",
                configuracion.getSmtpPort()
        );

        Session session = Session.getInstance(
                propiedades,
                new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication(
                        configuracion.getUsername(),
                        configuracion.getPassword()
                );
            }
        }
        );

        try {
            Message correo = new MimeMessage(session);

            correo.setFrom(
                    new InternetAddress(
                            configuracion.getUsername()
                    )
            );

            correo.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(correoDestinatario)
            );

            correo.setSubject(
                    "Notificacion - Ticket #" + ticket.getIdTicket()
            );

            correo.setText(mensaje);

            Transport.send(correo);

            System.out.println(
                    "Correo enviado correctamente a "
                    + correoDestinatario
            );

        } catch (Exception e) {

            System.err.println(
                    "No se pudo enviar el correo a "
                    + correoDestinatario
                    + ": "
                    + e.getMessage()
            );
        }
    }
}
