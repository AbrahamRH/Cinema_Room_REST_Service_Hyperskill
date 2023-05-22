package cinema.Model;

import org.springframework.stereotype.Component;

import com.google.common.collect.Table;
import com.google.common.collect.HashBasedTable;

@Component
public class theatre {
  int total_rows;
  int total_columns;
  Table<Integer, Integer,String> seats = HashBasedTable.create();

  public theatre(){
    this.total_columns = 9;
    this.total_rows = 9;
    initSeats();
  }

  public int getTotal_columns() {
    return total_columns;
  }

  public int getTotal_rows() {
    return total_rows;
  }

  public Table<Integer,Integer, String> getSeats(){
    return seats;
  }

  private void initSeats(){
    for(int i = 1 ; i <= total_rows ; i++){
      for(int j = 1 ; j <= total_columns ; j++){
        seats.put(i,j,"");
      }
    }
  }
}
