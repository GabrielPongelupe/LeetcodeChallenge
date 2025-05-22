# 704. Binary Search - Solution

## Approach: Classic Binary Search

This solution uses the classic **binary search algorithm** to efficiently find a target value in a sorted array by repeatedly dividing the search space in half.

### Key Concepts:
1. **Divide and Conquer**: Split the search space in half at each step
2. **Sorted Array Advantage**: Use the sorted property to eliminate half of the possibilities
3. **Two Pointers**: Maintain left and right boundaries of the search space
4. **Logarithmic Time**: Achieve O(log n) complexity by halving the search space each iteration

## Solution Code

```python
from typing import List

class Solution:
    def search(self, nums: List[int], target: int) -> int:
        l, r = 0, len(nums) - 1

        while l <= r:
            m = (l + r) // 2

            if nums[m] > target:
                r = m - 1
            elif nums[m] < target:
                l = m + 1
            else: 
                return m
        
        return -1
```

## How the Solution Works

### Setup Phase
We initialize two pointers: `l` (left) at index 0 and `r` (right) at the last index. These pointers define our current search boundaries.

### Main Loop Logic
The algorithm continues while `l <= r`, meaning there's still a valid search space:

**Find Middle**: Calculate the middle index `m = (l + r) // 2` to split the current search space.

**Three-Way Comparison**:
- If `nums[m] > target`: The target must be in the left half, so we move `r = m - 1`
- If `nums[m] < target`: The target must be in the right half, so we move `l = m + 1`  
- If `nums[m] == target`: We found it! Return the index `m`

**Search Space Reduction**: After each comparison, we eliminate half of the remaining possibilities.

### Termination
If the loop ends without finding the target (when `l > r`), the target doesn't exist in the array, so we return `-1`.

## Example Walkthrough

For `nums = [-1,0,3,5,9,12]` and `target = 9`:

**Initial**: `l=0, r=5`
1. `m=2`, `nums[2]=3 < 9` → move left boundary: `l=3`
2. `m=4`, `nums[4]=9 == 9` → found! Return `4`

For `nums = [-1,0,3,5,9,12]` and `target = 2`:

**Initial**: `l=0, r=5`  
1. `m=2`, `nums[2]=3 > 2` → move right boundary: `r=1`
2. `m=0`, `nums[0]=-1 < 2` → move left boundary: `l=1`
3. `m=1`, `nums[1]=0 < 2` → move left boundary: `l=2`
4. Now `l=2 > r=1` → exit loop, return `-1`

## Why This Works

**Sorted Array Property**: Since the array is sorted, we can determine which half contains our target based on a single comparison.

**Search Space Elimination**: Each comparison eliminates exactly half of the remaining possibilities, guaranteeing logarithmic time complexity.

**Boundary Management**: The two-pointer approach ensures we never miss the target and properly handle edge cases.

**Complete Coverage**: The algorithm examines every possible location where the target could exist without redundant checks.

## Complexity Analysis

**Time Complexity**: O(log n)
- Each iteration reduces search space by half
- Maximum iterations needed: log₂(n)

**Space Complexity**: O(1)
- Only uses a constant amount of extra variables
- No recursive calls or additional data structures

## Key Implementation Details

**Integer Division**: Using `//` ensures we get an integer middle index without floating point issues.

**Inclusive Boundaries**: The condition `l <= r` handles the case where the search space narrows to a single element.

**Immediate Return**: We return as soon as we find the target, avoiding unnecessary iterations.

**Proper Boundary Updates**: Moving `l = m + 1` and `r = m - 1` (not just `m`) prevents infinite loops and ensures we don't re-examine the middle element.

This classic binary search implementation is optimal for searching in sorted arrays and demonstrates the power of divide-and-conquer algorithms in achieving logarithmic time complexity.