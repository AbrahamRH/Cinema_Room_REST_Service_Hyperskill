package cinema.Model;

public class Ticket {
  private int row;
  private int column;
  private int price;

  public Ticket(int row, int column){
    this.row = row;
    this.column = column;
    this.price = (row <= 4) ? 10 : 8;
  }

  public Ticket(){

  }

  public int getRow() {
    return row;
  }

  public int getColumn() {
    return column;
  }

  public int getPrice() {
    return price;
  }
}
