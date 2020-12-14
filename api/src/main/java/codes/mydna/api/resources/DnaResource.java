package codes.mydna.api.resources;

import codes.mydna.api.resources.definitions.DnaResourceDefinition;
import codes.mydna.http.Headers;
import codes.mydna.lib.Dna;
import codes.mydna.services.DnaService;
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

@Path("dna")
@Tag(name = "Dna")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class DnaResource implements DnaResourceDefinition {

    @Context
    private UriInfo uriInfo;

    @Inject
    private DnaService dnaService;

    @Override
    public Response getDnas(){
        QueryParameters qp = QueryParametersBuilder.buildDefault(uriInfo.getRequestUri().getRawQuery());
        EntityList<Dna> dnas = dnaService.getDnas(qp);
        return Response.ok().entity(dnas.getList()).header(Headers.XTotalCount, dnas.getCount()).build();
    }

    @Override
    public Response getDna(String id){
        return Response.ok(dnaService.getDna(id)).build();
    }

    @Override
    public Response insertDna(Dna dna){
        return Response.ok(dnaService.insertDna(dna)).build();
    }

    @Override
    public Response updateDna(String id, Dna dna) {
        return Response.ok(dnaService.updateDna(dna, id)).build();
    }

    @Override
    public Response deleteDna(String id){
        return Response.ok(dnaService.removeDna(id)).build();
    }
}
