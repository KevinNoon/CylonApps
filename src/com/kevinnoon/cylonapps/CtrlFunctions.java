package com.kevinnoon.cylonapps;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;


/**
 * Created by kevin on 18/03/2016.
 */
public class CtrlFunctions {

    public static void main(String[] args) throws IOException {
//        System.out.println( GetDotNetTypes(Paths.get("C:\\temp\\controllers.xml")));
//        List<CtrlDotNet> dotList = GetDotNets( Paths.get("C:\\temp\\site.ini"));
//        for (int i = 0; i < dotList.size();i++){
//            System.out.println(dotList.get(i));
//        }
        List<CtrlSubNet> subNetList = GetSubNetCtrls( Paths.get( "C:\\temp\\siteunitron.ini" ) );
        for (int i = 0; i < subNetList.size(); i++) {
            System.out.println( subNetList.get( i ) );
        }
    }


    public static ArrayList<CtrlDotNet> GetDotNets(Path path) throws IOException {
        ArrayList<CtrlDotNet> dotNetList = new ArrayList<CtrlDotNet>();
        LinkedHashMap<String, String> sectionMOS = INIFunctions.GetSection( path, "CCConfig" );
        LinkedHashMap<String, String> sectionOS;
        for (int n = 1; n <= Integer.parseInt( sectionMOS.get( "MaxOutstation" ) ); n++) {
            sectionOS = INIFunctions.GetSection( path, "OS" + n );
            if (sectionOS.size() > 0) {
                CtrlDotNet siteDotNet = new CtrlDotNet();
                siteDotNet.setAddress( n );
                siteDotNet.setName( sectionOS.get( "Name" ) );
                siteDotNet.setType( Integer.parseInt( sectionOS.get( "UCC4Type" ) ) );
                if (sectionOS.get( "Router_BACnetDeviceID_" + n ) != null)
                    siteDotNet.setBacNetID( Integer.parseInt( sectionOS.get( "Router_BACnetDeviceID_" + n ) ) );
                if (sectionOS.get( "SubnetIndex" ) != null)
                    siteDotNet.setSubNetIndex( Integer.parseInt( sectionOS.get( "SubnetIndex" ) ) );
                dotNetList.add( siteDotNet );
            }
        }
        return dotNetList;
    }

    public static ArrayList<CtrlSubNet> GetSubNetCtrls(Path path) throws IOException {
        CtrlSubNet subNetCtrl = new CtrlSubNet();
        ArrayList<CtrlSubNet> subNetList = new ArrayList<CtrlSubNet>();
        LinkedHashMap<String, String> sectionMOS = INIFunctions.GetSection( path, "CCConfig" );
        LinkedHashMap<String, String> sectionOS;
        for (int n = 1; n <= Integer.parseInt( sectionMOS.get( "MaxOutstation" ) ); n++) {
            sectionOS = INIFunctions.GetSection( path, "OS" + n );
            if (sectionOS.size() > 0) {
                for (int c = 1; c <= Integer.parseInt( sectionOS.get( "MaxUC16" ) ); c++) {
                    if (sectionOS.get( "UC16_" + c ) != null) {
                        subNetCtrl = new CtrlSubNet();
                        subNetCtrl.setDotNetID( n );
                        subNetCtrl.setID( c );
                        subNetCtrl.setType( Integer.parseInt( sectionOS.get( "UC16Type_" + c ) ) );
                        subNetCtrl.setName( sectionOS.get( "UC16_" + c ) );
                        if (sectionOS.get( "UC16_BACnetDeviceID_" + c ) != null)
                            subNetCtrl.setBacNetID( Integer.parseInt( sectionOS.get( "UC16_BACnetDeviceID_" + c ) ) );
                        subNetList.add( subNetCtrl );
                    }
                }
            }
        }
        return subNetList;
    }

    public static  ArrayList<Integer> GetUsedDotNets(ArrayList<CtrlDotNet> ctrlDotNetList) {
        ArrayList<Integer> usedDotNets = new ArrayList<>();
        for (int n = 0; n < ctrlDotNetList.size(); n++) {
            usedDotNets.add( ctrlDotNetList.get( n ).getAddress() );
        }
        return usedDotNets;
    }

    public static  ArrayList<Integer> GetFreeDotNetTotal(ArrayList<CtrlDotNet> ctrlDotNetList) {
        ArrayList<Integer> freeDotNets = new ArrayList<>();
        for (int n = 1; n <= Constants.MaxDotNets; n++) {
            freeDotNets.add( n );
        }
        for (int a = 0; a < ctrlDotNetList.size(); a++) {
            freeDotNets.remove( freeDotNets.indexOf( ctrlDotNetList.get( a ).getAddress() ) );
        }
        return freeDotNets;
    }

    public static LinkedHashMap<String, String> GetDigitalUnits(Path path) throws IOException {
        return INIFunctions.GetSection( path, "DigitalUnits" );
    }

    public static LinkedHashMap<String, String> GetAnalogUnits(Path path) throws IOException {
        return INIFunctions.GetSection( path, "AnalogUnits" );
    }

    public static LinkedHashMap<String, String> GetAlarmQue(Path path) throws IOException {
        return INIFunctions.GetSection( path, "CCAlarms" );
    }

}
