package pizzabar;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class UserInterface {
  private final String TEXT_RESET = "\u001B[0m";
  private final String TEXT_RED = "\u001B[31m";
  private final String TEXT_GREEN = "\u001B[32m";
  Scanner in = new Scanner(System.in);

  public void printMainMenu() {
    System.out.println("--------------------------------------------------------------------------------------------");
    System.out.print("\n1. Make Order\n2. Edit Order\n3. Show Orders\n4. Exit\nSelect an action:");
  }

  public void printMenu(Menu menu) {
    printMenuPizzas(menu);
    printMenuToppings(menu);
  }
  
  public void printMenuPizzas(Menu menu) {
    ArrayList<Pizza> pizzas = menu.getPizzas();
    StringBuilder returnStr = new StringBuilder();
    
    // pizzas
    returnStr.append("PIZZAS\n");
    for (int i = 0; i < pizzas.size(); i++) {
      Pizza pizza = pizzas.get(i);
      returnStr.append(String.format("%2d - %-15s' %-61s' %4sKr\n", (i + 1), pizza.getName(), pizza.getDescription(), pizza.getPrice()));
    }
    
    System.out.println(returnStr);
  }
  
  public void printMenuToppings(Menu menu) {
    ArrayList<Topping> toppings = menu.getToppings();
    StringBuilder returnStr = new StringBuilder();
    
    // toppings
    returnStr.append("TOPPINGS - (price for extra topping)\n");
    for (int i = 0; i < toppings.size(); i++) {
      Topping topping = toppings.get(i);
      returnStr.append(String.format("%2d - %-78s' %4sKr\n", (i + 1), topping.getName(), topping.getPrice()));
    }
    
    System.out.println(returnStr);
  }
  
  public void printOrder(Order order, boolean printAll) {
    StringBuilder returnStr = new StringBuilder();
    
    // Order entries
    for (int i = 0; i < order.getPizzaTypes().size(); i++) {
      Pizza pizza = order.getPizzaTypes().get(i);
      int price = pizza.getPrice() * order.getAmountOfPizzaTypes().get(i);
      
      returnStr.append(String.format(" #[%2d] - %2d X '%-40s' %4dkr\n",
          (i + 1),
          order.getAmountOfPizzaTypes().get(i),
          pizza.getNameAndTopping(),
          price));
    }
    if (printAll) {
      // Total
      returnStr.append(String.format("TOTAL: %54dkr", order.getTotalPrice()));
      // Pickup-time
      if (order.getPickupTime() != null)
        returnStr.append(String.format("\nPICKUP-TIME: %s", timeFormat(order.getPickupTime()))); //fjernet \n efter %s
    }

    System.out.println();
    System.out.println(returnStr);
  }

  public void printOrderList(OrderList orderList, boolean printFullList) {
    StringBuilder returnStr = new StringBuilder();
    ArrayList<Order> orders = orderList.getOrders();
    Order order;
    
    // orders
    for (int i = 0; i < orders.size(); i++) {
      //print only if order status is neither paid nor canceled
      order = orders.get(i);
      if (!printFullList && order.getStatus().equals(String.valueOf(OrderStatuses.CANCELED)) || !printFullList && order.isPaid() && order.getStatus().equals(String.valueOf(OrderStatuses.DELIVERED))) {
      } else {
        returnStr.append("-".repeat(56) + "\n");

        // individual order
        // order entries
        for (int j = 0; j < order.getPizzaTypes().size(); j++) {
          Pizza pizza = order.getPizzaTypes().get(j);
          int price = pizza.getPrice() * order.getAmountOfPizzaTypes().get(j);

          returnStr.append(String.format("- %2d X '%-40s' %4dkr\n",
              order.getAmountOfPizzaTypes().get(j),
              pizza.getNameAndTopping(),
              price));
        }

        returnStr.append(String.format("ORDER[%2d] PICKUP-TIME %s      TOTAL: %5dkr \nSTATUS: %s\n",
            order.getId(), timeFormat(order.getPickupTime()), order.getTotalPrice(), order.getStatus()));
        if (order.isPaid())
          returnStr.append(String.format(TEXT_GREEN + "Payment has been made.\n" + TEXT_RESET));
        else
          returnStr.append(String.format(TEXT_RED + "Payment has not been made.\n" + TEXT_RESET));
      }
    }
    System.out.println();
    System.out.println(returnStr);
  }
  
  public String timeFormat(LocalDateTime localDateTime) {
    return String.format("%02d:%02d %s/%s",
        localDateTime.getHour(),
        localDateTime.getMinute(),
        localDateTime.getDayOfMonth(),
        localDateTime.getMonth());
  }

  public void printSelectOrderList() {
    System.out.printf("""
        
        Choose a number corresponding to the list you want to display.
        1. Current orders
        2. All orders
        """);
  }

  public void orderListContinueMessage() {
    System.out.println("Press enter to return to the main menu.");
  }

  public void selectOrderMessage() {
    System.out.println("Type the ID of the order do you want to change the status of.");
  }

  public void ChooseOrderInputErrorMessage() {
    System.out.println("Please only input numbers.");
  }

  public void orderOutOfRangeMessage() {
    System.out.println("You selected a number outside the range of order ID's.");
  }

  public void printSelectStatus(Order order) {
    printOrder(order, true);
    System.out.printf("STATUS: %s\n", order.getStatus());
    if (order.isPaid())
      System.out.printf((TEXT_GREEN + "Payment has been made.\n" + TEXT_RESET));
    else
      System.out.printf((TEXT_RED + "Payment has not been made.\n" + TEXT_RESET));
    System.out.printf("""
        
        Type a number corresponding to the action you want to take.
        1. Pending
        2. Ready
        3. Delivered
        4. Paid
        5. Not paid
        6. Canceled
        7. Return to menu.
        """
    );
  }

  public void addPizzaToOrderMessage() {
    System.out.println("PIZZA - Which pizza would you like to add to order? (name/number)");
  }

  public void addPizzaToOrderSuccessMessage(Pizza pizza) {
    System.out.printf("\nYou have added %s to the order\n", pizza.getName());
  }

  public boolean addPizzaToOrderErrorMessage() {
    System.out.println("There is no such pizza!, try again");
    return false;
  }

  public void toppingMenuMessage() {
    System.out.println("TOPPINGS - Would you like to add/remove a topping or continue with order? - [a]dd \"name/ID\", [r]emove \"name/ID\" or [Enter] to continue)");
  }

  public void addToppingErrorMessage() {
    System.out.println("There is no such topping!, try again");
  }

  public void removeToppingErrorMessage() {
    System.out.println("There is no such topping!, try again");
  }

  public void makeOrderMessage() {
    System.out.println("Do you want to add another pizza, or continue with order? (add/continue)");
  }

  public LocalDateTime pickupTimeMenu() {
    Integer hour = null;
    Integer min = null;

    // Until time found
    while (hour == null || min == null) {
      System.out.println("Pickup time for order? - (hh:mm or hh mm ex. 09:45, 09 45) or [enter] for 30 min from now");
      String userInput = in.nextLine();

      // custom time
      if (userInput.length() == 5) {
        hour = tryParseInteger(userInput.substring(0, 2));
        min = tryParseInteger(userInput.substring(3, 5));
      }

      // default time
      if (userInput.isEmpty()) return LocalDateTime.now().plusMinutes(30);

      // non-correct time
      if (hour == null || min == null) System.out.println("Time was not specified correctly");
    }

    // custom time
    return LocalDateTime.now().withHour(hour).withMinute(min);
  }

  public int pizzaQuantityMenu() {
    Integer quantity = null;
    System.out.println("QUANTITY? - [number] or [enter] for 1X)");

    // Until quantity found
    while (quantity == null) {
      String userInput = in.nextLine();

      // default quantity
      if (userInput.isEmpty()) return 1;

      // custom quantity
      quantity = tryParseInteger(userInput);

      // non-correct quantity
      if (quantity == null) System.out.println("Time was not specified correctly");
    }

    // custom quantity
    return quantity;
  }

  public Integer tryParseInteger(String text) {
    try {
      return parseInt(text);
    } catch (NumberFormatException e) {
      return null;
    }
  }



}
