package modelo;

import java.time.LocalDateTime;

public class Notificacion {

    private int idNotificacion;
    private int idUsuario;
    private int idTicket;
    private String mensaje;
    private boolean leida;
    private LocalDateTime fecha;

    public Notificacion() {
        this.fecha = LocalDateTime.now();
        this.leida = false;
    }

    public Notificacion(int idUsuario, int idTicket, String mensaje) {
        this.idUsuario = idUsuario;
        this.idTicket = idTicket;
        this.mensaje = mensaje;
        this.fecha = LocalDateTime.now();
        this.leida = false;
    }

    public int getIdNotificacion() {
        return idNotificacion;
    }

    public void setIdNotificacion(int idNotificacion) {
        this.idNotificacion = idNotificacion;
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

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public boolean isLeida() {
        return leida;
    }

    public void setLeida(boolean leida) {
        this.leida = leida;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}
