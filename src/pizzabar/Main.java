package pizzabar;

public class Main {
  UserInterface ui = new UserInterface();
  
  public static void main(String[] args) {
    Test test = new Test();
//    test.runAll();
    new Main().run();
  }
  
  public void run() {
    // Get marios menu
    Menu menu = createMenu();
    
    
    ui.printMenu(menu);
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
