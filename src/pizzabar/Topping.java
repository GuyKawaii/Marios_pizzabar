package pizzabar;

public class Topping {
  private String name;
  private int price;
  
  public Topping(String name, int price) {
    this.name = name;
    this.price = price;
  }
  
  public String getName() {
    return name;
  }
  
  public int getPrice() {
    return price;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  @Override
  public boolean equals(Object obj) {
    // Toppings are equal if they have the same name
    if (this == obj) return true;
    if (!(obj instanceof Topping)) return false;
    return this.name.equals(((Topping) obj).getName());
  }
  
  @Override
  public String toString() {
    return String.format("%s %s", name, price);
  }
}
