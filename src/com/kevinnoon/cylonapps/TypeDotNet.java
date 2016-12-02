package com.kevinnoon.cylonapps;

/**
 * Created by kevin on 16/03/2016.
 */
public class TypeDotNet {
    private int ID;
    private String variationName;
    private int releaseStatus;
    private String family;
    private boolean UECListPrimary;
    private int keyPadType;
    private boolean modbusSupport;
    private boolean eWebSupervisorPages;
    private boolean eWebEmailAlarms;
    private boolean eWebConfigPages;

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

    public boolean isModbusSupport() {
        return modbusSupport;
    }

    public void setModbusSupport(boolean modbusSupport) {
        this.modbusSupport = modbusSupport;
    }

    public boolean iseWebSupervisorPages() {
        return eWebSupervisorPages;
    }

    public void seteWebSupervisorPages(boolean eWebSupervisorPages) {
        this.eWebSupervisorPages = eWebSupervisorPages;
    }

    public boolean iseWebEmailAlarms() {
        return eWebEmailAlarms;
    }

    public void seteWebEmailAlarms(boolean eWebEmailAlarms) {
        this.eWebEmailAlarms = eWebEmailAlarms;
    }

    public boolean iseWebConfigPages() {
        return eWebConfigPages;
    }

    public void seteWebConfigPages(boolean eWebConfigPages) {
        this.eWebConfigPages = eWebConfigPages;
    }

    @Override
    public String toString() {
        return "TypeDotNet{" +
                "ID=" + ID +
                ", variationName='" + variationName + '\'' +
                ", releaseStatus=" + releaseStatus +
                ", family='" + family + '\'' +
                ", UECListPrimary=" + UECListPrimary +
                ", keyPadType=" + keyPadType +
                ", modbusSupport=" + modbusSupport +
                ", eWebSupervisorPages=" + eWebSupervisorPages +
                ", eWebEmailAlarms=" + eWebEmailAlarms +
                ", eWebConfigPages=" + eWebConfigPages +
                '}';
    }
}
