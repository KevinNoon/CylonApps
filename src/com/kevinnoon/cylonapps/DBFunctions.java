package com.kevinnoon.cylonapps;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kevin on 23/03/2016.
 */
public class DBFunctions {

public static List<DBPoints> GetPoints(Path FileName) {
    List<DBPoints> DBPointsList = new ArrayList<DBPoints>();

    Integer number, lastNumber = 0, lastNumber1 = 0, pointNo = 0, pointCount = 0, pointsRead = 0, pointType = 0; // To
    boolean endOfFile = false, first = true; // End of file flag
    // Open Numbers.dat as a binary file.
    FileInputStream fstream;
    try {
        fstream = new FileInputStream( String.valueOf( FileName ) );
        DataInputStream inputFile = new DataInputStream( fstream );
        // Read data from the file.
        while (!endOfFile) {
            try {
                number = inputFile.readUnsignedByte();
                lastNumber1 = lastNumber;
                lastNumber = number;
                String PointName = "";
                if (((lastNumber1 == 3) && (lastNumber == 128)) || (first)) {
                    DBPoints points = new DBPoints();
                    if (first) {
                        inputFile.skipBytes( 14 );
                        pointCount = inputFile.readUnsignedByte();
                        inputFile.skipBytes( 17 );
                        first = false;
                    }

                    if (pointCount == pointsRead)
                        break;
                    number = inputFile.readUnsignedByte();
                    for (int n = 0; n < number; n++) {
                        PointName = PointName + (char) inputFile.readUnsignedByte();
                    }
                    points.setName(PointName);
                    pointNo = inputFile.readUnsignedByte();
                    pointNo = pointNo + 256 * (Integer) inputFile.readUnsignedByte();
                    points.setNumber(pointNo);
                    inputFile.skipBytes( 6 );
                    points.setType(inputFile.readUnsignedByte());
                    inputFile.skipBytes( 7 );
                    points.setDigitalOnUnits(inputFile.readUnsignedByte());
                    inputFile.skipBytes( 3 );
                    points.setDigitalOffUnits(inputFile.readUnsignedByte());
                    points.setAnalogueUnits( points.getDigitalOffUnits());
                    inputFile.skipBytes( 3 );
                    points.setHardwareType(inputFile.readUnsignedByte());
                    pointsRead++;
                    DBPointsList.add(points);
                }
            } catch (EOFException e) {

                endOfFile = true;
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        // Close the file.
        inputFile.close();


    } catch (FileNotFoundException e1) {
        // TODO Auto-generated catch block
        e1.printStackTrace();
    } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    return DBPointsList;
}


    public static String GetPointName(Path FileName,Integer PointNo, Integer PointType) {

        Integer number, lastNumber = 0, lastNumber1 = 0, pointNo = 0, pointCount = 0, pointsRead = 0, pointType = 0; // To
        boolean endOfFile = false, first = true; // End of file flag
        // Open Numbers.dat as a binary file.
        FileInputStream fstream;
        try {
            fstream = new FileInputStream( String.valueOf( FileName ) );
            DataInputStream inputFile = new DataInputStream( fstream );
            // Read data from the file.
            while (!endOfFile) {

                try {
                    number = inputFile.readUnsignedByte();
                    lastNumber1 = lastNumber;
                    lastNumber = number;
                    String PointName = "";
                    if (((lastNumber1 == 3) && (lastNumber == 128)) || (first)) {
                        if (first) {
                            inputFile.skipBytes( 14 );
                            pointCount = inputFile.readUnsignedByte();
                            inputFile.skipBytes( 17 );
                            first = false;
                        }

                        if (pointCount == pointsRead)
                            break;
                        number = inputFile.readUnsignedByte();
                        for (int n = 0; n < number; n++) {
                            PointName = PointName + (char) inputFile.readUnsignedByte();
                        }

                        pointNo = inputFile.readUnsignedByte();
                        pointNo = pointNo + 256 *  inputFile.readUnsignedByte();
                        inputFile.skipBytes( 6 );
                        pointType = inputFile.readUnsignedByte();
                        if ((pointNo.equals(PointNo) && (PointType.equals(pointType))))
                            return PointName;

                    }
                    pointsRead++;
                } catch (EOFException e) {

                    endOfFile = true;
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            // Close the file.
            inputFile.close();

        } catch (FileNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }
}
