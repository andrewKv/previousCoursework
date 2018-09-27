from graphics import *
from functionLibrary import *

def drawPatch4(win,colour):
    colourOn = True
    for y in range(10,110,20):
        for centre in range(10,110,20):
            c = Circle(Point(centre,y),10)
            if colourOn:
                c.setFill(colour)
            c.draw(win)
        colourOn = not colourOn
    pause(win)