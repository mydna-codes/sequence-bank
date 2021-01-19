package codes.mydna.sequence_bank.api.resources;

import codes.mydna.sequence_bank.api.resources.definitions.DemoResourceDefinition;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Path("demo")
@Tag(name = "Other")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class DemoResource implements DemoResourceDefinition {

    @Override
    public Response demo() {

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("projectInfo1.json")) {
            StringBuilder content = new StringBuilder();
            if(inputStream != null){
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line);
                    content.append(System.lineSeparator());
                }
            }
            return Response.ok(content.toString()).build();
        } catch (IOException e) {
            return Response.serverError().build();
        }

    }

}
