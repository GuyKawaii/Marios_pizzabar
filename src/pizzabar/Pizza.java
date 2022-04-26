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
    // basePrice + toppings
    int price = basePrice;
    
    // Add toppings
    for (Topping topping : extraToppings) {
      price += topping.getPrice();
    }
    
    return price;
  }
  
  // extraToppings
  public void addTopping(Topping topping) {
    // same withdrawnToppings and extraToppings cancel/removes each other
    if (withdrawnToppings.contains(topping))
      withdrawnToppings.remove(topping);
    
    // add if not already present
    else if (!extraToppings.contains(topping))
      extraToppings.add(topping);
  }
  
  public void removeTopping(Topping topping) {
    extraToppings.remove(topping);
  }
  
  public ArrayList<Topping> getToppings() {
    return extraToppings;
  }

  public void setExtraToppings(ArrayList<Topping> extraToppings) {
    this.extraToppings = extraToppings;
  }
  
  // withdrawnToppings
  public void addWithdrawnTopping(Topping topping) {
    // same withdrawnToppings and extraToppings cancel each other
    if (extraToppings.contains(topping))
      extraToppings.remove(topping);
    
    // add if not already present
    else if (!withdrawnToppings.contains(topping))
      withdrawnToppings.add(topping);
  }
  
  public void removeWithdrawnTopping(Topping topping) {
    withdrawnToppings.remove(topping);
  }
  
  public ArrayList<Topping> getWithdrawnTopping() {
    return withdrawnToppings;
  }

  public void setWithdrawnToppings(ArrayList<Topping> withdrawnToppings) {
    this.withdrawnToppings = withdrawnToppings;
  }

  @Override
  public boolean equals(Object obj) {
    // Pizzas are equal if extra/withdrawn toppings and name are equal
    if (this == obj) return true;
    if (!(obj instanceof Pizza compare)) return false;
    return
        // base pizza name equal
        this.name.equals(compare.getName()) &&
        
        // extraToppings are equal
        this.extraToppings.size() == compare.extraToppings.size() &&
        this.extraToppings.containsAll(compare.extraToppings) &&
        compare.extraToppings.containsAll(this.extraToppings) &&
        
        // withdrawnToppings are equal
        this.withdrawnToppings.size() == compare.withdrawnToppings.size() &&
        this.withdrawnToppings.containsAll(compare.withdrawnToppings) &&
        compare.withdrawnToppings.containsAll(this.withdrawnToppings);
  }
  
  public String getNameAndTopping() {
    StringBuilder returnStr = new StringBuilder();
    
    returnStr.append(name);
    
    // Toppings
    for (Topping topping : extraToppings) {
      returnStr.append(String.format(" +%s", topping.getName()));
    }
    // Toppings
    for (Topping topping : withdrawnToppings) {
      returnStr.append(String.format(" -%s", topping.getName()));
    }
    
    return returnStr.toString();
  }
  
  @Override
  public String toString() {
    // Name, description and toppings
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
