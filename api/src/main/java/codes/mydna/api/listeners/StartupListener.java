package codes.mydna.api.listeners;

import codes.mydna.integrations.LiquibaseInitializer;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.logging.Logger;

@ApplicationScoped
public class StartupListener implements ServletContextListener {

    private Logger LOG = Logger.getLogger(StartupListener.class.getSimpleName());

    @Inject
    private LiquibaseInitializer liquibaseInitializer;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        LOG.info("Startup listener initialized.");
        liquibaseInitializer.initialize();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        LOG.info("Startup listener destroyed.");
    }
}
