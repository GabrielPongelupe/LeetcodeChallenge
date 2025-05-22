# 133. Clone Graph - Solution

## Approach: DFS with HashMap Memoization

This solution uses **Depth-First Search (DFS)** combined with a **HashMap** to create a deep copy of an undirected graph. The key insight is to maintain a mapping between original nodes and their clones to handle cycles and avoid infinite recursion.

### Key Concepts:
1. **Deep Copy**: Create completely new node objects with the same structure as the original graph
2. **Cycle Handling**: Use memoization to prevent infinite loops in cyclic graphs
3. **DFS Traversal**: Recursively clone each node and its neighbors
4. **HashMap Mapping**: Track original-to-clone relationships to reuse already created nodes

## Solution Code

```java
class Solution {
    public Node cloneGraph(Node node) {
        if (node == null) return null;

        Map<Node, Node> map = new HashMap<>();
        return dfs(node, map);
    }

    private Node dfs(Node node, Map<Node, Node> map) {
        if (map.containsKey(node)) {
            return map.get(node);
        }

        Node clone = new Node(node.val);
        map.put(node, clone);

        for (Node neighbor : node.neighbors) {
            clone.neighbors.add(dfs(neighbor, map));
        }

        return clone;
    }
}
```

## How the Solution Works

### Base Case Handling
First, we handle the edge case where the input node is `null`, returning `null` immediately for empty graphs.

### Main Algorithm Logic
The solution uses a two-step approach with DFS and memoization:

**Initialize HashMap**: Create a map to store the relationship between original nodes and their clones.

**Start DFS**: Call the recursive DFS function with the starting node and the empty map.

### DFS Helper Function
The `dfs()` method performs the core cloning logic:

**Check Memoization**: If the current node has already been cloned (exists in the map), return the existing clone to avoid infinite loops and duplicate work.

**Create Clone**: Create a new node with the same value as the original node.

**Store Mapping**: Immediately add the original-to-clone mapping to the HashMap before processing neighbors (crucial for cycle handling).

**Clone Neighbors**: Recursively clone each neighbor and add the cloned neighbors to the current clone's neighbor list.

**Return Clone**: Return the fully constructed clone node.

## Example Walkthrough

For `adjList = [[2,4],[1,3],[2,4],[1,3]]` (a square cycle):

**Initial Call**: `dfs(node1, {})`

**Step 1 - Clone Node 1**:
- `map.containsKey(node1)` = false
- Create `clone1` with val = 1
- `map = {node1 -> clone1}`
- Process neighbors: [node2, node4]

**Step 2 - Clone Node 2**:
- Call `dfs(node2, map)`
- `map.containsKey(node2)` = false
- Create `clone2` with val = 2
- `map = {node1 -> clone1, node2 -> clone2}`
- Process neighbors: [node1, node3]

**Step 3 - Handle Cycle (Node 1 revisited)**:
- Call `dfs(node1, map)`
- `map.containsKey(node1)` = true
- Return existing `clone1` (prevents infinite recursion)

**Step 4 - Clone Node 3**:
- Call `dfs(node3, map)`
- Create `clone3` with val = 3
- `map = {node1 -> clone1, node2 -> clone2, node3 -> clone3}`
- Process neighbors: [node2, node4]

**Step 5 - Reuse Existing Clones**:
- When processing node2 and node4 again, return existing clones from map
- Complete the neighbor relationships for all clones

**Result**: A completely independent copy of the original graph with the same structure.

## Why This Works

**Deep Copy Guarantee**: Each node in the cloned graph is a completely new object, ensuring no shared references with the original graph.

**Cycle Prevention**: The HashMap prevents infinite recursion by detecting when we've already started cloning a node.

**Structural Preservation**: The DFS traversal ensures we visit every node and maintain all neighbor relationships.

**Memory Efficiency**: Each original node is cloned exactly once, avoiding duplicate clones.

**Reference Integrity**: All neighbor references in the cloned graph point to cloned nodes, not original nodes.

## Complexity Analysis

**Time Complexity**: O(N + M)
- N = number of nodes, M = number of edges
- We visit each node exactly once: O(N)
- We traverse each edge exactly twice (once from each endpoint): O(M)
- HashMap operations are O(1) on average

**Space Complexity**: O(N)
- HashMap storage: O(N) for storing N node mappings
- Recursion stack depth: O(H) where H is the height of the DFS tree
- In worst case (linear graph): O(N) stack space
- Cloned graph space: O(N + M) but this is required output, not auxiliary space

## Key Implementation Details

**Early Memoization Check**: We check if a node is already cloned before creating a new clone, ensuring we never clone the same node twice.

**Immediate Mapping**: We add the original-to-clone mapping immediately after creating the clone, before processing neighbors. This is crucial for handling cycles correctly.

**Recursive Neighbor Processing**: Each neighbor is recursively cloned, and the returned clone (whether newly created or retrieved from the map) is added to the current clone's neighbor list.

**HashMap Efficiency**: Using the original node objects as keys provides O(1) average lookup time and naturally handles the node identity comparison.

**Null Safety**: The initial null check handles the empty graph case gracefully.

## Alternative Approaches

**BFS with Queue**: Could use breadth-first search with a queue instead of DFS, achieving the same time/space complexity but with iterative implementation.

**Two-Pass Approach**: First pass creates all nodes, second pass establishes neighbor relationships. Less elegant but avoids recursion.

This DFS + HashMap solution elegantly handles the graph cloning problem by leveraging memoization to prevent cycles while ensuring a complete deep copy of the graph structure.