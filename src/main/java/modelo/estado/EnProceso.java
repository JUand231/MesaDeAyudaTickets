package modelo.estado;

public class EnProceso implements EstadoTicket{

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
        return new Resuelto();
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
        return "EN_PROCESO";
    }
}
