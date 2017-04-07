package com.firstfactory.api.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("documents")
public class DocumentResources {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllDocuments() {
        return Response.ok().entity("{}").build();
    }
}
