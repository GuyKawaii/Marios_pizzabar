package pizzabar;

import java.util.ArrayList;

public class Order {
  ArrayList<Pizza> pizzaType;
  ArrayList<Integer> pizzaTypeAmount;
  
  public Order() {
    pizzaType = new ArrayList<>();
    pizzaTypeAmount = new ArrayList<>();
  }
  
  // TODO able to add the same pizza to amount if pizza toppings and name is the same
  public void addPizza(Pizza pizza) {
    // Increment count if pizza added already exists
    if (pizzaType.contains(pizza)) {
      int index = pizzaType.indexOf(pizza);
      pizzaTypeAmount.set(index, pizzaTypeAmount.get(index) + 1);
    
    // New Pizza added
    } else {
      pizzaType.add(pizza);
      pizzaTypeAmount.add(1);
    }
  }
  
  public void addPizza(Pizza pizza, int amount) {
    // Increment count if pizza added already exists
    if (pizzaType.contains(pizza)) {
      int index = pizzaType.indexOf(pizza);
      pizzaTypeAmount.set(index, pizzaTypeAmount.get(index) + amount);
      
    // New pizza added
    } else {
      pizzaType.add(pizza);
      pizzaTypeAmount.add(amount);
    }
  }
  
  @Override
  public String toString() {
    StringBuilder returnStr = new StringBuilder();
    
    for (int i = 0; i < pizzaType.size(); i++) {
      Pizza pizza = pizzaType.get(i);
      returnStr.append(String.format("ID:%02d - %2dX %s DKK\n",
          (i + 1),
          pizzaTypeAmount.get(i),
          pizza.toString()));
    }
    
    return returnStr.toString();
  }
}
