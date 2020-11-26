package codes.mydna.integrations;

import com.kumuluz.ee.configuration.utils.ConfigurationUtil;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;

import javax.enterprise.context.ApplicationScoped;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Logger;

@ApplicationScoped
public class LiquibaseInitializer {

    private static Logger LOG = Logger.getLogger(LiquibaseInitializer.class.getName());

    public void initialize() {
        final ConfigurationUtil config = ConfigurationUtil.getInstance();

        // Get datasources count
        Optional<Integer> datasourcesCount = config.getListSize("kumuluzee.datasources");
        Optional<Integer> liquibaseDatasourcesCount = config.getListSize("liquibase.datasources");

        if(liquibaseDatasourcesCount.isEmpty()) {
            // do nothing
            return;
        }

        if(datasourcesCount.isEmpty()) {
            LOG.severe("No datasources specified. Skipping Liquibase initialization...");
            return;
        }

        // Iterate trough configured Liquibase datasources
        for(int i = 0; i < liquibaseDatasourcesCount.get(); i++) {
            String liquibaseDatasource = "liquibase.datasources[" + i + "]";

            // Check if datasource merge is enabled
            boolean enabled = config.getBoolean(liquibaseDatasource + ".enabled").orElse(false);
            if (!enabled){
                // do nothing
                continue;
            }

            // Check if datasource jndi-name is provided
            Optional<String> ldbJndiName = config.get(liquibaseDatasource + ".jndi-name");
            if(ldbJndiName.isEmpty()) {
                LOG.severe("No 'jndi-name' provided for " + liquibaseDatasource);
                continue;
            }

            // Iterate trough configured datasources
            for(int j = 0; j < datasourcesCount.get(); j++){
                String datasource = "kumuluzee.datasources[" + i + "]";

                Optional<String> dbJndiName = config.get(datasource + ".jndi-name");

                Optional<String> changelog = config.get(liquibaseDatasource + ".changelog");
                if(changelog.isEmpty()) {
                    LOG.severe("Changelog not provided for " + liquibaseDatasource);
                    break;
                }

                if(dbJndiName.isPresent() && dbJndiName.equals(ldbJndiName)){
                    Optional<String> url = config.get(datasource + ".connection-url");
                    Optional<String> username = config.get(datasource + ".username");
                    Optional<String> password = config.get(datasource + ".password");

                    // Check credentials
                    if(url.isEmpty() || username.isEmpty() || password.isEmpty()) {
                        break;
                    }

                    // startup dropAll
                    boolean dropAll = config.getBoolean(liquibaseDatasource + ".startup.dropAll").orElse(false);
                    if(dropAll) {
                        dropAll(url.get(), username.get(), password.get(), changelog.get());
                    }

                    // startup update
                    boolean update = config.getBoolean(liquibaseDatasource + ".startup.update").orElse(false);
                    if(update) {
                        update(url.get(), username.get(), password.get(), changelog.get());
                    }

                    // Stop iteration trough datasources
                    break;
                }
            }
        }
    }

    public void update(String url, String username, String password, String changelog){
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            Liquibase liquibase =  new liquibase.Liquibase(changelog, new ClassLoaderResourceAccessor(), database);
            liquibase.update(new Contexts(), new LabelExpression());
        } catch (SQLException | LiquibaseException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropAll(String url, String username, String password, String changelog){
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            Liquibase liquibase =  new liquibase.Liquibase(changelog, new ClassLoaderResourceAccessor(), database);
            liquibase.dropAll();
        } catch (SQLException | LiquibaseException e) {
            throw new RuntimeException(e);
        }
    }

}
