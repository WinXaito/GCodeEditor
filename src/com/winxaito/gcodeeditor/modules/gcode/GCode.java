package com.winxaito.gcodeeditor.modules.gcode;

import javafx.scene.control.Tab;
import org.fxmisc.richtext.CodeArea;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by: WinXaito (Kevin Vuilleumier)
 */
public class GCode{
    private String name;
    private String content;
    private GCodeHighLighter highLighter;

    public GCode(String name, String content, CodeArea codeArea, Tab tab){
        this.name = name;
        this.content = content;
        highLighter = new GCodeHighLighter(codeArea, tab);
    }

    public int getLayerCount(){
        Pattern pattern = Pattern.compile("\n;Layer count: [0-9]+");
        Matcher matcher = pattern.matcher(content);

        if(matcher.find()){
            pattern = Pattern.compile("[0-9]+$");
            matcher = pattern.matcher(matcher.group());

            if(matcher.find())
                return Integer.valueOf(matcher.group());
        }

        return -1;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getContent(){
        return content;
    }

    public void setContent(String content){
        this.content = content;
    }
}
