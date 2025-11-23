import java.util.*;

class GreedyRandomConnectedGraph {
    static class Node {
        int id;
        List<Node> neighbors;

        public Node(int id) {
            this.id = id;
            this.neighbors = new ArrayList<>();
        }

        public boolean hasSpace(int maxDegree) {
            return neighbors.size() < maxDegree;
        }

        public boolean connect(Node other) {
            if (this == other){ return false; }
            if (!this.neighbors.contains(other)) {
                this.neighbors.add(other);
                other.neighbors.add(this);
                return true;
            }
            return false;
        }
    }

    public void generateGraph(int N,int K) {
        if (N > 2 && K < 2) {
            System.out.println("Error there is no integral graph  with " + N + " nodes.");
            return;
        }

        List<Node> allNodes = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            allNodes.add(new Node(i));
        }
        Collections.shuffle(allNodes, new Random());

        List<Node> connectedNodes = new ArrayList<>();
        List<Node> remainingNodes = new ArrayList<>(allNodes);
        List<Node> nodesWithSpace = new ArrayList<>();
        Node first = remainingNodes.removeFirst();
        connectedNodes.add(first);
        nodesWithSpace.add(first);

        Random rand = new Random();
        while (!remainingNodes.isEmpty()) {
            Node nodeToAdd = remainingNodes.removeFirst();

            if (nodesWithSpace.isEmpty()) {
                System.out.println("Error: No possible connection!");
                return;
            }
            int parentIndex = rand.nextInt(nodesWithSpace.size());
            Node parent = nodesWithSpace.get(parentIndex);
            parent.connect(nodeToAdd);

            if (!parent.hasSpace(K)) {
                nodesWithSpace.remove(parentIndex);
            }
            if (nodeToAdd.hasSpace(K)) {
                nodesWithSpace.add(nodeToAdd);
            }
            connectedNodes.add(nodeToAdd);
        }

        nodesWithSpace.clear();
        for(Node n : allNodes) {
            if(n.hasSpace(K)){ nodesWithSpace.add(n);}
        }

        int maxAttempts = N * 5;
        for (int i = 0; i < maxAttempts; i++) {
            if (nodesWithSpace.size() < 2){ break;}
            Node u = nodesWithSpace.get(rand.nextInt(nodesWithSpace.size()));
            Node v = nodesWithSpace.get(rand.nextInt(nodesWithSpace.size()));
            if (u != v && !u.neighbors.contains(v)) {
                boolean connected = u.connect(v);
                if (connected) {
                    if (!u.hasSpace(K)){ nodesWithSpace.remove(u);}
                    if (!v.hasSpace(K)){ nodesWithSpace.remove(v);}
                }
            }
        }

        allNodes.sort((a, b) -> Integer.compare(a.id, b.id));

        System.out.println("\n--- Graph ---");
        int edgesCount = 0;
        for (Node n : allNodes) {
            System.out.print("Node " + String.format("%2d", n.id) +
                    " [" + n.neighbors.size() + "/" + K + "] -> ");
            for (Node neighbor : n.neighbors) {
                System.out.print(neighbor.id + " ");
            }
            System.out.println();
            edgesCount += n.neighbors.size();
        }
        System.out.println("\nEdges: " + (edgesCount / 2));
    }
}
class Main {
    public static void main(String[] args) {
        GreedyRandomConnectedGraph grc = new GreedyRandomConnectedGraph();
        grc.generateGraph(15,80);
    }
}