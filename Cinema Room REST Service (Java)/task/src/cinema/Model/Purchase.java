package cinema.Model;
import java.util.UUID;
public class Purchase {
  private UUID token;
  private Ticket ticket;

  public Purchase(Ticket ticket){
    this.token = UUID.randomUUID();
    this.ticket = ticket;
  }

  public UUID getToken() {
    return token;
  }

  public Ticket getTicket() {
    return ticket;
  }
}
