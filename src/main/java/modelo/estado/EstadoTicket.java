package modelo.estado;

public interface EstadoTicket {
    EstadoTicket asignar();
    EstadoTicket iniciar();
    EstadoTicket resolver();
    EstadoTicket cerrar();
    EstadoTicket reabrir();
    EstadoTicket cancelar();
    String nombre();
}