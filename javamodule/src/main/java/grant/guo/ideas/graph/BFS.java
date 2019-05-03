package grant.guo.ideas.graph;

import grant.guo.ideas.graph.common.GraphGenerator;
import grant.guo.ideas.graph.common.Node;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class BFS {

    public void bfs(Node node) {

        Map<Integer, Node> visited = new HashMap<>();

        Queue<Node> queue = new LinkedList<>();
        queue.add(node);
        visited.put(node.value, node);

        while(queue.size() != 0) {
            Node curr = queue.poll();
            System.out.println(curr.value);

            curr.children.forEach(child -> {
                if(visited.get(child.value) == null){
                    queue.add(child);
                    visited.put(child.value, child);
                }
            });
        }
    }

    public static void main(String[] args) {
        new BFS().bfs(GraphGenerator.generateGraph());
    }
}
