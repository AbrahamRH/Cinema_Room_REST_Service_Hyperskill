package cinema.dto;

import cinema.Model.seat;

import java.util.ArrayList;
import java.util.List;

public class theatreDto {
  int total_rows;
  int total_columns;
  List<seat> available_seats = new ArrayList<>();

  public theatreDto(int total_rows, int total_columns, List<seat> available_seats) {
    this.total_rows = total_rows;
    this.total_columns = total_columns;
    this.available_seats = available_seats;
  }
}
