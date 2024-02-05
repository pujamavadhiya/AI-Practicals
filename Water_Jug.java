//Practical 3 a: water jug problem using BFS

//import java.util.*;
//
//public class Water_Jug {
//
//    public static void main(String[] args) {
//        int maxA = 5;
//        int maxB = 4;
//        List<State> visited = new ArrayList<>();
//        Queue<State> queue = new LinkedList<>();
//        State start = new State(0, 0);
//        State goal = new State(2, 0);
//        HashMap<State, List<State>> tree = new HashMap<>();
//
//        while (!start.equals(goal)) {
//            if (!visited.contains(start)) {
//                visited.add(start);
//                List<State> children = getChildren(start, maxA, maxB);
//                tree.put(start, children);
//                queue.addAll(children);
//            }
//            start = queue.poll();
//        }
//
//        List<State> path = new ArrayList<>();
//        State temp = goal;
//
//        for (State state : visited) {
//            if (temp.equals(start)) {
//                visited.add(start);
//                break;
//            }
//            if (tree.get(state).contains(temp)) {
//                path.add(state);
//                temp = state;
//            }
//        }
//
//        path.add(goal);
//        System.out.println("Path: " + path);
//    }
//
//
//    static class State {
//        int jugA;
//        int jugB;
//
//        public State(int jugA, int jugB) {
//            this.jugA = jugA;
//            this.jugB = jugB;
//        }
//
//        @Override
//        public boolean equals(Object obj) {
//            if (this == obj) return true;
//            if (obj == null || getClass() != obj.getClass()) return false;
//            State state = (State) obj;
//            return jugA == state.jugA && jugB == state.jugB;
//        }
//
//        @Override
//        public int hashCode() {
//            return 31 * jugA + jugB;
//        }
//
//        @Override
//        public String toString() {
//            return "(" + jugA + ", " + jugB + ")";
//        }
//    }
//
//    private static List<State> getChildren(State state, int maxA, int maxB) {
//        List<State> children = new ArrayList<>();
//        if (!fillA(state).equals(state) && !children.contains(fillA(state))) {
//            children.add(fillA(state));
//        }
//        if (!fillB(state).equals(state) && !children.contains(fillB(state))) {
//            children.add(fillB(state));
//        }
//        if (!emptyA(state).equals(state) && !children.contains(emptyA(state))) {
//            children.add(emptyA(state));
//        }
//        if (!emptyB(state).equals(state) && !children.contains(emptyB(state))) {
//            children.add(emptyB(state));
//        }
//        if (!fillAB(state, maxB).equals(state) && !children.contains(fillAB(state, maxB))) {
//            children.add(fillAB(state, maxB));
//        }
//        if (!fillBA(state, maxA).equals(state) && !children.contains(fillBA(state, maxA))) {
//            children.add(fillBA(state, maxA));
//        }
//        return children;
//    }
//
//    private static State fillA(State state) {
//        return new State(Math.min(state.jugA + 5, 5), state.jugB);
//    }
//
//    private static State fillB(State state) {
//        return new State(state.jugA, Math.min(state.jugB + 4, 4));
//    }
//
//    private static State emptyA(State state) {
//        return new State(0, state.jugB);
//    }
//
//    private static State emptyB(State state) {
//        return new State(state.jugA, 0);
//    }
//
//    private static State fillAB(State state, int maxB) {
//        int fillAmount = Math.min(state.jugA, maxB - state.jugB);
//        return new State(state.jugA - fillAmount, state.jugB + fillAmount);
//    }
//
//    private static State fillBA(State state, int maxA) {
//        int fillAmount = Math.min(state.jugB, maxA - state.jugA);
//        return new State(state.jugA + fillAmount, state.jugB - fillAmount);
//    }
//}





import java.util.*;

public class Water_Jug {

    public static void main(String[] args) {
        int maxA = 5;
        int maxB = 4;
        List<State> visited = new ArrayList<>();
        Queue<State> queue = new LinkedList<>();
        State start = new State(0, 0);
        State goal = new State(2, 0);
        HashMap<State, State> parentMap = new HashMap<>();

        while (!start.equals(goal)) {
            if (!visited.contains(start)) {
                visited.add(start);
                List<State> children = getChildren(start, maxA, maxB);
                for (State child : children) {
                    if (!visited.contains(child)) {
                        parentMap.put(child, start);
                        queue.add(child);
                    }
                }
            }
            start = queue.poll();
        }

        List<State> path = new ArrayList<>();
        State temp = goal;

        while (temp != null) {
            path.add(temp);
            temp = parentMap.get(temp);
        }

        Collections.reverse(path);
        System.out.println("Path: " + path);
    }

    static class State {
        int jugA;
        int jugB;

        public State(int jugA, int jugB) {
            this.jugA = jugA;
            this.jugB = jugB;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            State state = (State) obj;
            return jugA == state.jugA && jugB == state.jugB;
        }

        @Override
        public int hashCode() {
            return 31 * jugA + jugB;
        }

        @Override
        public String toString() {
            return "(" + jugA + ", " + jugB + ")";
        }
    }

    private static List<State> getChildren(State state, int maxA, int maxB) {
        List<State> children = new ArrayList<>();
        if (!fillA(state).equals(state)) {
            children.add(fillA(state));
        }
        if (!fillB(state).equals(state)) {
            children.add(fillB(state));
        }
        if (!emptyA(state).equals(state)) {
            children.add(emptyA(state));
        }
        if (!emptyB(state).equals(state)) {
            children.add(emptyB(state));
        }
        if (!fillAB(state, maxB).equals(state)) {
            children.add(fillAB(state, maxB));
        }
        if (!fillBA(state, maxA).equals(state)) {
            children.add(fillBA(state, maxA));
        }
        return children;
    }

    private static State fillA(State state) {
        return new State(Math.min(state.jugA + 5, 5), state.jugB);
    }

    private static State fillB(State state) {
        return new State(state.jugA, Math.min(state.jugB + 4, 4));
    }

    private static State emptyA(State state) {
        return new State(0, state.jugB);
    }

    private static State emptyB(State state) {
        return new State(state.jugA, 0);
    }

    private static State fillAB(State state, int maxB) {
        int fillAmount = Math.min(state.jugA, maxB - state.jugB);
        return new State(state.jugA - fillAmount, state.jugB + fillAmount);
    }

    private static State fillBA(State state, int maxA) {
        int fillAmount = Math.min(state.jugB, maxA - state.jugA);
        return new State(state.jugA + fillAmount, state.jugB - fillAmount);
    }
}
