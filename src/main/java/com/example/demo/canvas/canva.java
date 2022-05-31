package com.example.demo.canvas;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class canva extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Group root = new Group();
        Canvas canvas = new Canvas(300,250);
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        canvas.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                canvas.setHeight(stage.getHeight());
                canvas.setWidth(stage.getWidth());
            }
        });
        look(graphicsContext);
        root.getChildren().add(canvas);
        stage.setTitle("Canva");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void look(GraphicsContext gc){
        gc.setFill(Color.GREEN);
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(1);


        gc.fillRect(10,10,100,100);
        gc.strokeLine(300,0,0,250);
        gc.strokeLine(0,0,300,250);



    }
}
