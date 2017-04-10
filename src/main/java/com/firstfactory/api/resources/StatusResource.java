package com.firstfactory.api.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("status")
public class StatusResource {

    /**
     * Verifies the API is up and functional
     *
     * @return String with message API is alive
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return "API is up ";
    }
}
