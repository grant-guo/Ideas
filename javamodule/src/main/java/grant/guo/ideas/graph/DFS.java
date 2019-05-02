package grant.guo.ideas.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DFS {

    public DFS(){}

    public void dfs(Node node) {
        System.out.println(node.value);
        visited.put(node.value, node);
        node.children.forEach(child -> {
            if(!isVisited(child))
                dfs(child);
        });
    }

    private Map<Integer, Node> visited = new HashMap<>();

    private boolean isVisited(Node node) {
        if(visited.get(node.value) == null) return false; else return true;
    }

    private static class Node {
        public int value;
        public List<Node> children = new ArrayList<>();
        public Node(int v){
            this.value = v;
        }
    }

    private static class GraphGenerator {
        public static Node generateGraph() {
            Node node10 = new Node(10);

            Node node4 = new Node(4);
            node4.children.add(node10);

            Node node8 = new Node(8);
            node8.children.add(node4);

            Node node6 = new Node(6);
            node6.children.add(node4);
            node6.children.add(new Node(2));

            Node node7 = new Node(7);
            node7.children.add(node6);
            node7.children.add(node8);
            node7.children.add(new Node(9));

            Node node5 = new Node(5);
            node5.children.add(new Node(3));
            node5.children.add(node6);

            Node node1 = new Node(1);
            node1.children.add(node5);
            node1.children.add(node7);

            return node1;


        }
    }

    public static void main(String[] args) {
        DFS dfs = new DFS();
        dfs.dfs(GraphGenerator.generateGraph());
    }
}
