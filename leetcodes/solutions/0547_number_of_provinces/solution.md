# 547. Number of Provinces - Solution

## Approach: Depth-First Search (DFS)

This solution uses **Depth-First Search (DFS)** to identify connected components in an undirected graph represented as an adjacency matrix. Each connected component represents a province, and we count the total number of separate components.

### Key Concepts:
1. **Graph Theory**: The problem is essentially about finding connected components in an undirected graph
2. **DFS Traversal**: Use recursive depth-first search to explore all cities in a province
3. **Visited Array**: Track which cities have been processed to avoid counting the same province multiple times
4. **Connected Components**: Each separate group forms one province

## Solution Code

```java
class Solution {
    public int findCircleNum(int[][] isConnected) {
        int[] verticesMarcados = new int[isConnected.length];  // Array de visitação
        int numeroDeProvincias = 0;

        // Percorrer todas as cidades
        for (int i = 0; i < isConnected.length; i++) {
            if (verticesMarcados[i] == 0) {  // Se a cidade não foi visitada
                numeroDeProvincias++;  // Encontramos uma nova província
                buscaProfundidade(isConnected, i, verticesMarcados);  // Realiza a busca em profundidade
            }
        }

        return numeroDeProvincias;  // Retorna o total de províncias
    }

    private void buscaProfundidade(int[][] grafo, int indice, int[] verticesMarcados) {
        verticesMarcados[indice] = 1;  // Marca o vértice como visitado

        // Percorre os vizinhos do vértice
        for (int i = 0; i < grafo[indice].length; i++) {
            if (grafo[indice][i] == 1 && verticesMarcados[i] == 0) {
                buscaProfundidade(grafo, i, verticesMarcados);  // Chama recursivamente a busca
            }
        }
    }
}
```

## How the Solution Works

### Setup Phase
We initialize a visited array `verticesMarcados` to track which cities have been processed, and a counter `numeroDeProvincias` to count the total number of provinces.

### Main Algorithm Logic
The algorithm iterates through each city and applies DFS when encountering an unvisited city:

**Iterate Through Cities**: Loop through each city index from 0 to n-1.

**Check If Unvisited**: If `verticesMarcados[i] == 0`, this city hasn't been processed yet.

**New Province Found**: Increment the province counter since we've discovered a new connected component.

**DFS Exploration**: Call `buscaProfundidade()` to mark all cities connected to this one as visited.

### DFS Helper Function
The `buscaProfundidade()` method performs the recursive depth-first search:

**Mark Current City**: Set `verticesMarcados[indice] = 1` to mark the current city as visited.

**Explore Neighbors**: Loop through all possible connections in the adjacency matrix.

**Recursive Call**: If a connection exists (`grafo[indice][i] == 1`) and the neighbor is unvisited (`verticesMarcados[i] == 0`), recursively explore that neighbor.

**Complete Component**: The recursion continues until all cities in the current province have been visited.

## Example Walkthrough

For `isConnected = [[1,1,0],[1,1,0],[0,0,1]]`:

**Initial State**: `verticesMarcados = [0,0,0]`, `numeroDeProvincias = 0`

**i = 0**: City 0 is unvisited
- Increment: `numeroDeProvincias = 1`
- DFS from city 0: marks cities 0 and 1 as visited
- `verticesMarcados = [1,1,0]`

**i = 1**: City 1 is already visited, skip

**i = 2**: City 2 is unvisited
- Increment: `numeroDeProvincias = 2`
- DFS from city 2: marks only city 2 as visited
- `verticesMarcados = [1,1,1]`

**Result**: Return `2`

For `isConnected = [[1,0,0],[0,1,0],[0,0,1]]`:

**Initial State**: `verticesMarcados = [0,0,0]`, `numeroDeProvincias = 0`

**i = 0**: City 0 is unvisited
- Increment: `numeroDeProvincias = 1`
- DFS from city 0: marks only city 0 (no connections)
- `verticesMarcados = [1,0,0]`

**i = 1**: City 1 is unvisited
- Increment: `numeroDeProvincias = 2`
- DFS from city 1: marks only city 1 (no connections)
- `verticesMarcados = [1,1,0]`

**i = 2**: City 2 is unvisited
- Increment: `numeroDeProvincias = 3`
- DFS from city 2: marks only city 2 (no connections)
- `verticesMarcados = [1,1,1]`

**Result**: Return `3`

## Why This Works

**Connected Components**: Each DFS traversal explores exactly one connected component (province), ensuring we count each province exactly once.

**Adjacency Matrix**: The symmetric matrix property (`isConnected[i][j] == isConnected[j][i]`) guarantees that if city A connects to city B, then city B connects to city A.

**Visited Tracking**: The visited array prevents us from double-counting cities that belong to the same province.

**Complete Exploration**: DFS ensures we find all cities within a province before moving to the next unvisited city.

## Complexity Analysis

**Time Complexity**: O(n²)
- We iterate through each city once: O(n)
- For each unvisited city, DFS explores all connections: O(n) per city in worst case
- Total matrix traversal across all DFS calls: O(n²)

**Space Complexity**: O(n)
- Visited array: O(n)
- Recursion stack depth: O(n) in worst case (linear graph)

## Key Implementation Details

**Visited Array**: Using an integer array where 0 means unvisited and 1 means visited provides clear state tracking.

**Recursive DFS**: The recursive approach naturally handles the graph traversal and backtracking.

**Matrix Traversal**: For each city, we check all possible connections by iterating through its row in the adjacency matrix.

**Province Counting**: We only increment the province counter when discovering a completely new connected component.

**Symmetric Matrix**: The problem guarantees the matrix is symmetric, so we don't need to check both `isConnected[i][j]` and `isConnected[j][i]`.

This graph-based solution effectively transforms the province counting problem into a connected components problem, leveraging DFS to achieve an optimal and intuitive solution.