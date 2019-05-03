package grant.guo.ideas.graph.common;

import java.util.ArrayList;
import java.util.List;

public class Node {
    public int value;
    public List<Node> children = new ArrayList<>();
    public Node(int v){
        this.value = v;
    }
}
