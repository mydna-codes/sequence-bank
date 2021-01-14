package codes.mydna.api.resources.definitions;

import codes.mydna.exceptions.RestException;
import codes.mydna.lib.Dna;
import codes.mydna.api.resources.definitions.openapi.examples.OpenApiDnaExamples;
import codes.mydna.openapi.examples.OpenApiExceptionExamples;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.headers.Header;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

public interface DnaResourceDefinition {

    @Operation(
            description = "Returns list of user's DNAs.",
            summary = "Get list of user's DNAs"
    )
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "DNA list returned successfully.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    type = SchemaType.ARRAY,
                                    implementation = Dna.class,
                                    example = OpenApiDnaExamples.Responses.GET_DNAS
                            )
                    ),
                    headers = {@Header(name = "X-Total-Count", schema = @Schema(type = SchemaType.INTEGER))}
            )
    })
    @GET
    public Response getDnas();


    @Operation(
            description = "Returns a DNA found by id.",
            summary = "Get DNA by id"
    )
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "DNA object returned successfully.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    type = SchemaType.OBJECT,
                                    implementation = Dna.class,
                                    example = OpenApiDnaExamples.Responses.GET_DNA
                            )
                    )
            ),
            @APIResponse(
                    responseCode = "400",
                    description = "Empty or invalid input.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    type = SchemaType.OBJECT,
                                    implementation = RestException.class,
                                    example = OpenApiExceptionExamples.Response.BAD_REQUEST
                            )
                    )
            ),
            @APIResponse(
                    responseCode = "404",
                    description = "Object with provided id does not exist.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    type = SchemaType.OBJECT,
                                    implementation = RestException.class,
                                    example = OpenApiExceptionExamples.Response.NOT_FOUND
                            )
                    )
            )
    })
    @GET
    @Path("{id}")
    public Response getDna(
            @Parameter(
                    required = true,
                    description = "DNA's id",
                    example = OpenApiDnaExamples.EXAMPLE_ID
            )
            @PathParam("id") String id);


    @Operation(
            description = "Inserts DNA into database.",
            summary = "Insert DNA"
    )
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "DNA inserted successfully.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    type = SchemaType.OBJECT,
                                    implementation = Dna.class,
                                    example = OpenApiDnaExamples.Responses.INSERT_DNA
                            )
                    )
            ),
            @APIResponse(
                    responseCode = "400",
                    description = "Empty or invalid input.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    type = SchemaType.OBJECT,
                                    implementation = RestException.class,
                                    example = OpenApiExceptionExamples.Response.BAD_REQUEST
                            )
                    )
            ),
            @APIResponse(
                    responseCode = "404",
                    description = "Object with provided id does not exist.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    type = SchemaType.OBJECT,
                                    implementation = RestException.class,
                                    example = OpenApiExceptionExamples.Response.NOT_FOUND
                            )
                    )
            )
    })
    @RequestBody(
            required = true,
            description = "Dna to be inserted",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                            type = SchemaType.OBJECT,
                            implementation = Dna.class,
                            example = OpenApiDnaExamples.Requests.INSERT_DNA
                    )
            )
    )
    @POST
    public Response insertDna(Dna dna);


    @Operation(
            description = "Updates DNA with provided id.",
            summary = "Update DNA"
    )
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "DNA updated successfully.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    type = SchemaType.OBJECT,
                                    implementation = Dna.class,
                                    example = OpenApiDnaExamples.Responses.UPDATE_DNA
                            )
                    )
            ),
            @APIResponse(
                    responseCode = "400",
                    description = "Empty or invalid input.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    type = SchemaType.OBJECT,
                                    implementation = RestException.class,
                                    example = OpenApiExceptionExamples.Response.BAD_REQUEST
                            )
                    )
            ),
            @APIResponse(
                    responseCode = "404",
                    description = "Object with provided id does not exist.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    type = SchemaType.OBJECT,
                                    implementation = RestException.class,
                                    example = OpenApiExceptionExamples.Response.NOT_FOUND
                            )
                    )
            )
    })
    @RequestBody(
            required = true,
            description = "Dna that will update the current one",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                            type = SchemaType.OBJECT,
                            implementation = Dna.class,
                            example = OpenApiDnaExamples.Requests.UPDATE_DNA
                    )
            )
    )
    @PUT
    @Path("{id}")
    public Response updateDna(
            @Parameter(
                    required = true,
                    description = "DNA's id",
                    example = OpenApiDnaExamples.EXAMPLE_ID
            )
            @PathParam("id") String id,
            Dna dna);


    @Operation(
            description = "Deletes DNA with provided id.",
            summary = "Delete DNA"
    )
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "DNA deleted successfully.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    type = SchemaType.BOOLEAN,
                                    example = OpenApiDnaExamples.Responses.DELETE_DNA
                            )
                    )
            )
    })
    @DELETE
    @Path("{id}")
    public Response deleteDna(
            @Parameter(
                    required = true,
                    description = "Id of the DNA that will be deleted",
                    example = OpenApiDnaExamples.EXAMPLE_ID
            )
            @PathParam("id") String id);

}
