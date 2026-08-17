package WebServlet;

import repositorio.CategoriaRepository;
import repositorio.PrioridadRepository;
import repositorio.TicketRepository;
import repositorio.UsuarioRepository;
import repositorio.jdbc.CategoriaRepositoryJdbc;
import repositorio.jdbc.PrioridadRepositoryJdbc;
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
import repositorio.ComentarioRepository;
import repositorio.jdbc.ComentarioRepositoryJdbc;

@WebListener
public class AppContextListener implements ServletContextListener {

    public static final String TICKET_SERVICE = "ticketService";
    public static final String USUARIO_REPOSITORY = "usuarioRepository";
    public static final String CATEGORIA_REPOSITORY = "categoriaRepository";
    public static final String PRIORIDAD_REPOSITORY = "prioridadRepository";
    public static final String COMENTARIO_REPOSITORY = "comentarioRepository";

    @Override
    public void contextInitialized(ServletContextEvent evento) {
        ServletContext contexto = evento.getServletContext();

        // Repositorios
        TicketRepository ticketRepository = new TicketRepositoryJdbc();
        UsuarioRepository usuarioRepository = new UsuarioRepositoryJdbc();
        CategoriaRepository categoriaRepository = new CategoriaRepositoryJdbc();
        PrioridadRepository prioridadRepository = new PrioridadRepositoryJdbc();
        ComentarioRepository comentarioRepository = new ComentarioRepositoryJdbc();

        // Estrategias
        CalculadoraSLA calculadoraSLA = new CalculadoraSLAPorPrioridad();
        EstrategiaAsignacion estrategiaAsignacion = new AsignacionPorTurnoRotativo();
        Notificador notificador = new NotificadorEnAplicacion();

        // Servicio, con todo inyectado por constructor
        TicketService ticketService = new TicketService(
                ticketRepository, calculadoraSLA, estrategiaAsignacion, notificador);

        contexto.setAttribute(TICKET_SERVICE, ticketService);
        contexto.setAttribute(USUARIO_REPOSITORY, usuarioRepository);
        contexto.setAttribute(CATEGORIA_REPOSITORY, categoriaRepository);
        contexto.setAttribute(PRIORIDAD_REPOSITORY, prioridadRepository);
        contexto.setAttribute(COMENTARIO_REPOSITORY, comentarioRepository);
    }

    @Override
    public void contextDestroyed(ServletContextEvent evento) {

    }
}
