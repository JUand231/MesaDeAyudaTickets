package servicio.roles;

import modelo.Comentario;

/**
 * Acción de comentar un ticket
 */
public interface AccionesComentario {

    Comentario agregarComentario(
            int idTicket,
            int idUsuario,
            int idRol,
            String texto);
}
