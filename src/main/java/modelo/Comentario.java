package modelo;

import java.time.LocalDate;

public class Comentario {
    private int idComentario;
    private String autor;
    private String texto;
    private LocalDate fecha;

    public Comentario() {
    }

    public Comentario(int idComentario, String autor, String texto, LocalDate fecha) {
        this.idComentario = idComentario;
        this.autor = autor;
        this.texto = texto;
        this.fecha = fecha;
    }

    public int getIdComentario() {
        return idComentario;
    }

    public void setIdComentario(int idComentario) {
        this.idComentario = idComentario;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
    
    
    
}
