package pizzabar;

import java.util.ArrayList;

public class Pizza {
  private String name;
  private String description;
  private int basePrice;
  private ArrayList<Topping> extraToppings;
  private ArrayList<Topping> withdrawnToppings;
  
  public Pizza(String name, String description, int basePrice) {
    this.name = name;
    this.description = description;
    this.basePrice = basePrice;
    extraToppings = new ArrayList<>();
    withdrawnToppings = new ArrayList<>();
  }
  
  public String getName() {
    return name;
  }
  
  public String getDescription() {
    return description;
  }
  
  public int getPrice() {
    int price = basePrice;
    
    // Add toppings
    for (Topping topping : extraToppings) {
      price += topping.getPrice();
    }
    
    return price;
  }
  
  // extraToppings
  public void addTopping(Topping topping) {
    extraToppings.add(topping);
  }
  
  public void removeTopping(Topping topping) {
    extraToppings.remove(topping);
  }
  
  public ArrayList getToppings() {
    return extraToppings;
  }
  
  // withdrawnToppings
  public void addWithdrawnTopping(Topping topping) {
    withdrawnToppings.add(topping);
  }
  
  public void removeWithdrawnTopping(Topping topping) {
    withdrawnToppings.remove(topping);
  }
  
  public ArrayList getWithdrawnTopping() {
    return withdrawnToppings;
  }
  
  @Override
  public boolean equals(Object obj) {
    // Pizzas are the same if extra/withdrawn toppings and name are equal
    if (this == obj) return true;
    if (!(obj instanceof Pizza)) return false;
    return this.name.equals(((Pizza) obj).getName()) &&
           this.getToppings().equals(((Pizza) obj).getToppings()) &&
           this.getWithdrawnTopping().equals(((Pizza) obj).getWithdrawnTopping());
  }
  
  @Override
  public String toString() {
    StringBuilder returnStr = new StringBuilder();
    
    returnStr.append(String.format("%s: %s", name, description));
    
    // Toppings
    for (Topping topping : extraToppings) {
      returnStr.append(String.format(" +%s", topping.getName()));
    }
    // Toppings
    for (Topping topping : withdrawnToppings) {
      returnStr.append(String.format(" -%s", topping.getName()));
    }
    
    returnStr.append(String.format(" %sDKK", getPrice()));
    
    return returnStr.toString();
  }
}
