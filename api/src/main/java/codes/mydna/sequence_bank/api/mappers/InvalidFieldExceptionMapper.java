package codes.mydna.sequence_bank.api.mappers;

import codes.mydna.rest.exceptions.ExceptionResponse;
import com.kumuluz.ee.rest.exceptions.InvalidFieldValueException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class InvalidFieldExceptionMapper implements ExceptionMapper<InvalidFieldValueException>{

    @Override
    public Response toResponse(InvalidFieldValueException e) {
        int statusCode = Response.Status.BAD_REQUEST.getStatusCode();
        ExceptionResponse response = new ExceptionResponse();
        response.setStatusCode(statusCode);
        response.setMessage(e.getMessage());
        return Response.status(statusCode).entity(response).build();
    }
}
