package codes.mydna.api.resources;

import codes.mydna.api.resources.definitions.EnzymeResourceDefinition;
import codes.mydna.http.Headers;
import codes.mydna.lib.Enzyme;
import codes.mydna.services.EnzymeService;
import codes.mydna.utils.EntityList;
import codes.mydna.utils.QueryParametersBuilder;
import com.kumuluz.ee.rest.beans.QueryParameters;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("enzyme")
@Tag(name = "Enzyme")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class EnzymeResource implements EnzymeResourceDefinition {

    @Context
    private UriInfo uriInfo;

    @Inject
    private EnzymeService enzymeService;

    @Override
    public Response getEnzymes(){
        QueryParameters qp = QueryParametersBuilder.buildDefault(uriInfo.getRequestUri().getRawQuery());
        EntityList<Enzyme> enzymes = enzymeService.getEnzymes(qp);
        return Response.ok().entity(enzymes.getList()).header(Headers.XTotalCount, enzymes.getCount()).build();
    }

    @Override
    public Response getEnzyme(String id){
        return Response.ok(enzymeService.getEnzyme(id)).build();
    }

    @Override
    public Response insertEnzyme(Enzyme enzyme){
        return Response.ok(enzymeService.insertEnzyme(enzyme)).build();
    }

    @Override
    public Response updateEnzyme(String id, Enzyme enzyme) {
        return Response.ok(enzymeService.updateEnzyme(enzyme, id)).build();
    }

    @Override
    public Response deleteEnzyme(String id){
        return Response.ok(enzymeService.removeEnzyme(id)).build();
    }

}
