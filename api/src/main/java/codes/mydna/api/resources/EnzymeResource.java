package codes.mydna.api.resources;

import codes.mydna.http.Headers;
import codes.mydna.lib.Enzyme;
import codes.mydna.services.EnzymeService;
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

@Path("enzyme")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class EnzymeResource {

    @Context
    private UriInfo uriInfo;

    @Inject
    private EnzymeService enzymeService;

    @GET
    public Response getEnzymes(){
        QueryParameters qp = QueryParametersBuilder.build(uriInfo.getRequestUri().getRawQuery());
        EntityList<Enzyme> enzymes = enzymeService.getEnzymes(qp);
        return Response.ok().entity(enzymes.getList()).header(Headers.XTotalCount, enzymes.getCount()).build();
    }

    @GET
    @Path("{id}")
    public Response getEnzyme(@PathParam("id") String id){
        return Response.ok(enzymeService.getEnzyme(id)).build();
    }

    @POST
    public Response insertEnzyme(Enzyme enzyme){
        return Response.ok(enzymeService.insertEnzyme(enzyme)).build();
    }

    @PUT
    @Path("{id}")
    public Response updateEnzyme(@PathParam("id") String id, Enzyme enzyme) {
        return Response.ok(enzymeService.updateEnzyme(enzyme, id)).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteEnzyme(@PathParam("id") String id){
        return Response.ok(enzymeService.removeEnzyme(id)).build();
    }

}
