package graph;

import api.DrawingApi;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public abstract class Graph {
    /**
     * Bridge to drawing api
     */
    protected DrawingApi drawingApi;

    public Graph(DrawingApi drawingApi) {
        this.drawingApi = drawingApi;
    }

    public abstract void drawGraph();

    protected List<Point2D> drawAndGetVertices(int n, long width, long height) {
        double radius = Math.min(width, height) / 3.0;
        double x = width / 2.0;
        double y = height / 2.0;

        List<Point2D> vertices = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            double angle = 2 * Math.PI * i / n;
            drawingApi.drawText(
                    String.valueOf(i),
                    x + (radius + 20) * Math.cos(angle),
                    y + (radius + 20) * Math.sin(angle)
            );
            vertices.add(new Point2D.Double(x + radius * Math.cos(angle), y + radius * Math.sin(angle)));
        }

        return vertices;
    }
}
