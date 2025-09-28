package fi.jyu.ties4560.rest.resource;

import java.nio.file.Paths;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.NotSupportedException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import fi.jyu.ties4560.rest.model.Document;
import fi.jyu.ties4560.rest.model.DocumentType;
import fi.jyu.ties4560.rest.service.DocumentService;

@Path("/profiles/{profileId}/documents/{documentType}")
@Produces(MediaType.APPLICATION_JSON)
public class DocumentUploadResource {
	private final DocumentService documentService = new DocumentService();
	private static final java.nio.file.Path UPLOAD_DIR = Paths.get(System.getProperty("catalina.base", "."), "uploads");
	private static final java.util.Set<String> ALLOWED_CT =
	        new java.util.HashSet<>(java.util.Arrays.asList(
	            "application/pdf", "image/jpeg", "image/png"
	        ));
	
	private static String extFor(String ct) {
        if ("application/pdf".equalsIgnoreCase(ct)) return ".pdf";
        if ("image/jpeg".equalsIgnoreCase(ct))      return ".jpg";
        if ("image/png".equalsIgnoreCase(ct))       return ".png";
        return "";
    }
	
	@POST
	@Path("/upload")
	@Consumes({ "application/pdf", "image/jpeg", "image/png" })
	public Response uploadPdf(@PathParam("profileId") long profileId,
            @PathParam("documentType") String docTypeRaw,
            InputStream bodyStream,
            @Context UriInfo uriInfo,
            @HeaderParam("Content-Type") String contentType,
            @HeaderParam("Content-Length") @DefaultValue("0") long clen) throws IOException {
		if (bodyStream == null) throw new BadRequestException("PDF, JPEG or PNG body required");
		if (contentType == null || !ALLOWED_CT.contains(contentType.toLowerCase())) {
            throw new NotSupportedException(
                "Unsupported media type. Allowed: application/pdf, image/jpeg, image/png");
        }
		final DocumentType docType = DocumentType.fromString(docTypeRaw);
		
		Files.createDirectories(UPLOAD_DIR);
		String storedName = System.currentTimeMillis() + "_" + docType.name() + extFor(contentType);
	    java.nio.file.Path target = UPLOAD_DIR.resolve(storedName);
	    Files.copy(bodyStream, target, StandardCopyOption.REPLACE_EXISTING);

	    
	    Document doc = new Document();
	    doc.setProfileId(profileId);
	    doc.setDocumentType(docTypeRaw);
	    doc.setFileName(storedName);
	    doc.setStoragePath(target.toString());
	    doc.setContentType(contentType);
	    doc.setSizeBytes(clen > 0 ? clen : Files.size(target));
	    doc.setStatus("UPLOADED");
	    doc.setUploadDate(new Date());
	    
	    Document created = documentService.addDocument(profileId, doc);
	    
	    Map<String, Object> result = new LinkedHashMap<>();
	    result.put("document", created);

	    URI location = uriInfo.getAbsolutePathBuilder().path("..").path(String.valueOf(created.getId())).build();
	    addLinks(created, profileId, uriInfo);
		
		return Response.created(location).entity(result).build();
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
