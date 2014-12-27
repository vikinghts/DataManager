package com.soldev;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONObject;

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
        JSONObject jsonObject = new JSONObject(dataManagerBuilder.toString());
        System.out.println(jsonObject);
        // structure
        // totalDalPower totalPiekPower CurrentPower totalGas MeasureDataTime
        System.out.println(jsonObject.getInt("totalDalPower"));
        System.out.println(jsonObject.getInt("totalPiekPower"));
        System.out.println(jsonObject.getInt("CurrentPower"));
        System.out.println(jsonObject.getInt("totalGas"));
        System.out.println(jsonObject.getInt("MeasureDataTime"));
        Long iMDateTime = jsonObject.getLong("MeasureDataTime");
        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyyMMddHHmmss");
        DateTime mDateTime = fmt.parseDateTime(iMDateTime.toString());
        // int cpower, int tGas, int tDPower, int tPPower, DateTime mDateTime
        MeasurePoint measurePoint = new MeasurePoint(jsonObject.getInt("totalDalPower"),
                jsonObject.getInt("totalPiekPower"),
                jsonObject.getInt("CurrentPower"),
                jsonObject.getInt("totalGas"),
                mDateTime);

        measurePoint.printContents();
        
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
