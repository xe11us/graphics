package api;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class JavaFxDrawingApi implements DrawingApi {
    private static Canvas canvas;
    private final List<Consumer<GraphicsContext>> drawingTasks = new ArrayList<>();

    public JavaFxDrawingApi(int width, int height) {
        canvas = new Canvas(width, height);
    }

    @Override
    public long getWidth() {
        return (long) canvas.getWidth();
    }

    @Override
    public long getHeight() {
        return (long) canvas.getHeight();
    }

    @Override
    public void drawCircle(double x, double y, double r) {
        drawingTasks.add(graphicsContext -> graphicsContext.fillOval(x - r, y - r, 2 * r, 2 * r));
    }

    @Override
    public void drawLine(double x1, double y1, double x2, double y2) {
        drawingTasks.add(graphicsContext -> graphicsContext.strokeLine(x1, y1, x2, y2));
    }

    @Override
    public void drawText(String text, double x, double y) {
        drawingTasks.add(graphicsContext -> graphicsContext.strokeText(text, x, y));
    }

    @Override
    public void show() {
        GraphicsContext graphicsContext2D = canvas.getGraphicsContext2D();
        drawingTasks.forEach(drawingTasks -> drawingTasks.accept(graphicsContext2D));
        JavaFxApp.launch(JavaFxApp.class);
    }

    public static class JavaFxApp extends Application {
        @Override
        public void start(Stage stage) {
            Group group = new Group();
            group.getChildren().add(canvas);
            Scene scene = new Scene(group, Color.WHITE);
            stage.setScene(scene);
            stage.show();
            stage.setOnCloseRequest(windowEvent -> System.exit(0));
        }
    }
}
