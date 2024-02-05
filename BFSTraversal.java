//Practical 1 a: BFS traversal in which we have to define the source node, destination node and provide the final path from the goal node to the destination node.

import java.util.*;

public class BFSTraversal {
    public static List<String> bfs(Map<String, List<String>> inputTree, String startNode, String goalNode,
                                   List<String> visited) {
        Queue<List<String>> queue = new LinkedList<>();
        queue.add(new ArrayList<>(Collections.singletonList(startNode)));

        while (!queue.isEmpty()) {
            List<String> currentPath = queue.poll();
            String currentNode = currentPath.get(currentPath.size() - 1);

            if (visited.contains(currentNode)) {
                continue;
            }

            visited.add(currentNode);

            if (currentNode.equals(goalNode)) {
                return currentPath;
            }

            if (inputTree.containsKey(currentNode)) {
                for (String child : inputTree.get(currentNode)) {
                    List<String> newPath = new ArrayList<>(currentPath);
                    newPath.add(child);
                    queue.add(newPath);
                }
            }
        }

        return new ArrayList<>();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Map<String, List<String>> dict1 = new HashMap<>();

        System.out.print("Enter the number of edges: ");
        int numEdges = scanner.nextInt();

        System.out.print("Enter an edge (source destination): ");
        for (int i = 0; i < numEdges; i++) {

            String u = scanner.next();
            String v = scanner.next();

            dict1.computeIfAbsent(u, k -> new ArrayList<>()).add(v);
        }

        System.out.print("Enter starting node: ");
        String root = scanner.next();

        System.out.print("Enter goal node: ");
        String target = scanner.next();

        List<String> visited = new ArrayList<>();
        List<String> finalPath = bfs(dict1, root, target, visited);

        if (!finalPath.isEmpty()) {
            System.out.println("Traversal Path: " + visited);
            System.out.println("Final Path: " + finalPath);
        } else {
            System.out.println("Path not found.");
        }
    }
}
