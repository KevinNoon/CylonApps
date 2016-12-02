package com.kevinnoon.cylonapps;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by kevin on 12/11/2016.
 */
public class S32stgFileFunctions {

    static public  LinkedList<S32Setpoints> getSetPoints(Path filePath) throws IOException {
        LinkedList<S32Setpoints> spList = new LinkedList<>();
        String BlockMaxNumber;
        BlockMaxNumber = (filePath.toString().endsWith("s32")) ? Constants.s32BlockMaxNumber:Constants.stgBlockMaxNumber;
        try (Stream<String> lines = Files.lines(filePath, Charset.forName("Cp1252"))) {
            List<String> list;
            list = lines.collect(Collectors.toList());
            boolean lastBlockRead = false;
            for (String aList : list) {
                if (lastBlockRead) {
                    S32Setpoints sp =  new S32Setpoints();
                    sp.setPointNumber(Integer.valueOf(aList.substring(0,aList.indexOf("\t"))));
                    aList = aList.substring(aList.indexOf("\t") + 1);
                    sp.setType(Integer.valueOf(aList.substring(0,aList.indexOf("\t"))));
                    aList = aList.substring(aList.indexOf("\t") + 1);
                    sp.setValue(Double.valueOf(aList));
                    spList.add(sp);
                }
                if (aList.substring(0,4).contains(BlockMaxNumber)) lastBlockRead = true;
            }
            return spList;

        }
    }
}