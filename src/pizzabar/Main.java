package pizzabar;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Scanner;

public class Main {
  UserInterface ui = new UserInterface();
  Scanner in = new Scanner(System.in);

  public static void main(String[] args) {
    new Main().mainMenu();
  }

  void mainMenu () {
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
  }
}
