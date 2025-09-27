package fi.jyu.ties4560.rest.exception;

import fi.jyu.ties4560.rest.model.ErrorMessage;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ProfileNotFoundExceptionMapper implements ExceptionMapper<ProfileNotFoundException> {
    
    @Override
    public Response toResponse(ProfileNotFoundException ex) {
        ErrorMessage errorMessage = new ErrorMessage(
            ex.getMessage(), 
            404, 
            "https://docs.university.fi/api/errors"
        );
        
        return Response.status(Status.NOT_FOUND)
                .entity(errorMessage)
                .build();
    }
}