package modelo.estado;

public class Resuelto implements  EstadoTicket{

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
        return new Cerrado();
    }

    @Override
    public EstadoTicket reabrir() {
        return new EnProceso();
    } // el solicitante reabre

    @Override
    public EstadoTicket cancelar() {
        return new Cancelado();
    }

    @Override
    public String nombre() {
        return "RESUELTO";
    }

}
