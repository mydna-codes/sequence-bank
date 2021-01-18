package codes.mydna.api.resources;

import codes.mydna.api.resources.definitions.DnaResourceDefinition;
import codes.mydna.auth.common.models.User;
import codes.mydna.auth.keycloak.KeycloakContext;
import codes.mydna.http.Headers;
import codes.mydna.lib.Dna;
import codes.mydna.services.DnaService;
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

@Path("dna")
@Tag(name = "Dna")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@SecureResource
@RequestScoped
public class DnaResource implements DnaResourceDefinition {

    @Context
    private UriInfo uriInfo;

    @Inject
    private DnaService dnaService;

    @Inject
    private KeycloakContext keycloakContext;

    private User user;

    @PostConstruct
    private void retrieveUser(){
        user = keycloakContext.getUser();
    }

    @Override
    @PublicResource
    public Response getDnas(){
        QueryParameters qp = QueryParametersBuilder.buildDefault(uriInfo.getRequestUri().getQuery());
//        QueryUtil.addOrReplaceFilter(qp, new QueryFilter("ownerId", FilterOperation.EQ, user.getId()));
        EntityList<Dna> dnas = dnaService.getDnas(qp, user);
        return Response.ok().entity(dnas.getList()).header(Headers.XTotalCount, dnas.getCount()).build();
    }

    @Override
    @PublicResource
    public Response getDna(String id){
        return Response.ok(dnaService.getDna(id, user)).build();
    }

    @Override
    @AuthenticatedAllowed
    public Response insertDna(Dna dna){
        return Response.ok(dnaService.insertDna(dna, user)).build();
    }

    @Override
    @AuthenticatedAllowed
    public Response updateDna(String id, Dna dna) {
        return Response.ok(dnaService.updateDna(dna, id, user)).build();
    }

    @Override
    @AuthenticatedAllowed
    public Response deleteDna(String id){
        return Response.ok(dnaService.removeDna(id, user)).build();
    }
}
