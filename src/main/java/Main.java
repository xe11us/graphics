import api.AwtDrawingApi;
import api.DrawingApi;
import api.JavaFxDrawingApi;
import exception.ValidationException;
import graph.EdgeListGraph;
import graph.Graph;
import graph.MatrixGraph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class Main {
    private static final Set<String> supportedInputTypes = Set.of("edges", "matrix");
    private static final Set<String> supportedDrawingApis = Set.of("awt", "javafx");
    private static DrawingApi getGrawingApi(String arg) {
        if (!supportedDrawingApis.contains(arg)) {
            throw new ValidationException("Unsupported drawing api");
        }
        return arg.equals("awt") ? new AwtDrawingApi(600, 900) : new JavaFxDrawingApi(600, 900);
    }

    private static Graph getGraph(DrawingApi drawingApi, List<List<Integer>> graph, int n, String arg) {
        if (!supportedInputTypes.contains(arg)) {
            throw new ValidationException("Invalid input type");
        }
        return arg.equals("edges") ? new EdgeListGraph(drawingApi, graph, n) : new MatrixGraph(drawingApi, graph, n);
    }

    private static Graph fillGraph(DrawingApi drawingApi, String filePath, String inputType) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            int n = Integer.parseInt(reader.readLine());
            if (n <= 0) {
                throw new ValidationException("Expected at least 1 vertex");
            }
            List<List<Integer>> graph =
                    reader.lines()
                            .map(line ->
                                    Arrays.stream(line.split("\\s+")).map(Integer::parseInt).toList()
                            ).toList();
            return getGraph(drawingApi, graph, n, inputType);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static void main(String[] args) {
        Graph graph = fillGraph(getGrawingApi(args[0]), args[2], args[1]);
        graph.drawGraph();
    }
}
