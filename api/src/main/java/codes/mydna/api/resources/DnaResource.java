package codes.mydna.api.resources;

import codes.mydna.http.Headers;
import codes.mydna.lib.Dna;
import codes.mydna.services.DnaService;
import codes.mydna.utils.EntityList;
import codes.mydna.utils.QueryParametersBuilder;
import com.kumuluz.ee.rest.beans.QueryParameters;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("dna")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class DnaResource {

    @Context
    private UriInfo uriInfo;

    @Inject
    private DnaService dnaService;

    @GET
    public Response getDnas(){
        QueryParameters qp = QueryParametersBuilder.build(uriInfo.getRequestUri().getRawQuery());
        EntityList<Dna> dnas = dnaService.getDnas(qp);
        return Response.ok().entity(dnas.getList()).header(Headers.XTotalCount, dnas.getCount()).build();
    }

    @GET
    @Path("{id}")
    public Response getDna(@PathParam("id") String id){
        return Response.ok(dnaService.getDna(id)).build();
    }

    @POST
    public Response insertDna(Dna dna){
        return Response.ok(dnaService.insertDna(dna)).build();
    }

    @PUT
    @Path("{id}")
    public Response updateDna(@PathParam("id") String id, Dna dna) {
        return Response.ok(dnaService.updateDna(dna, id)).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteDna(@PathParam("id") String id){
        return Response.ok(dnaService.removeDna(id)).build();
    }
}
