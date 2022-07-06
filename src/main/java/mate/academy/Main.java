package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;

public class Main {
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.academy");

        Movie fastAndFurious = new Movie("Fast and Furious");
        Movie avengersEndgame = new Movie("Avengers: Endgame");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        avengersEndgame.setDescription("A combat action film from Marvel Studios. "
                + "The Finale of The Infinity Saga.");

        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);

        movieService.add(fastAndFurious);
        movieService.add(avengersEndgame);
        System.out.println("Getting Fast and Furious");
        System.out.println(movieService.get(fastAndFurious.getId()));
        System.out.println("Getting all movies");
        movieService.getAll().forEach(System.out::println);
        System.out.println("--------------------------------------------------------------------");

        CinemaHall blueHall = new CinemaHall();
        blueHall.setCapacity(100);
        blueHall.setDescription("Blue hall from a blue world to watch blue movies. "
                + "I'm blue, da ba dee da ba di...");
        CinemaHall anthraciteHall = new CinemaHall();
        anthraciteHall.setCapacity(120);
        anthraciteHall.setDescription("The mud under the moon was sparkling like anthracite, "
                + "and all guys from the N town were in love with the local committee secretary.");

        CinemaHallService cinemaHallService = (CinemaHallService) injector
                .getInstance(CinemaHallService.class);

        cinemaHallService.add(blueHall);
        cinemaHallService.add(anthraciteHall);
        System.out.println("Getting blue hall");
        System.out.println(cinemaHallService.get(blueHall.getId()));
        System.out.println("Getting all cinema halls");
        cinemaHallService.getAll().forEach(System.out::println);
        System.out.println("--------------------------------------------------------------------");

        MovieSession firstSession = new MovieSession();
        firstSession.setMovie(fastAndFurious);
        firstSession.setCinemaHall(blueHall);
        LocalDateTime firstDate = LocalDateTime.of(2022, 7, 11, 12, 0);
        firstSession.setShowTime(firstDate);
        MovieSession secondSession = new MovieSession();
        secondSession.setMovie(avengersEndgame);
        secondSession.setCinemaHall(anthraciteHall);
        LocalDateTime secondDate = LocalDateTime.of(2022, 8, 18, 18, 0);
        secondSession.setShowTime(secondDate);
        MovieSession thirdSession = new MovieSession();
        thirdSession.setMovie(fastAndFurious);
        thirdSession.setCinemaHall(anthraciteHall);
        LocalDateTime thirdDate = LocalDateTime.of(2021, 7, 11, 17, 0);
        thirdSession.setShowTime(thirdDate);

        MovieSessionService movieSessionService = (MovieSessionService) injector
                .getInstance(MovieSessionService.class);

        movieSessionService.add(firstSession);
        movieSessionService.add(secondSession);
        movieSessionService.add(thirdSession);
        System.out.println("Getting Fast and Furious session");
        System.out.println(movieSessionService.get(firstSession.getId()));
        System.out.println("Getting available movie sessions for Fast and Furious");
        System.out.println(movieSessionService.findAvailableSessions(
                fastAndFurious.getId(), firstDate.toLocalDate()));
        System.out.println("Getting available movie sessions for Avengers: Endgame");
        System.out.println(movieSessionService.findAvailableSessions(
                avengersEndgame.getId(), secondDate.toLocalDate()));
        System.out.println("Getting available movie sessions for Fast and Furious in 2021");
        System.out.println(movieSessionService.findAvailableSessions(
                fastAndFurious.getId(), thirdDate.toLocalDate()));
        System.out.println("Getting movie sessions for non-available date");
        System.out.println(movieSessionService.findAvailableSessions(
                avengersEndgame.getId(), LocalDate.of(1999, 12, 1)));
        System.out.println("--------------------------------------------------------------------");
    }
}
