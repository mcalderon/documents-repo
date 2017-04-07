package com.firstfactory.api.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return "API is up " + dtf.format(now);
    }
}
