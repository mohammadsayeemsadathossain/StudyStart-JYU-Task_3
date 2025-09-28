package fi.jyu.ties4560.rest.exception;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;


@Provider
public class BadRequestExceptionMapper implements ExceptionMapper<IllegalArgumentException> {
  @Override public Response toResponse(IllegalArgumentException ex) {
    Map<String, Object> body = new HashMap<>();
    body.put("error", "Bad Request");
    body.put("message", ex.getMessage());
    return Response.status(Response.Status.BAD_REQUEST).entity(body).type(MediaType.APPLICATION_JSON).build();
  }
}
