package modelo;

import java.time.LocalDateTime;

public class Comentario {

    private int idComentario;
    private int idUsuario; // autor: quien escribio
    private String texto;
    private LocalDateTime fecha = LocalDateTime.now();

    public Comentario() {
    }

    public Comentario(int idUsuario, String texto) {
        this.idUsuario = idUsuario;
        this.texto = texto;
    }

    public int getIdComentario() {
        return idComentario;
    }

    public void setIdComentario(int idComentario) {
        this.idComentario = idComentario;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
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
