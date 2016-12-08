package com.winxaito.gcodeeditor;

import com.winxaito.gcodeeditor.modules.gcode.GCodeDisplayer;
import com.winxaito.gcodeeditor.utils.Configuration;
import com.winxaito.gcodeeditor.utils.RecentFiles;
import com.winxaito.gcodeeditor.views.RootController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Created by: WinXaito (Kevin Vuilleumier)
 */
public class Main extends Application{
    public static final String version = "Bêta 0.0.1";
    public static Stage primaryStage;
    public static RootController rootController;
    private static GCodeDisplayer gCodeDisplayer;

    public static Configuration config;
    public static RecentFiles recentFiles;

    @Override
    public void start(Stage primaryStage) throws IOException{
        Main.primaryStage = primaryStage;

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("views/root.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("GCodeEditor");
        primaryStage.setMinWidth(700);
        primaryStage.setMinHeight(500);

        Scene scene = new Scene(root, 1000, 650);
        scene.getStylesheets().add(Main.class.getResource("/highligts.css").toExternalForm());
        scene.getStylesheets().add(Main.class.getResource("/theme_light.css").toExternalForm());
        primaryStage.setScene(scene);
        rootController = loader.getController();
        rootController.setMain(this);
        rootController.setWindow(primaryStage);
        primaryStage.show();
    }

    public static void main(String[] args){
        gCodeDisplayer = new GCodeDisplayer();
        config = new Configuration();
        recentFiles = new RecentFiles();
        launch(args);
    }


    /*
        Setters and getters
     */
    public GCodeDisplayer getGCodeDisplayer(){
        return gCodeDisplayer;
    }
}
