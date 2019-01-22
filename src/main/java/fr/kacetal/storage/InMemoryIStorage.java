package fr.kacetal.storage;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class InMemoryIStorage implements IStorage {
    
    private final int shortLinkLength;
    
    private final ConcurrentMap<String, String> storage;
    
    public InMemoryIStorage(final int initialCapacity, final int shortLinkLength) {
        this.shortLinkLength = shortLinkLength;
        this.storage = new ConcurrentHashMap<>(initialCapacity);
    }
    
    public InMemoryIStorage() {
        this(100, 8);
    }
    
    @Override
    public String addLink(final String longLink) {
        String shortLink = makeShortLinkFromLongLink(longLink);
        storage.put(shortLink, longLink);
        return shortLink;
    }
    
    @Override
    public String getLink(final String shortLink) {
        return storage.getOrDefault(shortLink, "Link not found");
    }
    
    @Override
    public String removeLink(String shortLink) {
        return storage.remove(shortLink);
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String key : storage.keySet()) {
            sb.append("/links/").append(key).append("=").append(storage.get(key)).append("\n");
        }
        return sb.toString();
    }
    
    private String makeShortLinkFromLongLink(final String longLink) {
        
        try {
            // getInstance() method is called with algorithm SHA-512
            MessageDigest md = MessageDigest.getInstance("SHA-512");
    
            // digest() method is called
            // to calculate message digest of the input string
            // returned as array of byte
            byte[] messageDigest = md.digest(longLink.getBytes(StandardCharsets.UTF_8));
    
            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);
    
            // Convert message digest into hex value
            String hashtext = no.toString(16);
    
            // Add preceding 0s to make it 32 bit
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            
            //return the short link
            return hashtext.substring(0, shortLinkLength >= hashtext.length() ? hashtext.length() : shortLinkLength);
    
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    
        return "";
    }
    
    
}
