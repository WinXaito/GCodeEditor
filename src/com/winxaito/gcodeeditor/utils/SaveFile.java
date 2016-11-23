package com.winxaito.gcodeeditor.utils;

import com.winxaito.gcodeeditor.Main;
import javafx.scene.control.Alert;
import javafx.scene.control.TabPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Created by: WinXaito (Kevin Vuilleumier)
 */
public class SaveFile{
    public static void saveFile(TabPane tabPane, ArrayList<FileInformations> fileInformations){
        FileInformations fileInfos = null;
        for(FileInformations fi : fileInformations){
            if(fi.getTab() == tabPane.getSelectionModel().getSelectedItem()){
                fileInfos = fi;
                break;
            }
        }

        if(fileInfos != null){
            try{
                PrintWriter out = new PrintWriter(fileInfos.getFile());
                out.println(fileInfos.getgCode().getContent());
            }catch(FileNotFoundException e){
                e.printStackTrace();
            }
        }else{
            saveError("#GCodeDisplay:97");
        }
    }

    public static void saveAs(TabPane tabPane, ArrayList<FileInformations> fileInformations){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Enregistrement...");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("GCode", "*.g", "*.gco", "*.gcode"),
                new FileChooser.ExtensionFilter("Tous les fichiers", "*.*")
        );
        File file = fileChooser.showSaveDialog(Main.primaryStage);

        if(!FileInformations.setFileWithTab(tabPane.getSelectionModel().getSelectedItem(), file, fileInformations))
            saveError("#GCodeDisplayer:105");
        else
            tabPane.getSelectionModel().getSelectedItem().setText(file.getName());

        saveFile(tabPane, fileInformations);
    }

    private static void saveFile(TabPane tabPane, File file, ArrayList<FileInformations> fileInformations){
        FileInformations fileInfos = null;
        for(FileInformations fi : fileInformations){
            if(fi.getTab() == tabPane.getSelectionModel().getSelectedItem()){
                fileInfos = fi;
                break;
            }
        }

        if(fileInfos != null){
            fileInfos.setFile(file);
            saveFile(tabPane, fileInformations);
        }else{
            saveError("#GCodeDisplay:122");
        }
    }

    private static void saveError(String errorCode){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Enregistrement");
        alert.setHeaderText("Enregistrement...");
        alert.setContentText("La sauvegarde n'as pas pu être effectué à cause d'une erreur interne, veuillez reporter cette erreur." +
                "\nRapport de bug: " + errorCode);
        alert.showAndWait();
    }
}
