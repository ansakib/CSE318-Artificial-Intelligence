import itertools
import random


class Minesweeper():
    """
    Minesweeper game representation
    """

    def __init__(self, height=8, width=8, mines=8):

        # Set initial width, height, and number of mines
        self.height = height
        self.width = width
        self.mines = set()

        # Initialize an empty field with no mines
        self.board = []
        for i in range(self.height):
            row = []
            for j in range(self.width):
                row.append(False)
            self.board.append(row)

        # Add mines randomly
        while len(self.mines) != mines:
            i = random.randrange(height)
            j = random.randrange(width)
            if not self.board[i][j]:
                self.mines.add((i, j))
                self.board[i][j] = True

        # At first, player has found no mines
        self.mines_found = set()

    def print(self):
        """
        Prints a text-based representation
        of where mines are located.
        """
        for i in range(self.height):
            print("--" * self.width + "-")
            for j in range(self.width):
                if self.board[i][j]:
                    print("|X", end="")
                else:
                    print("| ", end="")
            print("|")
        print("--" * self.width + "-")

    def is_mine(self, cell):
        i, j = cell
        return self.board[i][j]

    def nearby_mines(self, cell):
        """
        Returns the number of mines that are
        within one row and column of a given cell,
        not including the cell itself.
        """

        # Keep count of nearby mines
        # count = 0

        # # Loop over all cells within one row and column
        # for i in range(cell[0] - 1, cell[0] + 2):
        #     for j in range(cell[1] - 1, cell[1] + 2):

        #         # Ignore the cell itself
        #         if (i, j) == cell:
        #             continue

        #         # Update count if cell in bounds and is mine
        #         if 0 <= i < self.height and 0 <= j < self.width:
        #             if self.board[i][j]:
        #                 count += 1

        # return count

        # Get all cells within one row and column of a given cell, discard diagonal cells

        count = 0
        row = cell[0]
        col = cell[1]
        if row-1>=0 and self.board[row-1][col]:
            count += 1
        if row+1<self.height and self.board[row+1][col]:
            count += 1
        if col-1>=0 and self.board[row][col-1]:
            count += 1
        if col+1<self.width and self.board[row][col+1]:
            count += 1

        return count


    def won(self):
        """
        Checks if all mines have been flagged.
        """
        return self.mines_found == self.mines


class Sentence():
    """
    Logical statement about a Minesweeper game
    A sentence consists of a set of board cells,
    and a count of the number of those cells which are mines.
    """

    def __init__(self, cells, count):
        self.cells = set(cells)
        self.count = count

    def __eq__(self, other):
        return self.cells == other.cells and self.count == other.count

    def __str__(self):
        return f"{self.cells} = {self.count}"

    def known_mines(self):
        """
        Returns the set of all cells in self.cells known to be mines.
        """
        if len(self.cells) == self.count:
            #print("known_mines:", self.cells)
            return self.cells
        else:
            return set()
        

    def known_safes(self):
        """
        Returns the set of all cells in self.cells known to be safe.
        """
        if self.count == 0:
            return self.cells
        else:
            return set()

    def mark_mine(self, cell):
        """
        Updates internal knowledge representation given the fact that
        a cell is known to be a mine.
        """
        if cell in self.cells:
            self.cells.remove(cell)
            self.count -= 1

    def mark_safe(self, cell):
        """
        Updates internal knowledge representation given the fact that
        a cell is known to be safe.
        """
        if cell in self.cells:
            self.cells.remove(cell)


