import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class EdgeLimitedGraph {
    static class Node {
        int id;
        List<Node> neighbors;

        public Node(int id) {
            this.id = id;
            this.neighbors = new ArrayList<>();
        }

        public boolean connect(Node other) {
            if (this == other) return false;
            if (!this.neighbors.contains(other)) {
                this.neighbors.add(other);
                other.neighbors.add(this);
                return true;
            }
            return false;
        }

        public boolean isConnectedTo(Node other) {
            return this.neighbors.contains(other);
        }
    }

    public void generateGraph(int N, int K) {
        int minEdgesNeeded = N - 1;
        int maxPossibleEdges = (N * (N - 1)) / 2;

        if (K < minEdgesNeeded) {
            System.out.println("ERROR: To Small K ");
            return;
        }

        if (K > maxPossibleEdges) {
            System.out.println("INFO: There is no grapf with maximum of the edges.");
            K = maxPossibleEdges;
        }

        List<Node> nodes = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            nodes.add(new Node(i));
        }

        int currentEdgesCount = 0;
        Collections.shuffle(nodes, new Random());
        Random rand = new Random();

        for (int i = 1; i < N; i++) {
            Node newNode = nodes.get(i);
            Node parentNode = nodes.get(rand.nextInt(i));

            newNode.connect(parentNode);
            currentEdgesCount++;
        }
        int edgesLeftToSpend = K - currentEdgesCount;

        if (edgesLeftToSpend > 0) {
            List<int[]> potentialEdges = new ArrayList<>();
            for (int i = 0; i < N; i++) {
                for (int j = i + 1; j < N; j++) {
                    potentialEdges.add(new int[]{i, j});
                }
            }

            Collections.shuffle(potentialEdges, rand);
            for (int[] pair : potentialEdges) {
                if (currentEdgesCount >= K) break;

                Node u = nodes.get(pair[0]);
                Node v = nodes.get(pair[1]);
                Node nodeA = nodes.get(pair[0]);
                Node nodeB = nodes.get(pair[1]);

                if (!nodeA.isConnectedTo(nodeB)) {
                    nodeA.connect(nodeB);
                    currentEdgesCount++;
                }
            }
        }
        nodes.sort((a, b) -> Integer.compare(a.id, b.id));

        System.out.println("\n--- Generated Graph ---");
        System.out.println("Used edges: " + currentEdgesCount + " / " + K);

        for (Node n : nodes) {
            System.out.print("Node " + n.id + ": ");
            for (Node neighbor : n.neighbors) {
                System.out.print(neighbor.id + " ");
            }
            System.out.println();
        }
    }
}