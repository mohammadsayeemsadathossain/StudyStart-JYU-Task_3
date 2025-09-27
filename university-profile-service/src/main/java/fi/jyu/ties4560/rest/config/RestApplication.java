package fi.jyu.ties4560.rest.config;

import fi.jyu.ties4560.rest.resource.ProfileResource;
import fi.jyu.ties4560.rest.exception.ProfileNotFoundExceptionMapper;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("api")
public class RestApplication extends Application {
    
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        
        // Resources
        classes.add(ProfileResource.class);
        
        // Exception Mappers
        classes.add(ProfileNotFoundExceptionMapper.class);
        
        return classes;
    }
}