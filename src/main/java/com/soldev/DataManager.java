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

    //public static void main(String[] args) {
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
                response = response + ("{\"MeasureDataTime\":" + measurePoint.getMeasureDateTime().toString() + ",");
                response = response + ("\"CurrentPower\":" + curPower.toString() + "}");
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        System.out.print(response + "]}");
        return response.concat("]}");
    }

}
