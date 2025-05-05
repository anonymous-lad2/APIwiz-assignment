import java.util.ArrayList;
import java.util.List;

public class GraphTraversal {

    private static class Node{
        int id;
        String name;
        List<Node> children;
        List<Node> parents;
        int parentCount;

        public Node(int id, String name){
            this.id = id;
            this.name = name;
            this.children = new ArrayList<>();
            this.parents = new ArrayList<>();
            this.parentCount = 0;
        }
    }

    public static void main(String[] args) {

//        System.out.print("goal: ");
//        System.out.println("Traverse the entire graph starting from the root node, ensuring each node is executed once");


    }
}
