package pizzabar;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class UserInterface {
  public void printMainMenu () {
    System.out.println("Select an action: \n1. Make Order\n2. Edit Order\n3. Exit");
  }

  public void printMainMenuCommand (String string) {
    System.out.println("You have selected: "+string);
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
      returnStr.append(String.format("%2d - %-20s' %-40s' %4sKr\n", (i + 1), pizza.getName(), pizza.getDescription(), pizza.getPrice()));
    }
    
    System.out.println(returnStr);
  }
  
  public void printMenuToppings(Menu menu) {
    ArrayList<Topping> toppings = menu.getToppings();
    StringBuilder returnStr = new StringBuilder();
    
    // toppings
    returnStr.append("TOPPINGS\n");
    for (int i = 0; i < toppings.size(); i++) {
      Topping topping = toppings.get(i);
      returnStr.append(String.format("%2d - %-62s' %4sKr\n", (i + 1), topping.getName(), topping.getPrice()));
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
      returnStr.append(String.format("\nPICKUP-TIME: %s\n", timeFormat(order.getPickupTime())));
    
    System.out.println(returnStr);
  }
  
  
  public void printOrderList(OrderList orderList) {
    StringBuilder returnStr = new StringBuilder();
    ArrayList<Order> orders = orderList.getOrders();
    Order order;
    
    // orders
    for (int i = 0; i < orders.size(); i++) {
      order = orders.get(i);
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
      
      returnStr.append(String.format("ORDER[%2d] PICKUP-TIME %s      TOTAL: %5dkr \n", order.getId(), timeFormat(order.getPickupTime()), order.getTotalPrice()));
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
  
}
