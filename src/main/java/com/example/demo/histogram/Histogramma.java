package com.example.demo.histogram;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class Histogramma extends Application {

    private ImageView imageView;
    private PixelReader pixelReader;
    private final double sirka = 800;
    private final double vyska = 600;
    private BarChart<String, Number> bc;
    @Override
    public void start(Stage stage) throws Exception {
        VBox root = new VBox();
        HBox tool = new HBox(15);
        HBox pane = new HBox();
        pane.setPrefSize(sirka,vyska);
        tool.setAlignment(Pos.CENTER);
        tool.setPrefSize(sirka,vyska/12);
        Button btn = new Button("Load picture");
        tool.getChildren().add(btn);
        btn.setOnAction(actionEvent -> {
            FileChooser fileChooser = new FileChooser();
            String[] format = new String[]{"*.png","*.jpg"};
            FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("png,jpg files (*.png,*.jpg)",format);
            fileChooser.getExtensionFilters().add(filter);

            File file = fileChooser.showOpenDialog(stage);
            if(file!=null){
                Image image = new Image(file.toURI().toString());
                if (image==null || image.isError()){
                    new Alert(Alert.AlertType.ERROR,"Invalid picture format or file", ButtonType.OK).show();
                }
                else {
                    imageView = new ImageView(image);
                    imageView.setFitWidth(pane.getWidth()/2);
                    imageView.setFitHeight(pane.getHeight());
                    pane.getChildren().add(imageView);
                    pixelReader = imageView.getImage().getPixelReader();
                    readPixel(image);
                }
            }
        });
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Programming Language");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Percent");

        bc = new BarChart(xAxis,yAxis);
        bc.setPrefSize(pane.getWidth(),pane.getHeight());
        pane.getChildren().add(bc);
        root.getChildren().addAll(pane,tool);
        stage.setTitle("histogramma");
        stage.setScene(new Scene(root));
        stage.show();
    }
    private void readPixel(Image image){
        double[] brightnessArray = new double[11];
        double[] redArray = new double[11];
        double[] greenArray = new double[11];
        double[] blueArray = new double[11];

        XYChart.Series seriesBrightness = new XYChart.Series();
        XYChart.Series seriesRed = new XYChart.Series();
        XYChart.Series seriesGreen = new XYChart.Series();
        XYChart.Series seriesBlue = new XYChart.Series();
        seriesBrightness.setName("Pixel brightness");
        seriesRed.setName("Pixel red");
        seriesGreen.setName("Pixel Green");
        seriesBlue.setName("Pixel Blue");

        for (int i = 0; i < brightnessArray.length; i++) {
            brightnessArray[i] = 0;
            redArray[i] = 0;
            greenArray[i] = 0;
            blueArray[i] = 0;
        }

        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                Color color = pixelReader.getColor(i, j);
                double brightess = color.getBrightness();
                double red = color.getRed();
                double green = color.getGreen();
                double blue = color.getBlue();
                brightnessArray[(int)(brightess / 0.1)]++;
                redArray[(int)(red / 0.1)]++;
                greenArray[(int)(green / 0.1)]++;
                blueArray[(int)(blue / 0.1)]++;

            }
        }

        for (int i = 0; i < brightnessArray.length; i++) {
            seriesBrightness.getData().add(new XYChart.Data(
                    Integer.toString(i * 10) ,brightnessArray[i]));
            seriesRed.getData().add(new XYChart.Data(
                    Integer.toString(i * 10), redArray[i]));
            seriesGreen.getData().add(new XYChart.Data(
                    Integer.toString(i * 10), greenArray[i]));
            seriesBlue.getData().add(new XYChart.Data(
                    Integer.toString(i * 10), blueArray[i]));
        }
        bc.getData().addAll(seriesBrightness, seriesRed, seriesGreen, seriesBlue);
    }
}
