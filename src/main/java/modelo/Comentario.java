package modelo;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Comentario {

    private int idComentario;
    private int idAutor;
    private String texto;
    private LocalDateTime fecha = LocalDateTime.now();

    public Comentario() {
    }

    public Comentario(int idComentario, int idAutor, String texto) {
        this.idComentario = idComentario;
        this.idAutor = idAutor;
        this.texto = texto;
    }

    public int getIdComentario() {
        return idComentario;
    }

    public void setIdComentario(int idComentario) {
        this.idComentario = idComentario;
    }

    public int getIdAutor() {
        return idAutor;
    }

    public void setIdAutor(int idAutor) {
        this.idAutor = idAutor;
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
