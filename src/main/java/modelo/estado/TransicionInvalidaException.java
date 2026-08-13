package modelo.estado;

public class TransicionInvalidaException extends RuntimeException {

    public TransicionInvalidaException(String estadoActual, String accion) {
        super("No se puede '" + accion + "' un ticket en estado " + estadoActual);
    }

}
