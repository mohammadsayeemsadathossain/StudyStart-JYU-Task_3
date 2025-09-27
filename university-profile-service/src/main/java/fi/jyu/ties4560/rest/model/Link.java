package fi.jyu.ties4560.rest.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Link {
    private String href;
    private String rel;
    
    public Link() {}
    
    public Link(String href, String rel) {
        this.href = href;
        this.rel = rel;
    }
    
    // Getters and setters
    public String getHref() { return href; }
    public void setHref(String href) { this.href = href; }
    public String getRel() { return rel; }
    public void setRel(String rel) { this.rel = rel; }
}