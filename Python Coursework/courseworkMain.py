from graphics import *
#~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Patches code~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~#

def drawBrick(win, p1, p2, colour):
    r = Rectangle(p1, p2)
    r.setFill(colour)
    r.draw(win)
    
def drawPatch3(win, colour, x, y):
    tempColour = "white"  
      
    for column in range(10):
        y1 = y 
        if column % 2 == 0:
            y2 = y + 10 
            if tempColour == colour:
                tempColour = "white"
            else:
                tempColour = colour
        else:
            y2 = y + 20
        
        for row in range(0, 100, 20):
            p1 = Point((x + column * 10), y1)
            p2 = Point(x+((column+1) * 10), y2)
            drawBrick(win, p1 ,p2 , tempColour)
            y1 = y2
            y2 += 20
            
            if str(y2)[-2] == "9" and column % 2 == 0: # Small box needed at y = 90, 190, 290 etc...
                drawBrick(win, Point(x+(column*10), (y+90)),Point(x+((column+1)*10), (y+100)), tempColour)
    
    
def drawPatch4(win, colour, x, y):
    colourOn = True
    square = Rectangle(Point(x,y),Point(x + 100, y + 100))
    square.setFill("white")
    square.setWidth(0)
    square.draw(win)
    
    for y in range(y + 10, y + 100, 20):
        
        for centre in range(x + 10, x + 100, 20):
            c = Circle(Point(centre,y),10)
            if colourOn:
                c.setFill(colour)
                
            c.draw(win)
            
        colourOn = not colourOn

#~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Patches code~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~#

#~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Initial task~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~#

def getInputs():
    finished = False
    availableSizes = [5,7,9,11]
    availableColours = ["red", "green", "blue", "magenta", "cyan", "orange", "brown", "pink"]
    colourList = []
    
    while not finished:
        size = input("Enter patchwork size: ")
        try:
            size = int(size)
            if size in availableSizes:
            
                while True:
                    colour = input("Enter colour: ").lower()
                    if colour in availableColours:  
                        if colour not in colourList:
                            colourList.append(colour)
                            if len(colourList) == 3:
                                finished = True
                                break
                        else:
                            print("Colour already chosen")
                    else:
                        print("Invalid colour")
            else:
                print("Invalid size")
                
        except Exception:
            print("Invalid data type")
        
    return size, colourList


def drawGrid(patchworkSize, colourList):
    win = GraphWin("Patchwork Coursework", patchworkSize, patchworkSize)
    colourCount, design = 0,0
    grid = []

    for x in range(0, patchworkSize, 100):
        
        for y in range(0, patchworkSize, 100):
            if colourCount > (len(colourList)-1):
                colourCount = 0 # Cycles through colourList
                
            colour = colourList[colourCount]        
            if x  >= patchworkSize - 300 and y <= 200:
                drawPatch4(win, colour, y, x) # Draws by row, not by column 
                design = 1
            else:
                drawPatch3(win, colour, y, x)
                design = 0
                
            grid.append([y//100, x//100, colourCount, design]) 
            colourCount += 1
            
    return win, grid
    
#~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Initial task~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~#

#~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Advanced feature~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~#

def updateGrid(grid, patch, colourList):
    
    for square in grid:
        if square[0] == patch[0] and square[1] == patch[1]:
            grid[grid.index(square)] = [patch[0], patch[1], colourList.index(patch[2]), patch[3]]
            
    return grid


def swapSquares(win, grid, colourList):
    
    while True:
        clicks = []
        colourDesignList = []
        newPatchList = []
        
        while len(clicks) < 2:
            pos = win.getMouse()
            clicks.append([(int(pos.getX() // 100)), int((pos.getY() // 100))])

        for square in grid:
            
            for point in clicks:
                if square[0] == point[0] and square[1] == point[1]:
                    colourDesignList.append([colourList[square[2]], square[3]]) # Creates list of design and colour in order of occurence in grid
                
        
        if clicks[0] == clicks[1]:
            if colourDesignList[0][1] == 0:
                newPatchList.append([clicks[0][0], clicks[0][1], colourDesignList[0][0], 1]) # 0 design = 1 design
                drawPatch4(win, newPatchList[0][2], newPatchList[0][0] * 100, newPatchList[0][1] * 100)
                
            else:
                newPatchList.append([clicks[0][0], clicks[0][1], colourDesignList[0][0], 0]) # 1 design = 0 design
                drawPatch3(win, newPatchList[0][2], newPatchList[0][0] * 100, newPatchList[0][1] * 100)
                
        else:
            clicks.sort() # So clicks are ordered by position in grid - this allows colourDesignList to be directly compared
            if clicks[0][1] > clicks[1][1]:
                clicks.reverse()
            newPatchList.append([clicks[0][0], clicks[0][1], colourDesignList[1][0], colourDesignList[1][1]])
            newPatchList.append([clicks[1][0], clicks[1][1], colourDesignList[0][0], colourDesignList[0][1]])
            if newPatchList[0][3] == 0: #0 design = patch 3, 1 design = patch 4
                drawPatch3(win, newPatchList[0][2], newPatchList[0][0] * 100, newPatchList[0][1] * 100)
                
            else:
                drawPatch4(win, newPatchList[0][2], newPatchList[0][0] * 100, newPatchList[0][1] * 100)
            
            if newPatchList[1][3] == 0:
                drawPatch3(win, newPatchList[1][2], newPatchList[1][0] * 100, newPatchList[1][1] * 100)
                
            else:
                drawPatch4(win, newPatchList[1][2], newPatchList[1][0] * 100, newPatchList[1][1] * 100)
                
        for patch in newPatchList:
            grid = updateGrid(grid, patch, colourList)
        
#~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Advanced feature~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~#

def main():
    patchworkSize, colourList = getInputs()
    win, grid = drawGrid(patchworkSize*100, colourList)
    swapSquares(win, grid, colourList)
    
main()