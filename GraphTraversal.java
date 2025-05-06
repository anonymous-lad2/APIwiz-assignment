import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Map;

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

        Scanner ob = new Scanner(System.in);
        int N = ob.nextInt();
        ob.nextLine();
        Map<Integer, Node> vertexMap = new HashMap<>();

        for(int i = 0; i < N; i++){
            String[] parts = ob.nextLine().split(":");
            int id = Integer.parseInt(parts[0]);
            String name = parts[1];
            vertexMap.put(id, new Node(id, name));
        }
    }
}
