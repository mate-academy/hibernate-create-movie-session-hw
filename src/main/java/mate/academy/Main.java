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
import mate.academy.service.impl.MovieServiceImpl;
import org.hibernate.SessionFactory;

public class Main {
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.academy");
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        CinemaHallService cinemaHallService = (CinemaHallService) injector
                .getInstance(CinemaHallService.class);
        MovieSessionService movieSessionService = (MovieSessionService) injector
                .getInstance(MovieSessionService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        Movie avengersEndgame = new Movie("Avengers: Endgame");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        avengersEndgame.setDescription("A combat action film from Marvel Studios. "
                + "The Finale of The Infinity Saga.");
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
        cinemaHallService.add(blueHall);
        cinemaHallService.add(anthraciteHall);
        System.out.println("Getting blue hall");
        System.out.println(cinemaHallService.get(blueHall.getId()));
        System.out.println("Getting all cinema halls");
        cinemaHallService.getAll().forEach(System.out::println);
        System.out.println("--------------------------------------------------------------------");

        MovieSession firstSession = new MovieSession();
        MovieSession secondSession = new MovieSession();
        MovieSession thirdSession = new MovieSession();
        LocalDateTime firstDate = LocalDateTime.of(2022, 7, 11, 12, 0);
        LocalDateTime secondDate = LocalDateTime.of(2022, 8, 18, 18, 0);
        LocalDateTime thirdDate = LocalDateTime.of(2021, 7, 11, 17, 0);
        firstSession.setMovie(fastAndFurious);
        firstSession.setCinemaHall(blueHall);
        firstSession.setShowTime(firstDate);
        secondSession.setMovie(avengersEndgame);
        secondSession.setCinemaHall(anthraciteHall);
        secondSession.setShowTime(secondDate);
        thirdSession.setMovie(fastAndFurious);
        thirdSession.setCinemaHall(anthraciteHall);
        thirdSession.setShowTime(thirdDate);
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
