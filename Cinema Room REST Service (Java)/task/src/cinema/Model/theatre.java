package cinema.Model;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class theatre {
  int total_rows;
  int total_columns;
  List<seat> available_seats = new ArrayList<>();

  public theatre(){
    this.total_columns = 9;
    this.total_rows = 9;
    initSeats();
  }

  public void setTotal_rows(int total_rows) {
    this.total_rows = total_rows;
  }

  public void setTotal_columns(int total_columns) {
    this.total_columns = total_columns;
  }

  public void setAvailable_seats(List<seat> available_seats) {
    this.available_seats = available_seats;
  }

  public int getTotal_columns() {
    return total_columns;
  }

  public int getTotal_rows() {
    return total_rows;
  }

  public List<seat> getAvailable_seats() {
    return available_seats;
  }

  private void initSeats(){
    for(int i = 1 ; i <= total_rows ; i++){
      for(int j = 1 ; j <= total_columns ; j++){
        available_seats.add(new seat(i,j));
      }
    }
  }
}
