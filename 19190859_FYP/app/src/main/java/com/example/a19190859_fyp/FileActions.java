package com.example.a19190859_fyp;

import android.os.Environment;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class FileActions {
    String inputsFileName;
    String answersFileName;

    public FileActions()
    {
        inputsFileName = "inputs.txt";
        answersFileName = "answers.txt";
    }

    public File createFile(String fileName)
    {
        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File file = new File(dir, fileName);
        return file;
    }

    public String compileFileIntoString(File file)
    {
        String data = "";
        try {
            Scanner scanner = new Scanner(file);
            while(scanner.hasNextLine())
            {
                data += scanner.nextLine() + "\n";
            }

        }catch (Exception exception)
        {
            exception.printStackTrace();
        }
        return data;
    }

    public void setBlankInputsFile(File file)
    {
        try {
            //create new file to write input data to
            FileWriter myWriter = new FileWriter(file);
            myWriter.write("");
            myWriter.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

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

    public boolean checkForEmptyFile(File file)
    {
        boolean emptyFile = false;
        try {
            Scanner scanner = new Scanner(file);
            String data = "";
            while(scanner.hasNextLine())
            {
                data += scanner.nextLine() + "\n";

            }

            if (data == "")
            {
                emptyFile = true;
            }else
            {
                emptyFile = false;
            }
        }catch (Exception exception)
        {
            exception.printStackTrace();
        }
        return emptyFile;
    }


}
