package pizzabar;

import java.time.LocalDateTime;

public class Test {
  UserInterface ui = new UserInterface();
  
  public void runAll() {
    userStory_1_test_1();
    order_test();
    extraTesting();
  }
  
  public void extraTesting() {
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
  
  // 1 - print menu
  public void userStory_1_test_1() {
    // TEST 1
    System.out.println("\n//TEST 1 - Menu can be printed");
    Menu menu = new Menu();
    menu.addPizza(new Pizza("Cheese pizza", "cheese, tomato-sauce", 40));
    menu.addPizza(new Pizza("Pepperoni", "cheese, tomato-sauce and pepperoni", 55));
    menu.addPizza(new Pizza("pineapple", "cheese, tomato-sauce and pineapple", 69));
    menu.addTopping(new Topping("Bacon", 10));
    menu.addTopping(new Topping("Grease", 2));
    ui.printMenu(menu);
  }
  
  public void order_test() {
    Order order = new Order();
    Menu menu = new Menu();
    menu.addPizza(new Pizza("cheese pizza", "cheese, tomato-sauce", 150));
    menu.addPizza(new Pizza("cake pizza", "cheesecake, tomato-sauce", 40));
    menu.addTopping(new Topping("bacon", 10));
    menu.addTopping(new Topping("grease", 2));
    menu.addTopping(new Topping("cheese", 5));
    menu.addTopping(new Topping("tomato-sauce", 5));
    
    // TEST 1
    System.out.println("\n//TEST 1 - instances of same pizza from MENU can be changed without affecting other instances");
    // Create same pizza with and without topping
    Pizza pizza1 = menu.getPizza(1);
    Pizza pizza2 = menu.getPizza(1);
    pizza2.addTopping(menu.getTopping(1));
    
    order.addPizza(pizza1);
    order.addPizza(pizza2);
    
    ui.printOrder(order);
    
    // TEST 2
    System.out.println("\n//TEST 2 - identical pizza with identical topping but added in a different order are equal");
    Pizza A = menu.getPizza(2);
    Pizza B = menu.getPizza(2);
    
    // A
    A.addTopping(menu.getTopping(2));
    A.addTopping(menu.getTopping(1));
    A.addWithdrawnTopping(menu.getTopping("tomato-sauce"));
    A.addWithdrawnTopping(menu.getTopping("cheese"));
    
    // B
    B.addTopping(menu.getTopping(1));
    B.addTopping(menu.getTopping(2));
    B.addWithdrawnTopping(menu.getTopping("cheese"));
    B.addWithdrawnTopping(menu.getTopping("tomato-sauce"));
    
    System.out.println(A.equals(B));
    
    // TEST 3
    System.out.println("\n//TEST 3 - if a pizza matches a pizza with toppings already in the order it will just add that ID");
    Order order3 = new Order();
    
    // Menu
    Menu menu3 = new Menu();
    menu3.addPizza(new Pizza("cheese pizza", "cheese, tomato-sauce", 150));
    menu3.addPizza(new Pizza("cake pizza", "cheesecake, tomato-sauce", 40));
    menu3.addTopping(new Topping("tomato-sauce", 10));
    menu3.addTopping(new Topping("grease", 2));
    
    // Pizza
    Pizza A1 = menu.getPizza(1);
    Pizza A2 = menu.getPizza(1);
    Pizza B1 = menu.getPizza(2);
    Pizza B2 = menu.getPizza(2);
    // Toppings
    A1.addWithdrawnTopping(menu.getTopping(1));
    A2.addWithdrawnTopping(menu.getTopping(1));
    
    A1.addTopping(menu.getTopping(2));
    A2.addTopping(menu.getTopping(2));
    
    // Add to order with and without amount
    order3.addPizza(A1, 5);
    order3.addPizza(A2, 5);
    order.addPizza(B1);
    order.addPizza(B2);
    
    System.out.println(order3);
  
  }
  

  
}