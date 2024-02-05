//Practical 1 b: DFS traversal in which we have to define the source node, destination node and provide the final path from the goal node to the destination node.

import java.util.*;

public class DFSTraversal {
    public static List<Integer> dfs(Map<Integer, List<Integer>> graph, int current, int goal, boolean[] visited,
                                    List<Integer> traversalPath, List<Integer> pathToGoal) {
        visited[current] = true;
        traversalPath.add(current);

        if (current == goal) {
            pathToGoal.addAll(traversalPath.subList(0, traversalPath.size() - 1));
            return traversalPath;
        }

        for (int neighbor : graph.get(current)) {
            if (!visited[neighbor]) {
                List<Integer> result = dfs(graph, neighbor, goal, visited, traversalPath, pathToGoal);
                if (result != null) {
                    return result;
                }
            }
        }

        traversalPath.remove(traversalPath.size() - 1);
        return null;
    }

    public static Map<String, List<Integer>> dfsWrapper(Map<Integer, List<Integer>> graph, int start, int goal,
                                                        boolean[] visited, List<Integer> traversalPath,
                                                        List<Integer> pathToGoal) {
        List<Integer> resultPath = dfs(graph, start, goal, visited, traversalPath, pathToGoal);
        Map<String, List<Integer>> result = new HashMap<>();
        result.put("resultPath", resultPath);
        result.put("traversalPath", traversalPath);
        return result;
    }

    public static void dfsSimple(Map<Integer, List<Integer>> graph, int current, boolean[] visited,
                                 List<Integer> traversalPath) {
        visited[current] = true;
        traversalPath.add(current);

        for (int neighbor : graph.get(current)) {
            if (!visited[neighbor]) {
                dfsSimple(graph, neighbor, visited, traversalPath);
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of vertices: ");
        int numVertices = scanner.nextInt();

        System.out.print("Enter the number of edges: ");
        int numEdges = scanner.nextInt();

        Map<Integer, List<Integer>> graph = new HashMap<>();
        for (int i = 1; i <= numVertices; i++) {
            graph.put(i, new ArrayList<>());
        }

        System.out.println("Enter edges (source destination):");
        for (int i = 0; i < numEdges; i++) {
            int u = scanner.nextInt();
            int v = scanner.nextInt();
            graph.get(u).add(v);
            graph.get(v).add(u);
        }

        System.out.print("Enter the starting vertex: ");
        int startVertex = scanner.nextInt();

        System.out.print("Enter the goal vertex: ");
        int goalVertex = scanner.nextInt();

        boolean[] visited = new boolean[numVertices + 1];
        List<Integer> traversalPath = new ArrayList<>();

        Map<String, List<Integer>> result = dfsWrapper(graph, startVertex, goalVertex, visited, traversalPath, new ArrayList<>());

        List<Integer> resultPath = result.get("resultPath");
        List<Integer> traversalPathResult = result.get("traversalPath");

        if (resultPath != null) {
            System.out.println("DFS Path to Goal: " + resultPath);
        } else {
            System.out.println("Goal not found in the graph.");
        }

        Arrays.fill(visited, false);
        List<Integer> traversalPathSimple = new ArrayList<>();
        dfsSimple(graph, startVertex, visited, traversalPathSimple);
        System.out.println("DFS Traversal Path: " + traversalPathSimple);
    }
}
