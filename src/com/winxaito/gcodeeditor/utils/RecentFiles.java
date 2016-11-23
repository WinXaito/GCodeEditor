package com.winxaito.gcodeeditor.utils;

import java.io.*;
import java.util.Properties;

/**
 * Created by: WinXaito (Kevin Vuilleumier)
 */
public class RecentFiles{
    private File file;
    private Properties properties;

    public RecentFiles(){
        file = new File(Configuration.configDir + "recentFiles.properties");

        if(!file.exists()){
            try{
                if(!file.createNewFile())
                    return;
            }catch(IOException e){
                e.printStackTrace();
                return;
            }
        }

        properties = new Properties();

        try{
            FileReader reader = new FileReader(file);
            properties.load(reader);
            reader.close();
        }catch(IOException e){
            e.printStackTrace();
        }

        init();
        try{
            saveConfiguration();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void init(){
        if(!properties.containsKey("recent_file_1"))
            properties.setProperty("recent_file_1", "");
        if(!properties.containsKey("recent_file_2"))
            properties.setProperty("recent_file_2", "");
        if(!properties.containsKey("recent_file_3"))
            properties.setProperty("recent_file_3", "");
        if(!properties.containsKey("recent_file_4"))
            properties.setProperty("recent_file_4", "");
        if(!properties.containsKey("recent_file_5"))
            properties.setProperty("recent_file_5", "");
        if(!properties.containsKey("recent_file_6"))
            properties.setProperty("recent_file_6", "");
    }

    public boolean[] existRecentFile(){
        boolean file[] = new boolean[6];

        for(int i = 0;i < 6;i++)
            file[i] = false;

        for(int i = 1;i <= 6;i++){
            file[i - 1] = !properties.getProperty("recent_file_" + i, "").equalsIgnoreCase("");

            if(file[i - 1] = false)
                return file;
        }

        return file;
    }

    public String getRecentFile(int number){
        if(number > 0 && number <= 6)
            return properties.getProperty("recent_file_" + number, "");

        return "";
    }

    public void saveConfiguration() throws IOException{
        properties.store(new FileOutputStream(file), "GCodeEditor recent files");
    }
}
