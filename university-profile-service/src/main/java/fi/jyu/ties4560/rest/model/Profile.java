package fi.jyu.ties4560.rest.model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@XmlRootElement
public class Profile {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String department;
    private String program;
    private Date createdDate;
    private List<Link> links = new ArrayList<>();
    
    public Profile() {}
    
    public Profile(long id, String firstName, String lastName, String email, 
                  String department, String program) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.department = department;
        this.program = program;
        this.createdDate = new Date();
    }
    
    // HATEOAS support
    public void addLink(String href, String rel) {
        Link link = new Link(href, rel);
        links.add(link);
    }
    
    // Getters and setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    
    public String getProgram() { return program; }
    public void setProgram(String program) { this.program = program; }
    
    public Date getCreatedDate() { return createdDate; }
    public void setCreatedDate(Date createdDate) { this.createdDate = createdDate; }
    
    public List<Link> getLinks() { return links; }
    public void setLinks(List<Link> links) { this.links = links; }
}