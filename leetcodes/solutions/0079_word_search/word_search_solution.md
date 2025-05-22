# 79. Word Search - Solution

## Approach: Backtracking + Depth-First Search (DFS)

This solution uses **backtracking** with **Depth-First Search (DFS)** to explore all possible paths in the grid.

### Key Concepts:
1. **Backtracking**: Try a path, and if it doesn't work, undo the changes and try another path
2. **DFS**: Explore as deep as possible in one direction before backtracking
3. **State modification**: Temporarily mark visited cells to avoid reusing them

## Solution Code

```python
from typing import List

class Solution:
    def exist(self, board: List[List[str]], word: str) -> bool:
        n_rows = len(board)
        n_columns = len(board[0])

        # Try starting the search from every cell in the board
        for row in range(n_rows):
            for column in range(n_columns):
                result = self.backtrack(board, n_rows, n_columns, row, column, word)
                if result:
                    return True

        return False

    def backtrack(self, board, n_rows, n_columns, row, column, suffix):
        # Base case: if suffix is empty, we found the complete word
        if len(suffix) == 0:
            return True
        
        # Boundary checks and character matching
        if (row < 0 or row >= n_rows or 
            column < 0 or column >= n_columns or 
            board[row][column] != suffix[0]):
            return False

        # Mark current cell as visited by replacing with '#'
        board[row][column] = '#'

        # Define 4 directions: down, right, up, left
        directions = [(1,0), (0,1), (-1,0), (0,-1)]

        # Try all 4 directions
        for new_x, new_y in directions:
            result = self.backtrack(board, n_rows, n_columns, 
                                  row + new_x, column + new_y, suffix[1:])
            if result:
                return True

        # Backtrack: restore the original character
        board[row][column] = suffix[0]

        return False
```

## Detailed Step-by-Step Explanation

### 1. Main Function (`exist`)

```python
def exist(self, board: List[List[str]], word: str) -> bool:
    n_rows = len(board)
    n_columns = len(board[0])
```

**What it does**: Gets the board dimensions (number of rows and columns)

**Why we need it**: We need this information to perform boundary checks during the search

---

```python
for row in range(n_rows):
    for column in range(n_columns):
        result = self.backtrack(board, n_rows, n_columns, row, column, word)
        if result:
            return True
```

**What it does**: Tests each cell in the board as a potential starting point

**Why**: We don't know where the word begins, so we test all positions

**Important detail**: Returns `True` immediately if the word is found (optimization)

---

### 2. Backtracking Function (`backtrack`)

#### Base Case - Success
```python
if len(suffix) == 0:
    return True
```

**What is `suffix`**: The remaining part of the word that still needs to be found

**When this happens**: When we've already found all characters of the word

**Why it returns `True`**: It means the word has been completely found

---

#### Validity Checks
```python
if (row < 0 or row >= n_rows or 
    column < 0 or column >= n_columns or 
    board[row][column] != suffix[0]):
    return False
```

**Check 1**: `row < 0 or row >= n_rows`
- Ensures we don't go out of the vertical bounds of the board

**Check 2**: `column < 0 or column >= n_columns`
- Ensures we don't go out of the horizontal bounds of the board

**Check 3**: `board[row][column] != suffix[0]`
- The current cell must match the first character of the remaining word

**If any check fails**: Returns `False` (invalid path)

---

#### Mark Cell as Visited
```python
board[row][column] = '#'
```

**Why do this**: The problem states we cannot reuse the same cell

**How it works**: Temporarily replaces the character with `'#'`

**Advantage**: More efficient than maintaining a separate set of visited cells

---

#### Explore All Directions
```python
directions = [(1,0), (0,1), (-1,0), (0,-1)]
for new_x, new_y in directions:
    result = self.backtrack(board, n_rows, n_columns, 
                          row + new_x, column + new_y, suffix[1:])
    if result:
        return True
```

**The 4 directions**:
- `(1,0)`: Move down (increase row)
- `(0,1)`: Move right (increase column)  
- `(-1,0)`: Move up (decrease row)
- `(0,-1)`: Move left (decrease column)

**Recursive call**: Searches for `suffix[1:]` (word without the first character) from the new position

**Optimization**: Returns `True` immediately if any direction succeeds

---

#### Backtrack - Restore State
```python
board[row][column] = suffix[0]
```

**Why it's necessary**: Other paths might need to use this cell

**What it restores**: Puts back the original character (`suffix[0]`)

**When it happens**: When all directions have failed

---

## Execution Example

Let's trace through Example 1:
```
Board: [["A","B","C","E"],
        ["S","F","C","S"],
        ["A","D","E","E"]]
Word: "ABCCED"
```

### Step by step:

1. **Position (0,0) = 'A'**: ✅ Matches the first 'A'
2. **Mark (0,0) as '#'**, search for "BCCED"
3. **Try (0,1) = 'B'**: ✅ Matches 'B', search for "CCED"
4. **Try (0,2) = 'C'**: ✅ Matches the first 'C', search for "CED"
5. **Try (1,2) = 'C'**: ✅ Matches the second 'C', search for "ED"
6. **Try (2,2) = 'E'**: ✅ Matches 'E', search for "D"
7. **Try (2,1) = 'D'**: ✅ Matches 'D', search for ""
8. **Empty suffix**: ✅ Returns `True` - word found!

## Why This Solution Works

1. **Complete exploration**: We try every possible starting position
2. **Path validation**: We ensure each step is valid (bounds + character match)
3. **State management**: We properly mark and unmark visited cells
4. **Recursive structure**: The problem naturally breaks down into smaller subproblems
5. **Backtracking mechanism**: We can undo bad choices and try alternatives

This approach guarantees that if a word exists in the grid, we will find it, and if it doesn't exist, we will have exhaustively checked all possibilities.