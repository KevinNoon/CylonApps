package com.kevinnoon.cylonapps;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.LinkedList;

/**
 * Created by kevin on 16/06/2016 at 15:07.
 *
 */
@SuppressWarnings({"ResultOfMethodCallIgnored", "unused"})
public class FileOperations {
    public static LinkedList<String> copyFolder(File sourceFolder, File destinationFolder) throws  IOException {
        //Check if sourceFolder is a directory or file
        //If sourceFolder is file; then copy the file directly to new location
        LinkedList<String> copyList = new LinkedList<>();
        if (sourceFolder.isDirectory())
        {
            //Verify if destinationFolder is already present; If not then create it
            if (!destinationFolder.exists())
            {
                destinationFolder.mkdir();
                copyList.add("Directory created :: " + destinationFolder);
            }

            //Get all files from source directory
            String files[] = sourceFolder.list();

            //Iterate over all files and copy them to destinationFolder one by one
            for (String file : files)
            {
                File srcFile = new File(sourceFolder, file);
                File destFile = new File(destinationFolder, file);

                //Recursive function call
                copyFolder(srcFile, destFile);
                copyList.add("File copied :: " + destFile);
            }
        }
        else
        {
            //Copy the file content from one place to another
            Files.copy(sourceFolder.toPath(), destinationFolder.toPath(), StandardCopyOption.REPLACE_EXISTING);
            copyList.add("File copied :: " + destinationFolder);
        }
        return copyList;
    }
}
