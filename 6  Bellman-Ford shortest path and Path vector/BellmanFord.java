import java.util.Scanner;

public class BellmanFord {
    private int[] dist;
    private int ver;

    public BellmanFord(int ver) {
        this.ver = ver;
        dist = new int[ver + 1];
    }

    public void evaluate(int s, int[][] adj) {
        // Initialize distances to infinity
        for (int i = 1; i <= ver; i++) dist[i] = 999;
        dist[s] = 0;

        // Relax edges
        for (int i = 1; i < ver; i++)
            for (int u = 1; u <= ver; u++)
                for (int v = 1; v <= ver; v++)
                    if (adj[u][v] != 999 && dist[v] > dist[u] + adj[u][v])
                        dist[v] = dist[u] + adj[u][v];

        // Check for negative cycles
        for (int u = 1; u <= ver; u++)
            for (int v = 1; v <= ver; v++)
                if (adj[u][v] != 999 && dist[v] > dist[u] + adj[u][v]) {
                    System.out.println("Graph contains a negative-weight cycle");
                    return;
                }

        // Display distances
        System.out.println("\nShortest distance from source " + s + " to vertex ");
        for (int i = 1; i <= ver; i++)
            System.out.println( i + " is " + dist[i]);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Enter the number of vertices: ");
        int ver = sc.nextInt();
        int[][] adj = new int[ver + 1][ver + 1];

        System.out.println("Enter the adjacency matrix (use 999 for no direct edge): ");
        for (int i = 1; i <= ver; i++) 
            for (int j = 1; j <= ver; j++) 
                adj[i][j] = sc.nextInt();

        System.out.print("Enter the source vertex: ");
        int s = sc.nextInt();

        new BellmanFord(ver).evaluate(s, adj);

        sc.close();
    }
}

/*
Enter the number of vertices: 4
Enter the adjacency matrix (use 999 for no direct edge): 
999 5 999 999
5 999 3 7
999 3 999 2
999 7 2 999
Enter the source vertex: 2

Shortest distance from source 2 to vertex
1 is 5
2 is 0
3 is 3
4 is 5
*/