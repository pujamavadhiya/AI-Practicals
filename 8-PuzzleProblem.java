import java.util.Arrays;
import java.util.PriorityQueue;

class Node implements Comparable<Node> {
    int[][] array;
    Node parent;
    int h_score;
    int f_score;
    int g_score;
    int x;
    int y;

    public Node(int[][] array) {
        this.array = array;
        this.parent = null;
        this.h_score = 0;
        this.f_score = 0;
        this.g_score = 0;
        this.x = 0;
        this.y = 0;
    }

    @Override
    public int compareTo(Node other) {
        return Integer.compare(this.f_score, other.f_score);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Node)) {
            return false;
        }
        Node other = (Node) obj;
        return Arrays.deepEquals(this.array, other.array);
    }
}

public class PuzzleProblem {
    public static void printResult(Node node) {
        if (node.parent == null) {
            return;
        }
        printResult(node.parent);
        System.out.println("Step " + node.g_score + ": ");
        printMatrix(node.array);
        System.out.println();
    }

    public static boolean aStar8Puzzle(Node start, Node goal) {
        PriorityQueue<Node> queue = new PriorityQueue<>();
        queue.add(start);
        boolean found = false;
        int[][] moves = {{0, 1}, {0, -1}, {-1, 0}, {1, 0}};

        while (!queue.isEmpty() && !found) {
            Node current = queue.poll();
            if (Arrays.deepEquals(current.array, goal.array)) {
                goal.parent = current.parent;
                goal.g_score = current.g_score;
                found = true;
            }

            int x = current.x;
            int y = current.y;
            int g_score = current.g_score;
            for (int[] move : moves) {
                int dx = move[0];
                int dy = move[1];
                if (0 <= x + dx && x + dx < 3 && 0 <= y + dy && y + dy < 3) {
                    Node node = new Node(copyArray(current.array));
                    node.array[x][y] = node.array[x + dx][y + dy];
                    node.array[x + dx][y + dy] = -1;
                    node.x = x + dx;
                    node.y = y + dy;
                    node.g_score = g_score + 1;
                    node.h_score = countMisplaced(node.array, goal.array) - 1;
                    node.f_score = node.g_score + node.h_score;
                    node.parent = current;
                    queue.add(node);
                }
            }
        }
        return found;
    }

    public static int[][] copyArray(int[][] array) {
        int[][] copy = new int[array.length][];
        for (int i = 0; i < array.length; i++) {
            copy[i] = Arrays.copyOf(array[i], array[i].length);
        }
        return copy;
    }

    public static int countMisplaced(int[][] current, int[][] goal) {
        int count = 0;
        for (int i = 0; i < current.length; i++) {
            for (int j = 0; j < current[i].length; j++) {
                if (current[i][j] != goal[i][j] && current[i][j] != -1) {
                    count++;
                }
            }
        }
        return count;
    }

    public static void printMatrix(int[][] m) {
        for (int[] row : m) {
            for (int cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int[][] initial_state = {{2, 8, 3}, {1, 6, 4}, {7, -1, 5}};
        int[][] goal_state = {{1, 2, 3}, {8, -1, 4}, {7, 6, 5}};

        Node start = new Node(initial_state);
        Node goal = new Node(goal_state);
        start.g_score = 0;
        start.h_score = countMisplaced(start.array, goal.array);
        start.f_score = start.g_score + start.h_score;
        start.x = 2;
        start.y = 1;

        if (!aStar8Puzzle(start, goal)) {
            System.out.println("Goal state can't be reached");
        } else {
            System.out.println("The steps to the goal node:");
            printResult(goal);
        }
    }
}
