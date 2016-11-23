package com.winxaito.gcodeeditor.views;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.ArrayList;

/**
 * Created by: WinXaito (Kevin Vuilleumier)
 */
public class MultipleColorsController extends ViewController{
    private ArrayList<TextField> layers = new ArrayList<>();
    private ArrayList<TextField> temperatures = new ArrayList<>();

    @FXML private ComboBox<String> printers;
    @FXML private Label programmeName;
    @FXML private Label numberLayerLabel;
    @FXML private ComboBox<Integer> numberLayer;
    private int numberLayerInt;

    @FXML private TextField layer_1;
    @FXML private TextField temp_1;
    @FXML private TextField layer_2;
    @FXML private TextField temp_2;
    @FXML private TextField layer_3;
    @FXML private TextField temp_3;
    @FXML private TextField layer_4;
    @FXML private TextField temp_4;
    @FXML private TextField layer_5;
    @FXML private TextField temp_5;

    @FXML private void initialize(){
        numberLayer.getItems().addAll(1, 2, 3, 4, 5);
        numberLayer.setValue(1);
        printers.getItems().addAll("DiscoEasy200", "DiscoVery200");
        printers.setValue("DiscoEasy200");

        layers.add(layer_1);
        layers.add(layer_2);
        layers.add(layer_3);
        layers.add(layer_4);
        layers.add(layer_5);
        temperatures.add(temp_1);
        temperatures.add(temp_2);
        temperatures.add(temp_3);
        temperatures.add(temp_4);
        temperatures.add(temp_5);

        numberLayer.valueProperty().addListener((observable, oldValue, newValue) -> {
            disableLayerInstruction();

            for(int i = 0;i < newValue;i++){
                layers.get(i).setDisable(false);
                temperatures.get(i).setDisable(false);
            }
        });

        layers.forEach(this::addLayerListener);
    }

    @FXML public void handleButtonCancelAction(){
        window.close();
    }

    @FXML public void handleButtonValidButton(){
        Alert a = new Alert(Alert.AlertType.WARNING);
        a.setTitle("Attention");
        a.setHeaderText(null);
        a.setContentText("Cette option est indisponible dans cette version");
        a.showAndWait();
    }

    public void setProgrammeName(String programmeName){
        this.programmeName.setText(programmeName);
    }

    public void setNumberLayers(int numberLayers){
        numberLayerInt = numberLayers;
        this.numberLayerLabel.setText(String.valueOf(numberLayers));
    }


    private void disableLayerInstruction(){
        for(TextField layer : layers)
            layer.setDisable(true);

        for(TextField temp : temperatures)
            temp.setDisable(true);
    }

    private void addLayerListener(TextField layer){
        layer.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.equalsIgnoreCase(""))
                return;

            int value = 0;
            try{
                value = Integer.valueOf(newValue);
            }catch(Exception e){
                layer.setText(oldValue);
                return;
            }

            if(value < 0 || value > numberLayerInt){
                layer.setText(oldValue);
            }
        });
    }

    private void addTempListener(TextField temp){
        temp.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.equalsIgnoreCase(""))
                return;

            int value = 0;
            try{
                value = Integer.valueOf(newValue);
            }catch(Exception e){
                temp.setText(oldValue);
                return;
            }

            if(value < 0 || value > 300){
                temp.setText(oldValue);
            }
        });
    }
}
