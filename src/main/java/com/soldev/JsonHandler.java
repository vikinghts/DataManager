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
@SuppressWarnings("WeakerAccess")
@Path("/")
public class JsonHandler {
    private static final int STATUS_OK = 200;
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
        DataManager dm = DataManager.getInstance();

        dm.addMeasurePoint(jsonObject.getInt("totalDalPower"),
                jsonObject.getInt("totalPiekPower"),
                jsonObject.getInt("CurrentPower"),
                jsonObject.getInt("totalGas"),
                mDateTime);

        // return HTTP response 200 in case of success
        return Response.status(STATUS_OK).entity(dataManagerBuilder.toString()).build();
    }

    @GET
    @Path("/amIAlive/{param}")
    public Response amIAlive(@PathParam("param") String msg) {

        String output = "DataManger Is ALIVE : " + msg;

        return Response.status(STATUS_OK).entity(output).build();

    }

    @GET
    @Path("/getMeasurePoints")
    public Response getMeasurePoints() {
        DataManager dm = DataManager.getInstance();

        String output = dm.listAllMeasurePoints();

        return Response.status(STATUS_OK).entity(output).build();

    }

    @GET
    @Path("/getLastWeekMeasurePoints")
    public Response getLastWeekMeasurePoints() {
        DataManager dm = DataManager.getInstance();

        String output = dm.listMeasurePoints(7);

        return Response.status(STATUS_OK).entity(output).build();

    }

    @GET
    @Path("/getLastDayMeasurePoints")
    public Response getLastDayMeasurePoints() {
        DataManager dm = DataManager.getInstance();

        String output = dm.listMeasurePoints(1);

        return Response.status(STATUS_OK).entity(output).build();

    }


    @GET
    @Path("/listMeasurePointsToday")
    public Response listMeasurePointsToday() {
        DataManager dm = DataManager.getInstance();

        String output = dm.listMeasurePointsToday();

        return Response.status(STATUS_OK).entity(output).build();
    }

}


