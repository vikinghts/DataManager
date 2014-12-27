package com.soldev;


import org.joda.time.DateTime;

/**
 * Created by kjansen on 27/12/14.
 */
public class MeasurePoint {
    private Integer currentPower = 0;
    private Integer totalGas = 0;
    private Integer totalDalPower = 0;
    private Integer totalPiekPower = 0;
    private DateTime measureDateTime;

    public MeasurePoint() {
    }

    public MeasurePoint(int tDPower, int tPPower, int cpower, int tGas, DateTime mDateTime) {
        this.totalDalPower = tDPower;
        this.totalPiekPower = tPPower;
        this.currentPower = cpower;
        this.totalGas = tGas;
        this.measureDateTime = mDateTime;
    }

    public void printContents() {
        System.out.println("totalDalPower=" + this.totalDalPower.toString());
        System.out.println("totalPiekPower=" + this.totalPiekPower.toString());
        System.out.println("currentPower=" + this.currentPower.toString());
        System.out.println("totalGas=" + this.totalGas.toString());
        System.out.println("measureDateTime=" + this.measureDateTime.toString());
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
