package fi.jyu.ties4560.rest.service;

import fi.jyu.ties4560.rest.model.Profile;
import fi.jyu.ties4560.rest.exception.ProfileNotFoundException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class ProfileService {
    private static Map<Long, Profile> profiles = new ConcurrentHashMap<>();
    private static AtomicLong idCounter = new AtomicLong(1);
    
    static {
        // Initialize with sample data
        Profile profile1 = new Profile(1L, "Mikael", "Virtanen", "mikael.virtanen@jyu.fi", 
                                     "IT Faculty", "TIES4560");
        Profile profile2 = new Profile(2L, "Aino", "Korhonen", "aino.korhonen@jyu.fi", 
                                     "Business", "MBA");
        profiles.put(1L, profile1);
        profiles.put(2L, profile2);
        idCounter.set(3);
    }
    
    public List<Profile> getAllProfiles() {
        return new ArrayList<>(profiles.values());
    }
    
    public List<Profile> getProfilesByDepartment(String department) {
        return profiles.values().stream()
                .filter(p -> p.getDepartment().equalsIgnoreCase(department))
                .collect(Collectors.toList());
    }
    
    public List<Profile> getProfilesPaginated(int start, int size) {
        List<Profile> allProfiles = new ArrayList<>(profiles.values());
        if (start >= allProfiles.size()) {
            return new ArrayList<>();
        }
        int end = Math.min(start + size, allProfiles.size());
        return allProfiles.subList(start, end);
    }
    
    public Profile getProfile(long id) {
        Profile profile = profiles.get(id);
        if (profile == null) {
            throw new ProfileNotFoundException("Profile with id " + id + " not found");
        }
        return profile;
    }
    
    public Profile addProfile(Profile profile) {
        profile.setId(idCounter.getAndIncrement());
        profile.setCreatedDate(new Date());
        profiles.put(profile.getId(), profile);
        return profile;
    }
    
    public Profile updateProfile(Profile profile) {
        if (!profiles.containsKey(profile.getId())) {
            throw new ProfileNotFoundException("Profile with id " + profile.getId() + " not found");
        }
        profiles.put(profile.getId(), profile);
        return profile;
    }
    
    public void removeProfile(long id) {
        Profile removed = profiles.remove(id);
        if (removed == null) {
            throw new ProfileNotFoundException("Profile with id " + id + " not found");
        }
    }
}