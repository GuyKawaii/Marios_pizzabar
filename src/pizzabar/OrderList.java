package pizzabar;

import java.util.ArrayList;
import java.util.Collections;

public class OrderList {
  ArrayList<Order> orders;
  int nextOrderID = 1;
  
  public OrderList() {
    orders = new ArrayList<>();
  }
  
  public ArrayList<Order> getOrders() {
    return orders;
  }
  
  public void addOrder(Order order) {
    // assign unique order ids
    order.setId(nextOrderID);
    nextOrderID++;
    if (nextOrderID >= 100) nextOrderID = 1;
    // add order and sort by urgency
    orders.add(order);
    orders.sort(Collections.reverseOrder());
  }
  
  public void removeOrder(int orderID) {
    int index = orderID - 1;
  }

  //find order based off order ID
  public Order findOrder(int orderID) {
    for (int i = 0; i < orders.size(); i++) {
      if (orders.get(i).getId() == orderID) {
        Order order = orders.get(i);
        return order;
      }
    }
    return null;
  }
}
