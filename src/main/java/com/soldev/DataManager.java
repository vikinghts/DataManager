package com.soldev;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


/**
 * Created by kjansen on 22/12/14.
 * This class parses the meter data and extracts it values to post them to a web socket.
 */
public class DataManager {
    private static final String ALL_MEASURE_POINTS = "AllMeasurePoints";
    private static final String MEASURE_DATE_TIME = "MeasureDateTime";
    private static final String CURRENT_POWER = "CurrentPower";
    private static final String ESC_QUOTE = "\"";
    private static final Logger LOG = LoggerFactory.getLogger(DataManager.class);
    private static SessionFactory factory;
    private static DataManager instance = null;

    private DataManager() {
        // Exists only to defeat instantiation.
    }

    //singleton to make sure we only have one data handler.
    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
            instance.init();
        }
        return instance;
    }

    void init() {
        LOG.debug("DataManager init");
        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Exception ex) {
            LOG.error("Failed to create sessionFactory object." + ex);
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
            if (tx != null) {
                tx.rollback();
            }
            LOG.error("Failed to addMeasurePoint :" + e);
        } finally {
            session.close();
        }
        return measurePointID;
    }

    /* Method to  READ all the employees */
    public String listAllMeasurePoints() {
        Session session = factory.openSession();
        Transaction tx = null;
        String response = "{\"" + ALL_MEASURE_POINTS + "\":[";
        try {
            tx = session.beginTransaction();
            List measurePoints = session.createQuery("FROM MeasurePoint").list();
            for (Object measurePoint1 : measurePoints) {
                MeasurePoint measurePoint = (MeasurePoint) measurePoint1;
                Integer curPower = measurePoint.getCurrentPower();
                response = response + ("{" + ESC_QUOTE + MEASURE_DATE_TIME + ESC_QUOTE + ":" + ESC_QUOTE + measurePoint.getMeasureDateTime().toString() + ESC_QUOTE + ",");
                response = response + (ESC_QUOTE + CURRENT_POWER + ESC_QUOTE + ":" + curPower.toString() + "},");
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            LOG.error("Failed to listMeasurePoints :" + e);
        } finally {
            session.close();
        }
        //TODO Dirty fix by using json object
        response = response.substring(0, response.length() - 1);
        LOG.debug(response + "]}");
        return response.concat("]}");
    }


    public String listMeasurePoints(Integer days) {
        LOG.debug("listMeasurePoints Number of days : " + days.toString());
        Session session = factory.openSession();
        Transaction tx = null;
        String response = "{\"" + ALL_MEASURE_POINTS + "\":[";
        try {
            tx = session.beginTransaction();
            List measurePoints = session.createQuery("select totalDalPower,totalPiekPower,currentPower,totalGas,measureDateTime FROM MeasurePoint where measureDateTime = current_date()").list();
            for (Object measurePoint1 : measurePoints) {
                MeasurePoint measurePoint = (MeasurePoint) measurePoint1;
                Integer curPower = measurePoint.getCurrentPower();
                response = response + ("{" + ESC_QUOTE + MEASURE_DATE_TIME + ESC_QUOTE + ":" + ESC_QUOTE + measurePoint.getMeasureDateTime().toString() + ESC_QUOTE + ",");
                response = response + (ESC_QUOTE + CURRENT_POWER + ESC_QUOTE + ":" + curPower.toString() + "},");
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            LOG.error("Failed to listLastWeekMeasurePoints :" + e);
        } finally {
            session.close();
        }
        //TODO Dirty fix by using json object
        response = response.substring(0, response.length() - 1);
        LOG.debug(response + "]}");
        return response.concat("]}");
    }
}
