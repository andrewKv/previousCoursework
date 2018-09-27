package pizzaorderingsystemnetbeans;

/**
 * Class to store all input requests and validation methods
 *
 * @author UP855834
 */
public class InputValidation {

    private KeyboardInput input;

    /**
     * Constructor for InputValidation
     */
    public InputValidation() {
        input = new KeyboardInput();
    }
    
    /**
     * Method to obtain a valid 3-option choice string from the user
     *
     * @param s1 first string option
     * @param s2 second string option
     * @param s3 third string option
     * @return valid string option the user picked
     */
    public String getValid3StringInput(String s1, String s2, String s3) {
        do {
            System.out.print(s1 + ", " + s2 + ", or " + s3 + ": ");
            String userChoice = input.getInputString();
            if (userChoice.equalsIgnoreCase(s1) || userChoice.equalsIgnoreCase(s2)
                    || userChoice.equalsIgnoreCase(s3)) {
                return userChoice.toLowerCase();
            } else {
                System.out.println("Invalid Input");
            }
        } while (true);
    }

    /**
     * Method to get valid boolean choice
     *
     * @param questionNo the yes/no question identifier
     * @return boolean of yes or no
     */
    public boolean getBooleanInput(int questionNo) {
        do {
            if (questionNo == 0) {
                System.out.print("BBQ Sauce? (yes/no) ");
            } else if (questionNo == 1) {
                System.out.print("Edit order? (yes/no) ");
            } else if (questionNo == 2) {
                System.out.print("End order? (yes/no) ");
            }
            String boolChoice = input.getInputString().toLowerCase();
            if (boolChoice.equals("yes")) {
                return true;
            } else if (boolChoice.equals("no")) {
                return false;
            } else {
                System.out.println("Invalid Input");
            }
        } while (true);
    }

    /**
     * Method to get a valid integer value
     *@param questionNo which prompt is shown to the user
     * @param low low end of acceptable values (exclusive)
     * @param high high end of acceptable values (exclusive)
     * @return int appropriate number
     */
    public int getValidInteger(int low, int high, int questionNo) {
        do {
            if (questionNo == 0) {
                System.out.print("Number of Toppings (0,1,2): ");
            } else if (questionNo == 1){
                System.out.print("Pizza number: ");
            }
            else if (questionNo == 2){
                System.out.print("Page number: ");
            }
            int pizzaNum = input.getInputInteger();
            if (pizzaNum > low && pizzaNum <= high) {
                return pizzaNum;
            }
            System.out.println("Invalid number");
        } while (true);

    }

    /**
     * Method to get a valid topping choice from the user
     *
     * @param toppingNum the topping the user is on
     * @return String valid choice of topping
     */
    public String getValidTopping(int toppingNum) {
        do {
            System.out.print("Topping " + toppingNum + ": (Mushroom or Prawn) ");
            String toppingChoice = input.getInputString();
            if (toppingChoice.equalsIgnoreCase("mushroom")
                    || toppingChoice.equalsIgnoreCase("prawn")) {
                return toppingChoice.toLowerCase();
            } else {
                System.out.println("Invalid Input");
            }
        } while (true);
    }

   
}
