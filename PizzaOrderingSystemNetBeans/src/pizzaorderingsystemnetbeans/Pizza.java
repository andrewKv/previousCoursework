package pizzaorderingsystemnetbeans;

import java.awt.*;

/**
 * Class to represent a single pizza.
 *
 * @author UP855834
 */
public class Pizza {

    private Canvas canvas;
    private double topLeftX;
    private double topLeftY;
    private int pizzaNum;
    private String topping1;
    private String topping2;
    private String pSize;
    private String pCrust;
    private boolean sauce;

    /**
     * Constructor for Pizza class
     *
     * @param win the window to draw the pizza on
     * @param count the order Number of the pizza
     * @param startX the top-left x coordinate for the section of screen to draw
     * pizza on
     * @param startY the top-left y coordinate for the section of screen to draw
     * pizza on
     * @param size the size of the pizza
     * @param crust the crust type of the pizza
     * @param BBQsauce does the customer want BBQ sauce?
     * @param t1 topping 1
     * @param t2 topping 2
     */
    public Pizza(Canvas win, int count, double startX, double startY, String size, String crust, boolean BBQsauce, String t1, String t2) {
        canvas = win;
        pizzaNum = count;
        topLeftX = startX * 300;
        topLeftY = startY * 300;
        pSize = size;
        pCrust = crust;
        sauce = BBQsauce;
        topping1 = t1;
        topping2 = t2;

    }

    /**
     * Accessor method for the top-left x coordinate for the section a pizza is
     * drawn on
     *
     * @return top-left x coordinate for the section a pizza is drawn on as a
     * double
     */
    public double getX() {
        return topLeftX;
    }

    /**
     * Accessor method for the top-left y coordinate for the section a pizza is
     * drawn on
     *
     * @return top-left y coordinate for the section a pizza is drawn on as a
     * double
     */
    public double getY() {
        return topLeftY;
    }

    /**
     * Mutator method for the top-left x coordinate for the section a pizza is
     * drawn on
     *
     * @param newX the new x coordinate
     */
    public void setX(double newX) {
        topLeftX = newX;
    }

    /**
     * Mutator method for the top-left y coordinate for the section a pizza is
     * drawn on
     *
     * @param newY the new y coordinate
     */
    public void setY(double newY) {
        topLeftY = newY;
    }

    /**
     * Mutator method for the pizza number
     *
     * @param newNum the new number for the pizza
     */
    public void setNum(int newNum) {
        pizzaNum = newNum;
    }

    /**
     * Method to draw the base of the pizza
     */
    private void drawBase() {
        canvas.setForegroundColor(Color.YELLOW);
        canvas.fillCircle(topLeftX + 150, topLeftY + 150, 200);
    }

    /**
     * Method to draw the first topping(5 pieces) to a pizza
     */
    public void drawTopping1() {
        Topping topping = new Topping(canvas, topLeftX, topLeftY);
        if (topping1.equals("mushroom")) {
            topping.drawGridOf5("mushroom");
        } else if (topping1.equals("prawn")) {
            topping.drawGridOf5("prawn");
        }

    }

    /**
     * Method to draw the second topping(4 pieces) to a pizza
     */
    public void drawTopping2() {
        Topping topping = new Topping(canvas, topLeftX, topLeftY);
        if (topping2.equals("mushroom")) {
            topping.drawGridOf4("mushroom");
        } else if (topping2.equals("prawn")) {
            topping.drawGridOf4("prawn");
        }

    }

    /**
     * Method to draw sauce on pizza
     */
    public void drawSauce() {
        if (sauce) {
            canvas.setForegroundColor(Color.ORANGE);
        } else {
            canvas.setForegroundColor(Color.RED);
        }

        canvas.fillCircle(topLeftX + 150, topLeftY + 150, 180);
        canvas.setForegroundColor(Color.WHITE);
        canvas.fillCircle(topLeftX + 150, topLeftY + 150, 160);
    }

    /**
     * Method to display the pizza information on the screen.
     */
    public void displayPizza() {
        drawBase();
        drawSauce();
        drawTopping1();
        drawTopping2();
        drawTopLine();
        drawBottomLine();
    }

    /**
     * Method to write the information shown in the bottom line of the
     * individual pizza on the screen. This method displays the pizza number and
     * size at the top of the screen
     */
    private void drawTopLine() {
        String topLine = "Pizza " + pizzaNum + " (" + pSize + ")";

        double stringX = topLeftX + 10;
        double stringY = topLeftY + 25;

        canvas.setForegroundColor(Color.BLACK);
        canvas.setFontSize(15);
        canvas.drawString(topLine, stringX, stringY);
    }

    /**
     * Method to write the information shown in the bottom line of the
     * individual pizza on the screen. This method displays the type of crust
     * and sauce ordered
     */
    private void drawBottomLine() {
        String pSauce;
        if (sauce) {
            pSauce = "BBQ sauce";
        } else {
            pSauce = "tomato sauce";
        }
        String bottomLine = "Crust: " + pCrust + ", " + pSauce;
        double stringX = topLeftX + 10;
        double stringY = topLeftY + 290;
        canvas.setForegroundColor(Color.BLACK);
        canvas.setFontSize(15);
        canvas.drawString(bottomLine, stringX, stringY);
    }

    /**
     * Method for calculating the cost of a single pizza
     *
     * @return cost of pizza as double
     */
    public double calculateCostOfPizza() {
        double totalCost = 0;
        int count = 0;
        double crustCost = 0;
        String[] nameList = {"deep pan", "thin crust", "stuffed crust", "small", "medium", "large"};
        double[] priceList = {0.11, 0.08, 0.14, 10, 12, 14};
        //Pizza base cost
        for (String i : nameList) {
            if (count <= 2) {
                if (pCrust.equals(i)) {
                    crustCost += priceList[count];
                }
            }
            if (pSize.equals(i)) {
                totalCost += crustCost * (Math.PI * Math.pow((priceList[count] / 2), 2));
            }
            count += 1;
        }
        //Sauce cost
        if (sauce) {
            totalCost += 0.5;
        }
        //Toppings Cost
        if (topping1.equals("mushroom")) {
            totalCost += 0.05 * 5; //Enums?
        } else if (topping1.equals("prawn")) {
            totalCost += 0.06 * 5;
        }
        if (topping2.equals("mushroom")) {
            totalCost += 0.05 * 4;
        } else if (topping2.equals("prawn")) {
            totalCost += 0.06 * 4;
        }
        return totalCost;
    }

}
