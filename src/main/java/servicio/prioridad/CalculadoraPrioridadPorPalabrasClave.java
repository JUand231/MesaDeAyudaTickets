package servicio.prioridad;

import java.text.Normalizer;
import java.util.Locale;
import java.util.Map;

/**
 * Implementación de CalculadoraPrioridad.
 */
public class CalculadoraPrioridadPorPalabrasClave implements CalculadoraPrioridad {

    private static final String CRITICA = "CRITICA";
    private static final String ALTA = "ALTA";
    private static final String MEDIA = "MEDIA";
    private static final String BAJA = "BAJA";

    private static final String[] PALABRAS_CRITICA = {
        "caido", "caida", "cayo", "no hay internet", "sin internet",
        "sin acceso a la red", "servidor caido", "todos los equipos",
        "ningun equipo", "urgente", "incendio", "se quemo", "humo",
        "corto circuito", "riesgo"
    };

    private static final String[] PALABRAS_ALTA = {
        "no enciende", "no prende", "no imprime", "no funciona",
        "no inicia", "no arranca", "error critico", "se dano",
        "danado", "bloqueado", "bloqueada", "no carga", "sin señal",
        "sin senal", "virus"
    };

    private static final String[] PALABRAS_BAJA = {
        "instalar", "actualizar", "actualizacion", "consulta",
        "duda", "capacitacion", "revision periodica",
        "mantenimiento preventivo", "solicitud de acceso"
    };

    private static final Map<String, String> PRIORIDAD_POR_CATEGORIA = Map.of(
            "red", ALTA,
            "hardware", MEDIA,
            "software", MEDIA,
            "mantenimiento", BAJA
    );

    @Override
    public String calcular(String titulo, String descripcion, String nombreCategoria) {

        String texto = normalizar((titulo == null ? "" : titulo) + " "
                + (descripcion == null ? "" : descripcion));

        if (contieneAlguna(texto, PALABRAS_CRITICA)) {
            return CRITICA;
        }

        if (contieneAlguna(texto, PALABRAS_ALTA)) {
            return ALTA;
        }

        if (contieneAlguna(texto, PALABRAS_BAJA)) {
            return BAJA;
        }

        return PRIORIDAD_POR_CATEGORIA.getOrDefault(
                normalizar(nombreCategoria),
                MEDIA);
    }

    private boolean contieneAlguna(String texto, String[] palabrasClave) {

        for (String palabra : palabrasClave) {

            if (texto.contains(palabra)) {
                return true;
            }
        }

        return false;
    }

    private String normalizar(String texto) {

        if (texto == null) {
            return "";
        }

        String sinTildes = Normalizer.normalize(texto, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");

        return sinTildes.toLowerCase(Locale.forLanguageTag("es"));
    }
}
