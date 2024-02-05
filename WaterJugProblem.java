//Practical 3 b: Water jug problem using DFS

import java.util.*;

public class WaterJugProblem {

    static int c1 = 5;
    static int c2 = 4;

    public static void main(String[] args) {
        findPath(0, 0, 2, 0, c1, c2);
    }

    private static List<Integer> fillA(int curr1, int curr2, int c1, int c2) {
        return Arrays.asList(c1, curr2);
    }

    private static List<Integer> fillB(int curr1, int curr2, int c1, int c2) {
        return Arrays.asList(curr1, c2);
    }

    private static List<Integer> emptyA(int curr1, int curr2, int c1, int c2) {
        return Arrays.asList(0, curr2);
    }

    private static List<Integer> emptyB(int curr1, int curr2, int c1, int c2) {
        return Arrays.asList(curr1, 0);
    }

    private static List<Integer> transferBToA(int curr1, int curr2, int c1, int c2) {
        if (curr2 > c1 - curr1) {
            return Arrays.asList(c1, curr2 - (c1 - curr1));
        } else {
            return Arrays.asList(curr1 + curr2, 0);
        }
    }

    private static List<Integer> transferAToB(int curr1, int curr2, int c1, int c2) {
        if (curr1 > c2 - curr2) {
            return Arrays.asList(curr1 - (c2 - curr2), c2);
        } else {
            return Arrays.asList(0, curr1 + curr2);
        }

    }

    private static void findPath(int q1, int q2, int r1, int r2, int c1, int c2) {
        Map<List<Integer>, List<List<Integer>>> routes = new HashMap<>();
        Stack<List<Integer>> stack = new Stack<>();
        List<List<Integer>> visited = new ArrayList<>();
        List<List<Integer>> current = new ArrayList<>();

        stack.push(Arrays.asList(q1, q2));
        current.add(Arrays.asList(q1, q2));

        while (!stack.isEmpty()) {
            List<Integer> element = stack.pop();
            System.out.print(element + " ");

            if (element.get(0) == r1 && element.get(1) == r2) {
                System.out.println("FOUND " + element);
                break;
            }

            List<List<Integer>> list = new ArrayList<>();
            list.add(fillA(element.get(0), element.get(1), c1, c2));
            list.add(fillB(element.get(0), element.get(1), c1, c2));
            list.add(emptyA(element.get(0), element.get(1), c1, c2));
            list.add(emptyB(element.get(0), element.get(1), c1, c2));
            list.add(transferAToB(element.get(0), element.get(1), c1, c2));
            list.add(transferBToA(element.get(0), element.get(1), c1, c2));

            Set<List<Integer>> set = new HashSet<>(list);
            list = new ArrayList<>(set);

            visited.add(element);

            for (List<Integer> item : list) {
                try {
                    if (!visited.contains(item) && !stack.contains(item)) {
                        routes.computeIfAbsent(Arrays.asList(element.get(0), element.get(1)), k -> new ArrayList<>()).add(item);
                    }
                } catch (Exception e) {
                    if (!visited.contains(item) && !stack.contains(item)) {
                        routes.put(Arrays.asList(element.get(0), element.get(1)), Arrays.asList(item));
                    }
                }
                if (!visited.contains(item) && !stack.contains(item)) {
                    stack.push(item);
                }
            }
        }

        current.add(stack.pop());
        System.out.println("\nNodes Explored: " + visited);

        while (!current.get(current.size() - 1).equals(Arrays.asList(q1, q2))) {
            for (List<Integer> key : routes.keySet()) {
                if (current.get(current.size() - 1).equals(routes.get(key).get(0))) {
                    current.add(key);
                }
            }
        }

        Collections.reverse(current);
        System.out.println("Path: " + current);
        System.out.println("Routes: " + routes);
    }
}
