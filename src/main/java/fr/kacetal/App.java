package fr.kacetal;

import fr.kacetal.storage.InMemoryIStorage;
import fr.kacetal.storage.IStorage;

import static spark.Spark.*;

public class App {
    
    public static void main(String[] args) {
    
        final IStorage IStorage = new InMemoryIStorage();
        
        get("/links/:id", (request, response) -> IStorage.getLink(request.params(":id")));
        
        get("/links", (request, response) -> IStorage.toString());
    
        post("/links", (request, response) -> IStorage.addLink(request.body()));
        
        delete("/links/:id", (request, response) -> IStorage.removeLink(request.params(":id")));
    }
}


