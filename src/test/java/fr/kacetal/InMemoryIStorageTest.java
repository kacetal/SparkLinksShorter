package fr.kacetal;

import fr.kacetal.storage.InMemoryIStorage;
import fr.kacetal.storage.IStorage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InMemoryIStorageTest {
    
    private IStorage IStorage;
    
    private final String testLongLink = "www.google.com";
    
    @BeforeEach
    public void before() {
        IStorage = new InMemoryIStorage();
    }
    
    @AfterEach
    public void after() {
        IStorage = null;
    }
    
    @Test
    public void addLinkTest() {
        String shortLink = IStorage.addLink("www.google.com");
        String longLink = IStorage.getLink(shortLink);
        
        assertEquals(testLongLink, longLink);
    }
    
    @Test
    public void isLinksSame() {
        String shortLink = IStorage.addLink("www.google.com");
        String testShortLink = IStorage.addLink(testLongLink);
        
        assertEquals(testShortLink, shortLink);
    }
    
    @Test
    public void removeLinkTest() {
        String shortLink = IStorage.addLink(testLongLink);
        assertEquals(testLongLink, IStorage.getLink(shortLink));
        IStorage.removeLink(shortLink);
        assertEquals("Link not found", IStorage.getLink(shortLink));
    }
    
}