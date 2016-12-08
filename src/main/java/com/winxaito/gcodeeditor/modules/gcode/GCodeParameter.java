package com.winxaito.gcodeeditor.modules.gcode;

/**
 * Created by: WinXaito (Kevin Vuilleumier)
 */
public class GCodeParameter{
    private String key;
    private String value;

    public GCodeParameter(String key){
        this(key, "");
    }

    public GCodeParameter(String key, String value){
        this.key = key;
        this.value = value;
    }

    public String getKey(){
        return key;
    }

    public void setKey(String key){
        this.key = key;
    }

    public String getValue(){
        if(value.isEmpty())
            return "NO_DATA";

        return value;
    }

    public void setValue(String value){
        this.value = value;
    }
}
