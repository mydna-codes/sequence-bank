package codes.mydna.sequence_bank.api.resources.definitions;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import javax.ws.rs.GET;
import javax.ws.rs.core.Response;

public interface DemoResourceDefinition {

    @Operation(
            description = "Returns some data about the project. " +
                    "This endpoint is used for automatic testing and is irrelevant for the project itself.",
            summary = "Returns some data about the project."
    )
    @APIResponse(
            responseCode = "200",
            description = "Project data returned successfully.",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(type= SchemaType.OBJECT)
            )
    )
    @GET
    public Response demo();

}
