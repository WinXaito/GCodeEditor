package com.winxaito.gcodeeditor.views;

import com.winxaito.gcodeeditor.Main;
import com.winxaito.gcodeeditor.modules.gcode.GCode;
import com.winxaito.gcodeeditor.modules.gcode.GCodeDisplayer;
import com.winxaito.gcodeeditor.utils.FileInformations;
import com.winxaito.gcodeeditor.utils.SaveFile;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Created by: WinXaito (Kevin Vuilleumier)
 */
public class RootController extends ViewController{
    private GCodeDisplayer gCodeDisplayer;
    @FXML private Label versionLabel;
    @FXML private TabPane filesTabPane;

    @FXML private MenuItem menuFileSave;
    @FXML private MenuItem menuFileSaveAs;
    @FXML private MenuItem menuToolMulticolors;

    @FXML private Label homeLink_recentFilesLabel;
    @FXML private Hyperlink homeLink_recent_1;
    @FXML private Hyperlink homeLink_recent_2;
    @FXML private Hyperlink homeLink_recent_3;
    @FXML private Hyperlink homeLink_recent_4;
    @FXML private Hyperlink homeLink_recent_5;
    @FXML private Hyperlink homeLink_recent_6;

    @Override
    public void setMain(Main main){
        this.main = main;
        this.gCodeDisplayer = main.getGCodeDisplayer();
    }

    public TabPane getFilesTabPane(){
        return filesTabPane;
    }

    /*
        Init
     */
    @FXML private void initialize(){
        versionLabel.setText("Version " + Main.version);

        disableGCodeActions();
        filesTabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.getText().equalsIgnoreCase("accueil")){
                disableGCodeActions();
            }else{
                enableGCodeActions();
            }
        });

        /*
        TODO: Activate the "Recent file module"

        homeLink_recentFilesLabel.setVisible(!Main.recentFiles.existRecentFile()[0]);

        if(Main.recentFiles.existRecentFile()[0]){
            homeLink_recent_1.setVisible(true);
            homeLink_recent_1.setText(Main.recentFiles.getRecentFile(0));
        }
        if(Main.recentFiles.existRecentFile()[1]){
            homeLink_recent_2.setVisible(true);
            homeLink_recent_2.setText(Main.recentFiles.getRecentFile(1));
        }
        if(Main.recentFiles.existRecentFile()[2]){
            homeLink_recent_3.setVisible(true);
            homeLink_recent_3.setText(Main.recentFiles.getRecentFile(2));
        }
        if(Main.recentFiles.existRecentFile()[3]){
            homeLink_recent_4.setVisible(true);
            homeLink_recent_4.setText(Main.recentFiles.getRecentFile(3));
        }
        if(Main.recentFiles.existRecentFile()[4]){
            homeLink_recent_5.setVisible(true);
            homeLink_recent_5.setText(Main.recentFiles.getRecentFile(4));
        }
        if(Main.recentFiles.existRecentFile()[5]){
            homeLink_recent_6.setVisible(true);
            homeLink_recent_6.setText(Main.recentFiles.getRecentFile(5));
        }

        homeLink_recent_1.setOnMouseClicked(event -> openRecentFile(1));
        homeLink_recent_2.setOnMouseClicked(event -> openRecentFile(2));
        homeLink_recent_3.setOnMouseClicked(event -> openRecentFile(3));
        homeLink_recent_4.setOnMouseClicked(event -> openRecentFile(4));
        homeLink_recent_5.setOnMouseClicked(event -> openRecentFile(5));
        homeLink_recent_6.setOnMouseClicked(event -> openRecentFile(6));
        */
    }

    /*
        File actions
     */
    @FXML public void handleMenuFileNew(){
        gCodeDisplayer.createNewFile();
    }

    @FXML public void handleMenuFileOpen(){
        gCodeDisplayer.openFile();
    }

    @FXML public void handleMenuFileOpenDago(){
        gCodeDisplayer.openFile("G:/Dagoma0.g");
    }

    @FXML public void handleMenuFileSave(){
        SaveFile.saveFile(filesTabPane, gCodeDisplayer.getFileInformations());
    }

    @FXML public void handleMenuFileSaveAs(){
        SaveFile.saveAs(filesTabPane, gCodeDisplayer.getFileInformations());
    }

    @FXML public void handleMenuFileQuit(){
    }

    /*
        Edit actions
     */
    @FXML public void handleMenuEditPrinters(){
        Stage printersStage = createWindow("Imprimantes", "printers", this, false);
        printersStage.initOwner(window);
        printersStage.initModality(Modality.WINDOW_MODAL);
        printersStage.show();
    }

    @FXML public void handleMenuEditPreferences(){
        Stage preferencesStage = createWindow("Préférences", "preferences", this, false);
        preferencesStage.initOwner(window);
        preferencesStage.initModality(Modality.WINDOW_MODAL);
        preferencesStage.show();
    }

    /*
        Tools actions
     */
    @FXML public void handleMenuToolPrintMulticolors(){
        Stage multiColorsStage = createWindow("Impression", "multipleColors", this, false);
        MultipleColorsController controller = (MultipleColorsController)getWindowController().get(multiColorsStage);
        controller.setProgrammeName(filesTabPane.getSelectionModel().getSelectedItem().getText());
        GCode gCode = FileInformations.getGCodeWithTab(
                filesTabPane.getSelectionModel().getSelectedItem(), gCodeDisplayer.getFileInformations());

        assert gCode != null;
        controller.setNumberLayers(gCode.getLayerCount());

        multiColorsStage.initOwner(window);
        multiColorsStage.initModality(Modality.WINDOW_MODAL);
        multiColorsStage.show();
    }


    /*
        Display actions
     */


    /*
        Help actions
     */
    @FXML public void handleMenuHelpDoc(){
        main.getHostServices().showDocument("https://geditor.winxaito.com");
    }

    @FXML public void handleMenuHelpDocGCode(){
        main.getHostServices().showDocument("https://geditor.winxaito.com");
    }

    @FXML public void handleMenuHelpTutorials(){
        main.getHostServices().showDocument("https://geditor.winxaito.com");
    }

    @FXML public void handleMenuHelpAbout(){
        Stage aboutStage = createWindow("A propos", "about", this, false);
        aboutStage.initOwner(window);
        aboutStage.initModality(Modality.WINDOW_MODAL);
        aboutStage.show();
    }

    /* Private actions */
    private void openRecentFile(int number){

    }

    private void disableGCodeActions(){
        menuFileSave.setDisable(true);
        menuFileSaveAs.setDisable(true);
        menuToolMulticolors.setDisable(true);
    }

    private void enableGCodeActions(){
        menuFileSave.setDisable(false);
        menuFileSaveAs.setDisable(false);
        menuToolMulticolors.setDisable(false);
    }
}
