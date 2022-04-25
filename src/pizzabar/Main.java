package pizzabar;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class Main {
  UserInterface ui = new UserInterface();
  Scanner in = new Scanner(System.in);
  OrderList orderList = new OrderList();
  private Menu menu;
  
  public static void main(String[] args) {
    new Main().run();
//    new Test().runAll();
  }
  
  public boolean mainMenu(boolean loop) {
    ui.printMainMenu();
    String userInput = in.nextLine().toLowerCase(Locale.ROOT);
    switch (userInput.toLowerCase(Locale.ROOT)) {
      case "1", "m", "make order", "make" -> makeOrder();
      case "2", "e", "edit order", "edit" -> chooseOrder();
      case "3", "s", "show orders", "show" -> chooseList();
      case "4", "x", "exit" -> loop = false;
    }
    return loop;
  }
  
  void makeOrder() {
    boolean loop = true;
    Order order = new Order();
    addPizzaToOrder(order);
    while (loop) {
      
      ui.makeOrderMessage();
      String userInput = in.nextLine().toLowerCase();
      
      switch (userInput) {
        case "add", "a" -> addPizzaToOrder(order);
        case "remove", "r" -> ui.removePizzaFromOrder(order);
        case "" -> loop = false;
      }
      
    }
    
    // pickupTime
    LocalDateTime pickupTime = ui.pickupTimeMenu();
    order.setPickupTime(pickupTime);
    
    // Add finished order
    orderList.addOrder(order);
  }
  

  
  public void addPizzaToOrder(Order order) {
    // adding: customized pizzaType and its quantity to order
    Pizza pizza = null;
    int quantity;
    
    ui.addPizzaToOrderMessage();
    
    // Selecting pizza
    boolean noPizzaSelected = true;
    while (noPizzaSelected) {
      // UserInput
      String userInput = in.nextLine().toLowerCase();
      Integer pizzaID = tryParseInteger(userInput);
      
      // Select pizza by ID or name
      if (pizzaID != null)
        pizza = menu.getPizza(pizzaID);
      else
        pizza = menu.getPizza(userInput);
      
      // retry until pizza selected
      if (pizza != null) noPizzaSelected = false;
      else ui.addPizzaToOrderErrorMessage();
    }
    
    // Add toppings to current pizza
    toppingsMenu(pizza);
    
    // Select quantity of given pizza
    quantity = ui.pizzaQuantityMenu();
    
    // Add selected pizza to order
    order.addPizza(pizza, quantity);
    ui.addPizzaToOrderSuccessMessage(pizza);
    
    ui.printOrder(order, false);
  }
  
  public void toppingsMenu(Pizza pizza) {
    // modifies pizza by adding toppings
    boolean addingToppings = true;
    
    // Until chosen
    while (addingToppings) {
      ui.toppingMenuMessage();
      
      // get action and topping
      String[] userInput = in.nextLine().toLowerCase().split(" ", 2);
      String action = userInput[0];
      String topping = (userInput.length == 2) ? userInput[1] : null;
      
      // preform action
      switch (action) {
        case "add", "a" -> addTopping(topping, pizza);
        case "remove", "r" -> removeTopping(topping, pizza);
        case "" -> addingToppings = false; // continue/done
      }
    }
    
  }
  
  void addTopping(String toppingName, Pizza pizza) {
    Integer toppingID = tryParseInteger(toppingName);
    Topping topping;
    
    // Get topping from menu
    if (toppingID != null)
      topping = menu.getTopping(toppingID);
    else
      topping = menu.getTopping(toppingName);
    
    // Add topping to pizza
    if (topping != null)
      pizza.addTopping(topping);
    else
      ui.addToppingErrorMessage();
  }
  
  void removeTopping(String toppingName, Pizza pizza) {
    Integer toppingID = tryParseInteger(toppingName);
    Topping topping;
    
    // Get topping from menu
    if (toppingID != null)
      topping = menu.getTopping(toppingID);
    else
      topping = menu.getTopping(toppingName);
    
    // Add topping to pizza
    if (topping != null)
      pizza.addWithdrawnTopping(topping);
    else
      ui.removeToppingErrorMessage();
  }
  
  
  //Utilities
  public Integer tryParseInteger(String text) {
    try {
      return parseInt(text);
    } catch (NumberFormatException e) {
      return null;
    }
  }
  
  public void chooseList() {
    boolean loop = true;
    while (loop) {
      ui.printSelectOrderList();
      String input = in.nextLine();
      if (tryParseInteger(input) != null) {
        switch (parseInt(input)) {
          case 1 -> {
            displayOrderList(false);
            loop = false;
          }
          case 2 -> {
            displayOrderList(true);
            loop = false;
          }
        }
      }
    }
  }
  
  public void displayOrderList(boolean printFullList) {
    ui.printOrderList(orderList, printFullList);
    ui.orderListContinueMessage();
    in.nextLine();
  }
  
  public void chooseOrder() {
    ui.printOrderList(orderList, false);
    Order order = null;
    while (order == null) {
      ui.selectOrderMessage();
      String input = in.nextLine();
      //what does tryParse do
      if (tryParseInteger(input) != null) {
        int orderID = parseInt(input);
        order = orderList.findOrder(orderID); //Returns null if nothing is found
        if (order != null)
          editOrderStatus(order);
        else {
          ui.orderOutOfRangeMessage();
        }
      } else
        ui.ChooseOrderInputErrorMessage();
    }
  }
  
  public void editOrderStatus(Order order) {
    boolean loop = true;
    while (loop) {
      ui.printSelectStatus(order);
      String input = in.nextLine();
      if (tryParseInteger(input) != null) {
        switch (parseInt(input)) {
          case 1 -> order.setStatus(String.valueOf(OrderStatuses.PENDING));
          case 2 -> order.setStatus(String.valueOf(OrderStatuses.READY));
          case 3 -> order.setStatus(String.valueOf(OrderStatuses.DELIVERED));
          case 4 -> order.setPaid(true);
          case 5 -> order.setPaid(false);
          case 6 -> order.setStatus(String.valueOf(OrderStatuses.CANCELED));
          case 7 -> loop = false;
        }
      }
    }
  }
  
  public void run() {
    // Get marios menu
    // orderList
    boolean loop = true;
    
    while (loop) {
      ui.printOrderList(orderList, false);
      menu = createMenu();
      ui.printMenu(menu);
      loop = mainMenu(loop);
    }
  }
  
  public Menu createMenu() {
    // Create menu for Marions pizzabar
    Menu menuMario = new Menu();
    menuMario.addPizza(new Pizza("Vesuvio", "tomatsauce, ost, skinke, oregano", 57));
    menuMario.addPizza(new Pizza("Amerikaner", "tomatsauce, ost, oksefars, oregano", 53));
    menuMario.addPizza(new Pizza("Cacciatore", "tomatsauce, ost, pepperoni, oregano", 57));
    menuMario.addPizza(new Pizza("Carbona", "tomatsauce, ost, kødsauce, spaghetti, cocktailpølser, oregano", 57));
    menuMario.addPizza(new Pizza("Dennis", "tomatsauce, ost, skinke, pepperoni, cocktailpølser, oregano", 63));
    menuMario.addPizza(new Pizza("Bertil", "tomatsauce, ost, bacon, oregano", 65));
    menuMario.addPizza(new Pizza("Silvia", "tomatsauce, ost, pepperoni, rød peber, løg, oliven, oregano", 57));
    menuMario.addPizza(new Pizza("Victoria", "tomatsauce, ost, skinke, ananas, champignon, oliven, oregano", 61));
    menuMario.addPizza(new Pizza("Toronfo", "tomatsauce, ost, skinke, bacon, kebab, chili, oregano", 61));
    menuMario.addPizza(new Pizza("Capricciosa", "tomatsauce, ost, skinke, champignon, oregano", 61));
    menuMario.addPizza(new Pizza("Hawai", "tomatsauce, ost, skinke, ananas, oregano", 61));
    menuMario.addPizza(new Pizza("Le Blissola", "tomatsauce, ost, skinke, rejer, oregano", 61));
    menuMario.addPizza(new Pizza("Venezia", "tomatsauce, ost, skinke, oregano", 61));
    menuMario.addPizza(new Pizza("Mafia", "tomatsauce, ost, pepperoni, bacon, løg, oregano", 69));
    menuMario.addTopping(new Topping("tomatsauce", 5));
    menuMario.addTopping(new Topping("oregano", 5));
    menuMario.addTopping(new Topping("ost", 10));
    menuMario.addTopping(new Topping("løg", 10));
    menuMario.addTopping(new Topping("oliven", 10));
    menuMario.addTopping(new Topping("champignon", 10));
    menuMario.addTopping(new Topping("rød peber", 10));
    menuMario.addTopping(new Topping("chili", 10));
    menuMario.addTopping(new Topping("ananas", 10));
    menuMario.addTopping(new Topping("skinke", 10));
    menuMario.addTopping(new Topping("pepperoni", 10));
    menuMario.addTopping(new Topping("kødsauce", 15));
    menuMario.addTopping(new Topping("kebab", 15));
    menuMario.addTopping(new Topping("cocktailpølser", 15));
    menuMario.addTopping(new Topping("spaghetti", 15));
    menuMario.addTopping(new Topping("oksefars", 15));
    menuMario.addTopping(new Topping("bacon", 15));
    menuMario.addTopping(new Topping("rejer", 15));
    return menuMario;
  }

//  void addTopping(String topping, Pizza pizza) {
//    boolean noItem = true;
//    int counter = 0;
//    for (Topping topping : menu.getToppings()
//    ) {
//      counter++;
//      if (tryParse(toppingSelect) != null && tryParse(toppingSelect) == counter) {
//        pizza.addTopping(menu.getTopping(parseInt(toppingSelect)));
//        noItem = false;
//      } else if (toppingSelect.equalsIgnoreCase(topping.getName())) {
//        pizza.addTopping(menu.getTopping(toppingSelect));
//        noItem = false;
//      }
//    }
//    if (noItem) {
//      ui.addToppingErrorMessage();
//    }
//  }

//  void removeTopping(String text, Pizza pizza) {
//    boolean noItem = true;
//    int counter = 0;
//    for (Topping topping : menu.getToppings()
//    ) {
//      counter++;
//      if (tryParse(text) != null && tryParse(text) == counter) {
//        pizza.addWithdrawnTopping(menu.getTopping(parseInt(text)));
//        noItem = false;
//      } else if (text.equalsIgnoreCase(topping.getName())) {
//        pizza.addWithdrawnTopping(menu.getTopping(text));
//        noItem = false;
//      }
//    }
//    if (noItem) {
//      ui.removeToppingErrorMessage();
//    }
//  }

//  public void orderCleanup(Pizza pizza) {
//    ArrayList<Topping> forRemoval = new ArrayList<>();
//    pizza.setExtraToppings(removeDuplicates(pizza.getToppings()));
//    pizza.setWithdrawnToppings(removeDuplicates(pizza.getWithdrawnTopping()));
//    if (pizza.getToppings().size() >= pizza.getWithdrawnTopping().size()) {
//      for (Topping topping : pizza.getToppings()
//      ) {
//        if (pizza.getWithdrawnTopping().contains(topping))
//          forRemoval.add(topping);
//      }
//      pizza.getToppings().removeAll(forRemoval);
//      pizza.getWithdrawnTopping().removeAll(forRemoval);
//    } else {
//      for (Topping topping : pizza.getWithdrawnTopping()
//      ) {
//        if (pizza.getToppings().contains(topping))
//          forRemoval.add(topping);
//      }
//      pizza.getWithdrawnTopping().removeAll(forRemoval);
//      pizza.getToppings().removeAll(forRemoval);
//    }
//  }
}
