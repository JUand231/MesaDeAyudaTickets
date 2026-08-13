package modelo.estado;

public class EstadoTicketFactory {

    private EstadoTicketFactory() {

    }

    public static EstadoTicket desde(String nombre) {
        switch (nombre) {
            case "NUEVO":
                return new Nuevo();

            case "ASIGNADO":
                return new Asignado();

            case "EN_PROCESO":
                return new EnProceso();

            case "RESUELTO":
                return new Resuelto();

            case "CERRADO":
                return new Cerrado();

            case "CANCELADO":
                return new Cancelado();

            default:
                throw new IllegalArgumentException(
                        "Estado desconocido en la base de datos: " + nombre
                );
        }
    }
}
