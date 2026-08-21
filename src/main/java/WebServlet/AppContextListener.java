package WebServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import repositorio.CategoriaRepository;
import repositorio.ComentarioRepository;
import repositorio.NotificacionRepository;
import repositorio.PrioridadRepository;
import repositorio.TicketRepository;
import repositorio.UsuarioRepository;
import repositorio.jdbc.CategoriaRepositoryJdbc;
import repositorio.jdbc.ComentarioRepositoryJdbc;
import repositorio.jdbc.NotificacionRepositoryJdbc;
import repositorio.jdbc.PrioridadRepositoryJdbc;
import repositorio.jdbc.TicketRepositoryJdbc;
import repositorio.jdbc.UsuarioRepositoryJdbc;
import servicio.TicketService;
import servicio.asignacion.AsignacionPorMenorCarga;
import servicio.asignacion.EstrategiaAsignacion;
import servicio.notificacion.Notificador;
import servicio.notificacion.NotificadorEnAplicacion;
import servicio.prioridad.CalculadoraPrioridad;
import servicio.prioridad.CalculadoraPrioridadPorPalabrasClave;
import servicio.sla.CalculadoraSLA;
import servicio.sla.CalculadoraSLAPorPrioridad;

@WebListener
public class AppContextListener implements ServletContextListener {

    public static final String TICKET_SERVICE = "ticketService";
    public static final String USUARIO_REPOSITORY = "usuarioRepository";
    public static final String CATEGORIA_REPOSITORY = "categoriaRepository";
    public static final String PRIORIDAD_REPOSITORY = "prioridadRepository";
    public static final String COMENTARIO_REPOSITORY = "comentarioRepository";
    public static final String NOTIFICACION_REPOSITORY = "notificacionRepository";
    public static final String TICKET_REPOSITORY = "ticketRepository";

    @Override
    public void contextInitialized(ServletContextEvent evento) {

        ServletContext contexto = evento.getServletContext();

        TicketRepository ticketRepository
                = new TicketRepositoryJdbc();

        UsuarioRepository usuarioRepository
                = new UsuarioRepositoryJdbc();

        CategoriaRepository categoriaRepository
                = new CategoriaRepositoryJdbc();

        PrioridadRepository prioridadRepository
                = new PrioridadRepositoryJdbc();

        ComentarioRepository comentarioRepository
                = new ComentarioRepositoryJdbc();

        NotificacionRepository notificacionRepository
                = new NotificacionRepositoryJdbc();

        CalculadoraSLA calculadoraSLA
                = new CalculadoraSLAPorPrioridad();

        CalculadoraPrioridad calculadoraPrioridad
                = new CalculadoraPrioridadPorPalabrasClave();

        EstrategiaAsignacion estrategiaAsignacion
                = new AsignacionPorMenorCarga();

        Notificador notificador
                = new NotificadorEnAplicacion(
                        notificacionRepository
                );

        TicketService ticketService
                = new TicketService(
                        ticketRepository,
                        usuarioRepository,
                        categoriaRepository,
                        prioridadRepository,
                        comentarioRepository,
                        calculadoraSLA,
                        calculadoraPrioridad,
                        estrategiaAsignacion,
                        notificador
                );

        contexto.setAttribute(
                TICKET_SERVICE,
                ticketService
        );

        contexto.setAttribute(
                USUARIO_REPOSITORY,
                usuarioRepository
        );

        contexto.setAttribute(
                CATEGORIA_REPOSITORY,
                categoriaRepository
        );

        contexto.setAttribute(
                PRIORIDAD_REPOSITORY,
                prioridadRepository
        );

        contexto.setAttribute(
                COMENTARIO_REPOSITORY,
                comentarioRepository
        );

        contexto.setAttribute(
                NOTIFICACION_REPOSITORY,
                notificacionRepository
        );

        contexto.setAttribute(
                TICKET_REPOSITORY,
                ticketRepository
        );
    }

    @Override
    public void contextDestroyed(ServletContextEvent evento) {
    }
}
