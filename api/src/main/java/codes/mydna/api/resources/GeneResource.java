package codes.mydna.api.resources;

import codes.mydna.api.resources.definitions.GeneResourceDefinition;
import codes.mydna.http.Headers;
import codes.mydna.lib.Gene;
import codes.mydna.services.GeneService;
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

@Path("gene")
@Tag(name = "Gene")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class GeneResource implements GeneResourceDefinition {

    @Context
    private UriInfo uriInfo;

    @Inject
    private GeneService geneService;

    @Override
    public Response getGenes(){
        QueryParameters qp = QueryParametersBuilder.buildDefault(uriInfo.getRequestUri().getRawQuery());
        EntityList<Gene> genes = geneService.getGenes(qp);
        return Response.ok().entity(genes.getList()).header(Headers.XTotalCount, genes.getCount()).build();
    }

    @Override
    public Response getGene(String id){
        return Response.ok(geneService.getGene(id)).build();
    }

    @Override
    public Response insertGene(Gene gene){
        return Response.ok(geneService.insertGene(gene)).build();
    }

    @Override
    public Response updateGene(String id, Gene gene) {
        return Response.ok(geneService.updateGene(gene, id)).build();
    }

    @Override
    public Response deleteGene(String id){
        return Response.ok(geneService.removeGene(id)).build();
    }
    
}
