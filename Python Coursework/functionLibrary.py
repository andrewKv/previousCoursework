#~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Functional~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~#
def factorial(n):
    if n == 1:
        return n
    else:
        return (n * factorial(n-1))

def indexLen(l):
    return (len(l)-1)
    
#~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Graphics~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~#

def pause(win):
    win.getMouse()
    win.close()

def distanceBetweenPointsFormat(p1,p2):
    p1 = p1.getX(), p1.getY()
    p2 = p2.getX(), p2.getY()
    return (distanceBetweenPoints(p1,p2))
    
def distanceBetweenPoints(p1,p2):
    return ((((int(p2[0]) - int(p1[0]))**2) + ((int(p2[1]) - int(p1[1]))**2))**0.5)
    
def drawCircle(win, centre, radius, colour):
    circle = Circle(centre, radius)
    circle.setFill(colour)
    circle.setWidth(2)
    circle.draw(win)

def drawColouredEye(win, centre, radius, colour):
    colourList = ["white",colour,"black"]
    for i in range (3):
        colour = colourList[i]
        drawCircle(win, centre, radius, colour)
        radius = radius / 2



