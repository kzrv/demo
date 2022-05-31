package com.example.demo.chess;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


public class fx extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FlowPane flowPane = new FlowPane();
        flowPane.setOrientation(Orientation.HORIZONTAL);
        Pane pane = new Pane();
        pane.setPrefSize(720,480);
        //pane.setMinSize(420, 240);
        HBox hBox = new HBox();
        hBox.setSpacing(15);
//        hBox.setPrefHeight(50);
//        hBox.setPrefWidth(720);
        hBox.setPrefSize(flowPane.getWidth(),flowPane.getHeight()/8);

        hBox.setAlignment(Pos.CENTER);
        Label lbl = new Label("Rows");
        Label lbl1 = new Label("Columns");
        Spinner<Integer> spinner = new Spinner<>(1,100,4);
        Spinner<Integer> spinnerCol = new Spinner<>(1,100,4);


        ChangeListener<Number> changeListener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                pane.getChildren().clear();
                double rozmerX = pane.getWidth();
                double rozmerY = pane.getHeight();
                hBox.setPrefSize(flowPane.getWidth(),flowPane.getHeight()/8);
                pane.setPrefSize(flowPane.getWidth(),flowPane.getHeight()-hBox.getHeight());
                //ObservableList<Rectangle> list = FXCollections.observableArrayList();
                int rows = spinner.getValue();
                int colums = spinnerCol.getValue();
                int pocet =0;
                for(int i = 0; i<rows;i++){
                    for(int j = 0;j<colums;j++) {
                        Rectangle rect = new Rectangle(
                                rozmerX / colums * j,
                                rozmerY / rows* i,
                                rozmerX / colums,
                                rozmerY / rows
                        );pocet++;
                        if (pocet % 2 == 0) rect.setFill(Color.WHITE);
                        else rect.setFill(Color.BLACK);
                        pane.getChildren().add(rect);
                        rect.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent mouseEvent) {
                                if(rect.getFill()==Color.WHITE) rect.setFill(Color.BLACK);
                                else rect.setFill(Color.WHITE);
                            }
                        });
                    }
                        if((colums%2==0))pocet-=1;
                }
            }
        };
        pane.widthProperty().addListener(changeListener);
        pane.heightProperty().addListener(changeListener);
        hBox.widthProperty().addListener(changeListener);
        hBox.heightProperty().addListener(changeListener);
        flowPane.heightProperty().addListener(changeListener);
        flowPane.widthProperty().addListener(changeListener);
        spinner.valueProperty().addListener(changeListener);
        spinnerCol.valueProperty().addListener(changeListener);
        hBox.getChildren().addAll(lbl,spinner,lbl1,spinnerCol);
        flowPane.getChildren().addAll(pane,hBox);
        stage.setTitle("Chess");
        stage.setScene(new Scene(flowPane));
        stage.show();
    }
}
