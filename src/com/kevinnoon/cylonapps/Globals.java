package com.kevinnoon.cylonapps;

/**
 * Created by kevin on 31/03/2016.
 */
public class Globals {
    public int dotNetNo;
    private int number;
    private String name;
    private int servicePeriod;
    private String pointType;
    private int id;
    private int hasModule;

    public int getDotNetNo() {
        return dotNetNo;
    }

    public void setDotNetNo(int dotNetNo) {
        this.dotNetNo = dotNetNo;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getServicePeriod() {
        return servicePeriod;
    }

    public void setServicePeriod(int servicePeriod) {
        this.servicePeriod = servicePeriod;
    }

    public String getPointType() {
        return pointType;
    }

    public void setPointType(String pointType) {
        this.pointType = pointType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHasModule() {
        return hasModule;
    }

    public void setHasModule(int hasModule) {
        this.hasModule = hasModule;
    }

    @Override
    public String toString() {
        return "Globals{" +
                "dotNetNo=" + dotNetNo +
                ", number=" + number +
                ", name='" + name + '\'' +
                ", servicePeriod=" + servicePeriod +
                ", pointType='" + pointType + '\'' +
                ", id=" + id +
                ", hasModule=" + hasModule +
                '}';
    }
}
