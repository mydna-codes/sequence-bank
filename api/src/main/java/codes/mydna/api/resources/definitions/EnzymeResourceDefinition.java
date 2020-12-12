package codes.mydna.api.resources.definitions;

import codes.mydna.exceptions.RestException;
import codes.mydna.lib.Enzyme;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import javax.ws.rs.core.Response;

public interface EnzymeResourceDefinition {

    @Operation(
            description = "Returns list of enzymes.",
            summary = "Get list of enzymes",
            tags = "enzyme",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Request successful.",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Enzyme.class))
                            ),
                            headers = {@Header(name = "X-Total-Count", schema = @Schema(type = "integer"))}
                    )
            }
    )
    public Response getEnzymes();

    @Operation(
            description = "Returns a enzyme by id.",
            summary = "Get enzyme by id",
            tags = "enzyme",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Request successful.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Enzyme.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Empty or invalid input.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = RestException.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Object with provided id does not exist.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = RestException.class)
                            )
                    )
            }
    )
    public Response getEnzyme(
            @Parameter(required = true, description = "Enzyme's id") String id);

    @Operation(
            description = "Inserts enzyme into database.",
            summary = "Insert enzyme",
            tags = "enzyme",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Enzyme inserted successfully.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Enzyme.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Empty or invalid input.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = RestException.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Object with provided id does not exist.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = RestException.class)
                            )
                    )
            }
    )
    public Response insertEnzyme(
            @Parameter(required = true, description = "Enzyme object to be inserted") Enzyme enzyme);

    @Operation(
            description = "Updates enzyme with provided id.",
            summary = "Update enzyme",
            tags = "enzyme",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Enzyme updated successfully.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Enzyme.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Empty or invalid input.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = RestException.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Object with provided id does not exist.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = RestException.class)
                            )
                    )
            }
    )
    public Response updateEnzyme(
            @Parameter(required = true, description = "enzyme's id") String id,
            @Parameter(required = true, description = "New enzyme object") Enzyme enzyme);

    @Operation(
            description = "Deletes enzyme with provided id.",
            summary = "Delete enzyme",
            tags = "enzyme",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Enzyme deleted successfully.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Enzyme.class)
                            )
                    )
            }
    )
    public Response deleteEnzyme(
            @Parameter(name = "Id of the enzyme that will be deleted") String id);
}
