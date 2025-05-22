import java.util.ArrayList;
import java.util.List;

class Solution {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        if (prerequisites == null || prerequisites.length == 0) {
            return true;
        }
        
        List<Integer>[] graph = new ArrayList[numCourses];
        for (int i = 0; i < numCourses; i++) {
            graph[i] = new ArrayList<>();
        }
        
        for (int[] edge : prerequisites) {
            graph[edge[1]].add(edge[0]);
        }
        
        byte[] visited = new byte[numCourses];
        
        for (int i = 0; i < numCourses; i++) {
            if (visited[i] == 0 && !dfs(graph, visited, i)) {
                return false;
            }
        } 
        
        return true;
    }
    
    private boolean dfs(List<Integer>[] graph, byte[] visited, int node) {
        if (visited[node] == 1) {
            return false; 
        }
        
        if (visited[node] == 2) {
            return true;
        }
        
        visited[node] = 1;
        
        for (int neighbor : graph[node]) {
            if (!dfs(graph, visited, neighbor)) {
                return false;
            }
        }
        
        visited[node] = 2;
        return true;
    }
}