package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private static final MovieService movieService
            = (MovieService) injector.getInstance(MovieService.class);
    private static final CinemaHallService cinemaHallService
            = (CinemaHallService) injector.getInstance(CinemaHallService.class);
    private static final MovieSessionService movieSessionService
            = (MovieSessionService) injector.getInstance(MovieSessionService.class);

    public static void main(String[] args) {
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);

        Movie theGodfather = new Movie("The Godfather");
        theGodfather.setDescription("The Godfather is regarded as one of the greatest"
                + " and most influential films ever made.");
        movieService.add(theGodfather);

        System.out.println(movieService.get(fastAndFurious.getId()));
        System.out.println(movieService.get(theGodfather.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall bigHall = new CinemaHall();
        bigHall.setDescription("The biggest hall in our cinema");
        bigHall.setCapacity(300);
        cinemaHallService.add(bigHall);
        System.out.println(cinemaHallService.get(bigHall.getId()));

        CinemaHall smallHall = new CinemaHall();
        smallHall.setDescription("The smallest hall in our cinema");
        smallHall.setCapacity(40);
        cinemaHallService.add(smallHall);
        System.out.println(cinemaHallService.get(smallHall.getId()));

        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession theGodfatherMorningMovieSession = new MovieSession();
        theGodfatherMorningMovieSession.setMovie(theGodfather);
        theGodfatherMorningMovieSession.setCinemaHall(bigHall);
        theGodfatherMorningMovieSession.setShowTime(
                LocalDateTime.of(2022, Month.JULY, 4, 11, 00));
        movieSessionService.add(theGodfatherMorningMovieSession);

        MovieSession theGodfatherEveningMovieSession = new MovieSession();
        theGodfatherEveningMovieSession.setMovie(theGodfather);
        theGodfatherEveningMovieSession.setCinemaHall(bigHall);
        theGodfatherEveningMovieSession.setShowTime(
                LocalDateTime.of(2022, Month.JULY, 4, 21, 00));
        movieSessionService.add(theGodfatherEveningMovieSession);

        movieSessionService.get(theGodfather.getId());
        movieSessionService.findAvailableSessions(theGodfather.getId(), LocalDate.of(
                2022, Month.JULY, 4)).forEach(System.out::println);
    }
}
