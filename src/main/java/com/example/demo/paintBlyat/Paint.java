package com.example.demo.paintBlyat;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


public class Paint extends Application {
    private Color barva;
    private double size =10;
    private String shape;
    @Override
    public void start(Stage stage) throws Exception {
        FlowPane flowPane = new FlowPane();
        Pane pane = new Pane();
        pane.setPrefSize(720,480);
        HBox hBox = new HBox();
        Button exit = new Button("Exit");
        exit.setOnAction(actionEvent -> {
            stage.close();
        });
        Label lbl = new Label("Color: ");
        ColorPicker picker = new ColorPicker(Color.BLUE);
        picker.valueProperty().addListener(new ChangeListener<Color>() {
            @Override
            public void changed(ObservableValue<? extends Color> observableValue, Color color, Color t1) {
                barva = t1;
            }
        });

        Slider slider = new Slider(10,100,10);
        slider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                size = (double) t1;
            }
        });
        slider.setShowTickMarks(true);
        slider.setShowTickLabels(true);
        slider.setMajorTickUnit(10);
        slider.setMinorTickCount(0);
        slider.setSnapToTicks(true);

        ObservableList<String> list = FXCollections.observableArrayList("POLYGON", "CETVEREC", "KRUH");
        ComboBox<String> combo = new ComboBox<>(list);
        combo.setValue("Vyberte:");
        combo.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                shape = t1;
            }
        });
        pane.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getButton()== MouseButton.PRIMARY){
                    switch (shape){
                        case "CETVEREC" ->{
                            Rectangle rect = new Rectangle(mouseEvent.getX()-size/2,mouseEvent.getY()-size/2,size,size);
                            rect.setFill(barva);
                            pane.getChildren().add(rect);
                            rect.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(MouseEvent mouseEvent) {
                                    if(mouseEvent.getButton()==MouseButton.SECONDARY)
                                    pane.getChildren().remove(rect);
                                }
                            });
                        }
                        case "KRUH" ->{
                            Circle circle = new Circle(mouseEvent.getX(),mouseEvent.getY(),size/2);
                            circle.setFill(barva);
                            pane.getChildren().add(circle);

                            circle.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(MouseEvent mouseEvent) {
                                    if(mouseEvent.getButton()==MouseButton.SECONDARY)
                                        pane.getChildren().remove(circle);
                                }
                            });
                        }
                        case "POLYGON" ->{
                            double size1 = size/2;
                            double polg[] = {
                                    mouseEvent.getX()-size1, mouseEvent.getY(),
                                    mouseEvent.getX()-size1/2, mouseEvent.getY()+size1,
                                    mouseEvent.getX()+size1/2, mouseEvent.getY()+size1,
                                    mouseEvent.getX()+size1, mouseEvent.getY(),
                                    mouseEvent.getX()+size1/2, mouseEvent.getY()-size1,
                                    mouseEvent.getX()-size1/2, mouseEvent.getY()-size1};
                            Polygon pol = new Polygon(polg);
                            pol.setFill(barva);
                            pane.getChildren().add(pol);
                            pol.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(MouseEvent mouseEvent) {
                                    if(mouseEvent.getButton()==MouseButton.SECONDARY)
                                        pane.getChildren().remove(pol);
                                }
                            });
                        }
                    }
                }
            }
        });

        hBox.getChildren().addAll(exit,lbl,picker,slider,combo);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(25);
        hBox.setPrefSize(720,50);
        hBox.setStyle("-fx-background-color: #e8e8e8");


        flowPane.getChildren().addAll(pane,hBox);
        stage.setScene(new Scene(flowPane));
        stage.setTitle("Obdelnik");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
