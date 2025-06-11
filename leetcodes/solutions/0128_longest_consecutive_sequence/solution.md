# 128. Longest Consecutive Sequence - Solution

## Approach: Hash Set with Smart Sequence Starting

This solution achieves optimal O(n) time complexity by using a hash set for constant-time lookups and intelligently identifying sequence starting points to avoid redundant work.

### Key Concepts:
1. **Hash Set for O(1) Lookups**: Convert array to set for constant-time containment checks
2. **Sequence Starting Point Detection**: Only begin counting from numbers that are the start of a sequence
3. **Avoid Redundant Work**: Each number is processed at most twice across all iterations
4. **Greedy Extension**: Once a sequence start is found, extend it as far as possible

## Solution Code

```python
class Solution:
    def longestConsecutive(self, nums: List[int]) -> int:
        num_set = set(nums)
        longest_streak = 0

        for num in nums:
            if num - 1 not in num_set:
                current_num = num
                current_streak = 1

                while current_num + 1 in num_set:
                    current_num += 1
                    current_streak += 1
                longest_streak = max(longest_streak, current_streak)
            
        return longest_streak
```

## Why This Solution is Optimal O(n)

### Smart Starting Point Detection
The key insight is the condition `if num - 1 not in num_set`. This ensures we only start counting sequences from their actual beginning. For example, in sequence [1, 2, 3, 4], we only start counting from 1, not from 2, 3, or 4.

### Amortized Analysis
While there's a nested loop structure, each number in the input is visited **at most twice**:
1. **Once** during the outer loop iteration
2. **At most once** during the inner while loop as part of a sequence extension

**Example**: For sequence [1, 2, 3, 4]:
- Number 1: Visited in outer loop, then extended through 2, 3, 4 in inner loop
- Number 2: Visited in outer loop, but `2-1=1` exists in set, so no inner loop
- Number 3: Visited in outer loop, but `3-1=2` exists in set, so no inner loop  
- Number 4: Visited in outer loop, but `4-1=3` exists in set, so no inner loop

Total operations: 4 (outer) + 4 (inner extensions) = 8 = 2n

### Mathematical Proof of O(n) Complexity
Let S be the set of all numbers in the input.

**Outer loop**: Iterates exactly n times (once per element)

**Inner loop**: The total number of inner loop iterations across ALL outer loop iterations equals the total number of consecutive relationships in the input, which is bounded by n-k, where k is the number of sequences.

**Total operations**: n (outer) + (n-k) (inner) = 2n - k ≤ 2n = O(n)

## How the Solution Works

### Step 1: Hash Set Creation
Convert the input array to a hash set for O(1) containment checks. This eliminates duplicates and enables constant-time lookups.

### Step 2: Sequence Detection
For each number in the original array, check if it could be the start of a sequence by verifying that `num - 1` is not in the set.

### Step 3: Sequence Extension
When a sequence start is found, greedily extend it by continuously checking for `current_num + 1` in the set and incrementing both the current number and streak length.

### Step 4: Maximum Tracking
Maintain the longest streak found so far and update it whenever a longer sequence is discovered.

## Example Walkthrough

**Input**: `nums = [100,4,200,1,3,2]`

**Set**: `{100, 4, 200, 1, 3, 2}`

**Iteration Process**:
1. **num = 100**: `99 ∉ set` → Start sequence → `101 ∉ set` → Streak = 1
2. **num = 4**: `3 ∈ set` → Skip (not a sequence start)
3. **num = 200**: `199 ∉ set` → Start sequence → `201 ∉ set` → Streak = 1
4. **num = 1**: `0 ∉ set` → Start sequence → Extend: 1→2→3→4 → Streak = 4
5. **num = 3**: `2 ∈ set` → Skip (not a sequence start)
6. **num = 2**: `1 ∈ set` → Skip (not a sequence start)

**Result**: `4` (longest consecutive sequence: [1, 2, 3, 4])

## Alternative Approaches and Why They're Suboptimal

### Sorting Approach - O(n log n)
```python
def longestConsecutive(self, nums):
    if not nums:
        return 0
    nums.sort()
    # Then scan for consecutive elements
```
**Why suboptimal**: Sorting dominates the time complexity at O(n log n).

### Union-Find Approach - O(n α(n))
Using Union-Find with path compression and union by rank.
**Why suboptimal**: While nearly linear, the inverse Ackermann function α(n) makes it technically slower than O(n).

### Naive Hash Set - O(n²)
```python
def longestConsecutive(self, nums):
    num_set = set(nums)
    longest = 0
    for num in num_set:
        # Check both directions from each number
        # This leads to redundant work
```
**Why suboptimal**: Without smart starting point detection, each number could trigger a full sequence scan.

## Complexity Analysis

**Time Complexity**: O(n)
- Hash set creation: O(n)
- Outer loop: O(n) iterations
- Inner while loop: O(n) total iterations across all outer loop iterations
- Total: O(n) + O(n) + O(n) = O(n)

**Space Complexity**: O(n)
- Hash set storage: O(n) for storing all unique numbers
- Additional variables: O(1)

## Key Implementation Details

**Set Conversion**: Using `set(nums)` automatically handles duplicates and provides O(1) average-case lookups.

**Sequence Start Detection**: The condition `num - 1 not in num_set` is the critical optimization that prevents redundant sequence counting.

**Greedy Extension**: Once a sequence start is confirmed, the while loop greedily extends it to find the complete sequence length.

**Edge Case Handling**: Empty array returns 0; single element returns 1; all handled naturally by the algorithm structure.

This solution optimally solves the longest consecutive sequence problem by combining hash set efficiency with intelligent sequence detection, achieving the required O(n) time complexity through careful algorithmic design that avoids redundant computations.