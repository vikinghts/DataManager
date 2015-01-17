package com.soldev;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
public class JsonHandler {
    private static final Logger LOG = LoggerFactory.getLogger(JsonHandler.class);


    public JsonHandler() {
        LOG.debug("JsonHandler init");
    }

    @POST
    @Path("/DataManagerService")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response dataManagerREST(InputStream incomingData) {
        StringBuilder dataManagerBuilder = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
            String line;
            while ((line = in.readLine()) != null) {
                dataManagerBuilder.append(line);
            }
        } catch (Exception e) {
            LOG.error("Error Parsing: - " + e);
        }
        LOG.debug("Data Received: " + dataManagerBuilder.toString());
        // structure
        // totalDalPower totalPiekPower CurrentPower totalGas MeasureDataTime
        JSONObject jsonObject = new JSONObject(dataManagerBuilder.toString());
        Long iMDateTime = jsonObject.getLong("MeasureDataTime");
        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyyMMddHHmmss");
        DateTime mDateTime = fmt.parseDateTime(iMDateTime.toString());
        // return HTTP response 200 in case of success
        DataManager dm = DataManager.getInstance();

        dm.addMeasurePoint(jsonObject.getInt("totalDalPower"),
                jsonObject.getInt("totalPiekPower"),
                jsonObject.getInt("CurrentPower"),
                jsonObject.getInt("totalGas"),
                mDateTime);


        return Response.status(200).entity(dataManagerBuilder.toString()).build();
    }

    @GET
    @Path("/amIAlive/{param}")
    public Response amIAlive(@PathParam("param") String msg) {

        String output = "DataManger Is ALIVE : " + msg;

        return Response.status(200).entity(output).build();

    }

    @GET
    @Path("/getMesurePoints")
    public Response getMesurePoints() {
        DataManager dm = DataManager.getInstance();

        String output = dm.listMeasurePoints();

        return Response.status(200).entity(output).build();

    }

    @GET
    @Path("/getLastWeekMesurePoints")
    public Response getLastWeekMesurePoints() {
        DataManager dm = DataManager.getInstance();

        String output = dm.listLastWeekMeasurePoints();

        return Response.status(200).entity(output).build();

    }

    @GET
    @Path("/getLastDayMesurePoints")
    public Response getLastDayMesurePoints() {
        DataManager dm = DataManager.getInstance();

        String output = dm.listLastDayMeasurePoints();

        return Response.status(200).entity(output).build();

    }


}


