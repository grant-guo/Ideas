package grant.guo.ideas.graph;

import java.util.HashMap;
import java.util.Map;

import grant.guo.ideas.graph.common.GraphGenerator;
import grant.guo.ideas.graph.common.Node;

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

    public static void main(String[] args) {
        DFS dfs = new DFS();
        dfs.dfs(GraphGenerator.generateGraph());
    }
}
