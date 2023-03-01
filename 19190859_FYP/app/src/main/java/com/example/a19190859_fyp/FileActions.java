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

    //create file object
    public File createFile(String fileName)
    {
        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        File file = new File(dir, fileName);
        return file;
    }

    //take existing data from a file, and compile it into a string so as to ensure file operations do not overwrite it
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

    //set file contents to blank
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

    //check to see if a file is blank
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
