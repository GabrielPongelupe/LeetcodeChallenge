# 207. Course Schedule - Solution

## Approach: Cycle Detection in Directed Graph using DFS

This solution transforms the course scheduling problem into a **cycle detection problem** in a directed graph. If there's a cycle in the prerequisite dependencies, it's impossible to complete all courses.

### Key Concepts:
1. **Graph Representation**: Model courses and prerequisites as a directed graph
2. **Cycle Detection**: Use DFS with three states to detect cycles
3. **Three-State Tracking**: Unvisited (0), Currently Processing (1), Completely Processed (2)
4. **Topological Ordering**: A valid course schedule exists if and only if no cycles exist

## Solution Code

```java
class Solution {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        if (prerequisites == null || prerequisites.length == 0) {
            return true;
        }
        
        // Build adjacency list representation of the graph
        List<Integer>[] graph = new ArrayList[numCourses];
        for (int i = 0; i < numCourses; i++) {
            graph[i] = new ArrayList<>();
        }
        
        for (int[] edge : prerequisites) {
            graph[edge[1]].add(edge[0]);
        }
        
        // 0 = unvisited, 1 = currently processing, 2 = completely processed
        byte[] visited = new byte[numCourses];
        
        // Check each course for cycles
        for (int i = 0; i < numCourses; i++) {
            if (visited[i] == 0 && !dfs(graph, visited, i)) {
                return false;
            }
        }
        
        return true;
    }
    
    private boolean dfs(List<Integer>[] graph, byte[] visited, int node) {
        if (visited[node] == 1) {
            return false; // Cycle detected
        }
        
        if (visited[node] == 2) {
            return true; // Already processed, no cycle
        }
        
        visited[node] = 1; // Mark as currently processing
        
        // Visit all neighbors (courses that depend on current course)
        for (int neighbor : graph[node]) {
            if (!dfs(graph, visited, neighbor)) {
                return false;
            }
        }
        
        visited[node] = 2; // Mark as completely processed
        return true;
    }
}
```

## How the Solution Works

### Graph Construction
We build an adjacency list where each course points to courses that depend on it. For prerequisite `[a, b]`, we add an edge from `b` to `a`, meaning "after completing course `b`, you can take course `a`".

### Three-State DFS
The algorithm uses three states to track each course during DFS traversal:
- **State 0 (Unvisited)**: Course hasn't been explored yet
- **State 1 (Processing)**: Course is currently being explored (part of current DFS path)
- **State 2 (Processed)**: Course and all its dependencies have been completely explored

### Cycle Detection Logic
**Cycle Found**: If we encounter a course in state 1 during DFS, it means we've found a back edge, indicating a cycle in the dependency graph.

**No Cycle**: If we can completely process all courses without finding any back edges, then a valid course ordering exists.

### Main Algorithm Flow
We iterate through all courses and perform DFS on unvisited ones. If any DFS call detects a cycle, we return `false`. If all courses can be processed without cycles, we return `true`.

## Example Walkthrough

**Example 1**: `numCourses = 2, prerequisites = [[1,0]]`
- Graph: `0 → 1` (after course 0, you can take course 1)
- DFS from course 0: Mark 0 as processing → visit course 1 → mark 1 as processed → mark 0 as processed
- DFS from course 1: Already processed, skip
- Result: `true` (no cycles)

**Example 2**: `numCourses = 2, prerequisites = [[1,0],[0,1]]`
- Graph: `0 → 1, 1 → 0` (mutual dependency)
- DFS from course 0: Mark 0 as processing → visit course 1 → mark 1 as processing → visit course 0 → course 0 is already processing!
- Cycle detected: `0 → 1 → 0`
- Result: `false`

## Why This Works

**Graph Modeling**: The prerequisite relationships naturally form a directed graph where cycles represent impossible scheduling scenarios.

**Complete Exploration**: By checking all unvisited courses, we ensure no disconnected components with cycles are missed.

**State Tracking**: The three-state system efficiently distinguishes between different types of revisits during DFS traversal.

**Cycle Detection**: Back edges (leading to currently processing nodes) are the only way cycles can form in DFS traversal.

## Complexity Analysis

**Time Complexity**: O(V + E)
- V = numCourses (vertices)
- E = prerequisites.length (edges)
- Each course and prerequisite is visited exactly once

**Space Complexity**: O(V + E)
- O(V) for the visited array
- O(E) for the adjacency list storage
- O(V) for recursion stack in worst case

## Key Implementation Details

**Adjacency List**: Using `List<Integer>[]` provides efficient neighbor iteration compared to adjacency matrix.

**Byte Array**: Using `byte[]` instead of `int[]` for visited states saves memory since we only need 3 states.

**Edge Direction**: Adding edges as `graph[edge[1]].add(edge[0])` correctly models the dependency relationship.

**Early Termination**: Returning `false` immediately upon cycle detection avoids unnecessary computation.

This solution efficiently determines course schedulability by transforming the problem into cycle detection, leveraging the fundamental property that acyclic directed graphs have valid topological orderings.