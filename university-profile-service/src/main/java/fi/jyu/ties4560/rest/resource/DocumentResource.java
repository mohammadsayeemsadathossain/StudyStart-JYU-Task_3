package fi.jyu.ties4560.rest.resource;

import fi.jyu.ties4560.rest.model.Document;
import fi.jyu.ties4560.rest.service.DocumentService;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;

@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DocumentResource {
    private DocumentService documentService = new DocumentService();
    
    @GET
    public List<Document> getDocuments(@PathParam("profileId") long profileId,
                                     @Context UriInfo uriInfo) {
        List<Document> documents = documentService.getAllDocuments(profileId);
        
        // Add HATEOAS links
        for (Document document : documents) {
            addLinks(document, profileId, uriInfo);
        }
        
        return documents;
    }
    
    @GET
    @Path("/{documentId}")
    public Document getDocument(@PathParam("profileId") long profileId,
                              @PathParam("documentId") long documentId,
                              @Context UriInfo uriInfo) {
        Document document = documentService.getDocument(profileId, documentId);
        addLinks(document, profileId, uriInfo);
        return document;
    }
    
    @POST
    public Response addDocument(@PathParam("profileId") long profileId,
                              Document document,
                              @Context UriInfo uriInfo) {
    	if (document.getDocumentType() == null) {
    	    throw new IllegalArgumentException("documentType is required (PASSPORT, RP_CARD, ACCEPTANCE_LETTER)");
    	  }
        Document newDocument = documentService.addDocument(profileId, document);
        addLinks(newDocument, profileId, uriInfo);
        
        String newId = String.valueOf(newDocument.getId());
        URI uri = uriInfo.getAbsolutePathBuilder().path(newId).build();
        
        return Response.created(uri)
                .entity(newDocument)
                .build();
    }
    
    @PUT
    @Path("/{documentId}")
    public Document updateDocument(@PathParam("profileId") long profileId,
                                 @PathParam("documentId") long documentId,
                                 Document document,
                                 @Context UriInfo uriInfo) {
        document.setId(documentId);
        Document updatedDocument = documentService.updateDocument(profileId, document);
        addLinks(updatedDocument, profileId, uriInfo);
        return updatedDocument;
    }
    
    @DELETE
    @Path("/{documentId}")
    public Response deleteDocument(@PathParam("profileId") long profileId,
                                 @PathParam("documentId") long documentId) {
        documentService.removeDocument(profileId, documentId);
        return Response.noContent().build();
    }
    
    private void addLinks(Document document, long profileId, UriInfo uriInfo) {
        // Self link
        String selfUri = uriInfo.getBaseUriBuilder()
                .path(ProfileResource.class)
                .path(Long.toString(profileId))
                .path("documents")
                .path(Long.toString(document.getId()))
                .build()
                .toString();
        document.addLink(selfUri, "self");
        
        // Profile link
        String profileUri = uriInfo.getBaseUriBuilder()
                .path(ProfileResource.class)
                .path(Long.toString(profileId))
                .build()
                .toString();
        document.addLink(profileUri, "profile");
    }
}