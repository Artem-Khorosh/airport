package airport;

import com.skillbox.airport.Airport;
import com.skillbox.airport.Flight;
import java.time.Duration;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class Main {

  public static long findCountAircraftWithModelAirbus(Airport airport, String model) {

    return airport.getAllAircrafts()
        .stream()
        .filter(aircraft -> aircraft.getModel().startsWith(model))
        .count();
  }

  public static Map<String, Integer> findMapCountParkedAircraftByTerminalName(Airport airport) {

    return airport.getTerminals()
        .stream()
        .collect(Collectors.toMap(
            com.skillbox.airport.Terminal::getName,
            terminal -> terminal.getParkedAircrafts()
                .size()));
  }

  public static List<Flight> findFlightsLeavingInTheNextHours(Airport airport, int hours) {
    Instant currentTime = Instant.now();
    Instant endTime = currentTime.plus(Duration.ofHours(hours));

    return airport.getTerminals().stream()
        .flatMap(terminal -> terminal.getFlights().stream())
        .filter(flight -> flight.getType() == Flight.Type.DEPARTURE)
        .filter(
            flight -> flight.getDate().isAfter(currentTime) && flight.getDate().isBefore(endTime))
        .collect(Collectors.toList());
  }

  public static Optional<Flight> findFirstFlightArriveToTerminal(Airport airport,
      String terminalName) {

    return airport.getTerminals().stream()
        .filter(terminal -> terminal.getName().equals(terminalName))
        .flatMap(terminal -> terminal.getFlights().stream())
        .filter(flight -> flight.getType() == Flight.Type.ARRIVAL)
        .min(Comparator.comparing(Flight::getDate));
  }
}