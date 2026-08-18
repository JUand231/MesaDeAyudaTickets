package modelo;

import java.time.LocalDateTime;

public class Comentario {

    private int idComentario;
    private int idUsuario;
    private int idTicket;
    private String texto;
    private LocalDateTime fecha = LocalDateTime.now();
    // Datos adicionales para consultas
    private String nombreUsuario;
    private String nombreRol;
    private String tituloTicket;

    public Comentario() {
    }

    // Constructor para crear un nuevo comentario
    public Comentario(int idTicket, int idUsuario, String texto) {
        this.idTicket = idTicket;
        this.idUsuario = idUsuario;
        this.texto = texto;
        this.fecha = LocalDateTime.now();
    }

    // Constructor completo
    public Comentario(
            int idComentario,
            int idUsuario,
            int idTicket,
            String texto) {

        this.idComentario = idComentario;
        this.idUsuario = idUsuario;
        this.idTicket = idTicket;
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

    public int getIdTicket() {
        return idTicket;
    }

    public void setIdTicket(int idTicket) {
        this.idTicket = idTicket;
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

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }

    public String getTituloTicket() {
        return tituloTicket;
    }

    public void setTituloTicket(String tituloTicket) {
        this.tituloTicket = tituloTicket;
    }
}
