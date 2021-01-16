package codes.mydna.api.resources;

import codes.mydna.api.resources.definitions.EnzymeResourceDefinition;
import codes.mydna.auth.common.models.User;
import codes.mydna.auth.keycloak.KeycloakContext;
import codes.mydna.http.Headers;
import codes.mydna.lib.Enzyme;
import codes.mydna.services.EnzymeService;
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

@Path("enzyme")
@Tag(name = "Enzyme")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@SecureResource
@RequestScoped
public class EnzymeResource implements EnzymeResourceDefinition {

    @Context
    private UriInfo uriInfo;

    @Inject
    private EnzymeService enzymeService;

    @Inject
    private KeycloakContext keycloakContext;

    private User user;

    @PostConstruct
    private void retrieveUser(){
        user = keycloakContext.getUser();
    }

    @Override
    @PublicResource
    public Response getEnzymes(){
        User user = keycloakContext.getUser();
        QueryParameters qp = QueryParametersBuilder.buildDefault(uriInfo.getRequestUri().getQuery());
//        QueryUtil.addOrReplaceFilter(qp, new QueryFilter("ownerId", FilterOperation.EQ, user.getId()));
        EntityList<Enzyme> enzymes = enzymeService.getEnzymes(qp, user);
        return Response.ok().entity(enzymes.getList()).header(Headers.XTotalCount, enzymes.getCount()).build();
    }

    @Override
    @PublicResource
    public Response getEnzyme(String id){
        return Response.ok(enzymeService.getEnzyme(id, user)).build();
    }

    @Override
    @AuthenticatedAllowed
    public Response insertEnzyme(Enzyme enzyme){
        return Response.ok(enzymeService.insertEnzyme(enzyme, user)).build();
    }

    @Override
    @AuthenticatedAllowed
    public Response updateEnzyme(String id, Enzyme enzyme) {
        return Response.ok(enzymeService.updateEnzyme(enzyme, id, user)).build();
    }

    @Override
    @AuthenticatedAllowed
    public Response deleteEnzyme(String id){
        return Response.ok(enzymeService.removeEnzyme(id, user)).build();
    }

}
