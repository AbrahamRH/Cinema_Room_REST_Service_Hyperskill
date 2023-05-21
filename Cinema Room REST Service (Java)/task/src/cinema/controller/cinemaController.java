package cinema.controller;
import cinema.Main;
import cinema.Model.theatre;
import cinema.dto.theatreDto;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class cinemaController {
  private final theatre cinema;
  private final Logger log = LoggerFactory.getLogger(Main.class);

  @Autowired
  public cinemaController(theatre cinema){
    this.cinema = cinema;
  }

  @GetMapping(path="/seats")
  public String getSeats(){
    theatreDto cinemaDto = new theatreDto(cinema.getTotal_rows(),cinema.getTotal_columns(), cinema.getAvailable_seats());
    ObjectMapper om = new ObjectMapper();
    om.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
    try {
      return om.writeValueAsString(cinemaDto);
    } catch(Exception e){
      log.error("Error /seats  - {}", e.getMessage());
      return null;
    }
  }

}
