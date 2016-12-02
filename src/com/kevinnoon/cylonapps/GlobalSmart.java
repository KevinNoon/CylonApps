package com.kevinnoon.cylonapps;

import java.util.ArrayList;

/**
 * Created by kevin on 01/04/2016.
 */
public class GlobalSmart extends GlobalHeader {
    public GlobalXMLFunctions.globalType type = GlobalXMLFunctions.globalType.smart;
    public Source source = new Source();
    public Destination destination = new Destination();
    public Default adefault = new Default();

    static private int RunBurstLength;

    public int getRunBurstLength() {
        return RunBurstLength;
    }

    public void setRunBurstLength(int runBurstLength) {
        RunBurstLength = runBurstLength;
    }

    public static class Source {
        private int fieldControllerAddress;
        private  int pointAddress;
        private ArrayList<Integer> smartFieldAddesses = new ArrayList<>(  );

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

        public ArrayList<Integer> getSmartFieldAddesses() {
            return smartFieldAddesses;
        }

        public void setSmartFieldAddesses(ArrayList<Integer> smartFieldAddesses) {
            this.smartFieldAddesses = smartFieldAddesses;
        }

        @Override
        public String toString() {
            return "Source{" +
                    "fieldControllerAddress=" + fieldControllerAddress +
                    ", pointAddress=" + pointAddress +
                    ", smartFieldAddesses=" + smartFieldAddesses +
                    '}';
        }
    }
    public static class Destination {
        private int fieldControllerAddress;
        private  int pointAddress;
        private int maxAddresses;
        private int minAddresses;
        private int sumAddresses;
        private int averageAddresses;

        public int getFieldControllerAddress() {
            return fieldControllerAddress;
        }

        public void setFieldControllerAddress(int fieldControllerAddress) {
            this.fieldControllerAddress = fieldControllerAddress;
        }

        public int getPointAddress() {
            return pointAddress;
        }

        public int getMaxAddresses() {
            return maxAddresses;
        }

        public void setMaxAddresses(int maxAddresses) {
            this.maxAddresses = maxAddresses;
        }

        public int getMinAddresses() {
            return minAddresses;
        }

        public void setMinAddresses(int minAddresses) {
            this.minAddresses = minAddresses;
        }

        public int getSumAddresses() {
            return sumAddresses;
        }

        public void setSumAddresses(int sumAddresses) {
            this.sumAddresses = sumAddresses;
        }

        public int getAverageAddresses() {
            return averageAddresses;
        }

        public void setAverageAddresses(int averageAddresses) {
            this.averageAddresses = averageAddresses;
        }

        public void setPointAddress(int pointAddress) {
            this.pointAddress = pointAddress;


        }

        @Override
        public String toString() {
            return "Destination{" +
                    "fieldControllerAddress=" + fieldControllerAddress +
                    ", pointAddress=" + pointAddress +
                    ", maxAddresses=" + maxAddresses +
                    ", minAddresses=" + minAddresses +
                    ", sumAddresses=" + sumAddresses +
                    ", averageAddresses=" + averageAddresses +
                    '}';
        }
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
            return "DefaultValue{" +
                    "maxAddresses=" + maxAddresses +
                    ", minAddresses=" + minAddresses +
                    ", sumAddresses=" + sumAddresses +
                    ", averageAddresses=" + averageAddresses +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "GlobalSmart{" + super.toString() +
                ", runBurstLength=" + RunBurstLength +
                "source=" + source +
                ", destination=" + destination +
                ", defaultValue=" + adefault +
                '}';
    }
}
