package pizzabar;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Scanner;

public class Main {
  UserInterface ui = new UserInterface();
  Scanner in = new Scanner(System.in);

  public static void main(String[] args) {
    new Main().run();
  }

  public void mainMenu () {
    ui.printMainMenu();
    String userInput = in.nextLine();
    ui.printMainMenuCommand(userInput);

    switch (userInput.toLowerCase(Locale.ROOT)) {
      case "1", "make order", "make" -> orderPizzas();
      case "2", "edit order", "edit" -> System.out.println("editOrder()");
      case "3", "exit" -> System.out.println("exit");
      default -> mainMenu();
    }
  }

  void orderPizzas () {
    Order order1 = new Order();
    Order order2 = new Order();
    order2.setPickupTime(LocalDateTime.now().plusMinutes(15));

    order1.addPizza(new Pizza("pizza O1", "order1", 420), 2);
    order1.addPizza(new Pizza("pizza2 O1", "order1", 42));
    order2.addPizza(new Pizza("pizza O2", "order2", 420));
    order2.addPizza(new Pizza("pizza2 O2", "order2", 42));

    ui.printOrder(order1);
    ui.printOrder(order2);

    // oderList
    OrderList orderList = new OrderList();
    orderList.addOrder(order1);
    orderList.addOrder(order2);

    ui.printOrderList(orderList);
    Test test = new Test();
//    test.runAll();
    new Main().run();
  }

  public void run() {
    // Get marios menu
    // orderList
    Menu menu = createMenu();
    ui.printMenu(menu);
    mainMenu();



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

}
