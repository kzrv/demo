package com.example.demo.scrible;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.embed.swing.SwingFXUtils;


import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

public class Scrible extends Application {
    Point2D pint = null;
    GraphicsContext gc;
    ColorPicker cp;
    @Override
    public void start(Stage stage) throws Exception {

        VBox vBox = new VBox(12);
        Canvas canvas = new Canvas();
        gc = canvas.getGraphicsContext2D();
        canvas.setWidth(800);
        canvas.setHeight(400);
        cp = new ColorPicker();
        gc.setStroke(Color.BLACK);
        Button btn = new Button("Clear");
        Button save = new Button("Save");
        save.setOnAction(actionEvent -> {
            FileChooser savefile = new FileChooser();
            savefile.setTitle("Save File");
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PNG files", "*.PNG");
            savefile.getExtensionFilters().add(extFilter);

            File file = savefile.showSaveDialog(stage);
            if (file!=null){
                WritableImage writableImage = new WritableImage((int)canvas.getWidth(),(int)canvas.getHeight());
                canvas.snapshot(null,writableImage);
                RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage,null);
                try {
                    ImageIO.write(renderedImage,"png",file);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        btn.setOnAction(actionEvent -> {
            gc.clearRect(0,0,canvas.getWidth(),canvas.getHeight());
        });
        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                handleMousePressed(mouseEvent);
            }
        });
        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                handleMouseDragged(mouseEvent);
            }
        });
        canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                handleMouseRealesed(mouseEvent);
            }
        });
//        Button save = new Button("Save");
//        save.setOnAction(actionEvent -> {
//            FileChooser fileChooser = new FileChooser();
//            FileChooser.ExtensionFilter extensionFilter =   new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
//            fileChooser.getExtensionFilters().add(extensionFilter);
//            File file = fileChooser.showSaveDialog(stage);
//            if(file!=null){
//                WritableImage writableImage = new WritableImage(800,400);
//                canvas.snapshot(null,writableImage);
//
//                SwingFXUtils.fromFXImage
//            }
//        });

        HBox hbox = new HBox(cp, btn,save);
        vBox.getChildren().addAll(hbox,canvas);
        stage.setTitle("Scrible");
        stage.setScene(new Scene(vBox));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    public void handleMousePressed(MouseEvent mouseEvent) {
        gc.setStroke(cp.getValue());
        pint = new Point2D(mouseEvent.getX(),mouseEvent.getY());
        gc.strokeLine(pint.getX(),pint.getY(),pint.getX(),pint.getY());
    }
    public void handleMouseDragged(MouseEvent me){
        gc.strokeLine(pint.getX(),pint.getY(),me.getX(),me.getY());
        pint = new Point2D(me.getX(),me.getY());
    }
    public void handleMouseRealesed(MouseEvent me){
        gc.strokeLine(pint.getX(),pint.getY(),me.getX(),me.getY());
        pint = null;
    }
}
