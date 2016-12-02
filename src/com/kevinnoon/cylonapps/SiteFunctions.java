package com.kevinnoon.cylonapps;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import static com.kevinnoon.cylonapps.INIFunctions.*;

/**
 * Created by kevin on 15/06/2016 at 12:06.
 * This will create a site with the default folders
 */

@SuppressWarnings("WeakerAccess")
public class SiteFunctions {
    public static boolean CreateSiteFolders(Path NewSite) throws IOException {

        File file = NewSite.toFile();
        boolean mkdirs = file.mkdirs();
        File Archive = new File(file.getAbsolutePath() + "\\Archive");
        File DBase = new File(file.getAbsolutePath() + "\\DBase");
        File Drawings = new File(file.getAbsolutePath() + "\\Drawings");
        File Keypad = new File(file.getAbsolutePath() + "\\Keypad");
        File Macros = new File(file.getAbsolutePath() + "\\Macros");
        File Strat5 = new File(file.getAbsolutePath() + "\\Strat5");
        File Strategy = new File(file.getAbsolutePath() + "\\Strategy");
        File SystemD = new File(file.getAbsolutePath() + "\\System");

        if (!Archive.mkdir()) mkdirs = false;
        if (!DBase.mkdir()) mkdirs = false;
        if (!Drawings.mkdir()) mkdirs = false;
        if (!Keypad.mkdir()) mkdirs = false;
        if (!Macros.mkdir()) mkdirs = false;
        if (!Strat5.mkdir()) mkdirs = false;
        if (!Strategy.mkdir()) mkdirs = false;
        if (!SystemD.mkdir()) mkdirs = false;
        return mkdirs;
    }

    public static List<Site> SortSite(List<Site> aSite){
        aSite.sort((Site o1, Site o2) ->o1.SiteNumber - o2.SiteNumber);
        return aSite;
    }

    public static int GetFirstFree (List<Site> aSite){
        SortSite(aSite);
        int nextNo = 0;
        for (int i = 0; i < aSite.size() - 1; i++) {
            nextNo = aSite.get(i).SiteNumber;
            if ((aSite.get(i + 1).SiteNumber - nextNo - 1) > 0){
                nextNo++;
                break;
            }
        }
        return nextNo;
    }

    public static List<Site> GetSites (Path unitronPath) throws IOException {
        List<Site> SiteList = new LinkedList<>();

        LinkedHashMap<String, String> Sites = GetSection(unitronPath, "SiteList");

        for (int i = 1; i <= Integer.parseInt(Sites.get("TotalSites")); i++) {
            Site site = new Site();

            LinkedHashMap<String, String> SiteInfo = GetSection(unitronPath, "[Site" + Sites.get("Site" + i) + "]");

            site.SiteNumber = Integer.parseInt(SiteInfo.get("SectionName").substring(5,SiteInfo.get("SectionName").length()-1));
            if (SiteInfo.containsKey("AlarmScan")) site.AlarmScan = Integer.parseInt(SiteInfo.get("AlarmScan"));
            if (SiteInfo.containsKey("BACnet")) site.BACnet = Integer.parseInt(SiteInfo.get("BACnet"));
            if (SiteInfo.containsKey("DefaultType")) site.DefaultType = Integer.parseInt(SiteInfo.get("DefaultType"));
            if (SiteInfo.containsKey("Directory")) site.Directory = SiteInfo.get("Directory");
            if (SiteInfo.containsKey("IDCode")) site.IDCode = SiteInfo.get("IDCode");
            if (SiteInfo.containsKey("Internet")) site.Internet = Integer.parseInt(SiteInfo.get("Internet"));
            if (SiteInfo.containsKey("IPAddr")) site.IPAddr = SiteInfo.get("IPAddr");
            if (SiteInfo.containsKey("monitorstatus"))  site.monitorstatus = Integer.parseInt(SiteInfo.get("monitorstatus"));
            if (SiteInfo.containsKey("Name")) site.Name = SiteInfo.get("Name");

            if (SiteInfo.containsKey("Port")){
                if (SiteInfo.get("Port").length() > 0)
                site.Port = Integer.parseInt(SiteInfo.get("Port"));
                else
                site.Port = 0;
            }
            if (SiteInfo.containsKey("Remote")) site.Remote = Integer.parseInt(SiteInfo.get("Remote"));
            if (SiteInfo.containsKey("Telephone")) site.Telephone = SiteInfo.get("Telephone");


            SiteList.add(site);
        }
        return SiteList;
    }

