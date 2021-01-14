package codes.mydna.api.resources.definitions;

import codes.mydna.api.resources.definitions.openapi.examples.OpenApiGeneExamples;
import codes.mydna.exceptions.RestException;
import codes.mydna.lib.Gene;
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

public interface GeneResourceDefinition {

    @Operation(
            description = "Returns list of genes.",
            summary = "Get list of genes"
    )
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Gene list returned successfully.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    type = SchemaType.ARRAY,
                                    implementation = Gene.class,
                                    example = OpenApiGeneExamples.Responses.GET_GENES
                            )
                    ),
                    headers = {@Header(name = "X-Total-Count", schema = @Schema(type = SchemaType.INTEGER))}
            )
    })
    @GET
    public Response getGenes();


    @Operation(
            description = "Returns an gene found by id.",
            summary = "Get gene by id"
    )
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Gene object returned successfully.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    type = SchemaType.OBJECT,
                                    implementation = Gene.class,
                                    example = OpenApiGeneExamples.Responses.GET_GENE
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
    public Response getGene(
            @Parameter(
                    required = true,
                    description = "Gene's id",
                    example = OpenApiGeneExamples.EXAMPLE_ID
            )
            @PathParam("id") String id);


    @Operation(
            description = "Inserts gene into database.",
            summary = "Insert gene"
    )
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Gene inserted successfully.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    type = SchemaType.OBJECT,
                                    implementation = Gene.class,
                                    example = OpenApiGeneExamples.Responses.INSERT_GENE
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
            description = "Gene to be inserted",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                            type = SchemaType.OBJECT,
                            implementation = Gene.class,
                            example = OpenApiGeneExamples.Requests.INSERT_GENE
                    )
            )
    )
    @POST
    public Response insertGene(Gene gene);


    @Operation(
            description = "Updates gene with provided id.",
            summary = "Update gene"
    )
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Gene updated successfully.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    type = SchemaType.OBJECT,
                                    implementation = Gene.class,
                                    example = OpenApiGeneExamples.Responses.UPDATE_GENE
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
            description = "Gene that will update the current one",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                            type = SchemaType.OBJECT,
                            implementation = Gene.class,
                            example = OpenApiGeneExamples.Requests.UPDATE_GENE
                    )
            )
    )
    @PUT
    @Path("{id}")
    public Response updateGene(
            @Parameter(
                    required = true,
                    description = "Gene's id",
                    example = OpenApiGeneExamples.EXAMPLE_ID
            )
            @PathParam("id") String id,
            Gene gene);


    @Operation(
            description = "Deletes gene with provided id.",
            summary = "Delete gene"
    )
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Gene deleted successfully.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    type = SchemaType.BOOLEAN,
                                    example = OpenApiGeneExamples.Responses.DELETE_GENE
                            )
                    )
            )
    })
    @DELETE
    @Path("{id}")
    public Response deleteGene(
            @Parameter(
                    required = true,
                    description = "Id of the gene that will be deleted",
                    example = OpenApiGeneExamples.EXAMPLE_ID
            )
            @PathParam("id") String id);

}
