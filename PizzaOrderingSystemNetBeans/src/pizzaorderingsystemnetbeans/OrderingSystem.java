package pizzaorderingsystemnetbeans;

import java.awt.*;
import java.text.DecimalFormat;

/**
 * Class to manage the pizza order.
 *
 * @author UP855834
 */
public class OrderingSystem {

    private Canvas canvas;
    // private KeyboardInput input;
    private DecimalFormat TwoDP = new DecimalFormat(".##");

    /**
     * Constructor for the ordering system.
     */
    public OrderingSystem() {
        canvas = new Canvas("Pizza Ordering", 900, 650);
    }

    /**
     * Method to draw the outline of the order screen.
     */
    public void drawOrderScreen() {
        canvas.setForegroundColor(Color.WHITE);
        canvas.fillRectangle(0, 0, 900, 650);
        canvas.setForegroundColor(Color.BLACK);
        // vertical dividers
        canvas.drawLine(300, 0, 300, 600);
        canvas.drawLine(600, 0, 600, 600);

        // halfway divider
        canvas.drawLine(0, 300, 900, 300);

        // total price line
        canvas.drawLine(0, 600, 900, 600);

    }

    /**
     * Method to draw the total price of the order to screen
     *
     * @param newPrice cumulative price of pizza
     */
    public void drawPrice(double newPrice) {
        canvas.setFontSize(25);
        canvas.setForegroundColor(Color.WHITE);
        canvas.fillRectangle(0, 610, 800, 40);
        canvas.setForegroundColor(Color.BLACK);
        canvas.drawString("Total Price of the Order: £" + TwoDP.format(newPrice), 10, 640);
    }

    /**
     * Method to create a pizza
     *
     * @param count Pizza number displayed at the top
     * @param x xPosition of pizza (0,1,2)
     * @param y yPosition of pizza (0,1)
     * @return Pizza object
     */
    public Pizza makeNewPizza(int count, double x, double y) {
        InputValidation input = new InputValidation();
        String size = input.getValid3StringInput("Small", "Medium", "Large");
        String crust = input.getValid3StringInput("Deep pan", "Thin crust", "Stuffed crust");
        boolean BBQsauce = input.getBooleanInput(0);
        int toppingNo = input.getValidInteger(-1, 2, 0);
        String topping1 = "";
        String topping2 = "";

        for (int i = 0; i < toppingNo; i++) {
            if (i == 0) {
                topping1 = input.getValidTopping(i + 1);
            }
            if (i == 1) {
                topping2 = input.getValidTopping(i + 1);
            }
        }
        Pizza pizza = new Pizza(canvas, count, x, y, size, crust, BBQsauce, topping1, topping2);
        return pizza;
    }

    /**
     * Method to manage the ordering of the pizza, updates the screen as user
     * inputs order.
     */
    public void startOrdering() {
        int y = 0;
        double x = 0;
        int latestPizzaPosition = 0;
        InputValidation input = new InputValidation();
        PizzaCollection pizzaList = new PizzaCollection();

        do {
            // Make pizza
            System.out.println("New Pizza Details");
            Pizza pizza = makeNewPizza(latestPizzaPosition + 1, x, y);
            pizzaList.add(pizza);

            //Refresh to most recent screen after new pizza drawn
            int updatedScreenPosition = latestPizzaPosition;
            refreshScreen(latestPizzaPosition, pizzaList);

            // Additional options            
            do {

                //Check edit finished
                boolean moreEdits = input.getBooleanInput(1);
                if (moreEdits == false) {
                    break;
                }

                //List and receive edit order choices
                String choice = input.getValid3StringInput("Remove pizza", "Change pizza", "Change Page");

                if (choice.equals("remove pizza")) {
                    int pizzaToRemove = input.getValidInteger(0, latestPizzaPosition + 1, 1);
                    pizzaList.removeByNum(pizzaToRemove - 1);
                    latestPizzaPosition -= 1;
                    x -= 1;
                    if (x < 0) { //Reset x to the right after multiple removals
                        x = 2;
                        y ^= 1;
                    }

                } else if (choice.equals("change pizza")) {
                    int pizzaToReplace = input.getValidInteger(0, latestPizzaPosition + 1, 1) - 1;
                    System.out.println("Pizza replacement details: ");
                    Pizza newPizza = makeNewPizza(pizzaToReplace + 1, pizzaList.getPizzaX(pizzaToReplace),
                            pizzaList.getPizzaY(pizzaToReplace));
                    pizzaList.replaceByNum(pizzaToReplace, newPizza);

                } else if (choice.equals("change page")) {
                    int pageNo = input.getValidInteger(0, ((latestPizzaPosition) / 6) + 1, 2);
                    updatedScreenPosition = (pageNo * 6) - 1; //Last pizza position on this page
                }
                //Check if an incomplete page is selected
                if (updatedScreenPosition > latestPizzaPosition) {
                    updatedScreenPosition = latestPizzaPosition;
                }

                //Refresh screen to show edits 
                refreshScreen(updatedScreenPosition, pizzaList);
            } while (true);

            // Move to next pizza window
            x += 1;
            latestPizzaPosition += 1;
            if (x > 2) {
                x = 0; //Reset x to the left
                y ^= 1; //Flip y between 0 and 1
            }

            //Check order ended
            boolean endOrder = input.getBooleanInput(2);
            if (endOrder == true) {
                break;
            }
        } while (true);
    }

    /**
     * Method to refresh the screen
     *
     * @param pizzaNum the number of the last pizza in the grid
     * @param pizzaList the list of pizzas
     */
    public void refreshScreen(int pizzaNum, PizzaCollection pizzaList) {
        drawOrderScreen();
        pizzaList.drawAllPizzas(pizzaNum);
        drawPrice(pizzaList.getOrderCost());
    }
}
