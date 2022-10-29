'''
Created on Nov 29, 2018

@author: Anthony Bruno

'''

class Board(object):
    def __init__(self,width=7, height = 6):
        """Constructor for board."""
        self.__width = width
        self.__height = height
        
        
        self.__board = []
        
        for row in range(height):
            row = []
            for col in range(width):
                row += [" "]
            self.__board.append(row)
        
        
            
    def __str__(self):
        """Compiles the board to later be printed."""
        board = ""
        for x in range(self.__height):
            row = "|"
            for y in range(self.__width):
                row += self.__board[x][y] + "|" 
            board += row
            board += "\n"
            
            
        board +=  "-" * len(row)
        board += "\n"
        for i in range(7):
            board += str(i) + " "
        return board
    
    def allowsMove(self,col):
        """Checks if a user stated move is allowed."""
        if col not in range(self.__width):
            return False
        
        i = 0
        for x in range(self.__height):
            if self.__board[x][col] != " ":
                i+=1
                
        if i == self.__height:
            return False
        else:
            return True
            
    def addMove(self,col,ox):
        """Adds a move to the top most part of a row."""
        i = 0
        for x in range(self.__height):
            if self.__board[x][col] == " ":
                i += 1
        
        self.__board[i-1][col] = ox
        
        
    def setBoard(self,moveString):
        """ takes in a string of columns and places alternating checkers in those columns, starting with 'X' 
        For example, call b.setBoard('012345') to see 'X's and 'O's alternate on the bottom row, or b.setBoard('000000') to 
        see them alternate in the left column. moveString must be a string of integers""" 
        nextCh = 'X'   # start by playing 'X'         
        for colString in moveString:             
            col = int(colString)             
            if 0 <= col <= self.width:                 
                self    .addMove(col, nextCh)             
            if nextCh == 'X': 
                nextCh = 'O'             
            else: 
                nextCh = 'X' 
        
    def delMove(self,col):
        """Deletes the move of the top most piece in a column"""
        i = 0
        for x in range(self.__height):
            if self.__board[x][col] == " ":
                i+=1
                
        self.__board[i][col] = " "
        
        
        
         
    def winsFor(self,ox):
        """Checks if there a combination of 4 Xs or Os in a horizontal, vertical or diagnol pattern."""
        #Vertical
        for col in range(self.__width):
            checker = 0
            for row in range(self.__height):
                if self.__board[row][col] == ox:
                    checker += 1
                else:
                    checker = 0
                    
                if checker == 4:
                    return True
                    
        #Horizontal
        for row in range(self.__height):
            checker2 = 0
            for col in range(self.__width):
                if self.__board[row][col] == ox:
                    checker2 += 1
                else:
                    checker2 = 0
                    
                if checker2 == 4:
                    return True
        
        
        # Major Diagnols
        for row in range(self.__height-3):
            checker3 = 0
            for col in range(self.__width-3):
                    if self.__board[row][col] == ox:
                        checker3 += 1
                        if self.__board[row+1][col+1] == ox:
                            checker3 += 1
                        if self.__board[row+2][col+2] == ox:
                            checker3 += 1
                        if self.__board[row+3][col+3] == ox:
                            checker3 += 1
                    if checker3 == 4:
                        return True
                    else:
                        checker3 = 0
        
        #Minor Diagnols
        for row in range(self.__height-3):
            checker4 = 0
            for col in range(3,self.__width):
                    if self.__board[row][col] == ox:
                        checker4 += 1
                        if self.__board[row+1][col-1] == ox:
                            checker4 += 1
                        if self.__board[row+2][col-2] == ox:
                            checker4 += 1
                        if self.__board[row+3][col-3] == ox:
                            checker4 += 1
                         
                    if checker4 == 4:
                        return True
                    else:
                        checker4 = 0
                    
    def hostgame(self):
        """Hosts the game and compiles all the functions."""
        print("Welcome to Connect Four!")
        while True:
            print(self)
            player1 = input("X's choice: ")
            
            if self.allowsMove(int(player1)):
                self.addMove(int(player1), "X")
            else:
                print("Sorry, that operation isn't allowed, you lose a turn")
            
            if self.winsFor("X") == True:
                print()
                print("X wins -- Congratulations!")
                print(self)
                break    
            print(self)
            player2 = input("O's choice: ")
            
            if self.allowsMove(int(player2)):
                self.addMove(int(player2), "O")
            else:
                print("Sorry, that operation isn't allowed, you lose a turn")
            if self.winsFor("O") == True:
                print()
                print("O wins -- Congratulations!") 
                print(self)
                break
            
b = Board()
b.hostgame()
