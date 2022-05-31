package com.example.demo.semKruznice;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class Kruznice extends Application {

    private final double SIRKA = 800;
    private final double VYSKA = 480;
    private int pocet;

    @Override
    public void start(Stage stage) throws Exception {
        VBox root = new VBox();
        HBox tool = new HBox(15);
        tool.setAlignment(Pos.CENTER);
        Canvas canvas = new Canvas(SIRKA,VYSKA);
        tool.setPrefSize(SIRKA,VYSKA/12);
        Button ukon = new Button("Ukoncit aplikace");
        Button vymazat = new Button("Vymazat");
        Label lbl = new Label("Barva 1:");
        Label lbl1 = new Label("Barva 2:");
        ColorPicker cl1 = new ColorPicker(Color.RED);
        ColorPicker cl2 = new ColorPicker(Color.BLUE);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        extracted(canvas, gc);

        ukon.setOnAction(actionEvent -> stage.close());
        vymazat.setOnAction(actionEvent -> {
            extracted(canvas, gc);
        });
        tool.getChildren().addAll(ukon,vymazat,lbl,cl1,lbl1,cl2);

        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getButton()== MouseButton.SECONDARY){
                    start(gc,canvas.getWidth(),canvas.getHeight(),cl1.getValue(),cl2.getValue(),mouseEvent);
                }
            }
        });


        ChangeListener<Number> cl = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {

                tool.setPrefWidth(root.getWidth());
                tool.setPrefHeight(root.getHeight()/12);
                canvas.setWidth(root.getWidth());
                canvas.setHeight(root.getHeight()-tool.getHeight());
                extracted(canvas,gc);
            }
        };
        canvas.heightProperty().addListener(cl);
        canvas.widthProperty().addListener(cl);
        root.widthProperty().addListener(cl);
        root.heightProperty().addListener(cl);
        tool.widthProperty().addListener(cl);
        tool.heightProperty().addListener(cl);
        root.getChildren().addAll(tool,canvas);
        stage.setTitle("Zapocet: Kozyrev Vasilii");
        stage.setScene(new Scene(root));
        stage.show();
    }

    private void extracted(Canvas canvas, GraphicsContext gc) {
        pocet = 1;
        gc.setFill(Color.valueOf("FFFFD0"));
        gc.fillRect(0,0,canvas.getWidth(),canvas.getHeight());
        gc.setLineWidth(1);
        gc.setStroke(Color.BLACK);
        gc.strokeLine(0,0,canvas.getWidth(),canvas.getHeight());
        gc.strokeLine(canvas.getWidth(),0,0,canvas.getHeight());
    }

    private void start(GraphicsContext gc, double can_sirka, double can_vyska, Color color1, Color color2,MouseEvent me) {
            for (int i = 0; i < pocet+1; i++) {
                double rozmer = i * 10;
                if (me.getX()+rozmer/2 - rozmer > 0 && me.getX()-rozmer/2 + rozmer < can_sirka && me.getY() +rozmer/2 - rozmer > 0 && me.getY()-rozmer/2 +rozmer < can_vyska) {
                    if (i % 2 == 0) gc.setStroke(color1);
                    else gc.setStroke(color2);
                    gc.strokeOval(me.getX() - rozmer / 2, me.getY() - rozmer / 2, rozmer, rozmer);
                }

        }

            pocet++;
    }
}
