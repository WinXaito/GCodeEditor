package com.winxaito.gcodeeditor.utils;

import com.winxaito.gcodeeditor.modules.gcode.GCode;
import javafx.scene.control.Tab;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by: WinXaito (Kevin Vuilleumier)
 */
public class FileInformations{
    private File file;
    private GCode gCode;
    private Tab tab;

    public FileInformations(File file, GCode gCode, Tab tab){
        this.file = file;
        this.gCode = gCode;
        this.tab = tab;
    }

    public File getFile(){
        return file;
    }

    public void setFile(File file){
        this.file = file;
    }

    public GCode getgCode(){
        return gCode;
    }

    public void setgCode(GCode gCode){
        this.gCode = gCode;
    }

    public Tab getTab(){
        return tab;
    }

    public void setTab(Tab tab){
        this.tab = tab;
    }

    @Nullable
    public static GCode getGCodeWithTab(Tab tab, ArrayList<FileInformations> fileInformations){
        for(FileInformations fi : fileInformations){
            if(fi.getTab() == tab)
                return fi.getgCode();
        }

        return null;
    }

    public static boolean setFileWithTab(Tab tab, File file, ArrayList<FileInformations> fileInformations){
        for(FileInformations fi : fileInformations){
            if(fi.getTab() == tab){
                fi.setFile(file);
                return true;
            }
        }

        return false;
    }
}
