package fi.jyu.ties4560.rest.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@XmlRootElement
public class Document {
    private long id;
    private long profileId;
    private DocumentType documentType;
    private String fileName;
    private String status;
    private Date uploadDate;
    private Date expiryDate;
    private List<Link> links = new ArrayList<>();
    
    public Document() {}
    
    public Document(long id, long profileId, String documentType, 
                   String fileName, String status) {
        this.id = id;
        this.profileId = profileId;
        this.documentType = DocumentType.fromString(documentType);
        this.fileName = fileName;
        this.status = status;
        this.uploadDate = new Date();
    }
    
    public void addLink(String href, String rel) {
        Link link = new Link(href, rel);
        links.add(link);
    }
    
    // Getters and setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    
    public long getProfileId() { return profileId; }
    public void setProfileId(long profileId) { this.profileId = profileId; }
    
    public String getDocumentType() {
    	return documentType != null ? documentType.name() : null;
    }
    public void setDocumentType(String documentType) {
    	this.documentType = DocumentType.fromString(documentType);
    	}
    
    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public Date getUploadDate() { return uploadDate; }
    public void setUploadDate(Date uploadDate) { this.uploadDate = uploadDate; }
    
    public Date getExpiryDate() { return expiryDate; }
    public void setExpiryDate(Date expiryDate) { this.expiryDate = expiryDate; }
    
    public List<Link> getLinks() { return links; }
    public void setLinks(List<Link> links) { this.links = links; }
}