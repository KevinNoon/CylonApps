package com.kevinnoon.cylonapps;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.kevinnoon.cylonapps.GlobalXMLFunctions.GetGlobals;

/**
 * Created by kevin on 01/04/2016.
 */
public class GlobalsFunctions {
    public static void main(String[] args) {

        List<GlobalHeader> GlocalList = GetGlobals( Paths.get( "C:\\temp\\AA\\Test\\Globals.xml" ) );
  //      System.out.println(GlocalList);
        ArrayList<Integer> usedGlocalList = getUsedGlobal( GlocalList );
        ArrayList<Integer> usedDotNet = getUsedNotNets( GlocalList );

        Map<Integer,Integer> wideGlobalChange = getWideGlobalChange( usedGlocalList,usedGlocalList );

        Map<Integer,Integer> dotNetChange = getWideGlobalChange( usedDotNet,usedDotNet );

//        setNewGlobalNo( GlocalList, wideGlobalChange );

//        setNewDotNetNo( GlocalList, dotNetChange );

//        for(GlobalHeader global: GlocalList){
//            System.out.println(global);
//        }
    }

    public static void setNewGlobalNo(List<GlobalHeader> glocalList, Map<Integer, Integer> wideGlobalChange) {
        for(GlobalHeader global: glocalList){
            if (global.getClass().isInstance( new GlobalWideDestination())){
                int cn = (((GlobalWideDestination)global).getConnectionNumber());
                ((GlobalWideDestination)global).setConnectionNumber(wideGlobalChange.get(cn));
            }
            if (global.getClass().isInstance( new GlobalWideSource())){
                int cn = (((GlobalWideSource)global).getConnectionNumber());
                ((GlobalWideSource)global).setConnectionNumber(wideGlobalChange.get(cn));
            }
        }
    }

    public static void setNewDotNetNo(List<GlobalHeader> glocalList, Map<Integer, Integer> dotNetChange) {
        for(GlobalHeader global: glocalList){
                int cn = global.getDotNetNo();
                global.setDotNetNo(dotNetChange.get(cn));
        }
    }

    public static Map<Integer,Integer> getWideGlobalChange(ArrayList<Integer> rootList, ArrayList<Integer> childList){
        ArrayList<Integer> used2Store = new ArrayList<Integer>();
        ArrayList<Integer> usedStore = new ArrayList<Integer>();
        Map<Integer,Integer> newPos = new HashMap<>();
        usedStore.addAll(childList);
        used2Store.addAll( childList );
        used2Store.removeAll( rootList );

        for (int i = 0; i < used2Store.size(); i++) {
            newPos.put( used2Store.get( i ),used2Store.get( i ) );
        }
        usedStore.removeAll( used2Store );
        rootList.addAll( used2Store );
        int setCount = 0;

        for (int i = 1; i < 256; i++) {
            if (!rootList.contains( i )){
                newPos.put( usedStore.get( setCount ),i );
                setCount++;
                if (setCount > usedStore.size() - 1) break;
            }
        }
        return newPos;
    }

    public static ArrayList<Integer> getUsedGlobal(List<GlobalHeader> glocalList) {
        ArrayList<Integer> usedGlocalList = new ArrayList<>( );
        for (int i = 0; i < glocalList.size(); i++) {
            if (glocalList.get( i ).getClass().isInstance( new GlobalWideDestination() )) {
                GlobalWideDestination gwd = (GlobalWideDestination) glocalList.get( i );
                if(!usedGlocalList.contains( gwd.getConnectionNumber() ))
                {
                    usedGlocalList.add(gwd.getConnectionNumber());
                }
            }
            if (glocalList.get( i ).getClass().isInstance( new GlobalWideSource() )) {
                GlobalWideSource gws = (GlobalWideSource) glocalList.get( i );
                if(!usedGlocalList.contains( gws.getConnectionNumber() ))
                {
                    usedGlocalList.add(gws.getConnectionNumber());
                }
            }
        }
        return usedGlocalList;
    }

    private static ArrayList<Integer> getUsedNotNets(List<GlobalHeader> glocalList) {
        ArrayList<Integer> usedDotNetList = new ArrayList<>( );
        for (int i = 0; i < glocalList.size(); i++) {
            if (!usedDotNetList.contains(glocalList.get(i).getDotNetNo() )) {
                usedDotNetList.add( glocalList.get( i ).getDotNetNo() );
            }
            }

        return usedDotNetList;
    }

}
