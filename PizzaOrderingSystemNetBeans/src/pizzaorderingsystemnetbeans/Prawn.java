package pizzaorderingsystemnetbeans;

import java.awt.Color;

/**
 * Class to hold the prawn topping
 * 
 * @author UP855834
 */
public class Prawn extends Topping {
    
    /**
     * Method to draw the prawn ingredient
     *
     * @param canvas window to draw the prawn onto
     * @param xPos x circle centre 
     * @param yPos y circle centre
     */
    public Prawn(Canvas canvas, double xPos, double yPos) {
        super(canvas, xPos + 16, yPos + 13);
    }
    /**
     * Method to draw a prawn to the screen
     */
    public void draw() {
        double [] xPoints = {xPos + 8, xPos + 13, xPos + 11, xPos - 5, xPos + 15, xPos + 20, xPos + 5};
        double [] yPoints = {yPos + 6, yPos + 9, yPos + 20, yPos + 25, yPos + 23, yPos + 5, yPos -8};
        
        canvas.setForegroundColor(Color.PINK);
        canvas.fillPolygon(xPoints, yPoints);
        canvas.fillCircle(xPos, yPos, 20);
  
    }
}


