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
    //new Test().runAll();
  }

  public boolean mainMenu(boolean loop) {
    ui.printMainMenu();
    String userInput = in.nextLine();

    switch (userInput.toLowerCase(Locale.ROOT)) {
      case "1", "make order", "make" -> makeOrder();
      case "2", "edit order", "edit" -> chooseOrder();
      case "3" -> displayOrderList(false);
      case "4" -> displayOrderList(true);
      case "5", "exit" -> loop = false;
    }
    return loop;
  }

  void makeOrder() {
    Order order = new Order();
    boolean loop;
    do {
      addPizzaToOrder(order);
      loop = addAnotherPizzaToOrder();
    } while (loop);
    order.setPickupTime(LocalDateTime.now().plusMinutes(15));
    orderList.addOrder(order);
  }

  public void addPizzaToOrder(Order order) {
    Pizza pizza;
    boolean loop;
    ui.addPizzaToOrderMessage();
    String userInput = in.nextLine();
    if (tryParse(userInput) != null) {
      int userInputNum = parseInt(userInput);
      pizza = menu.getPizza(userInputNum);
    } else
      pizza = menu.getPizza(userInput);
    if (pizza != null) {
      order.addPizza(pizza);
      ui.addPizzaToOrderSuccessMessage(pizza);
      do {
        loop = toppingsMenu(pizza);
        //ui.printMenu(menu);
        ui.printOrderLite(order);
      } while (loop);
    } else {
      ui.addPizzaToOrderErrorMessage();
      addPizzaToOrder(order);
    }
  }

  public boolean toppingsMenu(Pizza pizza) {
    ui.toppingMenuMessage();
    String userInput = in.nextLine();
    if (userInput.contains("add")) {
      userInput = userInput.substring(4);
      addTopping(userInput, pizza);
    } else if (userInput.contains("remove")) {
      userInput = userInput.substring(7);
      removeTopping(userInput, pizza);
    } else return !userInput.contains("continue");
    return true;
  }

  void addTopping(String string, Pizza pizza) {
    boolean noItem = false;
    for (Topping topping : menu.getToppings()
    ) {
      if (tryParse(string) != null) { //TODO: ret FEJL!
        int stringNum = parseInt(string);
        pizza.addTopping(menu.getTopping(stringNum));
      } else if (string.equalsIgnoreCase(topping.getName())) {
        pizza.addTopping(menu.getTopping(string));
      } else {
        noItem = true;
      }
    }
    if (noItem) {
      ui.addToppingErrorMessage();
    }
  }

  void removeTopping(String string, Pizza pizza) {
    boolean noItem = false;
    for (Topping topping : menu.getToppings()
    ) {
      if (tryParse(string) != null) { //TODO: ret FEJL!
        int stringNum = parseInt(string);
        pizza.addWithdrawnTopping(menu.getTopping(stringNum));
      } else if (string.equalsIgnoreCase(topping.getName())) {
        pizza.addWithdrawnTopping(menu.getTopping(string));
      } else {
        noItem = true;
      }
    }
    if (noItem) {
      ui.removeToppingErrorMessage();
    }
  }

  public Integer tryParse(String text) {
    try {
      return parseInt(text);
    } catch (NumberFormatException e) {
      return null;
    }
  }

  public boolean addAnotherPizzaToOrder() {
    System.out.println("Do you want to add another pizza, or continue with order? (add/continue)");
    String userInput = in.next();
    if (userInput.contains("add")) {
      System.out.println();
    } else return !userInput.contains("continue");
    return true;
  }

  public void displayOrderList(boolean printFullList) {
    ui.printOrderList(orderList, printFullList);
    ui.printOrderListContinue();
    in.nextLine();
  }

  public void chooseOrder() {
    ui.printOrderList(orderList, false);
    ui.printSelectOrder();
    int chosenOrder = in.nextInt();
    while (chosenOrder <= 0 || chosenOrder > orderList.orders.size()) {
      ui.printOrderOutOfRange();
      ui.printSelectOrder();
      chosenOrder = in.nextInt();
    }
    for (int i = 0; i < orderList.orders.size(); i++) {
      if (orderList.orders.get(i).getId() == chosenOrder) {
        Order order = orderList.orders.get(i);
        editOrderStatus(order);
      }
    }
  }

  public void editOrderStatus(Order order) {
    boolean loop = true;
    while (loop) {
      ui.printSelectStatus(order);
      switch (in.nextInt()) {
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

  public void run() {
    // Get marios menu
    // orderList
    boolean loop = true;

    do {
      ui.printOrderList(orderList, false);
      menu = createMenu();
      ui.printMenu(menu);
      loop = mainMenu(loop);
    } while (loop);


  }

  public Menu createMenu() {
    // Create menu for Marions pizzabar
    Menu menuMario = new Menu();
    menuMario.addPizza(new Pizza("Vesuvio", "tomatsauce, ost, skinke, oregano", 57));
    menuMario.addPizza(new Pizza("Amerikaner", "tomatsauce, ost, oksefars, oregano", 53));
    menuMario.addPizza(new Pizza("Cacciatore", "tomatsauce, ost, pepperoni, oregano", 57));
    menuMario.addPizza(new Pizza("Carbona", "tomatsauce, ost, kødsauce, spaghetti, cocktailpølser, oregano", 57));
    menuMario.addPizza(new Pizza("Dennis", "tomatsauce, ost, skinke, pepperoni, cocktailpølser, oregano", 57));
    menuMario.addPizza(new Pizza("Bertil", "tomatsauce, ost, bacon, oregano", 57));
    menuMario.addPizza(new Pizza("Silvia", "tomatsauce, ost, pepperoni, rød peber, løg, oliven, oregano", 57));
    menuMario.addPizza(new Pizza("Victoria", "tomatsauce, ost, skinke, ananas, champignon, oliven, oregano", 57));
    menuMario.addPizza(new Pizza("Toronfo", "tomatsauce, ost, skinke, bacon, kebab, chili, oregano", 57));
    menuMario.addPizza(new Pizza("Capricciosa", "tomatsauce, ost, skinke, champignon, oregano", 57));
    menuMario.addPizza(new Pizza("Hawai", "tomatsauce, ost, skinke, ananas, oregano", 57));
    menuMario.addPizza(new Pizza("Le Blissola", "tomatsauce, ost, skinke, rejer, oregano", 57));
    menuMario.addPizza(new Pizza("Venezia", "tomatsauce, ost, skinke, oregano", 57));
    menuMario.addPizza(new Pizza("Mafia", "tomatsauce, ost, pepperoni, bacon, løg, oregano", 57));
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

}
