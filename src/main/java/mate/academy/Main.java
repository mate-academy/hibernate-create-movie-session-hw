package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
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

        final MovieService movieService
                = (MovieService) injector.getInstance(MovieService.class);
        final MovieSessionService movieSessionService
                = (MovieSessionService) injector.getInstance(MovieSessionService.class);
        final CinemaHallService cinemaHallService
                = (CinemaHallService) injector.getInstance(CinemaHallService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);

        Movie interstellar = new Movie("Interstellar");
        interstellar.setDescription("science-fiction");
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

        MovieSession movieSessionInter2 = new MovieSession();
        movieSessionInter2.setMovie(interstellar);
        movieSessionInter2.setCinemaHall(cinemaHallNorth);
        movieSessionInter2.setShowTime(LocalDateTime.of(
                2020, Month.FEBRUARY, 15, 20, 20));
        movieSessionService.add(movieSessionInter2);

        MovieSession movieSessionFast = new MovieSession();
        movieSessionFast.setMovie(fastAndFurious);
        movieSessionFast.setCinemaHall(cinemaHallEast);
        movieSessionFast.setShowTime(LocalDateTime.of(
                2020, Month.FEBRUARY, 15, 19, 50));
        movieSessionService.add(movieSessionFast);

        System.out.println("\nmovie service get");
        System.out.println(movieService.get(1L));
        System.out.println("\nmovieSession service get");
        System.out.println(movieSessionService.get(1L));
        System.out.println("\ncinemaHallService get");
        System.out.println(cinemaHallService.get(1L));

        System.out.println("\nmovieService getAll");
        movieService.getAll().forEach(System.out::println);
        System.out.println("\nmovieSession service findAvailable");
        List<MovieSession> availableSessions = movieSessionService.findAvailableSessions(
                interstellar.getId(), LocalDate.of(2020, Month.FEBRUARY, 15));
        availableSessions.forEach(System.out::println);
        System.out.println("\ncinemaHallService getAll");
        cinemaHallService.getAll().forEach(System.out::println);
    }
}
