package com.kevinnoon.cylonapps;

/**
 * Created by kevin on 01/04/2016.
 */
public class GlobalWideSource extends GlobalHeader {

    public GlobalXMLFunctions.globalType type = GlobalXMLFunctions.globalType.wideSource;
    private int connectionNumber;
    private double defaultValue;
    public Source source = new Source();

    public int getConnectionNumber() {
        return connectionNumber;
    }

    public void setConnectionNumber(int connectionNumber) {
        this.connectionNumber = connectionNumber;
    }

    public double getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(double defaultValue) {
        this.defaultValue = defaultValue;
    }

    public static class Source {
        private int fieldControllerAddress;
        private  int pointAddress;

        public int getFieldControllerAddress() {
            return fieldControllerAddress;
        }

        public void setFieldControllerAddress(int fieldControllerAddress) {
            this.fieldControllerAddress = fieldControllerAddress;
        }

        public int getPointAddress() {
            return pointAddress;
        }

        public void setPointAddress(int pointAddress) {
            this.pointAddress = pointAddress;
        }

        @Override
        public String toString() {
            return "Source{" +
                    "fieldControllerAddress=" + fieldControllerAddress +
                    ", pointAddress=" + pointAddress +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "GlobalWideSource{" + super.toString() +
                "connectionNumber=" + connectionNumber +
                ", defaultValue=" + defaultValue +
                ", source=" + source +
                '}';
    }
}
