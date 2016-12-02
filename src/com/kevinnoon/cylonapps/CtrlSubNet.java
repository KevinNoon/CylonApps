package com.kevinnoon.cylonapps;

/**
 * Created by kevin on 20/03/2016.
 */
public class CtrlSubNet {
    private int DotNetID;
    private int ID;
    private int Type;
    private String Name;
    private int BacNetID;



    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getBacNetID() {
        return BacNetID;
    }

    public void setBacNetID(int bacNetID) {
        BacNetID = bacNetID;
    }

    public int getDotNetID() {
        return DotNetID;
    }

    public void setDotNetID(int dotNetID) {
        DotNetID = dotNetID;
    }

    @Override
    public String toString() {
        return "CtrlSubNet{" +
                "DotNetID=" + DotNetID +
                ", ID=" + ID +
                ", Type=" + Type +
                ", Name='" + Name + '\'' +
                ", BacNetID=" + BacNetID +
                '}';
    }
}
