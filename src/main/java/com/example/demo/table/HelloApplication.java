package com.example.demo.table;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        ObservableList<Country> list = FXCollections.observableArrayList(
                new Country("Evropa","Russia"),
                new Country("Evropa","CR"),
                new Country("Huj znaetn", "Africa"),
                new Country("Canada" ,"America")
        );

        TreeItem<String> item = new TreeItem<>("Seznam statu");
        item.setExpanded(true);

        for(Country country : list){
            TreeItem<String> countryItem = new TreeItem<>(country.getCountry());
            TreeItem<String> continentItem = null;

            for(TreeItem<String> item1 : item.getChildren()){
                if (item1.getValue().equals(country.getContinent())){
                    continentItem = item1;
                    break;
                }
            }
            if(continentItem==null){
                continentItem = new TreeItem<>(country.getContinent());
                item.getChildren().add(continentItem);
                }
            continentItem.getChildren().add(countryItem);
            }

        TreeView<String> polozky = new TreeView(item);
        polozky.setEditable(true);
        polozky.setCellFactory(TextFieldTreeCell.forTreeView());

        MenuItem addMenuItem = new MenuItem("Add new");
        addMenuItem.setOnAction(actionEvent -> {
            TreeItem<String> vybrany = polozky.getSelectionModel().getSelectedItem();
            if(vybrany!=null){
                if (vybrany==polozky.getRoot())
                    vybrany.getChildren().add(new TreeItem<>("Novy continent"));
                else vybrany.getChildren().add(new TreeItem<>("New country"));
            }
        });
        MenuItem remove = new MenuItem("Remove");
        remove.setOnAction(actionEvent -> {
            TreeItem<String> vybrany = polozky.getSelectionModel().getSelectedItem();
            if(vybrany!=null){
                TreeItem tree = vybrany.getParent();
                tree.getChildren().remove(vybrany);
            }
        });

        ContextMenu bar = new ContextMenu(addMenuItem,remove);
        polozky.setContextMenu(bar);


        FlowPane pane = new FlowPane(polozky);

        stage.setTitle("Hello!");
        stage.setScene(new Scene(pane));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}