package com.soldev;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.joda.time.DateTime;

import java.util.Iterator;
import java.util.List;


/**
 * Created by kjansen on 22/12/14.
 * This class parses the meter data and extracts it values to post them to a web socket.
 */
public class DataManager {
    private static SessionFactory factory;

    private static DataManager instance = null;

    protected DataManager() {
        // Exists only to defeat instantiation.
    }

    //singleton to make sure we only have one datahandler.
    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
            instance.init();
        }
        return instance;
    }

    protected void init() {
        System.out.println("DataManager init");
        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
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

    /* Method to  READ all the employees */
    public String listMeasurePoints() {
        Session session = factory.openSession();
        Transaction tx = null;
        String response = "{\"AllMeasurePoints\":[";
        try {
            tx = session.beginTransaction();
            List measurePoints = session.createQuery("FROM MeasurePoint").list();
            for (Iterator iterator =
                         measurePoints.iterator(); iterator.hasNext(); ) {
                MeasurePoint measurePoint = (MeasurePoint) iterator.next();
                Integer curPower = measurePoint.getCurrentPower();
                response = response + ("{\"MeasureDateTime\":\"" + measurePoint.getMeasureDateTime().toString() + "\",");
                response = response + ("\"CurrentPower\":" + curPower.toString() + "},");
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        //TODO Dirty fix by using json object
        response = response.substring(0, response.length() - 1);
        System.out.print(response + "]}");
        return response.concat("]}");
    }


    public String listLastWeekMeasurePoints() {
        Session session = factory.openSession();
        Transaction tx = null;
        String response = "{\"AllMeasurePoints\":[";
        try {
            tx = session.beginTransaction();
            List measurePoints = session.createQuery("select totalDalPower,totalPiekPower,currentPower,totalGas,measureDateTime FROM MeasurePoint where measureDateTime = current_date()").list();
            for (Iterator iterator =
                         measurePoints.iterator(); iterator.hasNext(); ) {
                MeasurePoint measurePoint = (MeasurePoint) iterator.next();
                Integer curPower = measurePoint.getCurrentPower();
                response = response + ("{\"MeasureDateTime\":\"" + measurePoint.getMeasureDateTime().toString() + "\",");
                response = response + ("\"CurrentPower\":" + curPower.toString() + "},");
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        //TODO Dirty fix by using json object
        response = response.substring(0, response.length() - 1);
        System.out.print(response + "]}");
        return response.concat("]}");
    }

    public String listLastDayMeasurePoints() {
        Session session = factory.openSession();
        Transaction tx = null;
        String response = "{\"AllMeasurePoints\":[";
        try {
            tx = session.beginTransaction();
            List measurePoints = session.createQuery("select totalDalPower,totalPiekPower,currentPower,totalGas,measureDateTime FROM MeasurePoint where measureDateTime = current_date()").list();
            for (Iterator iterator =
                         measurePoints.iterator(); iterator.hasNext(); ) {
                MeasurePoint measurePoint = (MeasurePoint) iterator.next();
                Integer curPower = measurePoint.getCurrentPower();
                response = response + ("{\"MeasureDateTime\":\"" + measurePoint.getMeasureDateTime().toString() + "\",");
                response = response + ("\"CurrentPower\":" + curPower.toString() + "},");
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        //TODO Dirty fix by using json object
        response = response.substring(0, response.length() - 1);
        System.out.print(response + "]}");
        return response.concat("]}");
    }



}
