package graph;

import api.DrawingApi;
import exception.ValidationException;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class EdgeListGraph extends Graph {
    private final List<Edge> edges = new ArrayList<>();
    private final int n;

    public EdgeListGraph(DrawingApi drawingApi, List<List<Integer>> graph, int n) {
        super(drawingApi);
        graph.forEach(
                row -> {
                    validateRow(n, row);
                    this.edges.add(new Edge(row.get(0), row.get(1)));
                }
        );
        this.n = n;
    }

    private void validateRow(int n, List<Integer> row) {
        if (row.size() != 2) {
            throw new ValidationException("Expected 2 numbers in a row");
        }
        if (!row.stream().allMatch(i -> 0 <= i && i < n)) {
            throw new ValidationException("Expected vertices ids from 0 to " + (n - 1));
        }
    }

    @Override
    public void drawGraph() {
        long width = drawingApi.getWidth();
        long height = drawingApi.getHeight();

        List<Point2D> centers = drawAndGetVertices(n, width, height);
        centers.forEach(center -> drawingApi.drawCircle(center.getX(), center.getY(), 10));

        edges.forEach(e -> {
            Point2D v = centers.get(e.from());
            Point2D u = centers.get(e.to());
            drawingApi.drawLine(v.getX(), v.getY(), u.getX(), u.getY());
        });

        drawingApi.show();
    }
}
