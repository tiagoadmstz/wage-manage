package io.github.tiagoadmstz.wagemanage;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.annotation.WebServlet;
import jakarta.faces.application.Application;
import jakarta.faces.application.ApplicationFactory;
import jakarta.faces.context.FacesContext;
import jakarta.faces.webapp.FacesServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

@WebServlet(name = "MainServlet", urlPatterns = {"/"})
public class WageManageApplication extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ApplicationFactory applicationFactory = (ApplicationFactory) facesContext.getExternalContext().getApplicationMap().get(ApplicationFactory.class.getName());
            Application application = applicationFactory.getApplication();
            application.getProjectStage(); // Exemplo de uso da Application

            HttpSession session = request.getSession(true);
            session.setAttribute("message", "Hello Jakarta EE!");

            response.getWriter().write("Application started successfully.");
        } catch (Exception e) {
            throw new ServletException("Failed to start application.", e);
        }
    }

    public static void main(String[] args) throws Exception {
        int port = 8080;
        String contextPath = "/";
        String webappDir = "src/main/webapp";

        Server server = new Server(port);
        ServletContextHandler servletContextHandler = new ServletContextHandler();
        servletContextHandler.setContextPath(contextPath);
        servletContextHandler.setResourceBase(webappDir);
        server.setHandler(servletContextHandler);

        ServletHolder facesServletHolder = servletContextHandler.addServlet(FacesServlet.class, "*.xhtml");
        facesServletHolder.setInitParameter("javax.faces.PROJECT_STAGE", "Development");

        server.start();
        server.join();
    }
}
