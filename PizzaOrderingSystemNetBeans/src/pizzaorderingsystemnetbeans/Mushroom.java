package pizzaorderingsystemnetbeans;

import java.awt.*;

/**
 * Class to hold the mushroom topping
 *
 * @author UP855834
 */
public class Mushroom extends Topping {

    /**
     * Constructor for Mushroom
     *
     * @param win the window mushroom will be drawn to
     * @param x top left x position mushroom will be drawn
     * @param y top left y position mushroom will be drawn
     */
    public Mushroom(Canvas win, double x, double y) {
        super(win, x, y);
    }

    /**
     * Method to draw the mushroom ingredient
     *
     * @param fill colour of mushroom
     * @param x top left x-coordinate of mushroom
     * @param y top left y-coordinate of mushroom
     * @param width width of mushroom
     * @param height height of mushroom
     */
    public void drawMushroom(Color fill, double x, double y, int width, int height) {
        canvas.setForegroundColor(fill);

        canvas.fillSemiCircle(x, y, width, height, false, true);
        canvas.fillRectangle(x + width / 2 - width / 10, y + height / 2, width / 5, height / 1.5);

    }

    /**
     * Method to draw a single mushroom to the screen
     */
    public void draw() {
        drawMushroom(Color.LIGHT_GRAY, xPos, yPos, 40, 30);
        drawMushroom(Color.GRAY, xPos + 7.75, yPos + 3, 25, 20);
    }
}
