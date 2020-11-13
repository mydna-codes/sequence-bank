package codes.mydna.api.resources;

import codes.mydna.entities.SampleEntity;
import codes.mydna.services.SampleService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("sample")
public class SampleResource {

    @Inject
    private SampleService sampleService;

    @GET
    public Response getSamples(){
        return Response.ok(sampleService.getSamples()).build();
    }

    @POST
    public Response addSamples(SampleEntity entity){
        return Response.ok(sampleService.addSampleEntity(entity)).build();
    }

}
