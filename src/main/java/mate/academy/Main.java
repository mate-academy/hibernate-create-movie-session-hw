package mate.academy;

import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.academy");

        MovieService movieService
                = (MovieService) injector.getInstance(MovieService.class);
        MovieSessionService movieSessionService
                = (MovieSessionService) injector.getInstance(MovieSessionService.class);
        CinemaHallService cinemaHallService
                = (CinemaHallService) injector.getInstance(CinemaHallService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);

        Movie interstellar = new Movie("Interstellar");
        fastAndFurious.setDescription("science-fiction");
        movieService.add(interstellar);

        CinemaHall cinemaHallNorth = new CinemaHall();
        cinemaHallNorth.setCapacity(250);
        cinemaHallNorth.setDescription("North wing");
        cinemaHallService.add(cinemaHallNorth);

        CinemaHall cinemaHallEast = new CinemaHall();
        cinemaHallEast.setCapacity(100);
        cinemaHallEast.setDescription("East wing");
        cinemaHallService.add(cinemaHallEast);

        MovieSession movieSessionInter = new MovieSession();
        movieSessionInter.setMovie(interstellar);
        movieSessionInter.setCinemaHall(cinemaHallNorth);
        movieSessionInter.setShowTime(LocalDateTime.of(
                2020, Month.FEBRUARY, 15, 14, 20));
        movieSessionService.add(movieSessionInter);

        MovieSession movieSessionFast = new MovieSession();
        movieSessionFast.setMovie(fastAndFurious);
        movieSessionFast.setCinemaHall(cinemaHallEast);
        movieSessionFast.setShowTime(LocalDateTime.of(
                2020, Month.FEBRUARY, 15, 19, 50));
        movieSessionService.add(movieSessionFast);

        System.out.println("================");
        System.out.println(movieService.get(1L));
        System.out.println("================");
        System.out.println(movieSessionService.get(1L));
        System.out.println("================");
        System.out.println(cinemaHallService.get(1L));
        System.out.println("================");

        movieService.getAll().forEach(System.out::println);
        System.out.println("================");
        List<MovieSession> availableSessions = movieSessionService.findAvailableSessions(
                fastAndFurious.getId(), LocalDate.of(2020, Month.FEBRUARY, 15));
        System.out.println("================");
        availableSessions.forEach(System.out::println);
        System.out.println("================");
        cinemaHallService.getAll().forEach(System.out::println);
        System.out.println("================");
    }
}
