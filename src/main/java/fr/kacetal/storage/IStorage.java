package fr.kacetal.storage;

public interface IStorage {
    
    String addLink(final String longLink);
    
    String getLink(final String shortLink);
    
    String removeLink(final String shortLink);
}
