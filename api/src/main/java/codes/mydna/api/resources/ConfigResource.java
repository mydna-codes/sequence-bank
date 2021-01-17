package codes.mydna.api.resources;

import codes.mydna.auth.common.RealmRole;
import codes.mydna.configurations.ConfigUserLimits;
import com.mjamsek.auth.keycloak.annotations.RealmRolesAllowed;
import com.mjamsek.auth.keycloak.annotations.SecureResource;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("config")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@SecureResource
@RequestScoped
public class ConfigResource {

    @Inject
    private ConfigUserLimits config;

    @GET
    @RealmRolesAllowed(RealmRole.ADMIN)
    public Response config(){

        String response = "{\n" +
                "    \"limits\": {\n" +
                "        \"guest\": {\n" +
                "            \"max-dna-length\": " + config.getGuestMaxDnaLength() + ",\n" +
                "            \"max-enzyme-length\": " + config.getGuestMaxEnzymeLength() + ",\n" +
                "            \"max-gene-length\": " + config.getGuestMaxGeneLength() + "\n" +
                "        },\n" +
                "        \"reg\": {\n" +
                "            \"max-dna-length\": " + config.getRegMaxDnaLength() + ",\n" +
                "            \"max-enzyme-length\": " + config.getRegMaxEnzymeLength() + ",\n" +
                "            \"max-gene-length\": " + config.getRegMaxGeneLength() + "\n" +
                "        },\n" +
                "        \"pro\": {\n" +
                "            \"max-dna-length\": " + config.getProMaxDnaLength() + ",\n" +
                "            \"max-enzyme-length\": " + config.getProMaxEnzymeLength() + ",\n" +
                "            \"max-gene-length\": " + config.getProMaxGeneLength() + "\n" +
                "        }\n" +
                "    }\n" +
                "}";

        return Response.ok().entity(response).build();
    }

}
