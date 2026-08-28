package prueba;

import modelo.Ticket;
import modelo.Usuario;
import servicio.notificacion.NotificadorCorreo;

public class pruebaCorreo {

    public static void main(String[] args) {

        try {
            // Correo que recibirá la prueba
            Usuario destinatario = new Usuario();

            destinatario.setIdUsuario(1);
            destinatario.setNombre("Juan David");
            destinatario.setCorreo("calixtoangeljuandavid@gmail.com");

            // Ticket de prueba
            Ticket ticket = new Ticket();

            ticket.setIdTicket(999);
            ticket.setTitulo("Prueba de notificacion por correo");
            ticket.setDescripcion(
                    "Este correo fue enviado mediante JavaMail y Gmail SMTP."
            );

            // Crear el notificador
            NotificadorCorreo notificador = new NotificadorCorreo();

            // Enviar correo
            notificador.notificar(
                    destinatario,
                    ticket,
                    "Hola Juan David,\n\n"
                    + "Esta es una prueba del sistema de notificaciones "
                    + "de la Mesa de Ayuda.\n\n"
                    + "Ticket: #" + ticket.getIdTicket() + "\n"
                    + "Titulo: " + ticket.getTitulo() + "\n\n"
                    + "El envio de correo funciona correctamente."
            );

            System.out.println(
                    "PRUEBA FINALIZADA CORRECTAMENTE."
            );

        } catch (Exception e) {

            System.out.println(
                    "ERROR AL ENVIAR EL CORREO:"
            );

            e.printStackTrace();
        }
    }
}
