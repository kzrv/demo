package com.example.demo.malovani;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


public class mainFX extends Application {
    private Color barva;
    private final double size = 30;
    @Override
    public void start(Stage stage) throws Exception {

        Pane pane = new Pane();
        ColorPicker picker = new ColorPicker();
        picker.valueProperty().addListener(new ChangeListener<Color>() {
            @Override
            public void changed(ObservableValue<? extends Color> observableValue, Color color, Color t1) {
                barva = t1;
            }
        });
        pane.getChildren().add(picker);
        pane.setPrefSize(720,480);
        pane.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                switch (mouseEvent.getButton()){
                    case PRIMARY -> {
                        Rectangle rect = new Rectangle(mouseEvent.getX()-size/2,mouseEvent.getY()-size/2,size,size);
                        rect.setFill(barva);
                        pane.getChildren().add(rect);

                        rect.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent mouseEvent) {
                                if(mouseEvent.getButton()== MouseButton.SECONDARY){
                                    pane.getChildren().remove(rect);
                                }
                            }
                        });
                        rect.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent mouseEvent) {
                                rect.setFill(Color.BLUE);
                            }
                        });
                        rect.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent mouseEvent) {
                                rect.setFill(barva);
                            }
                        });
                    }
                }
            }
        });
        stage.setScene(new Scene(pane));
        stage.setTitle("Obdelnik");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
