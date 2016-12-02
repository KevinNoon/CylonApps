package com.kevinnoon.cylonapps;

/**
 * Created by kevin on 18/03/2016.
 */
public class CtrlDotNet {
    private int Address;
    private String Name;
    private int Type;
    private int BacNetID;
    private  int SubNetIndex;

    public int getAddress() {
        return Address;
    }

    public void setAddress(int address) {
        Address = address;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public int getBacNetID() {
        return BacNetID;
    }

    public void setBacNetID(int bacNetID) {
        BacNetID = bacNetID;
    }

    public int getSubNetIndex() {
        return SubNetIndex;
    }

    public void setSubNetIndex(int subNetIndex) {
        SubNetIndex = subNetIndex;
    }

    @Override
    public String toString() {
        return "CtrlDotNet{" +
                "Address=" + Address +
                ", Name='" + Name + '\'' +
                ", Type=" + Type +
                ", BacNetID=" + BacNetID +
                ", SubNetIndex=" + SubNetIndex +
                '}';
    }
}
