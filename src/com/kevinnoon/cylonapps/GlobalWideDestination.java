package com.kevinnoon.cylonapps;

/**
 * Created by kevin on 01/04/2016.
 */
public class GlobalWideDestination extends GlobalHeader {

    public GlobalXMLFunctions.globalType type = GlobalXMLFunctions.globalType.wideDestination;
    private int connectionNumber;
    private double defaultValue;

    public Destination destination = new Destination();
    public Broadcast broadcast = new Broadcast();

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

    public static class Destination {
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
            return "Destination{" +
                    "fieldControllerAddress=" + fieldControllerAddress +
                    ", pointAddress=" + pointAddress +
                    '}';
        }
    }

    public static class Broadcast {
        private String destinationFieldControllers;

        public String getDestinationFieldControllers() {
            return destinationFieldControllers;
        }

        public void setDestinationFieldControllers(String destinationFieldControllers) {
            this.destinationFieldControllers = destinationFieldControllers;
        }

        @Override
        public String toString() {
            return "Broadcast{" +
                    "destinationFieldControllers='" + destinationFieldControllers + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "GlobalWideDestination{" + super.toString() +
                "connectionNumber=" + connectionNumber +
                ", defaultValue=" + defaultValue +
                ", destination=" + destination +
                ", broadcast=" + broadcast +
                '}';
    }
}
