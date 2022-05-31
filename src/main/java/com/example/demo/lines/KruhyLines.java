package com.example.demo.lines;

import com.sun.javafx.geom.Area;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;

public class KruhyLines extends Application {
    private final double sirka = 800;
    private final double vysska = 520;
    private double rozmer;
    private double getX ;
    private double getY;
    private Circle circle1;
    ArrayList<Bounds> arrayList;

    @Override
    public void start(Stage stage) throws Exception {
        VBox root = new VBox();
        Pane pane = new Pane();
        pane.setPrefHeight(vysska);
        pane.setPrefWidth(sirka);
        HBox tool = new HBox(15);
        tool.setAlignment(Pos.CENTER_LEFT);
        tool.setPrefSize(sirka,vysska/10);
        Button konec = new Button("konec");
        Button vymazat = new Button("Vymazat");
        Label pocet = new Label("0");
        rozmer = 5;
        arrayList = new ArrayList<>();
        tool.getChildren().addAll(konec,vymazat,pocet);
        konec.setOnAction(actionEvent -> stage.close());
        vymazat.setOnAction(actionEvent -> {
            pane.getChildren().clear();
            Rectangle rectangle = new Rectangle(0,0,pane.getWidth(),pane.getHeight());
            rectangle.setFill(creatG(pane));
            pane.getChildren().add(rectangle);
            arrayList.clear();

        });
        pane.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getButton()== MouseButton.PRIMARY){
                    getX = mouseEvent.getX();
                    getY = mouseEvent.getY();

                }
            }
        });
        pane.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getButton()== MouseButton.PRIMARY) {
                    pane.getChildren().remove(circle1);
                    double rozmer1 = (mouseEvent.getX() - getX) + (mouseEvent.getY() - getY) +rozmer;
                    circle1 = new Circle(getX, getY, rozmer1);
                    circle1.setFill(Color.AQUA);
                    circle1.setOpacity(0.5);
                    pane.getChildren().add(circle1);
                }
            }
        });

        pane.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getButton()== MouseButton.PRIMARY) {
                    pane.getChildren().remove(circle1);
                    double rozmer1 =rozmer+ (mouseEvent.getX() - getX) + (mouseEvent.getY() - getY);
                    Circle circle2 = new Circle(getX, getY, rozmer1);


                    circle2.setStrokeWidth(2);
                    if(arrayList.stream().anyMatch(s->circle2.getBoundsInParent().intersects(s))){
                        circle2.setFill(Color.GREEN);
                        circle2.setOpacity(0.5);
                        circle2.setStroke(Color.DARKGREEN);
                    } else {
                        circle2.setFill(Color.BLUE);
                        circle2.setStroke(Color.DARKBLUE);
                    }
                    //circle.getStrokeDashArray().addAll(2d,21d);

                    arrayList.add(circle2.getBoundsInParent());
                    circle2.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent1) {
                            if(mouseEvent1.getButton()==MouseButton.SECONDARY){
                                pane.getChildren().remove(circle2);
                            }
                        }
                    });
                    circle2.getStrokeDashArray().add(10.0);
                    pane.getChildren().add(circle2);
                }
            }
        });

        ChangeListener<Number> cl = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {

                tool.setPrefSize(root.getWidth(),root.getHeight()/10);
                pane.setPrefSize(root.getWidth(),root.getHeight()-tool.getHeight());
                vymazat.fire();
            }
        };
        root.heightProperty().addListener(cl);
        root.widthProperty().addListener(cl);
        pane.widthProperty().addListener(cl);
        pane.heightProperty().addListener(cl);
        tool.widthProperty().addListener(cl);
        tool.heightProperty().addListener(cl);

        vymazat.fire();
        root.getChildren().addAll(pane,tool);
        stage.setTitle("fd");
        stage.setScene(new Scene(root));
        stage.show();
    }
    private LinearGradient creatG(Pane pane){
        Stop[] stoplist = new Stop[]{new Stop(0, Color.WHITE), new Stop(pane.getWidth(),Color.RED)};
        LinearGradient lg = new LinearGradient(0,0,pane.getWidth(),pane.getHeight(),false, CycleMethod.NO_CYCLE,stoplist);
        return lg;
    }
}
