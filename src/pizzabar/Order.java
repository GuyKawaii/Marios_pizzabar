package pizzabar;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Order implements Comparable<Order> {
  private ArrayList<Pizza> pizzaType;
  private ArrayList<Integer> amountOfPizzaType;
  private LocalDateTime pickupTime;
  private int totalPrice;
  private int id;
  private String status;
  private boolean isPaid;

  public Order() {
    pizzaType = new ArrayList<>();
    amountOfPizzaType = new ArrayList<>();
    //pickupTime = LocalDateTime.now().plusMinutes(30); // default X min from now
    totalPrice = 0;
    id = 0;
    setStatus(String.valueOf(OrderStatuses.PENDING));
    setPaid(false);
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getStatus() {
    return status;
  }

  public boolean isPaid() {
    return isPaid;
  }

  public void setPaid(boolean paid) {
    isPaid = paid;
  }

  public ArrayList<Pizza> getPizzaTypes() {
    return pizzaType;
  }
  
  public ArrayList<Integer> getAmountOfPizzaTypes() {
    return amountOfPizzaType;
  }
  
  public LocalDateTime getPickupTime() {
    return pickupTime;
  }
  
  public void addPizza(Pizza pizza) {
    // Increment count if pizza added already exists
    if (pizzaType.contains(pizza)) {
      int index = pizzaType.indexOf(pizza);
      amountOfPizzaType.set(index, amountOfPizzaType.get(index) + 1);
      
      // New Pizza added
    } else {
      pizzaType.add(pizza);
      amountOfPizzaType.add(1);
    }
    
    updateTotalPrice();
  }
  
  public void addPizza(Pizza pizza, int amount) {
    // Increment count if pizza added already exists
    if (pizzaType.contains(pizza)) {
      int index = pizzaType.indexOf(pizza);
      amountOfPizzaType.set(index, amountOfPizzaType.get(index) + amount);
      
      // New pizza added
    } else {
      pizzaType.add(pizza);
      amountOfPizzaType.add(amount);
    }
    
    updateTotalPrice();
  }
  
  public void updateTotalPrice() {
    int tmpTotalPrice = 0;
    
    for (int i = 0; i < pizzaType.size(); i++)
      tmpTotalPrice += pizzaType.get(i).getPrice() * amountOfPizzaType.get(i);
    
    totalPrice = tmpTotalPrice;
  }
  
  public int getTotalPrice() {
    return totalPrice;
  }
  
  public int getId() {
    return id;
  }
  
  public void setId(int id ) {
    this.id = id;
  }
  
  public boolean removePizzaID(Integer pizzaID) {
    if (pizzaID !=null && 0 <= pizzaID - 1 && pizzaID - 1 < pizzaType.size()) {
      pizzaType.remove(pizzaID - 1);
      amountOfPizzaType.remove(pizzaID - 1);
      updateTotalPrice();
      return true;
    
    } else
      return false;
  }
  
  public void setPickupTime(LocalDateTime localDateTime) {
    pickupTime = localDateTime;
  }
  
  @Override
  public int compareTo(Order order) {
    return this.pickupTime.compareTo(order.pickupTime);
  }
  
//  @Override
//  public String toString() {
//    StringBuilder returnStr = new StringBuilder();
//
//    for (int i = 0; i < pizzaType.size(); i++) {
//      Pizza pizza = pizzaType.get(i);
//      int price = pizza.getPrice() * amountOfPizzaType.get(i);
//      totalPrice += price;
//
//      returnStr.append(String.format("ID:%02d - %2d X '%-40s' %4dkr\n",
//          (i + 1),
//          amountOfPizzaType.get(i),
//          pizza.getNameAndTopping(),
//          price));
//    }
//
//    returnStr.append(String.format("TOTAL: %53dkr", getTotalPrice()));
//
//    if (pickupTime != null)
//      returnStr.append(String.format("\nPICKUP-TIME: %S:%s %s/%s\n",
//          pickupTime.getHour(),
//          pickupTime.getMinute(),
//          pickupTime.getDayOfMonth(),
//          pickupTime.getMonth()));
//
//    return returnStr.toString();
//  }
}
