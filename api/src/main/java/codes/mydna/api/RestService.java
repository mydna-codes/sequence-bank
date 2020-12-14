package codes.mydna.api;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.servers.Servers;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@OpenAPIDefinition(
        info = @Info(title="Sequence bank API",
                version="v1",
                contact = @Contact(),
                license = @License(),
                description = "API for storing sequences like DNAs, genes and enzymes"
        ),
        servers = {
                @Server(url = "https://sequence-bank-test.mydna.codes"),
                @Server(url = "http://localhost:8080")
        }
)
@ApplicationPath("v1")
public class RestService extends Application {

}
