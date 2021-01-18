package codes.mydna.api.resources;

import codes.mydna.api.resources.definitions.GeneResourceDefinition;
import codes.mydna.auth.common.models.User;
import codes.mydna.auth.keycloak.KeycloakContext;
import codes.mydna.http.Headers;
import codes.mydna.lib.Gene;
import codes.mydna.services.GeneService;
import codes.mydna.utils.EntityList;
import codes.mydna.utils.QueryParametersBuilder;
import com.kumuluz.ee.rest.beans.QueryParameters;
import com.mjamsek.auth.keycloak.annotations.AuthenticatedAllowed;
import com.mjamsek.auth.keycloak.annotations.PublicResource;
import com.mjamsek.auth.keycloak.annotations.SecureResource;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.annotation.PostConstruct;
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
@SecureResource
@RequestScoped
public class GeneResource implements GeneResourceDefinition {

    @Context
    private UriInfo uriInfo;

    @Inject
    private GeneService geneService;

    @Inject
    private KeycloakContext keycloakContext;

    private User user;

    @PostConstruct
    private void retrieveUser(){
        user = keycloakContext.getUser();
    }

    @Override
    @PublicResource
    public Response getGenes(){
        QueryParameters qp = QueryParametersBuilder.buildDefault(uriInfo.getRequestUri().getQuery());
//        QueryUtil.addOrReplaceFilter(qp, new QueryFilter("ownerId", FilterOperation.EQ, user.getId()));
        EntityList<Gene> genes = geneService.getGenes(qp, user);
        return Response.ok().entity(genes.getList()).header(Headers.XTotalCount, genes.getCount()).build();
    }

    @Override
    @PublicResource
    public Response getGene(String id){
        return Response.ok(geneService.getGene(id, user)).build();
    }

    @Override
    @AuthenticatedAllowed
    public Response insertGene(Gene gene){
        return Response.ok(geneService.insertGene(gene, user)).build();
    }

    @Override
    @AuthenticatedAllowed
    public Response updateGene(String id, Gene gene) {
        return Response.ok(geneService.updateGene(gene, id, user)).build();
    }

    @Override
    @AuthenticatedAllowed
    public Response deleteGene(String id){
        return Response.ok(geneService.removeGene(id, user)).build();
    }
    
}
