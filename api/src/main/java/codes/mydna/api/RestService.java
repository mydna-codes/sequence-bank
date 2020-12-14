package codes.mydna.api;


import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@OpenAPIDefinition(
        info = @Info(title="Sequence bank API",
                version="v1",
                contact = @Contact(),
                license = @License(name = "No licence"),
                description = "API for storing sequences like DNAs, genes and enzymes"
        )
)
@ApplicationPath("v1")
public class RestService extends Application {

}
