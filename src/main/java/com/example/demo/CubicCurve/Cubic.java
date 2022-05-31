package com.example.demo.CubicCurve;

import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import javafx.stage.Stage;

public class Cubic extends Application {
    private Circle crl1;
    private int pocet;
    private final int maxPocet = 3;
    CubicCurve cubic;

    @Override
    public void start(Stage stage) throws Exception {

        FlowPane root = new FlowPane();
        createDraw();
        Pane pane = new Pane(cubic);
        pane.setPrefSize(800,600);
        Button btn = new Button("Vymazat");
        btn.setOnAction((t) -> {
            pocet = 0;
            pane.getChildren().clear();
            Rectangle rectangle = new Rectangle(0,0,pane.getWidth(),pane.getHeight());
            rectangle.setFill(Color.WHITE);
            pane.getChildren().addAll(rectangle,createDraw());
        });
        pocet = 0;
        cubic.setStroke(Color.GREEN);
        cubic.setStrokeWidth(2);
        pane.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getButton()== MouseButton.PRIMARY) {
                    if(pocet<4){
                        extracted(mouseEvent,pane);
                        points(mouseEvent.getX(),mouseEvent.getY(),pocet);

                    pocet++;
                }}
            }
        });



        root.getChildren().addAll(btn,pane);


        stage.setTitle("Cubic");
        stage.setScene(new Scene(root));
        stage.show();
    }

    private void extracted(MouseEvent mouseEvent, Pane pane) {
        Circle crl = new Circle(mouseEvent.getX(), mouseEvent.getY(),5, Color.BLUE);
        pane.getChildren().add(crl);

        crl.setStroke(Color.AQUA);
        crl.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getButton()==MouseButton.SECONDARY){
                    crl.setFill(Color.YELLOW);
                }
            }
        });
        crl.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent1) {
                if(mouseEvent1.getButton()==MouseButton.SECONDARY) {
                    pane.getChildren().remove(crl1);
                    crl1 = new Circle(mouseEvent1.getX(), mouseEvent1.getY(), 5, Color.BLUE);
                    crl1.setOpacity(0.5);
                    pane.getChildren().add(crl1);

                }
            }
        });
        crl.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getButton()==MouseButton.SECONDARY) {
                    crl.setCenterX(mouseEvent.getX());
                    crl.setCenterY(mouseEvent.getY());
                    crl.setFill(Color.BLUE);
                    pane.getChildren().remove(crl1);
                    int j=0;
                    double x = crl.getCenterX();
                    double y = crl.getCenterY();
                    if(x == cubic.getStartX() && y == cubic.getStartY()) j = 0;
                    if(x == cubic.getEndX() && y == cubic.getEndY()) j = 3;

                    if(x == cubic.getControlX1() && y == cubic.getControlY1()) j = 1;

                    if(x == cubic.getControlX2() && y == cubic.getControlY2()) j = 2;
                    points(mouseEvent.getX(),mouseEvent.getY(),j);

                }
            }

        });


    }

    private CubicCurve createDraw() {
        cubic = new CubicCurve();
        cubic.setStroke(Color.GREEN);
        cubic.setStrokeWidth(2);
        cubic.setFill(Color.WHITE);
        return cubic;
    }
    private void points(double x, double y, int pct){
        if (pct == 0) {
            cubic.setStartX(x);
            cubic.setStartY(y);
        } else if (pct == maxPocet) {
            cubic.setEndX(x);
            cubic.setEndY(y);
        }
        else if (pct == 1) {

            cubic.setControlX1(x);
            cubic.setControlY1(y);
        } else {

            cubic.setControlX2(x);
            cubic.setControlY2(y);
        }

    }
//    Circle c1 = null, c2 = null, c3 = null, c4 = null;
//    CubicCurve cubicCurve = null;
//    final double CIRCLE_RADIUS = 5;
//    final double ANIMATED_RADIUS = 20;
//
//    public Cvik_Graph() {
//        this.widthProperty().addListener((o) -> {
//            redraw();
//        });
//        this.heightProperty().addListener((o) -> {
//            redraw();
//        });
//        this.addEventHandler(MouseEvent.MOUSE_CLICKED, (t) -> {
//            if (t.getButton() == MouseButton.PRIMARY) {
//                Point2D point = new Point2D(t.getX(), t.getY());
//                if (c1 == null) {
//                    c1 = getCircle(point);
//                } else if (c2 == null) {
//                    c2 = getCircle(point);
//                } else if (c3 == null) {
//                    c3 = getCircle(point);
//                } else if (c4 == null) {
//                    c4 = getCircle(point);
//                }
//            }
//            redraw();
//        });
//    }
//
//    private void redraw() {
//        this.getChildren().clear(); // clearing things to allow for modification (hot reload?)
//        boolean valid = true;
//
//        valid &= draw(c1); // first time seeing this operation &=, guessing its for multiple comparison (if valid and if draw)
//        valid &= draw(c2);
//        valid &= draw(c3);
//        valid &= draw(c4);
//
//        if (valid) {
//            cubicCurve = getCubicCurve();
//            this.getChildren().add(cubicCurve); // adding cubic curve to children to work properly
//            animate();
//        }
//    }
//
//    private Circle getCircle(Point2D point) {
//        Circle circle = new Circle(point.getX(), point.getY(), CIRCLE_RADIUS);
//        circle.setStroke(Color.BLACK);
//        circle.setFill(Color.AQUA);
//
//        circle.addEventHandler(MouseEvent.MOUSE_DRAGGED, (t) -> { // if clicked
//            if (t.getButton() == MouseButton.PRIMARY) { // if clicked with LMB
//                circle.setCenterX(t.getX());
//                circle.setCenterY(t.getY());
//            }
//        });
//
//        return circle;
//    }
//
//    private boolean draw(Circle c) {
//        if (c != null) {
//            if (c.getCenterX() <= this.getWidth() && c.getCenterY() <= this.getHeight()) {
//                this.getChildren().add(c);
//                return true;
//            }
//        }
//        return false;
//    }
//
//    private CubicCurve getCubicCurve() {
//        CubicCurve curve = new CubicCurve(
//                c1.getCenterX(), c1.getCenterY(),
//                c2.getCenterX(), c2.getCenterY(),
//                c3.getCenterX(), c3.getCenterY(),
//                c4.getCenterX(), c4.getCenterY());
//        curve.setStroke(Color.RED);
//        curve.setFill(null);
//
//        return curve;
//    }
//
//    private void animate() {
////        Circle animatedObject = new Circle(c1.getCenterX(), c1.getCenterY(), ANIMATED_RADIUS);
////        animatedObject.setStroke(Color.RED);
////        animatedObject.setStrokeWidth(5);     //old code of a circle
////        animatedObject.setFill(Color.YELLOW);
//
//        Image image = new Image("plane.png");
//        ImageView animatedObject = new ImageView(image); //modernized by image import
//
//        this.getChildren().add(animatedObject);
//
//        PathTransition transition = new PathTransition();
//        transition.setNode(animatedObject);
//        transition.setPath(cubicCurve); // the path is our curve
//        transition.setDuration(javafx.util.Duration.millis(600));
//        transition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT); // pretty way of its direction
//
//        transition.play();
//
//    }

}
