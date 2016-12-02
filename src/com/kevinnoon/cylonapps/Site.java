package com.kevinnoon.cylonapps;

/**
 * Created by kevin on 28/06/2016 at 07:03.
 */
public class Site implements Comparable<Site>{
    public int getSiteNumber() {
        return SiteNumber;
    }

    public void setSiteNumber(int siteNumber) {
        SiteNumber = siteNumber;
    }

    public int getAlarmScan() {
        return AlarmScan;
    }

    public void setAlarmScan(int alarmScan) {
        AlarmScan = alarmScan;
    }

    public int getBACnet() {
        return BACnet;
    }

    public void setBACnet(int BACnet) {
        this.BACnet = BACnet;
    }

    public int getDefaultType() {
        return DefaultType;
    }

    public void setDefaultType(int defaultType) {
        DefaultType = defaultType;
    }

    public String getDirectory() {
        return Directory;
    }

    public void setDirectory(String directory) {
        Directory = directory;
    }

    public int getInternet() {
        return Internet;
    }

    public void setInternet(int internet) {
        Internet = internet;
    }

    public String getIDCode() {
        return IDCode;
    }

    public void setIDCode(String IDCode) {
        this.IDCode = IDCode;
    }

    public String getIPAddr() {
        return IPAddr;
    }

    public void setIPAddr(String IPAddr) {
        this.IPAddr = IPAddr;
    }

    public int getMonitorstatus() {
        return monitorstatus;
    }

    public void setMonitorstatus(int monitorstatus) {
        this.monitorstatus = monitorstatus;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getNetwork() {
        return Network;
    }

    public void setNetwork(int network) {
        Network = network;
    }

    public int getPort() {
        return Port;
    }

    public void setPort(int port) {
        Port = port;
    }

    public int getRemote() {
        return Remote;
    }

    public void setRemote(int remote) {
        Remote = remote;
    }

    public String getTelephone() {
        return Telephone;
    }

    public void setTelephone(String telephone) {
        Telephone = telephone;
    }

    public int getAlarmScan0001() {
        return AlarmScan0001;
    }

    public void setAlarmScan0001(int alarmScan0001) {
        AlarmScan0001 = alarmScan0001;
    }

    public int getInternet0001() {
        return Internet0001;
    }

    public void setInternet0001(int internet0001) {
        Internet0001 = internet0001;
    }

    public String getIPAddr0001() {
        return IPAddr0001;
    }

    public void setIPAddr0001(String IPAddr0001) {
        this.IPAddr0001 = IPAddr0001;
    }

    public int getMonitorstatus0001() {
        return monitorstatus0001;
    }

    public void setMonitorstatus0001(int monitorstatus0001) {
        this.monitorstatus0001 = monitorstatus0001;
    }

    public int getPort0001() {
        return Port0001;
    }

    public void setPort0001(int port0001) {
        Port0001 = port0001;
    }

    int SiteNumber;
    int AlarmScan;
    int BACnet;
    int DefaultType;
    String Directory;
    int Internet;
    String IDCode;
    String IPAddr;
    int monitorstatus;
    String Name;
    int Network;
    int Port;
    int Remote=0;
    String Telephone;

    int AlarmScan0001;
    int Internet0001;
    String IPAddr0001;
    int monitorstatus0001;
    int Port0001;


    // Overriding the compareTo method
    @Override
    public int compareTo(Site s){
        return this.SiteNumber - s.SiteNumber;//return (this.Name).compareTo(s.Name);
    }

    // Overriding the compare method to sort the age
 //   @Override
    public int compare(Site s, Site s1){
        return s.SiteNumber - s1.SiteNumber;
    }
}
