import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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

        int M = ob.nextInt();
        ob.nextLine();
        for(int i = 0; i < M; i++){
            String[] parts = ob.nextLine().split(":");
            int from = Integer.parseInt(parts[0]);
            int to = Integer.parseInt(parts[1]);

            Node parent = vertexMap.get(from);
            Node child = vertexMap.get(to);

            parent.children.add(child);   // parent -> child
            child.parents.add(parent);    // child -> parent
            child.parentCount++;
        }

        helper(vertexMap.get(1));   // starting node will always have a key 1
    }

    private static void helper(Node root){
        ExecutorService executor = Executors.newCachedThreadPool();   // for parallel execution
        Queue<Node> queue = new LinkedList<>();   // Bfs traversal
        List<String> output = Collections.synchronizedList(new ArrayList<>());
        queue.add(root);

        while(!queue.isEmpty()){
            int size = queue.size();
            List<Future<?>> futures = new ArrayList<>();

            for(int i = 0; i < size; i++){
                Node current = queue.poll();
                output.add(current.name);

                // process children
                for(Node child : current.children){
                    synchronized (child) {   // prevent race condition
                        child.parentCount--;
                        if(child.parentCount == 0){
                            futures.add(executor.submit(() -> {
                                synchronized (queue) {
                                    queue.add(child);
                                }
                            }));
                        }
                    }
                }
            }
            for(Future<?> future : futures){
                try {
                    future.get();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

        executor.shutdown();

        // result:
        for(String name : output){
            System.out.println(name);
        }
        System.out.println(output.size());
    }
}
