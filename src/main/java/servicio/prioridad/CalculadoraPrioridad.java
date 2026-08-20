package servicio.prioridad;

/**
 * Calcula la prioridad de un ticket de forma automática (RF-03), sin que el
 * solicitante la escoja manualmente.
 *
 * Es una Strategy: TicketService depende de esta interfaz, no de una
 * implementación concreta, así que se puede reemplazar la forma de calcular la
 * prioridad (por palabras clave, por historial, por IA, etc.) sin tocar
 * TicketService (principio OCP).
 */
public interface CalculadoraPrioridad {

    /**
     * @param titulo titulo del ticket
     * @param descripcion descripcion del ticket
     * @param nombreCategoria nombre de la categoria seleccionada (ej. "Red")
     * @return el tipo de prioridad calculado: "BAJA", "MEDIA", "ALTA" o
     * "CRITICA" (debe coincidir con la columna Tipo de la tabla Prioridad).
     */
    String calcular(
            String titulo,
            String descripcion,
            String nombreCategoria);
}