    public static void AddSite (Path unitronPath,Site site) throws IOException {
        //Check if directory exists
        File file = unitronPath.toFile();

        file = new File(file.getAbsolutePath() +"\\" + site.Directory);
        if(file.exists()){
            throw new FileAlreadyExistsException("Can not create site as directory exists");
        };
        //Get site list
        Path unitronPathWINI =  Paths.get(unitronPath.toAbsolutePath() + "\\System\\WN3000.ini");
        LinkedHashMap<String, String> SiteList = GetSection(unitronPathWINI, "SiteList");

        //Get first free site No
        List<Site> sites;
        sites = GetSites(unitronPathWINI);

        // Integer newSiteNumber = GetFirstFree(sites);
        //Add new site No to site list
        Integer totalsites = Integer.parseInt(SiteList.get("TotalSites")) + 1;
        SiteList.put("Site" + totalsites ,Integer.toString(site.SiteNumber));
        SiteList.put("TotalSites",totalsites.toString());

        DeleteSection(unitronPathWINI,"SiteList");
        AddSelection(unitronPathWINI,SiteList,false);

//Add new site
        AddSelection(unitronPathWINI,SiteToSection(site),false);
        //Need to sort site list before adding new site!
        //Save site list

        //Create directories
    }

    public static LinkedHashMap<String, String> SiteToSection(Site site){
        LinkedHashMap<String, String> siteSection = new LinkedHashMap<>();
        siteSection.put("SectionName","[Site" + site.SiteNumber + "]");

        if (site.AlarmScan > 0) siteSection.put("AlarmScan", Integer.toString(site.AlarmScan));
        if (site.BACnet > 0) siteSection.put("BACNet", Integer.toString(site.BACnet));
        if (site.DefaultType > 0) siteSection.put("DefaultType", Integer.toString(site.DefaultType));
        siteSection.put("Directory",site.Directory);
        if (site.Internet > 0) siteSection.put("Internet", Integer.toString(site.Internet));
        if (site.IDCode != null) siteSection.put("IDCode", site.IDCode);
        if (site.IPAddr != null) siteSection.put("IPAddr", site.IPAddr);
        if (site.monitorstatus > 0) siteSection.put("monitorstatus", Integer.toString(site.monitorstatus));
        siteSection.put("Name",site.Name);
        if (site.Network > 0) siteSection.put("Network", Integer.toString(site.Network));
        if (site.Port > 0) siteSection.put("Port", Integer.toString(site.Port));
        if (site.Remote > 0) siteSection.put("Remote", Integer.toString(site.Remote));
        if (site.Telephone != null) siteSection.put("Port", site.Telephone);

        if (site.AlarmScan0001 > 0) siteSection.put("AlarmScan0001", Integer.toString(site.AlarmScan0001));
        if (site.Internet0001 > 0) siteSection.put("Internet0001", Integer.toString(site.Internet0001));
        if (site.IPAddr0001 != null) siteSection.put("IPAddr0001", site.IPAddr0001);
        if (site.monitorstatus0001 > 0) siteSection.put("monitorstatus0001", Integer.toString(site.monitorstatus0001));
        if (site.Port0001 > 0) siteSection.put("Port0001", Integer.toString(site.Port0001));
        return siteSection;
    };
}
