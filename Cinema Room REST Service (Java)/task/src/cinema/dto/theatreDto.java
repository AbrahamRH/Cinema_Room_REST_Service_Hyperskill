package cinema.dto;

import cinema.Exceptions.PurchasedException;
import cinema.Model.Purchase;
import cinema.Model.Ticket;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Table;

import java.util.*;

public class theatreDto {
  int total_rows;
  int total_columns;
  List<Ticket> available_seats = new ArrayList<>();
  @JsonIgnore
  Table<Integer,Integer,String> seats;
  @JsonIgnore
  Map<UUID, Ticket> tickets_purchase = new HashMap<>();

  public theatreDto(int total_rows, int total_columns, Table<Integer,Integer,String> seats) {
    this.total_rows = total_rows;
    this.total_columns = total_columns;
    this.seats = seats;
    getAvailable_seats();
  }

  public void getAvailable_seats(){
    available_seats.clear();
    for(int i = 1 ; i <= seats.rowKeySet().size() ; i++){
      for(int j = 1 ; j <= seats.columnKeySet().size() ; j++){
        available_seats.add(new Ticket(i,j));
      }
    }
  }
  public Purchase purchase(int row, int column) throws PurchasedException {
    if(seats.contains(row, column)){
      Purchase purchase =  new Purchase(new Ticket(row, column));
      tickets_purchase.put(purchase.getToken(), purchase.getTicket());
      seats.remove(row,column);
      return  purchase;
    } else if ( column > seats.columnKeySet().size() || row > seats.rowKeySet().size() || column < 1 || row < 1) {
      throw new IndexOutOfBoundsException("The number of a row or a column is out of bounds!");
    }
    throw new PurchasedException("The ticket has been already purchased!");
  }

  public Ticket refundPurchase(UUID token) throws Exception {
    if(tickets_purchase.containsKey(token)){
      Ticket refund = tickets_purchase.get(token);
      tickets_purchase.remove(token);
      seats.put(refund.getRow(), refund.getColumn(), "");
      return refund;
    }
    throw new Exception("Wrong token!");
  }
  public int current_income(){
    if(tickets_purchase.size() > 0){
      List<Ticket> tickets = new  ArrayList<>(tickets_purchase.values());
      int income = 0;
      for(Ticket ticket: tickets){
        income += ticket.getPrice();
      }
      return income;
    } else {
      return 0;
    }
  }

  public int number_of_available_seats() {
    return seats.size();
  }

  public int number_purchase_tickets() {
    return tickets_purchase.size();
  }
}

