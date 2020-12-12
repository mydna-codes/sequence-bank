package codes.mydna.api.resources.definitions;

import codes.mydna.exceptions.RestException;
import codes.mydna.lib.Gene;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import javax.ws.rs.core.Response;

public interface GeneResourceDefinition {

    @Operation(
            description = "Returns list of genes.",
            summary = "Get list of genes",
            tags = "gene",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Request successful.",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Gene.class))
                            ),
                            headers = {@Header(name = "X-Total-Count", schema = @Schema(type = "integer"))}
                    )
            }
    )
    public Response getGenes();

    @Operation(
            description = "Returns a gene by id.",
            summary = "Get gene by id",
            tags = "gene",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Request successful.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Gene.class)
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
    public Response getGene(
            @Parameter(required = true, description = "Gene's id") String id);

    @Operation(
            description = "Inserts gene into database.",
            summary = "Insert gene",
            tags = "gene",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Gene inserted successfully.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Gene.class)
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
    public Response insertGene(
            @Parameter(required = true, description = "Gene object to be inserted") Gene gene);

    @Operation(
            description = "Updates gene with provided id.",
            summary = "Update gene",
            tags = "gene",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Gene updated successfully.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Gene.class)
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
    public Response updateGene(
            @Parameter(required = true, description = "gene's id") String id,
            @Parameter(required = true, description = "New gene object") Gene gene);

    @Operation(
            description = "Deletes gene with provided id.",
            summary = "Delete gene",
            tags = "gene",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Gene deleted successfully.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Gene.class)
                            )
                    )
            }
    )
    public Response deleteGene(
            @Parameter(name = "Id of the gene that will be deleted") String id);
    
}
