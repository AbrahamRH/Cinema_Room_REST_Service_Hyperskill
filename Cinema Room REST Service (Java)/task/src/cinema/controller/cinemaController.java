package cinema.controller;
import cinema.Main;
import cinema.Model.seat;
import cinema.Model.theatre;
import cinema.dto.theatreDto;
import cinema.Exceptions.PurchasedException;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
  public ResponseEntity<?> purchase(@RequestBody seat seat) throws JsonProcessingException {
    try {
      return new ResponseEntity<>(om.writeValueAsString(cinemaDto.purchase(seat.getRow(), seat.getColumn())), HttpStatus.OK);
    } catch (JsonProcessingException jsonEx) {
      log.error("Error /purchase  - {}", jsonEx.getMessage());
      return null;
    } catch (Exception indexEx){
      return new ResponseEntity<>(Map.of("error", indexEx.getMessage()),HttpStatus.BAD_REQUEST);
    }
  }

}
