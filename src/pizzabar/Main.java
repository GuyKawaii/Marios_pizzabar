package pizzabar;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    boolean loop = true;
    Order order = new Order();
    addPizzaToOrder(order);
    while (loop) {
      ui.makeOrderMessage();
      String userInput = in.next().toLowerCase(Locale.ROOT);
      if (userInput.contains("add")) {
        addPizzaToOrder(order);
      } else if (userInput.contains("continue")) {
        loop = false;
      }
    }
    order.setPickupTime(LocalDateTime.now().plusMinutes(15));
    orderList.addOrder(order);
  }

  public void addPizzaToOrder(Order order) {
    Pizza pizza;
    boolean loop = true;

    ui.addPizzaToOrderMessage();
    String userInput = in.nextLine().toLowerCase(Locale.ROOT);
    if (tryParse(userInput) != null) {
      pizza = menu.getPizza(parseInt(userInput));
    } else
      pizza = menu.getPizza(userInput);

    System.out.println("//TEST "+pizza); // Fejlsøgning
    System.out.println("//TEST "+userInput); // Fejlsøgning

    if (pizza != null) {
      order.addPizza(pizza);
      ui.addPizzaToOrderSuccessMessage(pizza);
      while (loop) {
        loop = toppingsMenu(pizza, loop);
      }
      ui.printOrderLite(order);
    } else {
      ui.addPizzaToOrderErrorMessage(); //TODO!: besked bliver altid vist ved add pizza, da pizza = null. Efter en mindre undersøgelse tyder det på at være en fejl der opstår ved recursion eller scanner bug.
      addPizzaToOrder(order);
    }
  }

  public boolean toppingsMenu(Pizza pizza, boolean loop) {

    ui.toppingMenuMessage();
    String userInput = in.nextLine().toLowerCase(Locale.ROOT);
    if (userInput.contains("add ")) {
      userInput = userInput.substring(4);
      addTopping(userInput, pizza);
    } else if (userInput.contains("remove ")) {
      userInput = userInput.substring(7);
      removeTopping(userInput, pizza);
    } else loop = !userInput.contains("continue");
    orderCleanup(pizza);
    return loop;
  }

  void addTopping(String text, Pizza pizza) {
    boolean noItem = true;
    int counter = 0;
    for (Topping topping : menu.getToppings()
    ) {
      counter++;
      if (tryParse(text) != null && tryParse(text) == counter) {
        pizza.addTopping(menu.getTopping(parseInt(text)));
        noItem = false;
      } else if (text.equalsIgnoreCase(topping.getName())) {
        pizza.addTopping(menu.getTopping(text));
        noItem = false;
      }
    }
    if (noItem) {
      ui.addToppingErrorMessage();
    }
  }

  void removeTopping(String text, Pizza pizza) {
    boolean noItem = true;
    int counter = 0;
    for (Topping topping : menu.getToppings()
    ) {
      counter++;
      if (tryParse(text) != null && tryParse(text) == counter) {
        pizza.addWithdrawnTopping(menu.getTopping(parseInt(text)));
        noItem = false;
      } else if (text.equalsIgnoreCase(topping.getName())) {
        pizza.addWithdrawnTopping(menu.getTopping(text));
        noItem = false;
      }
    }
    if (noItem) {
      ui.removeToppingErrorMessage();
    }
  }

  public void orderCleanup(Pizza pizza) {
    ArrayList<Topping> forRemoval = new ArrayList<>();
    pizza.setExtraToppings(removeDuplicates(pizza.getToppings()));
    pizza.setWithdrawnToppings(removeDuplicates(pizza.getWithdrawnTopping()));

    if (pizza.getToppings().size() >= pizza.getWithdrawnTopping().size()) {
      for (Topping topping : pizza.getToppings()
      ) {
        if (pizza.getWithdrawnTopping().contains(topping))
          forRemoval.add(topping);
      }
      pizza.getToppings().removeAll(forRemoval);
      pizza.getWithdrawnTopping().removeAll(forRemoval);
    } else {
      for (Topping topping : pizza.getWithdrawnTopping()
      ) {
        if (pizza.getToppings().contains(topping))
          forRemoval.add(topping);
      }
      pizza.getWithdrawnTopping().removeAll(forRemoval);
      pizza.getToppings().removeAll(forRemoval);
    }
  }

  //Utilities
  public Integer tryParse(String text) {
    try {
      return parseInt(text);
    } catch (NumberFormatException e) {
      return null;
    }
  }

  public static <T> ArrayList<T> removeDuplicates(ArrayList<T> list) {
    ArrayList<T> newList = new ArrayList<>();
    for (T element : list) {
      if (!newList.contains(element)) {
        newList.add(element);
      }
    }
    return newList;
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
