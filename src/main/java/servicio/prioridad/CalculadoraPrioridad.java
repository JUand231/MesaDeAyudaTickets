package servicio.prioridad;

/**
 * Calcula la prioridad de un ticket de forma automática.
 */
public interface CalculadoraPrioridad {

    String calcular(
            String titulo,
            String descripcion,
            String nombreCategoria);
}
