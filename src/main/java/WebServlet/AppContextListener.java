package WebServlet;

import repositorio.TicketRepository;
import repositorio.UsuarioRepository;
import repositorio.jdbc.TicketRepositoryJdbc;
import repositorio.jdbc.UsuarioRepositoryJdbc;
import servicio.TicketService;
import servicio.asignacion.AsignacionPorTurnoRotativo;
import servicio.asignacion.EstrategiaAsignacion;
import servicio.notificacion.Notificador;
import servicio.notificacion.NotificadorEnAplicacion;
import servicio.sla.CalculadoraSLA;
import servicio.sla.CalculadoraSLAPorPrioridad;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Composition root: el unico lugar del proyecto donde se hace "new" de las
 * implementaciones concretas. Arma TicketService y UsuarioRepository con todas
 * sus dependencias inyectadas por constructor, y los deja disponibles en el
 * ServletContext para que los Servlets los usen sin construir nada ellos mismos
 * (DIP).
 */
@WebListener
public class AppContextListener implements ServletContextListener {

    public static final String TICKET_SERVICE = "ticketService";
    public static final String USUARIO_REPOSITORY = "usuarioRepository";

    @Override
    public void contextInitialized(ServletContextEvent evento) {
        ServletContext contexto = evento.getServletContext();

        // Repositorios
        TicketRepository ticketRepository = new TicketRepositoryJdbc();
        UsuarioRepository usuarioRepository = new UsuarioRepositoryJdbc();

        // Estrategias
        CalculadoraSLA calculadoraSLA = new CalculadoraSLAPorPrioridad();
        EstrategiaAsignacion estrategiaAsignacion = new AsignacionPorTurnoRotativo();
        Notificador notificador = new NotificadorEnAplicacion();

        // Servicio, con todo inyectado por constructor
        TicketService ticketService = new TicketService(
                ticketRepository, calculadoraSLA, estrategiaAsignacion, notificador);

        contexto.setAttribute(TICKET_SERVICE, ticketService);
        contexto.setAttribute(USUARIO_REPOSITORY, usuarioRepository);
    }

    @Override
    public void contextDestroyed(ServletContextEvent evento) {

    }
}
