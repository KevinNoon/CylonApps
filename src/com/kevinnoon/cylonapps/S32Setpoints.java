package com.kevinnoon.cylonapps;

/**
 * Created by kevin on 12/11/2016.
 */
public class S32Setpoints {
    Integer PointNumber;
    Integer Type;

    public Integer getPointNumber() {
        return PointNumber;
    }

    public void setPointNumber(Integer pointNumber) {
        PointNumber = pointNumber;
    }

    public Integer getType() {
        return Type;
    }

    public void setType(Integer type) {
        Type = type;
    }

    public Double getValue() {
        return Value;
    }

    public void setValue(Double value) {
        Value = value;
    }

    Double Value;

    @Override
    public String toString() {
        return "S32Setpoints{" +
                "PointNumber=" + PointNumber +
                ", Type=" + Type +
                ", Value=" + Value +
                '}';
    }
}
