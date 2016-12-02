package com.kevinnoon.cylonapps;

/**
 * Created by kevin on 23/03/2016.
 */
public class DBPoints {
    private int number;
    private String name;
    private int type;
    private int hardwareType;
    private int digitalOnUnits;
    private int digitalOffUnits;
    private int analogueUnits;

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    private double value;

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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getHardwareType() {
        return hardwareType;
    }

    public void setHardwareType(int hardwareType) {
        this.hardwareType = hardwareType;
    }

    public int getDigitalOnUnits() {
        return digitalOnUnits;
    }

    public void setDigitalOnUnits(Integer digitalOnUnits) {
        this.digitalOnUnits = digitalOnUnits;
    }

    public int getDigitalOffUnits() {
        return digitalOffUnits;
    }

    public void setDigitalOffUnits(Integer digitalOffUnits) {
        this.digitalOffUnits = digitalOffUnits;
    }

    public int getAnalogueUnits() {
        return analogueUnits;
    }

    public void setAnalogueUnits(Integer analogueUnits) {
        this.analogueUnits = analogueUnits;
    }

    @Override
    public String toString() {
        return "DBPoints{" +
                "number=" + number +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", hardwareType=" + hardwareType +
                ", digitalOnUnits='" + digitalOnUnits + '\'' +
                ", digitalOffUnits='" + digitalOffUnits + '\'' +
                ", analogueUnits='" + analogueUnits + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}


