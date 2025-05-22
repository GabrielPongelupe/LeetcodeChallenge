from typing import List, Tuple

class Solution:
    def exist(self, board: List[List[str]], word: str) -> bool:
       
        n_rows = len(board)
        n_columns = len(board[0])

        for row in range(n_rows):
            for column in range(n_columns):
                result = self.backtrack(board, n_rows, n_columns, row, column, word)
                if result:
                    return True

        return False

    

    def backtrack(self, board, n_rows, n_columns, row, column, suffix):
        if len(suffix) == 0:
            return True
        if row < 0 or row >= n_rows or column < 0 or column >= n_columns or board[row][column] != suffix[0]:
            return False

        board[row][column] = '#'

        directions = [(1,0), (0,1), (-1,0), (0,-1)]

        for new_x, new_y in directions:
            result = self.backtrack(board, n_rows, n_columns, row + new_x, column + new_y, suffix[1:])
            if result:
                return True

        board[row][column] = suffix[0]

        return False