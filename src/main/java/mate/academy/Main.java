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
    private static final Injector injector = Injector.getInstance("mate");

    public static void main(String[] args) {
        MovieService movieService = (MovieService) injector
                .getInstance(MovieService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        Movie fastAndFuriousTwo = new Movie("Fast and Furious 2");
        fastAndFuriousTwo.setDescription("Continuation of the story from the previous part.");
        movieService.add(fastAndFuriousTwo);

        movieService.getAll().forEach(System.out::println);
        System.out.println(movieService.get(fastAndFurious.getId()) + System.lineSeparator());

        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        CinemaHall bigCinemaHall = new CinemaHall();
        bigCinemaHall.setCapacity(100);
        bigCinemaHall.setDescription("Big hall");
        cinemaHallService.add(bigCinemaHall);
        CinemaHall smallCinemaHall = new CinemaHall();
        smallCinemaHall.setCapacity(20);
        smallCinemaHall.setDescription("Small hall");
        cinemaHallService.add(smallCinemaHall);

        cinemaHallService.getAll().forEach(System.out::println);
        System.out.println(cinemaHallService.get(bigCinemaHall.getId()) + System.lineSeparator());

        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        MovieSession firstMovieSession =
                new MovieSession(LocalDateTime.of(2023, 2,26,15,30));
        firstMovieSession.setMovie(fastAndFurious);
        firstMovieSession.setCinemaHall(smallCinemaHall);
        movieSessionService.add(firstMovieSession);

        MovieSession secondMovieSession =
                new MovieSession(LocalDateTime.of(2023, 2,27,19,30));
        secondMovieSession.setMovie(fastAndFuriousTwo);
        secondMovieSession.setCinemaHall(bigCinemaHall);
        movieSessionService.add(secondMovieSession);

        System.out.println(movieSessionService.get(secondMovieSession.getId()));
        movieSessionService.findAvailableSessions(fastAndFurious.getId(), LocalDate.of(2023, 2, 26))
                .forEach(System.out::println);
    }
}
