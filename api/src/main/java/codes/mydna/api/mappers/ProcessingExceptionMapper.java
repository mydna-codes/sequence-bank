package codes.mydna.api.mappers;

import codes.mydna.exceptions.ExceptionResponse;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class JsonbExceptionMapper implements ExceptionMapper<ProcessingException> {

    @Override
    public Response toResponse(ProcessingException e) {
        int statusCode = Response.Status.BAD_REQUEST.getStatusCode();
        ExceptionResponse response = new ExceptionResponse();
        response.setStatusCode(statusCode);
        response.setMessage(e.getMessage());
        return Response.status(statusCode).entity(response).build();
    }
}
