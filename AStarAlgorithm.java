import java.util.*;

class Node implements Comparable<Node> {
    int id;
    Map<Node, Integer> near;
    int g, h;
    int f;

    public Node(int id) {
        this.id = id;
        this.near = new HashMap<>();
        this.g = 0;
        this.h = 0;
        this.f = 0;
    }

    @Override
    public int compareTo(Node other) {
        return Integer.compare(this.f, other.f);
    }
}

public class AStarAlgorithm {

    private static final Map<Integer, Integer> heuristicValues = Map.of(
            1, 16,
            2, 4,
            3, 16,
            4, 2,
            5, 1,
            6, 4,
            7, 0
    );

    public static void main(String[] args) {
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        Node node5 = new Node(5);
        Node node6 = new Node(6);
        Node node7 = new Node(7);

        node1.near.put(node2, 10);
        node1.near.put(node3, 4);
        node2.near.put(node3, 3);
        node2.near.put(node4, 6);
        node3.near.put(node1, 4);
        node3.near.put(node2, 4);
        node3.near.put(node5, 6);
        node4.near.put(node2, 6);
        node4.near.put(node5, 6);
        node4.near.put(node6, 4);
        node5.near.put(node3, 6);
        node5.near.put(node4, 6);
        node5.near.put(node7, 14);
        node6.near.put(node4, 4);
        node6.near.put(node7, 4);
        node7.near.put(node5, 14);
        node7.near.put(node6, 4);

        Node startNode = node1;
        Node endNode = node7;

        List<Node> path = findPath(startNode, endNode);

        if (path.isEmpty()) {
            System.out.println("No path found!");
        } else {
            System.out.println("Path found:");

            int totalPathCost = 0;
            for (int i = 0; i < path.size() - 1; i++) {
                Node currentNode = path.get(i);
                Node nextNode = path.get(i + 1);
                int edgeCost = currentNode.near.get(nextNode);
                totalPathCost += edgeCost;
                System.out.println("Node " + currentNode.id + " -> Node " + nextNode.id + " (Cost: " + edgeCost + ")");
            }
            System.out.println("Total Path Cost: " + totalPathCost);
        }
    }

    public static List<Node> findPath(Node startNode, Node endNode) {
        PriorityQueue<Node> openList = new PriorityQueue<>();
        Set<Node> closeList = new HashSet<>();
        Map<Node, Node> cameFrom = new HashMap<>();

        startNode.g = 0;
        startNode.h = hcost(startNode, endNode);
        startNode.f = startNode.g + startNode.h;

        openList.add(startNode);

        while (!openList.isEmpty()) {
            Node current = openList.poll();

            if (current == endNode) {
                return reconstructPath(cameFrom, current);
            }
            closeList.add(current);

            for (Map.Entry<Node, Integer> entry : current.near.entrySet()) {
                Node neighbor = entry.getKey();
                int edgeCost = entry.getValue();

                if (closeList.contains(neighbor)) {
                    continue;
                }
                int tentativeGScore = current.g + edgeCost;

                if (!openList.contains(neighbor) || tentativeGScore < neighbor.g) {
                    openList.add(neighbor);
                    neighbor.g = tentativeGScore;
                    neighbor.h = hcost(neighbor, endNode);
                    neighbor.f = neighbor.g + neighbor.h;
                    cameFrom.put(neighbor, current);
                }
            }
        }
        return Collections.emptyList();
    }

    private static List<Node> reconstructPath(Map<Node, Node> cameFrom, Node current) {
        List<Node> path = new ArrayList<>();
        path.add(current);

        while (cameFrom.containsKey(current)) {
            current = cameFrom.get(current);
            path.add(current);
        }

        Collections.reverse(path);
        return path;
    }

    private static int hcost(Node current, Node endNode) {
        return Math.abs(current.id - endNode.id);
    }
}
