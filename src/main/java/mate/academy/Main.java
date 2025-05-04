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
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        final MovieService movieService = (MovieService) injector
                .getInstance(MovieService.class);
        final CinemaHallService cinemaHallService = (CinemaHallService) injector
                .getInstance(CinemaHallService.class);
        final MovieSessionService movieSessionService = (MovieSessionService) injector
                .getInstance(MovieSessionService.class);

        Movie lordOfTheRings = new Movie("The Lord of the Rings");
        lordOfTheRings.setDescription("One Ring to rule them all.");
        Movie inception = new Movie("Inception");
        inception.setDescription("Your mind is the scene of the crime.");
        movieService.add(lordOfTheRings);
        movieService.add(inception);

        CinemaHall smallHall = new CinemaHall();
        smallHall.setDescription("Small Hall");
        smallHall.setCapacity(300);

        CinemaHall bigHall = new CinemaHall();
        bigHall.setDescription("Big Hall");
        bigHall.setCapacity(500);
        cinemaHallService.add(smallHall);
        cinemaHallService.add(bigHall);

        MovieSession firstMovieSession = new MovieSession();
        firstMovieSession.setMovie(movieService.get(1L));
        firstMovieSession.setCinemaHall(cinemaHallService.get(2L));
        firstMovieSession.setShowTime(LocalDateTime.of(2023, 8, 20, 12, 30));

        MovieSession secondMovieSession = new MovieSession();
        secondMovieSession.setMovie(movieService.get(2L));
        secondMovieSession.setCinemaHall(cinemaHallService.get(1L));
        secondMovieSession.setShowTime(LocalDateTime.of(2023, 9, 20, 17, 30));
        movieSessionService.add(firstMovieSession);
        movieSessionService.add(secondMovieSession);

        System.out.println("Saved Movie Sessions:");
        System.out.println(movieSessionService.get(2L));
        System.out.println(movieSessionService.findAvailableSessions(2L,
                LocalDate.of(2023, 9, 20)));
    }
}
