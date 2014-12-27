package com.soldev;


import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Created by kjansen on 27/12/14.
 */
public class MeasurePoint {
    DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyyMMddHHmmss");
    private Integer currentPower = 0;
    private Integer totalGas = 0;
    private Integer totalDalPower = 0;
    private Integer totalPiekPower = 0;
    private DateTime measureDateTime;

    public MeasurePoint() {
    }

    public MeasurePoint(int cpower, int tGas, int tDPower, int tPPower, DateTime mDateTime) {
        this.currentPower = cpower;
        this.totalGas = tGas;
        this.totalDalPower = tDPower;
        this.totalPiekPower = tPPower;
        this.measureDateTime = mDateTime;
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
