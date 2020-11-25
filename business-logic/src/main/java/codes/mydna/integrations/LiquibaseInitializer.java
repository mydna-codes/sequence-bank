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

@ApplicationScoped
public class LiquibaseInitializer {

    public void initialize() {
        ConfigurationUtil config = ConfigurationUtil.getInstance();
        String dbUrl      = config.get("kumuluzee.datasources[0].connection-url").orElseThrow(RuntimeException::new);
        String dbUsername = config.get("kumuluzee.datasources[0].username").orElseThrow(RuntimeException::new);
        String dbPassword = config.get("kumuluzee.datasources[0].password").orElseThrow(RuntimeException::new);
        String changelog  = config.get("liquibase.master-changelog").orElseThrow(RuntimeException::new);
        try {
            Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            Liquibase liquibase =  new liquibase.Liquibase(changelog, new ClassLoaderResourceAccessor(), database);
            liquibase.update(new Contexts(), new LabelExpression());
        } catch (SQLException | LiquibaseException e) {
            throw new RuntimeException(e);
        }
    }

}
