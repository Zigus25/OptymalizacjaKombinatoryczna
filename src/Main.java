public class Main {
    public static void main(String[] args) {
        int N = 15, K = 80;
        GreedyRandomConnectedGraph greed = new GreedyRandomConnectedGraph();
        EdgeLimitedGraph edgeLimited = new EdgeLimitedGraph();
        edgeLimited.generateGraph(N, K);
    }
}
