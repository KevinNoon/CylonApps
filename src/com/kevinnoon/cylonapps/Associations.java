package com.kevinnoon.cylonapps;

/**
 * Created by kevin on 27/03/2016.
 */
public class Associations {
    private int ctrlNumber;
    private int subNetNumber;
    private String fileName;

    public int getCtrlNumber() {
        return ctrlNumber;
    }

    public void setCtrlNumber(int ctrlNumber) {
        this.ctrlNumber = ctrlNumber;
    }

    public int getSubNetNumber() {
        return subNetNumber;
    }

    public void setSubNetNumber(int subNetNumber) {
        this.subNetNumber = subNetNumber;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return "Associations{" +
                "ctrlNumber=" + ctrlNumber +
                ", subNetNumber=" + subNetNumber +
                ", fileName='" + fileName + '\'' +
                '}';
    }
}
