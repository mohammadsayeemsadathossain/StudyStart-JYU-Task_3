package fi.jyu.ties4560.rest.resource;

import fi.jyu.ties4560.rest.model.Profile;
import fi.jyu.ties4560.rest.service.ProfileService;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;

@Path("/profiles")
@Produces(MediaType.APPLICATION_JSON)
public class ProfileResource {
    private ProfileService profileService = new ProfileService();
    
    @GET
    public List<Profile> getProfiles(@QueryParam("department") String department,
                                   @QueryParam("start") int start,
                                   @QueryParam("size") int size,
                                   @Context UriInfo uriInfo) {
        List<Profile> profiles;
        
        if (department != null && !department.isEmpty()) {
            profiles = profileService.getProfilesByDepartment(department);
        } else if (start >= 0 && size > 0) {
            profiles = profileService.getProfilesPaginated(start, size);
        } else {
            profiles = profileService.getAllProfiles();
        }
        
        // Add HATEOAS links
        for (Profile profile : profiles) {
            addLinks(profile, uriInfo);
        }
        
        return profiles;
    }
    
    @GET
    @Path("/{profileId}")
    public Profile getProfile(@PathParam("profileId") long profileId,
                            @Context UriInfo uriInfo) {
        Profile profile = profileService.getProfile(profileId);
        addLinks(profile, uriInfo);
        return profile;
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addProfile(Profile profile, @Context UriInfo uriInfo) {
        Profile newProfile = profileService.addProfile(profile);
        addLinks(newProfile, uriInfo);
        
        String newId = String.valueOf(newProfile.getId());
        URI uri = uriInfo.getAbsolutePathBuilder().path(newId).build();
        
        return Response.created(uri)
                .entity(newProfile)
                .build();
    }
    
    @PUT
    @Path("/{profileId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Profile updateProfile(@PathParam("profileId") long profileId,
                               Profile profile,
                               @Context UriInfo uriInfo) {
        profile.setId(profileId);
        Profile updatedProfile = profileService.updateProfile(profile);
        addLinks(updatedProfile, uriInfo);
        return updatedProfile;
    }
    
    @DELETE
    @Path("/{profileId}")
    public Response deleteProfile(@PathParam("profileId") long profileId) {
        profileService.removeProfile(profileId);
        return Response.noContent().build();
    }
    
    // Nested resource for documents
    @Path("/{profileId}/documents")
    public DocumentResource getDocumentResource() {
        return new DocumentResource();
    }
    
    private void addLinks(Profile profile, UriInfo uriInfo) {
        // Self link
        String selfUri = uriInfo.getBaseUriBuilder()
                .path(ProfileResource.class)
                .path(Long.toString(profile.getId()))
                .build()
                .toString();
        profile.addLink(selfUri, "self");
        
        // Documents link
        String documentsUri = uriInfo.getBaseUriBuilder()
                .path(ProfileResource.class)
                .path(ProfileResource.class, "getDocumentResource")
                .resolveTemplate("profileId", profile.getId())
                .build()
                .toString();
        profile.addLink(documentsUri, "documents");
    }
}