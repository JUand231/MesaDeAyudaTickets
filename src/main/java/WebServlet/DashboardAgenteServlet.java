package WebServlet;

import modelo.Ticket;
import servicio.TicketService;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/dashboardAgente")
public class DashboardAgenteServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null
                || session.getAttribute("idUsuario") == null) {

            response.sendRedirect(
                    request.getContextPath() + "/login");

            return;
        }

        int idUsuario
                = (Integer) session.getAttribute("idUsuario");

        int idRol
                = (Integer) session.getAttribute("idRol");

        // Verificar que sea Agente
        if (idRol != 2) {

            response.sendRedirect(
                    request.getContextPath() + "/login");

            return;
        }

        TicketService ticketService
                = (TicketService) getServletContext()
                        .getAttribute(
                                AppContextListener.TICKET_SERVICE);

        List<Ticket> tickets
                = ticketService.listarPorAgente(idUsuario);

        int totalTickets = tickets.size();
        int pendientes = 0;
        int enProceso = 0;
        int resueltos = 0;

        for (Ticket ticket : tickets) {

            String estado
                    = ticket.getEstadoNombre();

            if (estado == null) {
                continue;
            }

            if (estado.equalsIgnoreCase("NUEVO")
                    || estado.equalsIgnoreCase("PENDIENTE")) {

                pendientes++;

            } else if (estado.equalsIgnoreCase("EN PROCESO")) {

                enProceso++;

            } else if (estado.equalsIgnoreCase("RESUELTO")) {

                resueltos++;
            }
        }

        request.setAttribute(
                "tickets", tickets);

        request.setAttribute(
                "totalTickets", totalTickets);

        request.setAttribute(
                "pendientes", pendientes);

        request.setAttribute(
                "enProceso", enProceso);

        request.setAttribute(
                "resueltos", resueltos);

        request.getRequestDispatcher(
                "/WEB-INF/jsp/Agente/dashboardAgente.jsp")
                .forward(request, response);
    }
}
