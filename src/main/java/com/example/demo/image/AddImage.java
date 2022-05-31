package com.example.demo.image;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;

public class AddImage extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Image image = new Image(new FileInputStream("D:\\desktop\\image038.jpg"));
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        FlowPane flowPane = new FlowPane(imageView);
        PixelReader pixelReader = image.getPixelReader();
        Button add = new Button("Add new Image");



        stage.setTitle("32");
        stage.setScene(new Scene(flowPane));
        stage.show();

        imageView.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getButton()== MouseButton.SECONDARY){
                    Color color = pixelReader.getColor((int)mouseEvent.getX(),(int)mouseEvent.getY());
                    String msg = "Color: " + color + "\n";
                    msg+= String.format("R: %1.3f \n", color.getRed());
                    msg+= String.format("G: %1.3f \n", color.getGreen());
                    msg+= String.format("B: %1.3f \n", color.getBlue());
                    msg+= String.format("Opacity: %1.3f \n", color.getOpacity());
                    msg+= String.format("Saturation: %1.3f \n", color.getSaturation());
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information");
                    alert.setHeaderText(msg);
                    //alert.getButtonTypes().add(ButtonType.OK);
                    alert.show();

                }
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
