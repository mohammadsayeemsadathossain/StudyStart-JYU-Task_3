package fi.jyu.ties4560.rest.service;

import fi.jyu.ties4560.rest.model.Document;
import fi.jyu.ties4560.rest.exception.ProfileNotFoundException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class DocumentService {
    private static Map<Long, Document> documents = new ConcurrentHashMap<>();
    private static AtomicLong idCounter = new AtomicLong(1);
    
    static {
        // Initialize with sample data
        Document doc1 = new Document(1L, 1L, "PASSPORT", "passport_mikael.pdf", "VERIFIED");
        Document doc2 = new Document(2L, 1L, "TRANSCRIPT", "transcript_mikael.pdf", "PENDING");
        documents.put(1L, doc1);
        documents.put(2L, doc2);
        idCounter.set(3);
    }
    
    public List<Document> getAllDocuments(long profileId) {
        return documents.values().stream()
                .filter(d -> d.getProfileId() == profileId)
                .collect(Collectors.toList());
    }
    
    public Document getDocument(long profileId, long documentId) {
        Document document = documents.get(documentId);
        if (document == null || document.getProfileId() != profileId) {
            throw new ProfileNotFoundException("Document with id " + documentId + 
                                             " not found for profile " + profileId);
        }
        return document;
    }
    
    public Document addDocument(long profileId, Document document) {
        document.setId(idCounter.getAndIncrement());
        document.setProfileId(profileId);
        document.setUploadDate(new Date());
        documents.put(document.getId(), document);
        return document;
    }
    
    public Document updateDocument(long profileId, Document document) {
        Document existing = documents.get(document.getId());
        if (existing == null || existing.getProfileId() != profileId) {
            throw new ProfileNotFoundException("Document with id " + document.getId() + 
                                             " not found for profile " + profileId);
        }
        document.setProfileId(profileId);
        documents.put(document.getId(), document);
        return document;
    }
    
    public void removeDocument(long profileId, long documentId) {
        Document document = documents.get(documentId);
        if (document == null || document.getProfileId() != profileId) {
            throw new ProfileNotFoundException("Document with id " + documentId + 
                                             " not found for profile " + profileId);
        }
        documents.remove(documentId);
    }
}