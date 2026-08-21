package modelo.estado;

public class Asignado implements EstadoTicket {

    @Override
    public EstadoTicket asignar() {
        return new Asignado();
    }

    @Override
    public EstadoTicket iniciar() {
        return new EnProceso();
    }

    @Override
    public EstadoTicket resolver() {
        throw new TransicionInvalidaException(nombre(), "resolver");
    }

    @Override
    public EstadoTicket cerrar() {
        throw new TransicionInvalidaException(nombre(), "cerrar");
    }

    @Override
    public EstadoTicket reabrir() {
        throw new TransicionInvalidaException(nombre(), "reabrir");
    }

    @Override
    public EstadoTicket cancelar() {
        return new Cancelado();
    }

    @Override
    public String nombre() {
        return "ASIGNADO";
    }
}