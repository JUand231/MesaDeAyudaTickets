package modelo;

import java.time.LocalDateTime;
import modelo.estado.EstadoTicket;
import modelo.estado.Nuevo;
import java.util.ArrayList;
import java.util.List;

public class Ticket {

    private int idTicket;
    private String titulo;
    private String descripcion;
    private int idCategoria;
    private int idPrioridad;
    private int idSolicitante;
    private Integer idAgente;
    private EstadoTicket estado = new Nuevo();
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    private List<Comentario> comentarios = new ArrayList<>();

    public Ticket() {
    }

    public void asignar() {
        this.estado = this.estado.asignar();
    }

    public void iniciar() {
        this.estado = this.estado.iniciar();
    }

    public void resolver() {
        this.estado = this.estado.resolver();
    }

    public void cerrar() {
        this.estado = this.estado.cerrar();
    }

    public void reabrir() {
        this.estado = this.estado.reabrir();
    }

    public void cancelar() {
        this.estado = this.estado.cancelar();
    }

    public String getEstadoNombre() {
        return estado.nombre();
    }

    public void agregarComentario(Comentario comentario) {
        this.comentarios.add(comentario);
    }

    public int getIdTicket() {
        return idTicket;
    }

    public void setIdTicket(int idTicket) {
        this.idTicket = idTicket;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public int getIdPrioridad() {
        return idPrioridad;
    }

    public void setIdPrioridad(int idPrioridad) {
        this.idPrioridad = idPrioridad;
    }

    public int getIdSolicitante() {
        return idSolicitante;
    }

    public void setIdSolicitante(int idSolicitante) {
        this.idSolicitante = idSolicitante;
    }

    public Integer getIdAgente() {
        return idAgente;
    }

    public void setIdAgente(Integer idAgente) {
        this.idAgente = idAgente;
    }

    public EstadoTicket getEstado() {
        return estado;
    }

    public void setEstado(EstadoTicket estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public List<Comentario> getComentarios() {
        return comentarios;
    }
}
