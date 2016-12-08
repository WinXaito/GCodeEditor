package com.winxaito.gcodeeditor.views;

import com.winxaito.gcodeeditor.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by: WinXaito (Kevin Vuilleumier)
 */
public class ViewController{
    protected Main main;
    protected ViewController controllerParent;
    protected Stage window;
    protected HashMap<Stage, ViewController> windowController = new HashMap<>();

    public void setMain(Main main){
        this.main = main;
    }

    public Main getMain(){
        return main;
    }

    public void setControllerParent(ViewController controllerParent){
        this.controllerParent = controllerParent;
    }

    public ViewController getControllerParent(){
        return controllerParent;
    }

    public void setWindow(Stage window){
        this.window = window;
        insertLogo(window);
    }

    public Stage getWindow(){
        return window;
    }

    public HashMap<Stage, ViewController> getWindowController(){
        return windowController;
    }

    protected Stage createWindow(String title, String loadName, ViewController controllerParent, boolean resizable){
        Stage stage = null;

        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(loadName + ".fxml"));
            Parent root = loader.load();

            stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle(title);
            stage.setResizable(resizable);
            scene.getStylesheets().addAll(window.getScene().getStylesheets());

            ViewController controller = loader.getController();
            windowController.put(stage, controller);

            if(controller != null){
                controller.setMain(main);
                controller.setControllerParent(controllerParent);
                controller.setWindow(stage);
            }
        }catch(IOException e){
            e.printStackTrace();
        }

        return stage;
    }

    protected void insertLogo(Stage stage){
        try{
            stage.getIcons().add(new Image(Main.class.getResource("/logo.png").openStream()));
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    protected void addAlertLogo(Alert alert){
        Stage alertStage = (Stage)alert.getDialogPane().getScene().getWindow();
        insertLogo(alertStage);
    }
}
