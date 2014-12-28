package com.soldev;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.joda.time.DateTime;



/**
 * Created by kjansen on 22/12/14.
 * This class parses the meter data and extracts it values to post them to a web socket.
 */
public class DataManager {
    private static DataManager DM;
    private static SessionFactory factory;

    //public static void main(String[] args) {
    public void init() {

        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
        DM = new DataManager();
        DateTime mDateTime = new DateTime();
        Integer mPID1 = DM.addMeasurePoint(800, 487, 293, 20, mDateTime);
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
