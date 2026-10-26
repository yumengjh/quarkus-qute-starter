package com.example.web;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path("/api/hello")
@Produces(MediaType.APPLICATION_JSON)
public class GreetingResource {

    @GET
    public Greeting hello(@QueryParam("name") String name) {
        return new Greeting("你好，" + normalizeName(name) + "！");
    }

    static String normalizeName(String name) {
        return name == null || name.isBlank() ? "开发者" : name.strip();
    }

    public record Greeting(String message) {
    }
}
