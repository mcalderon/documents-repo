package com.firstfactory.api.resources;

import com.firstfactory.api.exception.DocumentHandlerException;
import com.firstfactory.api.service.DefaultDocumentServices;
import com.firstfactory.api.service.DocumentServices;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;

@Path("documents")
@Produces(MediaType.APPLICATION_JSON)
public class DocumentResources {

    private DocumentServices documentServices = new DefaultDocumentServices();

    @GET
    public Response getAllDocuments() {
        try {
            return Response.ok().entity(this.documentServices.listAllDocuments()).build();
        } catch (DocumentHandlerException e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/{id}")
    public Response getOneDocument(@PathParam("id") int documentId) {
        try {
            return Response.ok().entity(this.documentServices.getDocument(documentId)).build();
        } catch (DocumentHandlerException e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/download/{id}")
    public void downloadDocument(@Suspended final AsyncResponse response,
                                 @PathParam("id") int documentId) {
        final String fileName = this.documentServices.getDocument(documentId).getFileText();
        response.resume(
                Response.ok(
                        this.documentServices.downloadDocument(fileName),
                        MediaType.APPLICATION_OCTET_STREAM
                ).header("content-disposition", "attachment; filename = \"" + fileName + "\"").build()
        );
    }

    @DELETE
    @Path("/{id}")
    public Response deleteDocument(@PathParam("id") int documentId) {
        try {
            this.documentServices.deleteDocument(documentId);
            return Response.noContent().build();
        } catch (DocumentHandlerException e) {
            return Response.serverError().entity("error: Failed to upload file").build();
        }
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public void uploadDocument(@Suspended final AsyncResponse response,
                               @FormDataParam("file") InputStream file,
                               @FormDataParam("file") FormDataContentDisposition fileDetail,
                               @FormDataParam("type") FormDataBodyPart type,
                               @FormDataParam("notes") FormDataBodyPart notes) {
        try {
            this.documentServices.createDocument(file, fileDetail.getFileName(), type.getValue(), notes.getValue());
            response.resume(Response.accepted().build());
        } catch (DocumentHandlerException e) {
            response.resume(Response.serverError().entity(e.getMessage()).build());
        }
    }
}
