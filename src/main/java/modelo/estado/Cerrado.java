package modelo.estado;

public class Cerrado implements EstadoTicket {

    @Override
    public EstadoTicket asignar() {
        throw new TransicionInvalidaException(nombre(), "asignar");
    }

    @Override
    public EstadoTicket iniciar() {
        throw new TransicionInvalidaException(nombre(), "iniciar");
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
        throw new TransicionInvalidaException(nombre(), "cancelar");
    }

    @Override
    public String nombre() {
        return "CERRADO";
    }
}
