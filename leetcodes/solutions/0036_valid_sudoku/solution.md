# 36. Valid Sudoku - Solution

## Approach: Hash Set Tracking with Single Pass

This solution validates a Sudoku board by tracking seen digits in rows, columns, and 3x3 sub-boxes using hash sets. The key insight is to process the entire board in a single pass while simultaneously checking all three Sudoku constraints.

### Key Concepts:
1. **Hash Set Storage**: Use sets to efficiently track seen digits and detect duplicates
2. **Single Pass Validation**: Check all constraints (rows, columns, sub-boxes) in one iteration
3. **Sub-box Indexing**: Map cell coordinates to sub-box indices using integer division
4. **Early Termination**: Return false immediately when a duplicate is found

## Solution Code

```python
from collections import defaultdict
from typing import List

class Solution:
    def isValidSudoku(self, board: List[List[str]]) -> bool:
        # Use defaultdict to automatically create sets for new keys
        row = defaultdict(set)
        col = defaultdict(set)
        boxes = defaultdict(set)

        # Iterate through each cell in the 9x9 board
        for r in range(9):
            for c in range(9):
                current = board[r][c]

                # Skip empty cells
                if current == ".":
                    continue

                # Check if current digit violates any Sudoku rule
                if (current in row[r] or 
                    current in col[c] or 
                    current in boxes[(r//3, c//3)]):
                    return False

                # Add current digit to respective tracking sets
                row[r].add(current)
                col[c].add(current)
                boxes[(r//3, c//3)].add(current)

        return True
```

## How the Solution Works

### Data Structure Design
The solution uses three separate hash maps, each mapping to sets:
- **`row[i]`**: Set of digits seen in row `i`
- **`col[j]`**: Set of digits seen in column `j`  
- **`boxes[(i//3, j//3)]`**: Set of digits seen in the 3x3 sub-box containing cell `(i,j)`

### Sub-box Mapping Strategy
The crucial insight is mapping cell coordinates `(r, c)` to sub-box indices using `(r//3, c//3)`:
- Cells `(0,0)` to `(2,2)` map to sub-box `(0,0)`
- Cells `(0,3)` to `(2,5)` map to sub-box `(0,1)`
- Cells `(3,0)` to `(5,2)` map to sub-box `(1,0)`
- And so on...

This creates a unique identifier for each of the 9 sub-boxes in the Sudoku grid.

### Validation Logic
For each non-empty cell, we check if its digit already exists in:
1. The same row
2. The same column  
3. The same 3x3 sub-box

If any duplicate is found, the board is invalid. Otherwise, we add the digit to all three tracking sets.

## Example Walkthrough

**Example 1**: Valid board
- Process cell `(0,0)` with value `"5"`: Add to `row[0]`, `col[0]`, `boxes[(0,0)]`
- Process cell `(0,1)` with value `"3"`: Add to `row[0]`, `col[1]`, `boxes[(0,0)]`
- Continue processing... no duplicates found
- Result: `true`

**Example 2**: Invalid board
- Process cell `(0,0)` with value `"8"`: Add to `row[0]`, `col[0]`, `boxes[(0,0)]`
- Process cell `(3,0)` with value `"8"`: Check `row[3]` (empty), `col[0]` (contains "8")
- Duplicate found in column 0!
- Result: `false`

## Why This Works

**Comprehensive Coverage**: By tracking all three constraint types simultaneously, we ensure no Sudoku rule violation goes undetected.

**Efficient Duplicate Detection**: Hash sets provide O(1) average-case lookup and insertion, making duplicate detection very fast.

**Space-Efficient Sub-box Mapping**: Using coordinate pairs `(r//3, c//3)` as keys elegantly maps cells to their respective sub-boxes without complex indexing calculations.

**Early Termination**: Returning `false` immediately upon finding any duplicate avoids unnecessary computation.

## Complexity Analysis

**Time Complexity**: O(1)
- We always process exactly 81 cells (9×9 board)
- Each cell operation (set lookup and insertion) is O(1) on average
- Since the board size is fixed, this is effectively constant time

**Space Complexity**: O(1)  
- Maximum of 9 digits per row/column/sub-box
- 9 rows + 9 columns + 9 sub-boxes = 27 sets maximum
- Each set contains at most 9 elements
- Total space is bounded by a constant: 27 × 9 = 243 elements maximum

## Key Implementation Details

**defaultdict Usage**: Using `defaultdict(set)` automatically creates empty sets for new keys, eliminating the need for explicit initialization checks.

**String Comparison**: Digits are stored as strings (`"1"` through `"9"`), so we use string equality for comparisons.

**Tuple Keys for Sub-boxes**: Using `(r//3, c//3)` as dictionary keys creates a natural mapping from cell coordinates to sub-box identifiers.

**Single Condition Check**: Combining all three constraint checks in one `if` statement enables short-circuit evaluation for efficiency.

This solution efficiently validates Sudoku boards by leveraging hash sets for fast duplicate detection and clever coordinate mapping for sub-box identification, achieving optimal performance for this constraint-checking problem.