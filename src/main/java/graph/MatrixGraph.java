package graph;

import api.DrawingApi;
import exception.ValidationException;

import java.awt.geom.Point2D;
import java.util.List;

public class MatrixGraph extends Graph {
    List<List<Boolean>> matrix;

    public MatrixGraph(DrawingApi drawingApi, List<List<Integer>> graph, int n) {
        super(drawingApi);
        this.matrix = graph.stream().map(
                row -> {
                    validateRow(n, row);
                    return row.stream().map(i -> i > 0).toList();
                }
        ).toList();
        validateMatrix(n);
    }

    private void validateRow(int n, List<Integer> row) {
        if (row.size() != n) {
            throw new ValidationException("Expected " + n + " vertices in a row");
        }
    }

    private void validateMatrix(int n) {
        if (matrix.size() != n) {
            throw new ValidationException("Expected " + n + " rows");
        }
        for (int i = 0; i < matrix.size(); i++) {
            for (int j = 0; j < i; j++) {
                if (matrix.get(i).get(j) != matrix.get(j).get(i)) {
                    throw new ValidationException("Matrix is not symmetric");
                }
            }
        }
    }

    @Override
    public void drawGraph() {
        long width = drawingApi.getWidth();
        long height = drawingApi.getHeight();
        List<Point2D> centers = drawAndGetVertices(matrix.size(), width, height);
        centers.forEach(center -> drawingApi.drawCircle(center.getX(), center.getY(), 10));

        for (int i = 0; i < matrix.size(); i++) {
            for (int j = 0; j < i; j++) {
                if (matrix.get(i).get(j)) {
                    Point2D v = centers.get(i);
                    Point2D u = centers.get(j);
                    drawingApi.drawLine(v.getX(), v.getY(), u.getX(), u.getY());
                }
            }
        }

        drawingApi.show();
    }
}
