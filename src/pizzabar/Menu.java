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
    if (0 <= index && index < pizzas.size()) {
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
  
  public Topping getTopping(String toppingName) {
    for (Topping topping : toppings)
      if (topping.getName().equalsIgnoreCase(toppingName)) return topping;
    
    return null;
  }
  
  public Topping getTopping(int toppingID) {
    int index = toppingID - 1;
    
    // Uses ID not index i.e. first ID = 1
    if (0 <= index && index < toppings.size())
      return toppings.get(index);
    
    return null;
  }
  
  public ArrayList<Topping> getToppings() {
    return toppings;
  }
}
