package com.example.a19190859_fyp;

import android.os.Environment;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class FileActions {
    String inputsFileName;
    String fileExtension;

    public FileActions()
    {
        inputsFileName = "inputs";
        fileExtension = ".txt";
    }

    //create file object
    public File createFile(String fileName)
    {
        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        File file = new File(dir, fileName);
        return file;
    }


    //write to file as necessary
    public void writeToFile(File file, String s)
    {
        try
        {
            FileWriter myWriter = new FileWriter(file);
            myWriter.write(s);
            myWriter.close();
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
