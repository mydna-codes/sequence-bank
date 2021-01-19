package codes.mydna.sequence_bank.api.mappers;

import codes.mydna.rest.exceptions.ExceptionResponse;

import javax.json.stream.JsonParsingException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class JsonParsingExceptionMapper implements ExceptionMapper<JsonParsingException> {

    @Override
    public Response toResponse(JsonParsingException e) {
        int statusCode = Response.Status.BAD_REQUEST.getStatusCode();
        ExceptionResponse response = new ExceptionResponse();
        response.setStatusCode(statusCode);
        response.setMessage(e.getMessage());
        return Response.status(statusCode).entity(response).build();
    }
}
