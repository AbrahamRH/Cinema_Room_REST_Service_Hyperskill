package cinema.controller;
import cinema.Main;
import cinema.Model.Purchase;
import cinema.Model.Ticket;
import cinema.Model.theatre;
import cinema.dto.theatreDto;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.UUIDDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

class Token {
  private UUID token;
  public Token(){

  }
  public UUID getToken() {
    return token;
  }
}

@RestController
public class cinemaController {
  private final theatre cinema;
  private theatreDto cinemaDto;
  private final Logger log = LoggerFactory.getLogger(Main.class);
  ObjectMapper om = new ObjectMapper();


  @Autowired
  public cinemaController(theatre cinema){
    this.cinema = cinema;
    this.cinemaDto = new theatreDto(cinema.getTotal_rows(),cinema.getTotal_columns(), cinema.getSeats());
    om.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
  }

  @GetMapping(path="/seats")
  public String getSeats(){
    try {
      return om.writeValueAsString(cinemaDto);
    } catch(Exception e){
      log.error("Error /seats  - {}", e.getMessage());
      return null;
    }
  }

  @PostMapping(path = "/purchase")
  public ResponseEntity<?> purchase(@RequestBody Ticket ticket) throws JsonProcessingException {
    try {
      return new ResponseEntity<>(om.writeValueAsString(cinemaDto.purchase(ticket.getRow(), ticket.getColumn())), HttpStatus.OK);
    } catch (JsonProcessingException jsonEx) {
      log.error("Error /purchase  - {}", jsonEx.getMessage());
      return null;
    } catch (Exception indexEx){
      return new ResponseEntity<>(Map.of("error", indexEx.getMessage()),HttpStatus.BAD_REQUEST);
    }
  }

  @PostMapping(path = "/return")
  public ResponseEntity<?> returnPurchase(@RequestBody Token token){
    try {
      return new ResponseEntity<>(Map.of("returned_ticket", cinemaDto.refundPurchase(token.getToken())) , HttpStatus.OK);
    } catch (JsonProcessingException JsonEx){
      log.error("Error /purchase  - {}", JsonEx.getMessage());
      return null;
    } catch (Exception tokenEx) {
      return new ResponseEntity<>(Map.of("error", tokenEx.getMessage()),HttpStatus.BAD_REQUEST);
    }
  }

  @PostMapping(path=("/stats"))
  public ResponseEntity<?> getStats(@RequestParam(required = false) String password){
    if(password != null && password.equals("super_secret")){
      Map<String, Integer> response = Map.of("current_income", cinemaDto.current_income(),
        "number_of_available_seats", cinemaDto.number_of_available_seats(),
        "number_of_purchased_tickets", cinemaDto.number_purchase_tickets());
      return new ResponseEntity<>(response, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(Map.of("error", "The password is wrong!"), HttpStatus.valueOf(401));
    }

  }

}
