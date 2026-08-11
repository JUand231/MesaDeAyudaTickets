package modelo;

import java.time.LocalDateTime;

public class Comentario {

    private int idComentario;
    private int idTicket;
    private int idUsuario;
    private String texto;
    private LocalDateTime fecha;

    public Comentario() {
    }

    public Comentario(int idComentario, int idTicket, int idUsuario, String texto, LocalDateTime fecha) {
        this.idComentario = idComentario;
        this.idTicket = idTicket;
        this.idUsuario = idUsuario;
        this.texto = texto;
        this.fecha = fecha;
    }

    public int getIdComentario() {
        return idComentario;
    }

    public void setIdComentario(int idComentario) {
        this.idComentario = idComentario;
    }

    public int getidTicket() {
        return idTicket;
    }

    public void setidTicketr(int idTicket) {
        this.idTicket = idTicket;
    }
    
    public int getidUsuario() {
        return idUsuario;
    }

    public void setidUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

}
