package com.winxaito.gcodeeditor.modules.gcode;

import com.winxaito.gcodeeditor.Main;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by: WinXaito (Kevin Vuilleumier)
 */
public class GCodeParameters{
    private static ArrayList<GCodeParameter> gCodeParameters = new ArrayList<>();

    private static GCodeParameter gCodeEditor_version = new GCodeParameter("GCE_VERSION", Main.version);

    public static void defineGCodeParameters(GCode gCode){
        if(getGCodeParameter(gCode, gCodeEditor_version.getKey()) != null)
            setGCodeParameter(gCode, gCodeEditor_version);
    }

    @Nullable
    public static GCodeParameter getGCodeParameter(GCode gCode, String name){
        Pattern pattern = Pattern.compile(";@GCE:PARAM:" + name);
        Matcher matcher = pattern.matcher(gCode.getContent());

        if(matcher.find()){
            String key = matcher.group();

            for(GCodeParameter gCodeParameter : gCodeParameters){
                if(gCodeParameter.getKey().equalsIgnoreCase(key))
                    return gCodeParameter;
            }
        }

        return null;
    }

    private static void setGCodeParameter(GCode gCode, GCodeParameter parameter){
        Pattern pattern = Pattern.compile(";@GCE:PARAM:[a-zA-Z0-9]\n");
        Matcher matcher = pattern.matcher(gCode.getContent());

        int lastMatch = 0;
        while(matcher.find()){
            lastMatch = matcher.end();
        }

        StringBuilder stringBuilder = new StringBuilder(gCode.getContent());
        stringBuilder.insert(lastMatch, parameter.getKey());
        gCode.setContent(stringBuilder.toString());
    }

    private static void init(){
        gCodeParameters.add(gCodeEditor_version);
    }
}
