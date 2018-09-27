package pizzaorderingsystemnetbeans;

import java.util.ArrayList;

/**
 * Class to hold a collection of Pizza objects
 *
 * @author UP855834
 */
public class PizzaCollection {

    private ArrayList<Pizza> pizzaList;

    /**
     * Constructor for PizzaCollection
     */
    public PizzaCollection() {
        pizzaList = new ArrayList<>();
    }

    /**
     * Method to add a pizza to the collection
     *
     * @param p the pizza to be added
     */
    public void add(Pizza p) {
        pizzaList.add(p);
    }

    /**
     * Method to replace an existing pizza with a new pizza
     *
     * @param pNum the position of the pizza in the collection
     * @param newPizza the pizza that will replace the pizza at this position
     */
    public void replaceByNum(int pNum, Pizza newPizza) {
        pizzaList.set(pNum, newPizza);
    }

    /**
     * Method to remove pizza from list by number and shift all following pizzas
     * up one on the screen
     *
     * @param pNum Position of pizza in array to be removed
     */
    public void removeByNum(int pNum) {
        int removedPosition = pNum;
        pizzaList.remove(pNum);
        for (int i = removedPosition; i < (pizzaList.size()); i++) {
            Pizza p = pizzaList.get(i);
            double x = p.getX();
            double y = p.getY();

            if (y == 300 && x == 0) {
                x = 600;
                y = 0;
            } else if (y == 0 && x == 0) {
                x = 600;
                y = 300;
            } else {
                x -= 300;
            }
            p.setX(x);
            p.setY(y);
            p.setNum(i + 1);
        }

    }

    /**
     * Method to return the total price of each pizza in the collection
     *
     * @return total order price as double
     */
    public double getOrderCost() {
        double orderCost = 0;
        for (Pizza p : pizzaList) {
            orderCost += p.calculateCostOfPizza();
        }
        return orderCost;
    }

    /**
     * Method to obtain the top-left x coordinate of section the pizza is drawn
     *
     * @param position the position of the pizza in the collection
     * @return double, the top-left x coordinate of section the pizza is drawn
     */
    public double getPizzaX(int position) {
        Pizza p = pizzaList.get(position);
        return p.getX() / 300;

    }

    /**
     * Method to obtain the top-left y coordinate of section the pizza is drawn
     *
     * @param position the position of the pizza in the collection
     * @return double, the top-left y coordinate of section the pizza is drawn
     */
    public double getPizzaY(int position) {
        Pizza p = pizzaList.get(position);
        return p.getY() / 300;

    }

    /**
     * Method pizzas up to the specified pizza number(inclusive)
     *
     * @param maxPizzaNum the latest number of the pizza added
     */
    public void drawAllPizzas(int maxPizzaNum) {
        int start = maxPizzaNum - (maxPizzaNum % 6);
        for (int i = start; i <= maxPizzaNum; i++) {
            Pizza p = pizzaList.get(i);
            p.displayPizza();
        }
    }
}
