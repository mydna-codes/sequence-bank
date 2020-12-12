package codes.mydna.api.resources.definitions;

import codes.mydna.exceptions.RestException;
import codes.mydna.lib.Dna;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

public interface DnaResourceDefinition {

    @Operation(
            description = "Returns list of DNAs.",
            summary = "Get list of DNAs",
            tags = "dna",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Request successful.",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Dna.class))
                            ),
                            headers = {@Header(name = "X-Total-Count", schema = @Schema(type = "integer"))}
                    )
            }
    )
    public Response getDnas();

    @Operation(
            description = "Returns a DNA by id.",
            summary = "Get DNA by id",
            tags = "dna",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Request successful.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Dna.class)
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
    public Response getDna(
            @Parameter(required = true, in = ParameterIn.PATH, description = "DNA's id") String id);

    @Operation(
            description = "Inserts DNA into database.",
            summary = "Insert DNA",
            tags = "dna",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "DNA inserted successfully.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Dna.class)
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
    public Response insertDna(
            @Parameter(required = true, description = "DNA object to be inserted") Dna dna);

    @Operation(
            description = "Updates DNA with provided id.",
            summary = "Update DNA",
            tags = "dna",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "DNA updated successfully.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Dna.class)
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
    public Response updateDna(
            @Parameter(required = true, in = ParameterIn.PATH, description = "DNA's id") String id,
            @Parameter(required = true, description = "New DNA object") Dna dna);

    @Operation(
            description = "Deletes DNA with provided id.",
            summary = "Delete DNA",
            tags = "dna",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "DNA deleted successfully.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Dna.class)
                            )
                    )
            }
    )
    public Response deleteDna(
            @Parameter(required = true, in = ParameterIn.PATH, description = "Id of the DNA that will be deleted") String id);

}
