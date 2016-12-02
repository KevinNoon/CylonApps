package com.kevinnoon.cylonapps;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by kevin on 02/06/2016 at 07:44.
 */
public class INIFunctions {

    static public boolean IsSectionExist(Path path,String Section) throws IOException {
        Boolean SectionExits = false;
        try (Stream<String> lines = Files.lines(path, Charset.forName("Cp1252"))) {
            List<String> list;
            list = lines.collect( Collectors.toList());
            for (String aList : list) {
                if (aList.contains(Section)) {
                    SectionExits = true;
                }
            }
        }
        return SectionExits;
    }

    static public  LinkedHashMap<String ,String> GetSection(Path path,String Section) throws IOException {
        LinkedHashMap<String ,String> SectionMap = new LinkedHashMap<>(  );

        try (Stream<String> lines = Files.lines(path, Charset.forName("cp1250"))) {
            Boolean SectionStart = false;
            List<String> list;
            list = lines.collect( Collectors.toList());
            for (String aList : list) {
                if (SectionStart){
                    if (aList.contains( "[" )) {
                        break;
                    }
                    if (aList.length()>0)
                    SectionMap.put( aList.substring( 0,aList.indexOf( "=" ) ),aList.substring(aList.indexOf( "=" )+1) );
                }
                if (aList.contains(Section)) {
                    SectionStart = true;
                    SectionMap.put( "SectionName",aList );
                }
            }
        }
        return SectionMap;
    }

    static public int AddSelection(Path path, HashMap<String,String> section, Boolean insertAtStart) throws IOException {

        if (!IsSectionExist( path,section.get( "SectionName" ) )){

            ArrayList<String> linesOut = new ArrayList<>(  );
            FileReader reader = new FileReader(path.toFile());
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
            while((line = bufferedReader.readLine()) != null) {
                linesOut.add( line.trim() );
            }
            bufferedReader.close();
            try {
                FileWriter writer = new FileWriter(path.toFile(), false);
                BufferedWriter bufferedWriter =
                        new BufferedWriter(writer);
                if (!insertAtStart){
                for (int i = 0; i < linesOut.size(); i++) {
                    bufferedWriter.write(linesOut.get(i).trim());
                    bufferedWriter.newLine();
                }}

                for(Map.Entry<String,String> m :section.entrySet()){
                    if (m.getKey().contains( "SectionName" )) {
                        bufferedWriter.write(m.getValue().trim());
                        bufferedWriter.newLine();
                    } else {
                        bufferedWriter.write(m.getKey().trim() + "=" + m.getValue().trim());
                        bufferedWriter.newLine();
                    }
                }
                if (insertAtStart){
                    for (int i = 0; i < linesOut.size(); i++) {
                        bufferedWriter.write(linesOut.get(i));
                        bufferedWriter.newLine();
                    }
                }
                bufferedWriter.close();
                writer.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            return 1;
        }
        return -1;
    }

    static public int AddOSSection(Path path,HashMap<String,String> section) throws IOException {

        String newSectionName = section.get("SectionName");
        // Can't add an OS if it's already there
        if (IsSectionExist(path,newSectionName)) return -1;

        AddSelection(path,section,false);
        HashMap<String,String > CCConfig = GetSection( path,"[CCConfig]" );
        int OldMaxOS = 0;
        if (!(CCConfig.get( "MaxOutstation" )==null )) OldMaxOS = Integer.parseInt(CCConfig.get( "MaxOutstation" ));
        int newOSNumber = Integer.parseInt( newSectionName.substring(3,newSectionName.indexOf("]")) );
        if (OldMaxOS < newOSNumber){
            DeleteSection(path,"[CCConfig]");
            CCConfig.replace("MaxOutstation", String.valueOf(newOSNumber).trim());
            AddSelection(path,CCConfig,true);
        }

    return -1;
    }

    static public int  DeleteSection(Path path,String Section) throws IOException {

        ArrayList<String> linesOut = new ArrayList<>();
        FileReader reader = new FileReader(path.toFile());
        BufferedReader bufferedReader = new BufferedReader(reader);
        String line;
        Boolean SectionStart = false;
        while ((line = bufferedReader.readLine()) != null) {
            if (SectionStart) {
                if (line.contains("[")) {
                    SectionStart = false;
                }
            }
            if (line.contains(Section)) {
                SectionStart = true;
            }
            if (!SectionStart) {
                linesOut.add(line.trim());
            }
        }
        bufferedReader.close();

        try {
            FileWriter writer = new FileWriter(path.toFile(), false);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);

            for (int i = 0; i < linesOut.size(); i++) {{
                bufferedWriter.write(linesOut.get(i).trim());
                bufferedWriter.newLine();
                }
            }
            bufferedWriter.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return -1;
    }

    static public int DeleteOS(Path path,int OS) throws IOException {
        HashMap<String,String > CCConfig = GetSection( path,"[CCConfig]" );
        int OSMax = Integer.parseInt( CCConfig.get( "MaxOutstation" ) );
        DeleteSection(path,"[OS" + OS + "]");
        if (OSMax == OS) {
            int OldMaxOS = OSMax;
            for (int n = 1; n <= OldMaxOS; n++){
                if (IsSectionExist(path,"[OS" + n + "]")){
                    OS = n;
                }
            };
            CCConfig.replace("MaxOutstation", String.valueOf(OS));
            if (OldMaxOS < OS){
                DeleteSection(path,"[CCConfig]");
                CCConfig.replace("MaxOutstation", String.valueOf(OS).trim());
                AddSelection(path,CCConfig,true);
            }
        }
        //TODO Return 1 is good
        return -1;
    };

}