class MinesweeperAI():
    """
    Minesweeper game player
    """

    def __init__(self, height=8, width=8):

        # Set initial height and width
        self.height = height
        self.width = width

        # Keep track of which cells have been clicked on
        self.moves_made = set()

        # Keep track of cells known to be safe or mines
        self.mines = set()
        self.safes = set()

        # List of sentences about the game known to be true
        self.knowledge = []

    def mark_mine(self, cell):
        """
        Marks a cell as a mine, and updates all knowledge
        to mark that cell as a mine as well.
        """
        self.mines.add(cell)
        for sentence in self.knowledge:
            sentence.mark_mine(cell)

    def mark_safe(self, cell):
        """
        Marks a cell as safe, and updates all knowledge
        to mark that cell as safe as well.
        """
        self.safes.add(cell)
        for sentence in self.knowledge:
            sentence.mark_safe(cell)

    def add_knowledge(self, cell, count):
        """
        Called when the Minesweeper board tells us, for a given
        safe cell, how many neighboring cells have mines in them.

        This function should:
            1) mark the cell as a move that has been made
            2) mark the cell as safe
            3) add a new sentence to the AI's knowledge base
               based on the value of `cell` and `count`
            4) mark any additional cells as safe or as mines
               if it can be concluded based on the AI's knowledge base
            5) add any new sentences to the AI's knowledge base
               if they can be inferred from existing knowledge
        """
        # task-1
        self.moves_made.add(cell)

        # task-2
        self.mark_safe(cell)

        # task-3
        neighbors = set()
        row = cell[0]
        col = cell[1]
        if row-1>=0:
            if (row-1,col) in self.mines:
                count -= 1
            elif (row-1,col) not in self.safes:
                neighbors.add((row-1,col))

        if row+1<self.height:
            if (row+1,col) in self.mines:
                count -= 1
            elif (row+1,col) not in self.safes:
                neighbors.add((row+1,col))

        if col-1>=0:
            if (row,col-1) in self.mines:
                count -= 1
            elif (row,col-1) not in self.safes:
                neighbors.add((row,col-1))
        
        if col+1<self.width:
            if (row,col+1) in self.mines:
                count -= 1
            elif (row,col+1) not in self.safes:
                neighbors.add((row,col+1))

        formed_sentence = Sentence(neighbors, count)
        self.knowledge.append(formed_sentence)

        # task-4
        while True:
            flag = False
            safe_set = set()
            mine_set = set()
            for sentence in self.knowledge:
                safe_set = safe_set.union(sentence.known_safes())
                mine_set = mine_set.union(sentence.known_mines())
                # if len(sentence.known_safes()) != 0:
                #     safe_set = safe_set.union(sentence.known_safes())
                # if len(sentence.known_mines()) != 0:
                #     mine_set = mine_set.union(sentence.known_mines())

            if len(safe_set) != 0:
                for x in safe_set:
                    self.mark_safe(x)
                flag = True
            
            if len(mine_set) != 0:
                for x in mine_set:
                    self.mark_mine(x)
                flag = True
            
            #remove empty sentences from knowledge base
            for sentence in self.knowledge:
                if len(sentence.cells) == 0:
                    self.knowledge.remove(sentence)

            for first_sentence in self.knowledge:
                for second_sentence in self.knowledge:
                    if first_sentence != second_sentence:
                        if first_sentence.cells.issubset(second_sentence.cells):
                            new_cells = second_sentence.cells - first_sentence.cells
                            new_count = second_sentence.count - first_sentence.count
                            new_sentence = Sentence(new_cells, new_count)
                            if new_sentence not in self.knowledge:
                                self.knowledge.append(new_sentence)
                                flag = True

            if(flag == False):
                break                    

        print("known mines:", self.mines)
        print("known safes:", self.safes-self.moves_made)



    def make_safe_move(self):
        """
        Returns a safe cell to choose on the Minesweeper board.
        The move must be known to be safe, and not already a move
        that has been made.

        This function may use the knowledge in self.mines, self.safes
        and self.moves_made, but should not modify any of those values.
        """
        moveableSafes = self.safes - self.moves_made
        #print("moveableSafes:", moveableSafes)
        if len(moveableSafes) == 0:
            return None
        else:
            x = random.choice(list(moveableSafes))
            print("safe Clicked:", x)
            return x

    def make_random_move(self):
        """
        Returns a move to make on the Minesweeper board.
        Should choose randomly among cells that:
            1) have not already been chosen, and
            2) are not known to be mines
        """
        moveableCells = set()
        for row in range(self.height):
            for col in range(self.width):
                if (row,col) not in self.moves_made and (row,col) not in self.mines:
                    moveableCells.add((row,col))
        #print("moveableCells:", moveableCells)
        if len(moveableCells) == 0:
            return None
        else:
            x = random.choice(list(moveableCells))
            print("random Clicked:", x)
            return x
