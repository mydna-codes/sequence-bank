package codes.mydna.api.resources;

import codes.mydna.api.resources.definitions.GeneResourceDefinition;
import codes.mydna.http.Headers;
import codes.mydna.lib.Gene;
import codes.mydna.services.GeneService;
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

@Path("gene")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class GeneResource implements GeneResourceDefinition {

    @Context
    private UriInfo uriInfo;

    @Inject
    private GeneService geneService;

    @GET
    public Response getGenes(){
        QueryParameters qp = QueryParametersBuilder.build(uriInfo.getRequestUri().getRawQuery());
        EntityList<Gene> genes = geneService.getGenes(qp);
        return Response.ok().entity(genes.getList()).header(Headers.XTotalCount, genes.getCount()).build();
    }

    @GET
    @Path("{id}")
    public Response getGene(@PathParam("id") String id){
        return Response.ok(geneService.getGene(id)).build();
    }

    @POST
    public Response insertGene(Gene gene){
        return Response.ok(geneService.insertGene(gene)).build();
    }

    @PUT
    @Path("{id}")
    public Response updateGene(@PathParam("id") String id, Gene gene) {
        return Response.ok(geneService.updateGene(gene, id)).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteGene(@PathParam("id") String id){
        return Response.ok(geneService.removeGene(id)).build();
    }
    
}
