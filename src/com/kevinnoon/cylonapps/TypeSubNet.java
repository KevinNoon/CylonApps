package com.kevinnoon.cylonapps;

import java.util.Map;

/**
 * Created by kevin on 17/03/2016.
 */
public class TypeSubNet {

    private int ID;
    private String variationName;
    private int releaseStatus;
    private String family;
    private boolean UECListPrimary;
    private int keyPadType;

    private int subnetLimit;
    private int strategyBlocks;
    private int controllerPoints;
    private boolean pulseCountSupport;
    private boolean virtual;
    private boolean peerToPeerSupport;
    private int decBlocks;

    private int dataLogNumbers;
    private int dataLogLength;

    private int bacNetMaxChar;
    private int bacNetMaxExpPoints;
    private int bacNetMaxUnitString;

    private int type01Count;
    private int type02Count;
    private int type03Count;
    private int type04Count;
    private int type05Count;
    private int type06Count;
    private int type07Count;
    private int type08Count;
    private int type09Count;
    private int type10Count;
    private int totalCount;

    private Map<Integer,Integer> points;

    public Map<Integer, Integer> getPoints() {
        return points;
    }

    public void setPoints(Map<Integer, Integer> points) {
        this.points = points;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getVariationName() {
        return variationName;
    }

    public void setVariationName(String variationName) {
        this.variationName = variationName;
    }

    public int getReleaseStatus() {
        return releaseStatus;
    }

    public void setReleaseStatus(int releaseStatus) {
        this.releaseStatus = releaseStatus;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public boolean isUECListPrimary() {
        return UECListPrimary;
    }

    public void setUECListPrimary(boolean UECListPrimary) {
        this.UECListPrimary = UECListPrimary;
    }

    public int getKeyPadType() {
        return keyPadType;
    }

    public void setKeyPadType(int keyPadType) {
        this.keyPadType = keyPadType;
    }

    public int getSubnetLimit() {
        return subnetLimit;
    }

    public void setSubnetLimit(int subnetLimit) {
        this.subnetLimit = subnetLimit;
    }

    public int getStrategyBlocks() {
        return strategyBlocks;
    }

    public void setStrategyBlocks(int strategyBlocks) {
        this.strategyBlocks = strategyBlocks;
    }

    public int getControllerPoints() {
        return controllerPoints;
    }

    public void setControllerPoints(int controllerPoints) {
        this.controllerPoints = controllerPoints;
    }

    public boolean isPulseCountSupport() {
        return pulseCountSupport;
    }

    public void setPulseCountSupport(boolean pulseCountSupport) {
        this.pulseCountSupport = pulseCountSupport;
    }

    public boolean isVirtual() {
        return virtual;
    }

    public void setVirtual(boolean virtual) {
        this.virtual = virtual;
    }

    public boolean isPeerToPeerSupport() {
        return peerToPeerSupport;
    }

    public void setPeerToPeerSupport(boolean peerToPeerSupport) {
        this.peerToPeerSupport = peerToPeerSupport;
    }

    public int getDecBlocks() {
        return decBlocks;
    }

    public void setDecBlocks(int decBlocks) {
        this.decBlocks = decBlocks;
    }

    public int getDataLogNumbers() {
        return dataLogNumbers;
    }

    public void setDataLogNumbers(int dataLogNumbers) {
        this.dataLogNumbers = dataLogNumbers;
    }

    public int getDataLogLength() {
        return dataLogLength;
    }

    public void setDataLogLength(int dataLogLength) {
        this.dataLogLength = dataLogLength;
    }

    public int getBacNetMaxChar() {
        return bacNetMaxChar;
    }

    public void setBacNetMaxChar(int bacNetMaxChar) {
        this.bacNetMaxChar = bacNetMaxChar;
    }

    public int getBacNetMaxExpPoints() {
        return bacNetMaxExpPoints;
    }

    public void setBacNetMaxExpPoints(int bacNetMaxExpPoints) {
        this.bacNetMaxExpPoints = bacNetMaxExpPoints;
    }

    public int getBacNetMaxUnitString() {
        return bacNetMaxUnitString;
    }

    public void setBacNetMaxUnitString(int bacNetMaxUnitString) {
        this.bacNetMaxUnitString = bacNetMaxUnitString;
    }

    public int getType01Count() {
        return type01Count;
    }

    public void setType01Count(int type01Count) {
        this.type01Count = type01Count;
    }

    public int getType02Count() {
        return type02Count;
    }

    public void setType02Count(int type02Count) {
        this.type02Count = type02Count;
    }

    public int getType03Count() {
        return type03Count;
    }

    public void setType03Count(int type03Count) {
        this.type03Count = type03Count;
    }

    public int getType04Count() {
        return type04Count;
    }

    public void setType04Count(int type04Count) {
        this.type04Count = type04Count;
    }

    public int getType05Count() {
        return type05Count;
    }

    public void setType05Count(int type05Count) {
        this.type05Count = type05Count;
    }

    public int getType06Count() {
        return type06Count;
    }

    public void setType06Count(int type06Count) {
        this.type06Count = type06Count;
    }

    public int getType07Count() {
        return type07Count;
    }

    public void setType07Count(int type07Count) {
        this.type07Count = type07Count;
    }

    public int getType08Count() {
        return type08Count;
    }

    public void setType08Count(int type08Count) {
        this.type08Count = type08Count;
    }

    public int getType09Count() {
        return type09Count;
    }

    public void setType09Count(int type09Count) {
        this.type09Count = type09Count;
    }

    public int getType10Count() {
        return type10Count;
    }

    public void setType10Count(int type10Count) {
        this.type10Count = type10Count;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    @Override
    public String toString() {
        return "TypeSubNet{" +
                " ID=" + ID +
                ", variationName='" + variationName + '\'' +
                ", releaseStatus=" + releaseStatus +
                ", family='" + family + '\'' +
                ", UECListPrimary=" + UECListPrimary +
                ", keyPadType=" + keyPadType +
                ", subnetLimit=" + subnetLimit +
                ", strategyBlocks=" + strategyBlocks +
                ", controllerPoints=" + controllerPoints +
                ", pulseCountSupport=" + pulseCountSupport +
                ", virtual=" + virtual +
                ", peerToPeerSupport=" + peerToPeerSupport +
                ", decBlocks=" + decBlocks +
                ", dataLogNumbers=" + dataLogNumbers +
                ", dataLogLength=" + dataLogLength +
                ", bacNetMaxChar=" + bacNetMaxChar +
                ", bacNetMaxExpPoints=" + bacNetMaxExpPoints +
                ", bacNetMaxUnitString=" + bacNetMaxUnitString +
                ", type01Count=" + type01Count +
                ", type02Count=" + type02Count +
                ", type03Count=" + type03Count +
                ", type04Count=" + type04Count +
                ", type05Count=" + type05Count +
                ", type06Count=" + type06Count +
                ", type07Count=" + type07Count +
                ", type08Count=" + type08Count +
                ", type09Count=" + type09Count +
                ", type10Count=" + type10Count +
                "totalCount=" + totalCount +
                '}';
    }
}
