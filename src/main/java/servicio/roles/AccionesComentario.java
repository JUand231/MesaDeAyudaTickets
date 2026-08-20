package servicio.roles;

import modelo.Comentario;

/**
 * Acción de comentar un ticket (RF-07). La comparten los tres roles, así que
 * vive aparte en vez de duplicarse en cada interfaz de rol.
 */
public interface AccionesComentario {

    Comentario agregarComentario(
            int idTicket,
            int idUsuario,
            int idRol,
            String texto);
}
