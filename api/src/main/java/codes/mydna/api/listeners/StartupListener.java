package codes.mydna.api.listeners;

import javax.enterprise.context.ApplicationScoped;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.logging.Logger;

@ApplicationScoped
public class StartupListener implements ServletContextListener {

    private static final Logger LOG = Logger.getLogger(StartupListener.class.getSimpleName());

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        LOG.info("Startup listener initialized.");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        LOG.info("Startup listener destroyed.");
    }
}
