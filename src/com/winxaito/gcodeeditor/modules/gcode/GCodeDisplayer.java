package com.winxaito.gcodeeditor.modules.gcode;

import com.winxaito.gcodeeditor.Main;
import com.winxaito.gcodeeditor.utils.FileInformations;
import com.winxaito.gcodeeditor.utils.OpenFile;
import javafx.scene.control.Alert;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.FileChooser;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Created by: WinXaito (Kevin Vuilleumier)
 */
public class GCodeDisplayer{
    private ArrayList<FileInformations> fileInformations = new ArrayList<>();

    public void createNewFile(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Veuillez sélectionner votre fichier");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("GCode", "*.g", "*.gco", "*.gcode"),
                new FileChooser.ExtensionFilter("Tous les fichiers", "*.*")
        );
        File file = fileChooser.showSaveDialog(Main.primaryStage);

        createTab(file, true);
    }

    public void openFile(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Veuillez sélectionner votre fichier");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("GCode", "*.g", "*.gco", "*.gcode"),
                new FileChooser.ExtensionFilter("Tous les fichiers", "*.*")
        );
        File file = fileChooser.showOpenDialog(Main.primaryStage);

        if(file == null)
            return;

        openFile(file);
    }

    public void openFile(File file){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);

        if(file != null && file.exists()){
            createTab(file, false);
        }else if(file != null){
            alert.setContentText("Le fichier <" + file.getPath() + "> n'a pas été trouvé sur le disque");
            alert.showAndWait();
        }else{
            alert.setContentText("Le fichier n'a pas été trouvé sur le disque");
            alert.showAndWait();
        }
    }

    public void openFile(String path){
        openFile(new File(path));
    }



    public ArrayList<FileInformations> getFileInformations(){
        return fileInformations;
    }

    /*
        Private method
     */
    private void createTab(File file, boolean newFile){
        CodeArea codeArea = new CodeArea();
        VirtualizedScrollPane<CodeArea> scrollPane = new VirtualizedScrollPane<>(codeArea);
        Tab tab = new Tab(file.getName(), scrollPane);

        GCode gCode;
        if(newFile)
            gCode = new GCode(file.getName(), "", codeArea, tab);
        else
            gCode = new GCode(file.getName(), new OpenFile(file).getFileContent(), codeArea, tab);

        codeArea.appendText(gCode.getContent());
        codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));
        codeArea.moveTo(0);

        codeArea.textProperty().addListener((observable, oldValue, newValue) -> gCode.setContent(newValue));

        Main.rootController.getFilesTabPane().getTabs().add(tab);
        Main.rootController.getFilesTabPane().getSelectionModel().select(tab);
        fileInformations.add(new FileInformations(file, gCode, tab));
    }


}
