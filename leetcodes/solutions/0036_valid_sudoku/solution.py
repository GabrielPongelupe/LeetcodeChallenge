from collections import defaultdict
from typing import List


class Solution:
    def isValidSudoku(self, board: List[List[str]]) -> bool:
        row = defaultdict(set)
        col = defaultdict(set)
        boxes = defaultdict(set)

        for r in range(9):
            for c in range(9):
                current = board[r][c]

                if current == ".":
                    continue

                if current in row[r] or current in col[c] or current in boxes[(r//3, c//3)]:
                    return False

                row[r].add(current)
                col[c].add(current)
                boxes[(r//3, c//3)].add(current)

        return True

