package pizzabar;

public class Main {
  public static void main(String[] args) {
    Pizza p1 = new Pizza("Pepperoni", "cheese, tomato-sauce and pepperoni", 55);
    Pizza p2 = new Pizza("Pepperoni", "cheese, tomato-sauce and pepperoni", 55);
    Pizza p3 = new Pizza("Pepperoni", "cheese, tomato-sauce and pepperoni", 55);
    Topping t1 = new Topping("egg", 10);
    Topping t2 = new Topping("egg", 10);
    Topping t3 = new Topping("cheese", 15);
    p1.addTopping(t1);
    p2.addTopping(t2);
    p1.addWithdrawnTopping(t3);
    p2.addWithdrawnTopping(t3);
    
    Order o1 = new Order();
    o1.addPizza(p1);
    o1.addPizza(p2, 4);
    o1.addPizza(p3);
  
//    System.out.println(p1);
//    System.out.println(p2);
//    System.out.println(t1);
    System.out.println(o1);
  
    System.out.println(p1.getToppings());
    System.out.println(p2.getToppings());
    System.out.println(p1.equals(p2));
  }
}
