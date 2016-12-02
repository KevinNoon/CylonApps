package com.kevinnoon.cylonapps;

/**
 * Created by kevin on 01/04/2016.
 */
public class GlobalTemporary extends GlobalHeader {

    public GlobalXMLFunctions.globalType type = GlobalXMLFunctions.globalType.temporary;
    public Source source = new Source();
    public Destination destination = new Destination();
    private double defaultValue;
    public Default adefault = new Default();

    private int RunBurstLength;

    public int getRunBurstLength() {
        return RunBurstLength;
    }

    public void setRunBurstLength(int runBurstLength) {
        RunBurstLength = runBurstLength;
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

    public double getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(double defaultValue) {
        this.defaultValue = defaultValue;
    }

    public static class Default {
        private double maxAddresses;
        private double minAddresses;
        private double sumAddresses;
        private double averageAddresses;

        public double getMaxAddresses() {
            return maxAddresses;
        }

        public void setMaxAddresses(double maxAddresses) {
            this.maxAddresses = maxAddresses;
        }

        public double getMinAddresses() {
            return minAddresses;
        }

        public void setMinAddresses(double minAddresses) {
            this.minAddresses = minAddresses;
        }

        public double getSumAddresses() {
            return sumAddresses;
        }

        public void setSumAddresses(double sumAddresses) {
            this.sumAddresses = sumAddresses;
        }

        public double getAverageAddresses() {
            return averageAddresses;
        }

        public void setAverageAddresses(double averageAddresses) {
            this.averageAddresses = averageAddresses;
        }

        @Override
        public String toString() {
            return "Default{" +
                    "maxAddresses=" + maxAddresses +
                    ", minAddresses=" + minAddresses +
                    ", sumAddresses=" + sumAddresses +
                    ", averageAddresses=" + averageAddresses +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "GlobalTemporary{" + super.toString() +
                ", runBurstLength=" + RunBurstLength +
                "source=" + source +
                ", destination=" + destination +
                ", defaultValue=" + defaultValue +
                ", adefault=" + adefault +
                '}';
    }
}
