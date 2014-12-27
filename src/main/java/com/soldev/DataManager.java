package com.soldev;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;


/**
 * Created by kjansen on 22/12/14.
 * This class parses the meter data and extracts it values to post them to a web socket.
 */
@Path("/")
public class DataManager {

    @POST
    @Path("/DataManagerService")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response dataManagerREST(InputStream incomingData) {
        StringBuilder dataManagerBuilder = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
            //String line = null;
            String line;
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

    @GET
    @Path("/amIAlive/{param}")
    public Response amIAlive(@PathParam("param") String msg) {

        String output = "DataManger Is ALIVE : " + msg;

        return Response.status(200).entity(output).build();

    }
}
