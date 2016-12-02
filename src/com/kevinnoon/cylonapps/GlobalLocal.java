package com.kevinnoon.cylonapps;

/**
 * Created by kevin on 01/04/2016.
 *  Completed on 22/05/2016
 */
@SuppressWarnings("WeakerAccess")
public class GlobalLocal extends GlobalHeader {
    globalType  type = globalType.local;
    public Source source = new Source();
     public Destination destination = new Destination();
     public Broadcast broadcast = new Broadcast();
    private double defaultValue;

    @SuppressWarnings({"WeakerAccess", "unused"})
    public static class Source {
        private int fieldControllerAddress;
        private  int pointAddress;
        private int scheduleOnTimePointAddress;
        private int ScheduleOffTimePointAddress;

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

          public int getScheduleOnTimePointAddress() {
            return scheduleOnTimePointAddress;
        }

         public void setScheduleOnTimePointAddress(int scheduleOnTimePointAddress) {
            this.scheduleOnTimePointAddress = scheduleOnTimePointAddress;
        }

         public int getScheduleOffTimePointAddress() {
            return ScheduleOffTimePointAddress;
        }

         public void setScheduleOffTimePointAddress(int scheduleOffTimePointAddress) {
            this.ScheduleOffTimePointAddress = scheduleOffTimePointAddress;
        }

        @Override
        public String toString() {
            return "Source{" +
                    "fieldControllerAddress=" + fieldControllerAddress +
                    ", pointAddress=" + pointAddress +
                    ", scheduleOnTimePointAddress=" + scheduleOnTimePointAddress +
                    ", ScheduleOffTimePointAddress=" + ScheduleOffTimePointAddress +
                    '}';
        }
    }

     public static class Destination {
        private int fieldControllerAddress;
        private int pointAddress;

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
        String destinationFieldControllers;

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

     public double getDefaultValue() {
        return defaultValue;
    }

     public void setDefaultValue(double defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public String toString() {
        return "GlobalLocal{" + super.toString() +
                "source=" + source +
                ", destination=" + destination +
                ", broadcast=" + broadcast +
                ", defaultValue=" + defaultValue +
                '}';

    }
}
