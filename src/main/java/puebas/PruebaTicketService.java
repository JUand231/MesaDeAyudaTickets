package puebas;

import dto.TicketDTO;
import java.util.List;
import mapper.TicketMapper;
import modelo.Prioridad;
import modelo.Ticket;
import modelo.Usuario;
import repositorio.TicketRepositoryEnMemoria;
import servicio.TicketService;
import servicio.asignacion.AsignacionPorTurnoRotativo;
import servicio.asignacion.EstrategiaAsignacion;
import servicio.sla.CalculadoraSLA;
import servicio.sla.CalculadoraSLAPorPrioridad;

public class PruebaTicketService {

    public static void main(String[] args) {
        TicketService service = new TicketService(new TicketRepositoryEnMemoria());
        Ticket t = service.crearTicket("Prueba", "Descripcion de prueba", 1, 1, 1);
        System.out.println("Creado: " + t.getEstadoNombre());
        service.asignarAgente(t.getIdTicket(), 5);
        System.out.println("Despues de asignar: " + t.getEstadoNombre());

        TicketDTO dto = TicketMapper.aDTO(t);
        System.out.println(dto.getTitulo() + " - " + dto.getEstado() + " - " + dto.getFechaCreacion());

        Prioridad alta = new Prioridad("ALTA", 8);
        CalculadoraSLA calculadora = new CalculadoraSLAPorPrioridad();
        System.out.println("Fecha limite: " + calculadora.calcularFechaLimite(t, alta));
        System.out.println("Vencido: " + calculadora.estaVencido(t, alta));

        List<Usuario> agentes = List.of(
                new Usuario(1, "Ana", "ana@cimm.edu", 2),
                new Usuario(2, "Luis", "luis@cimm.edu", 2)
        );
        EstrategiaAsignacion estrategia = new AsignacionPorTurnoRotativo();
        int idAgente = estrategia.asignarAgente(t, agentes, service.listarTodos());
        System.out.println("Agente elegido: " + idAgente);
    }
}
