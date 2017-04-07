package com.firstfactory.api.resources;

import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;

@Path("documents")
public class DocumentResources {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllDocuments() {
        String response = "List all documents: true";
        return Response.ok().entity(response).build();
    }

    @GET
    @Path("/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOneDocument(@PathParam("name") String fileName) {
        String response = "List document for: " + fileName;
        return Response.ok().entity(response).build();
    }

    @DELETE
    @Path("/{name}")
    public void deleteDocument(@PathParam("name") String fileName) {
        throw new UnsupportedOperationException();
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public void uploadDocument(@FormDataParam("file") InputStream inputStream,
                               @FormDataParam("notes") FormDataBodyPart notes) {
        throw new UnsupportedOperationException();
    }
}
