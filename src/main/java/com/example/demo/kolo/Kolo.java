package com.example.demo.kolo;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.stage.Stage;

public class Kolo extends Application {

    public final double canvas_vyska = 600;
    public final double canvas_sirka = 800;
    public final double min_kolo = 50;

    @Override
    public void start(Stage stage) throws Exception {

        Canvas canvas = new Canvas();
        Label lbl = new Label("Spices count:");
        Slider spice = new Slider(3,100,3);
        Label lbl1 = new Label("Center disk radius[%]: ");
        Slider disk = new Slider(0.3,0.5,0.3);
        Label lbl2 = new Label("Tire thickness [%]");
        Slider kolo = new Slider(0.1, 0.38, 0.1);

        ToolBar toolBar = new ToolBar(lbl,spice,lbl1,disk,lbl2,kolo);
        toolBar.setPrefHeight(50);
        toolBar.setCenterShape(true);
        VBox vBox = new VBox(canvas,toolBar);
        vBox.setPrefSize(canvas_sirka,canvas_vyska);
        draw(canvas.getGraphicsContext2D(),(int)spice.getValue(),disk.getValue(),kolo.getValue(),canvas.getWidth(),canvas.getHeight());


        ChangeListener<Number> change = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                int spices = (int)spice.getValue();
                double disks = disk.getValue();
                double tire = kolo.getValue();
                draw(canvas.getGraphicsContext2D(),spices,disks,tire,canvas.getWidth(),canvas.getHeight());
            }
        };

        canvas.widthProperty().addListener(change);
        canvas.heightProperty().addListener(change);
        spice.valueProperty().addListener(change);
        disk.valueProperty().addListener(change);
        kolo.valueProperty().addListener(change);

        canvas.widthProperty().bind(vBox.widthProperty());
        canvas.heightProperty().bind(vBox.heightProperty().subtract(toolBar.prefHeightProperty()));


        stage.setTitle("Kolo");
        stage.setScene(new Scene(vBox));
        stage.show();
    }

    private void draw(GraphicsContext gc, int spices, double disks, double tire,double sirka, double vyska) {

        double koloR = sirka/4*disks;

        gc.clearRect(0,0,sirka,vyska);
        gc.setFill(Color.BLACK);
        gc.fillOval(sirka/2-(koloR/2),vyska/2-(koloR/2), koloR,koloR);
        double koloL = vyska/4*tire*10;
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(8);
        gc.strokeOval(sirka/2-(koloL/2),vyska/2-(koloL/2), koloL,koloL);
        double koloRed = vyska/4*tire*10+5;
        gc.setStroke(Color.RED);
        gc.setLineWidth(3);
        gc.strokeOval(sirka/2-(koloRed/2),vyska/2-(koloRed/2), koloRed,koloRed);
        Affine affine =  new Affine();
        double rotate = 360.0/spices;
        affine.appendRotation(rotate,sirka/2,vyska/2);
        gc.setLineWidth(1);
        gc.setStroke(Color.BLACK);
        for (int i = 0 ; i<spices ; i++){
            gc.strokeLine(sirka/2,vyska/2,sirka/2-(koloL/2.85),vyska/2-(koloL/2.85));
            gc.transform(affine);

        }

    }
}
