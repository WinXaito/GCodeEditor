package com.winxaito.gcodeeditor.utils;

import java.io.*;

/**
 * Created by: WinXaito (Kevin Vuilleumier)
 */
public class OpenFile{
    private File file;

    public OpenFile(File file){
        this.file = file;
    }

    public OpenFile(String path){
        file = new File(path);
    }

    public File getFile(){
        return file;
    }

    public String getFileContent(){
        StringBuilder stringBuilder = new StringBuilder();
        String lineSeparator = System.getProperty("line.separator");

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(lineSeparator);
            }
        }catch(IOException e){
            e.printStackTrace();
        }

        return stringBuilder.toString();
    }
}
