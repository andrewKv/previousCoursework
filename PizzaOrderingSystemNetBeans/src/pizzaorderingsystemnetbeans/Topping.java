package pizzaorderingsystemnetbeans;

/**
 * Class for drawing pizza toppings
 *
 * @author UP855834
 */
public class Topping {

    protected Canvas canvas;
    protected double xPos;
    protected double yPos;

    /**
     * Constructor for Topping class
     *
     * @param win the window the topping is drawn to
     * @param x top left x-coordinate for the section of screen the pizza is
     * drawn on
     * @param y top left y-coordinate for the section of screen the pizza is
     * drawn on
     */
    public Topping(Canvas win, double x, double y) {
        canvas = win;
        xPos = x;
        yPos = y;
    }

    /**
     * Method to draw topping 1
     *
     * @param ingredient Which topping will be drawn
     */
    public void drawGridOf5(String ingredient) {
        double x = xPos + 90; // Starting x,y coordinates   
        double y = yPos + 90;
        int gap = 80;
        for (int i = 0; i < 5; i++) {
            if (i > 1 && i <= 3) {
                y += 45;          // Vertical distance between ingredients
            }
            if (i == 0 || i == 3) {
                x = xPos + 90;   // Horizontal distance between ingredients
            } else if (i == 2) {
                x -= gap / 2;
            } else {
                x += gap;
            }
            if (ingredient.equals("mushroom")) {
                Mushroom mushroom = new Mushroom(canvas, x, y);
                mushroom.draw();
            } else if (ingredient.equals("prawn")) {
                Prawn prawn = new Prawn(canvas, x, y);
                prawn.draw();
            }
        }
    }

    /**
     * Method to draw topping 2
     *
     * @param ingredient Which topping will be drawn
     */
    public void drawGridOf4(String ingredient) {

        double x = xPos + 75; // Starting x,y coordinates  
        double y = yPos + 70;
        int gap = 60;
        for (int i = 0; i < 4; i++) {
            if (i % 2 != 0) {
                y += 60;      //Vertical distance between ingredients
                x = xPos + 10;
            }
            if (i == 2) {
                gap *= 2;
            }
            x += gap;  //Horizontal distance between ingredients

            if (ingredient.equals("mushroom")) {
                Mushroom mushroom = new Mushroom(canvas, x, y);
                mushroom.draw();
            } else if (ingredient.equals("prawn")) {
                Prawn prawn = new Prawn(canvas, x, y);
                prawn.draw();
            }

        }

    }
}
