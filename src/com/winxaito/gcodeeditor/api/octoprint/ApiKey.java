package com.winxaito.gcodeeditor.api.octoprint;

/**
 * Created by: WinXaito (Kevin Vuilleumier)
 */
public class ApiKey{
    private String key;

    public String getKey(){
        if(key == null)
            loadApiKey();

        return key;
    }

    public void setKey(String key){
        if(!key.equals(this.key)){
            this.key = key;
            saveApiKey();
        }
    }

    private void loadApiKey(){
        //TODO: Load api key
    }

    private void saveApiKey(){
        //TODO: Save api key
    }
}
