package com.winxaito.gcodeeditor.views;

import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;

/**
 * Created by: WinXaito (Kevin Vuilleumier)
 */
public class AboutController extends ViewController{
    @FXML private Hyperlink aboutLink_license;
    @FXML private Hyperlink aboutLink_github;
    @FXML private Hyperlink aboutLink_website;

    @FXML private Hyperlink aboutLink_richtextfx_license;
    @FXML private Hyperlink aboutLink_richtextfx_github;

    @FXML
    private void initialize(){
        aboutLink_license.setOnMouseClicked(event -> main.getHostServices().showDocument(
                "https://github.com/WinXaito/"));
        aboutLink_github.setOnMouseClicked(event -> main.getHostServices().showDocument(
                "https://github.com/WinXaito/"));
        aboutLink_website.setOnMouseClicked(event -> main.getHostServices().showDocument(
                "http://winxaito.com"));

        aboutLink_richtextfx_license.setOnMouseClicked(event -> main.getHostServices().showDocument(
                "https://github.com/TomasMikula/RichTextFX/blob/master/LICENSE"));
        aboutLink_richtextfx_github.setOnMouseClicked(event -> main.getHostServices().showDocument(
                "https://github.com/TomasMikula/RichTextFX"));
    }
}
