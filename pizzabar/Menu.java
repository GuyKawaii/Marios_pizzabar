package pizzabar;

import java.util.ArrayList;

public class Menu {
  ArrayList<Pizza> pizzas;
  ArrayList<Topping> toppings;
  
  public Menu() {
    pizzas = new ArrayList<>();
    toppings = new ArrayList<>();
  }
  
  // Pizzas
  public void addPizza(Pizza pizza) {
    pizzas.add(pizza);
  }
  
  public Pizza getPizza(String pizzaName) {
    // return copy of pizza
    for (Pizza menuPizza : pizzas)
      if (menuPizza.getName().equalsIgnoreCase(pizzaName))
        return new Pizza(menuPizza.getName(), menuPizza.getDescription(), menuPizza.getPrice());
    
    return null;
  }
  
  public Pizza getPizza(int pizzaID) {
    // return copy of pizza
    int index = pizzaID - 1;
    
    // Uses ID not index i.e. first ID = 1
    if (pizzas.get(index) != null) {
      Pizza menuPizza = pizzas.get(index);
      return new Pizza(menuPizza.getName(), menuPizza.getDescription(), menuPizza.getPrice());
    }
    
    return null;
  }
  
  public ArrayList<Pizza> getPizzas() {
    return pizzas;
  }
  
  // Toppings
  public void addTopping(Topping topping) {
    toppings.add(topping);
  }
  
  public Topping getTopping(String pizzaName) {
    for (Topping topping : toppings)
      if (topping.getName().equalsIgnoreCase(pizzaName)) return topping;
    
    return null;
  }
  
  public Topping getTopping(int toppingID) {
    // Uses ID not index i.e. first ID = 1
    if (toppings.get(toppingID - 1) != null)
      return toppings.get(toppingID - 1);
    
    return null;
  }
  
  public ArrayList<Topping> getToppings() {
    return toppings;
  }
  
//  @Override
//  public String toString() {
//    return String.format("%s%s", pizzasToString(), toppingsToString());
//  }
//
//  public String pizzasToString() {
//    StringBuilder returnStr = new StringBuilder();
//
//    // pizzas
//    returnStr.append("PIZZAS\n");
//    for (int i = 0; i < pizzas.size(); i++) {
//      Pizza pizza = pizzas.get(i);
//      returnStr.append(String.format("%2d - %-20s' %-40s' %4sKr\n", (i + 1), pizza.getName(), pizza.getDescription(), pizza.getPrice()));
//    }
//
//    return returnStr.toString();
//  }
//
//  public String toppingsToString() {
//    StringBuilder returnStr = new StringBuilder();
//
//    // toppings
//    returnStr.append("TOPPINGS\n");
//    for (int i = 0; i < toppings.size(); i++) {
//      Topping topping = toppings.get(i);
//      returnStr.append(String.format("%2d - %-62s' %4sKr\n", (i + 1), topping.getName(), topping.getPrice()));
//    }
//
//    return returnStr.toString();
//  }
}
