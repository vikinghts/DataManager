package com.soldev;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
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
    private static DataManager DM;
    private static SessionFactory factory;

    public static void main(String[] args) {
        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
        DM = new DataManager();

    }

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
        // structure
        // totalDalPower totalPiekPower CurrentPower totalGas MeasureDataTime
        JSONObject jsonObject = new JSONObject(dataManagerBuilder.toString());
        Long iMDateTime = jsonObject.getLong("MeasureDataTime");
        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyyMMddHHmmss");
        DateTime mDateTime = fmt.parseDateTime(iMDateTime.toString());
        /*
        MeasurePoint measurePoint = new MeasurePoint(jsonObject.getInt("totalDalPower"),
                jsonObject.getInt("totalPiekPower"),
                jsonObject.getInt("CurrentPower"),
                jsonObject.getInt("totalGas"),
                mDateTime);
        measurePoint.printContents();
        */
        Integer mPID1 = DM.addMeasurePoint(jsonObject.getInt("totalDalPower"),
                jsonObject.getInt("totalPiekPower"),
                jsonObject.getInt("CurrentPower"),
                jsonObject.getInt("totalGas"),
                mDateTime);

        // return HTTP response 200 in case of success
        return Response.status(200).entity(dataManagerBuilder.toString()).build();
    }

    @GET
    @Path("/amIAlive/{param}")
    public Response amIAlive(@PathParam("param") String msg) {

        String output = "DataManger Is ALIVE : " + msg;

        return Response.status(200).entity(output).build();

    }

    /* Method to CREATE an measurePoint in the database */
    public Integer addMeasurePoint(int tDPower, int tPPower, int cpower, int tGas, DateTime mDateTime) {
        Session session = factory.openSession();
        Transaction tx = null;
        Integer measurePointID = null;
        try {
            tx = session.beginTransaction();
            MeasurePoint measurePoint = new MeasurePoint(tDPower, tPPower, cpower, tGas, mDateTime);
            measurePointID = (Integer) session.save(measurePoint);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return measurePointID;
    }

}
