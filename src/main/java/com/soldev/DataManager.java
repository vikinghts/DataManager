package com.soldev;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


/**
 * Created by kjansen on 22/12/14.
 * This class parses the meter data and extracts it values to post them to a web socket.
 */
@Path("/")
public class DataManager {
    @POST
    @Path("/dataManager")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response dataManagerREST(InputStream incomingData) {
        StringBuilder dataManagerBuilder = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
            String line = null;
            while ((line = in.readLine()) != null) {
                dataManagerBuilder.append(line);
            }
        } catch (Exception e) {
            System.out.println("Error Parsing: - ");
        }
        System.out.println("Data Received: " + dataManagerBuilder.toString());

        // return HTTP response 200 in case of success
        return Response.status(200).entity(dataManagerBuilder.toString()).build();
    }
}
