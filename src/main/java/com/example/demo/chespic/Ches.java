package com.example.demo.chespic;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class Ches extends Application {
    private final double PANE_SIRKA = 800;
    private final double PANE_VYSKA = 600;
    private ImageView imageView;
    private boolean isUpload;

    @Override
    public void start(Stage stage) throws Exception {
        FlowPane root = new FlowPane();
        Pane pane = new Pane();
        pane.setPrefSize(PANE_SIRKA,PANE_VYSKA);
        Button btn = new Button("Load picture");
        Spinner<Integer> strokes = new Spinner<>(1,100,8);
        Spinner<Integer> columns = new Spinner<>(1,100,8);
        btn.setOnAction(actionEvent -> {
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(stage);
            if(file!=null){
                Image image = new Image(file.toURI().toString());
                imageView = new ImageView(image);
                imageView.setFitHeight(PANE_VYSKA);
                imageView.setFitWidth(PANE_SIRKA);
                pane.getChildren().add(imageView);
                draw(pane.getWidth(), pane.getHeight(),pane,columns.getValue(),strokes.getValue());
                isUpload=true;
            }
        });
        ToolBar toolBar = new ToolBar(btn,strokes,columns);
        toolBar.setPrefSize(PANE_SIRKA,PANE_VYSKA/16);
        ChangeListener<Number> listener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                pane.getChildren().clear();
                toolBar.setPrefSize(root.getWidth(),root.getHeight()/16);
                pane.setPrefSize(root.getWidth(),root.getHeight()-toolBar.getHeight());

                if(isUpload)pane.getChildren().add(imageView);
                draw(pane.getWidth(), pane.getHeight(),pane,columns.getValue(),strokes.getValue());
            }
        };
        strokes.valueProperty().addListener(listener);
        columns.valueProperty().addListener(listener);




        draw(pane.getWidth(), pane.getHeight(),pane,columns.getValue(),strokes.getValue());
        pane.heightProperty().addListener(listener);
        pane.widthProperty().addListener(listener);
        toolBar.heightProperty().addListener(listener);
        toolBar.widthProperty().addListener(listener);
        root.widthProperty().addListener(listener);
        root.heightProperty().addListener(listener);
        root.getChildren().addAll(pane,toolBar);

        stage.setTitle("Chess");
        stage.setScene(new Scene(root));
        stage.show();
    }

    private void draw(double delkaPane, double vyskaPane, Pane pane,int columnsRN,int strokesRN){
        int pocet = 0;
        double delka = delkaPane/columnsRN+1;
        double vyska = vyskaPane/strokesRN+1;
        for(int i = 0; i<strokesRN;i++){
            for (int j = 0; j<columnsRN;j++){
                pocet++;
                Rectangle rect = new Rectangle(j*delka,i*vyska,delka,vyska);
                rect.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        if(rect.getOpacity()==0) rect.setOpacity(1);
                        else rect.setOpacity(0);
                    }
                });
                rect.setFill(Color.BLACK);
                if(pocet%2 !=0) rect.setOpacity(0);
                pane.getChildren().add(rect);
            }
            if(columnsRN%2==0) pocet--;
        }
    }
}
