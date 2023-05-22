package cinema.dto;

import cinema.Exceptions.PurchasedException;
import cinema.Model.seat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Table;

import java.util.ArrayList;
import java.util.List;

public class theatreDto {
  int total_rows;
  int total_columns;
  List<seat> available_seats = new ArrayList<>();
  @JsonIgnore
  Table<Integer,Integer,Integer> seats;

  public theatreDto(int total_rows, int total_columns, Table<Integer,Integer,Integer> seats) {
    this.total_rows = total_rows;
    this.total_columns = total_columns;
    this.seats = seats;
    getAvailable_seats();
  }

  public void getAvailable_seats(){
    for(int i = 1 ; i <= seats.rowKeySet().size() ; i++){
      for(int j = 1 ; j <= seats.columnKeySet().size() ; j++){
        available_seats.add(new seat(i,j));
      }
    }
  }
  public seat purchase(int row, int column) throws PurchasedException {
    if(seats.contains(row,column)){
      seat newSeat =  new seat(row, column);
      seats.remove(row, column);
      return newSeat;
    } else if ( column > seats.columnKeySet().size() || row > seats.rowKeySet().size() || column < 1 || row < 1) {
      throw new IndexOutOfBoundsException("The number of a row or a column is out of bounds!");
    }
    throw new PurchasedException("The ticket has been already purchased!");
  }
}

