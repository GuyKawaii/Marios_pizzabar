package pizzabar;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class UserInterface {
  private final String TEXT_RESET = "\u001B[0m";
  private final String TEXT_RED = "\u001B[31m";
  private final String TEXT_GREEN = "\u001B[32m";

  public void printMainMenu () {
    System.out.println("--------------------------------------------------------------------------------------------");
    System.out.print("\n1. Make Order\n2. Edit Order\n3. Show Order list\n4. Show Full Order List\n5. Exit\nSelect an action: \n");
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
  
  public void printOrder(Order order) {
    StringBuilder returnStr = new StringBuilder();
    
    // Order entries
    for (int i = 0; i < order.getPizzaTypes().size(); i++) {
      Pizza pizza = order.getPizzaTypes().get(i);
      int price = pizza.getPrice() * order.getAmountOfPizzaTypes().get(i);
      
      returnStr.append(String.format("ID[%2d] - %2d X '%-40s' %4dkr\n",
          (i + 1),
          order.getAmountOfPizzaTypes().get(i),
          pizza.getNameAndTopping(),
          price));
    }
    
    // Total
    returnStr.append(String.format("TOTAL: %54dkr", order.getTotalPrice()));
    // Pickup-time
    if (order.getPickupTime() != null)
      returnStr.append(String.format("\nPICKUP-TIME: %s", timeFormat(order.getPickupTime()))); //fjernet \n efter %s
    
    System.out.println(returnStr);
  }

  public void printOrderLite(Order order) {
    StringBuilder returnStr = new StringBuilder();

    // Order entries
    for (int i = 0; i < order.getPizzaTypes().size(); i++) {
      Pizza pizza = order.getPizzaTypes().get(i);
      int price = pizza.getPrice() * order.getAmountOfPizzaTypes().get(i);

      returnStr.append(String.format("ID[%2d] - %2d X '%-40s' %4dkr\n",
          (i + 1),
          order.getAmountOfPizzaTypes().get(i),
          pizza.getNameAndTopping(),
          price));
    }
    System.out.println("---------------------------------------------------------------------------------------------");
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
      }
      else {
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
    
    System.out.println(returnStr);
  }
  
  public String timeFormat(LocalDateTime localDateTime) {
    return String.format("%02d:%02d %s/%s",
        localDateTime.getHour(),
        localDateTime.getMinute(),
        localDateTime.getDayOfMonth(),
        localDateTime.getMonth());
  }
  public void printOrderListContinue() {
    System.out.println("Press enter to return to the main menu.");
  }
  public void printSelectOrder() {
    System.out.println("Type the ID of the order do you want to change the status of.");
  }
  public void printOrderOutOfRange() {
    System.out.println("You selected a number outside the range of order ID's.");
  }
  public void printSelectStatus(Order order) {
    printOrder(order);
    System.out.printf("STATUS: %s\n", order.getStatus());
    if (order.isPaid())
      System.out.printf((TEXT_GREEN + "Payment has been made.\n" + TEXT_RESET));
    else
      System.out.printf((TEXT_RED + "Payment has not been made.\n" + TEXT_RESET));
    System.out.printf("""
        
        You are changing the status of Order #%s.
        Type a number corresponding to the action you want to take.
        1 Pending
        2 Ready
        3 Delivered
        4 Paid
        5 Not paid
        6 Canceled
        7 Return to menu.
        """
        , order.getId());
  }
  public void printOrderStatusContinue() {
    System.out.println("""
        Do you want to change another order?
        1 Yes
        2 No
        """);
  }

  public void addPizzaToOrderMessage() {
    System.out.println("Which pizza would you like to add to order? (name/number)");
  }

  public void addPizzaToOrderSuccessMessage(Pizza pizza) {
    System.out.printf("\nYou have added %s to the order\n", pizza.getName());
  }

  public void addPizzaToOrderErrorMessage() {
    System.out.println("There is no such pizza!, try again");
  }

  public void toppingMenuMessage() {
    System.out.println("Would you like to add/remove a topping or continue with order? (add+name/remove+name/continue)");
  }

  public void addToppingErrorMessage() {
    System.out.println("There is no such topping!, try again");
  }

  public void removeToppingErrorMessage() {
    System.out.println("There is no such topping!, try again");
  }
}
