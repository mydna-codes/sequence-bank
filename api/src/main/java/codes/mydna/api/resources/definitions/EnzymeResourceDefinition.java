package codes.mydna.api.resources.definitions;

import codes.mydna.exceptions.RestException;
import codes.mydna.lib.Enzyme;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.headers.Header;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

public interface EnzymeResourceDefinition {

    @Operation(
            description = "Returns list of enzymes.",
            summary = "Get list of enzymes"
    )
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Enzyme list returned successfully.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(type = SchemaType.ARRAY, implementation = Enzyme.class)
                    ),
                    headers = {@Header(name = "X-Total-Count", schema = @Schema(type = SchemaType.INTEGER))}
            )
    })
    @GET
    public Response getEnzymes();



    @Operation(
            description = "Returns an enzyme found by id.",
            summary = "Get enzyme by id"
    )
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Enzyme object returned successfully.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(type = SchemaType.OBJECT, implementation = Enzyme.class)
                    )
            ),
            @APIResponse(
                    responseCode = "400",
                    description = "Empty or invalid input.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(type = SchemaType.OBJECT, implementation = RestException.class)
                    )
            ),
            @APIResponse(
                    responseCode = "404",
                    description = "Object with provided id does not exist.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(type = SchemaType.OBJECT, implementation = RestException.class)
                    )
            )
    })
    @GET
    @Path("{id}")
    public Response getEnzyme(
            @Parameter(required = true, description = "Enzyme's id") @PathParam("id") String id);



    @Operation(
            description = "Inserts enzyme into database.",
            summary = "Insert enzyme"
    )
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Enzyme inserted successfully.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(type = SchemaType.OBJECT, implementation = Enzyme.class)
                    )
            ),
            @APIResponse(
                    responseCode = "400",
                    description = "Empty or invalid input.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(type = SchemaType.OBJECT, implementation = RestException.class)
                    )
            ),
            @APIResponse(
                    responseCode = "404",
                    description = "Object with provided id does not exist.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(type = SchemaType.OBJECT, implementation = RestException.class)
                    )
            )
    })
    @POST
    public Response insertEnzyme(
            @Parameter(required = true, description = "Enzyme object to be inserted") Enzyme enzyme);



    @Operation(
            description = "Updates enzyme with provided id.",
            summary = "Update enzyme"
    )
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Enzyme updated successfully.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(type = SchemaType.OBJECT, implementation = Enzyme.class)
                    )
            ),
            @APIResponse(
                    responseCode = "400",
                    description = "Empty or invalid input.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(type = SchemaType.OBJECT, implementation = RestException.class)
                    )
            ),
            @APIResponse(
                    responseCode = "404",
                    description = "Object with provided id does not exist.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(type = SchemaType.OBJECT, implementation = RestException.class)
                    )
            )
    })
    @PUT
    @Path("{id}")
    public Response updateEnzyme(
            @Parameter(required = true, description = "Enzyme's id") @PathParam("id")  String id,
            @Parameter(required = true, description = "New enzyme object") Enzyme enzyme);



    @Operation(
            description = "Deletes enzyme with provided id.",
            summary = "Delete enzyme"
    )
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Enzyme deleted successfully.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(type = SchemaType.BOOLEAN)
                    )
            )
    })
    @DELETE
    @Path("{id}")
    public Response deleteEnzyme(
            @Parameter(required = true, description = "Id of the enzyme that will be deleted") @PathParam("id") String id);

}
