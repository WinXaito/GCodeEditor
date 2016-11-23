package com.winxaito.gcodeeditor.utils;

import java.io.File;

/**
 * Created by: WinXaito (Kevin Vuilleumier)
 */
public class Configuration{
    public static final String configDir = System.getProperty("user.home") + "/.GCodeEditor/";
    private File configDirFile = new File(configDir);
    private File file;

    public Configuration(){
        file = new File(configDir + "config.properties");

        if(!configDirFile.exists()){
            if(!configDirFile.mkdirs())
                System.err.println("Error for create path: " + configDir);
        }
    }
}
