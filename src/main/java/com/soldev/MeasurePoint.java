package com.soldev;


import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by kjansen on 27/12/14.
 * This is the class the hold all the measured points of the SmartMeter *
 */
class MeasurePoint {
    private static final Logger LOG = LoggerFactory.getLogger(MeasurePoint.class);

    private int id;
    private int currentPower = 0;
    private int totalGas = 0;
    private int totalDalPower = 0;
    private int totalPiekPower = 0;
    private DateTime measureDateTime;

    private MeasurePoint() {
    }

    public MeasurePoint(int tDPower, int tPPower, int cpower, int tGas, DateTime mDateTime) {
        this.totalDalPower = tDPower;
        this.totalPiekPower = tPPower;
        this.currentPower = cpower;
        this.totalGas = tGas;
        this.measureDateTime = mDateTime;
    }

    public void printContents() {
        LOG.debug("measureDateTime=" + this.measureDateTime.toString());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCurrentPower() {
        return currentPower;
    }

    public void setCurrentPower(int cPower) {
        this.currentPower = cPower;
    }

    public int getTotalGas() {
        return totalGas;
    }

    public void setTotalGas(int tGas) {
        this.totalGas = tGas;
    }

    public int getTotalDalPower() {
        return totalDalPower;
    }

    public void setTotalDalPower(int tDPower) {
        this.totalDalPower = tDPower;
    }

    public int getTotalPiekPower() {
        return totalPiekPower;
    }

    public void setTotalPiekPower(int tPPower) {
        this.totalPiekPower = tPPower;
    }

    public DateTime getMeasureDateTime() {
        return measureDateTime;
    }

    public void setMeasureDateTime(DateTime mDateTime) {
        this.measureDateTime = mDateTime;
    }
}
