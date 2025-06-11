# 128. Longest Consecutive Sequence

## Problem Description

Given an unsorted array of integers `nums`, return *the length of the longest consecutive elements sequence.*
You must write an algorithm that runs in `O(n)` time.

## Examples

**Example 1:**
```
Input: nums = [100,4,200,1,3,2]
Output: 4
Explanation: The longest consecutive elements sequence is [1, 2, 3, 4]. Therefore its length is 4.
```

**Example 2:**
```
Input: nums = [0,3,7,2,5,8,4,6,0,1]
Output: 9
Explanation: The longest consecutive elements sequence is [0, 1, 2, 3, 4, 5, 6, 7, 8]. Therefore its length is 9.
```

**Example 3:**
```
Input: nums = [1,0,1,2]
Output: 3
Explanation: The longest consecutive elements sequence is [0, 1, 2]. Therefore its length is 3.
```

## Constraints

- `0 <= nums.length <= 10^5`
- `-10^9 <= nums[i] <= 10^9`

## Function Signature

```python
class Solution:
    def longestConsecutive(self, nums: List[int]) -> int:
        # Your solution here
```

## Key Insights

The challenge is to find consecutive sequences in an unsorted array while maintaining O(n) time complexity. This rules out sorting-based approaches (which would be O(n log n)) and requires a more clever strategy.

The optimal approach uses a hash set for O(1) lookups and only starts counting from the beginning of each potential sequence, ensuring each number is visited at most twice.