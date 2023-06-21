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
    private static Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {

        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        Movie goneIn60Seconds = new Movie("Gone in 60 seconds");
        goneIn60Seconds.setDescription("Awesome movie about people who love beautifull cars. "
                + "Much better than others.....)");
        movieService.add(fastAndFurious);
        movieService.add(goneIn60Seconds);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall vipCinemaHall = new CinemaHall();
        vipCinemaHall.setCapacity(5);
        vipCinemaHall.setDescription("VIP Hall");
        CinemaHall regularCinemaHall = new CinemaHall();
        regularCinemaHall.setCapacity(20);
        regularCinemaHall.setDescription("regular Hall");

        CinemaHallService cinemaHallService = (CinemaHallService) injector
                .getInstance(CinemaHallService.class);
        cinemaHallService.add(vipCinemaHall);
        cinemaHallService.add(regularCinemaHall);
        System.out.println(cinemaHallService.get(vipCinemaHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession firstMovieSession = new MovieSession();
        firstMovieSession.setMovie(fastAndFurious);
        firstMovieSession.setCinemaHall(regularCinemaHall);
        LocalDateTime localDateTime = LocalDateTime.of(2023, 06, 23, 12,30);
        firstMovieSession.setShowTime(localDateTime);
        MovieSession secondMovieSession = new MovieSession();
        secondMovieSession.setCinemaHall(vipCinemaHall);
        secondMovieSession.setMovie(goneIn60Seconds);
        secondMovieSession.setShowTime(localDateTime.plusDays(1L));

        MovieSessionService movieSessionService = (MovieSessionService) injector
                .getInstance(MovieSessionService.class);
        movieSessionService.add(firstMovieSession);
        movieSessionService.add(secondMovieSession);

        System.out.println(movieSessionService.get(secondMovieSession.getId()));
        LocalDate localDate = LocalDate.of(2023, 06, 24);
        System.out.println(movieSessionService
                .findAvailableSessions(1L, localDate));
    }
}
