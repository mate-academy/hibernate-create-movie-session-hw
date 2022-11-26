package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
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
        MovieService movieService
                = (MovieService) injector.getInstance(MovieService.class);
        MovieSessionService movieSessionService
                = (MovieSessionService) injector.getInstance(MovieSessionService.class);
        CinemaHallService cinemaHallService
                = (CinemaHallService) injector.getInstance(CinemaHallService.class);

        List<Movie> movieList = testMovieService(movieService);
        CinemaHall cinemaHall = testCinemaHallService(cinemaHallService);

        testMovieSessionService(movieSessionService, cinemaHallService, movieService,
                movieList, cinemaHall);
    }

    private static List<Movie> testMovieService(MovieService movieService) {
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);

        Movie harryPotter = new Movie("Harry Potter and Goblet of Fire");
        harryPotter.setDescription("A fantasy film about magic, mythical creatures.");
        movieService.add(harryPotter);

        Movie lotr = new Movie("Lord of the Rings: The Fellowship of the Ring");
        lotr.setDescription("A fantasy film about adventure, friendship, and magic.");
        movieService.add(lotr);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);
        return List.of(fastAndFurious, harryPotter, lotr);
    }

    private static CinemaHall testCinemaHallService(CinemaHallService cinemaHallService) {
        CinemaHall chaplinCinema = new CinemaHall();
        chaplinCinema.setCapacity(100L);
        chaplinCinema.setDescription("The biggest cinema hall in Lviv");
        cinemaHallService.add(chaplinCinema);
        return chaplinCinema;
    }

    private static void testMovieSessionService(
            MovieSessionService movieSessionService,
            CinemaHallService cinemaHallService, MovieService movieService,
            List<Movie> movies, CinemaHall cinemaHall
    ) {
        Movie fastAndFurious = movies.get(1);
        MovieSession firstMovieSession = new MovieSession();
        firstMovieSession.setMovie(movieService.get(fastAndFurious.getId()));
        firstMovieSession.setCinemaHall(cinemaHallService.get(cinemaHall.getId()));
        firstMovieSession.setShowTime(LocalDateTime.of(2022, 11, 10, 2, 0));
        movieSessionService.add(firstMovieSession);

        MovieSession secondMovieSession = new MovieSession();
        secondMovieSession.setMovie(movieService.get(fastAndFurious.getId()));
        secondMovieSession.setCinemaHall(cinemaHallService.get(cinemaHall.getId()));
        secondMovieSession.setShowTime(LocalDateTime.of(2022, 11, 12, 11, 0));
        movieSessionService.add(secondMovieSession);

        Movie lotr = movies.get(3);
        MovieSession thirdMovieSession = new MovieSession();
        thirdMovieSession.setMovie(movieService.get(lotr.getId()));
        thirdMovieSession.setCinemaHall(cinemaHallService.get(cinemaHall.getId()));
        thirdMovieSession.setShowTime(LocalDateTime.of(2022, 11, 10, 16, 0));
        movieSessionService.add(thirdMovieSession);

        List<MovieSession> movieSessionList
                = movieSessionService.findAvailableSessions(lotr.getId(),
                LocalDate.from(LocalDateTime.of(2022, 11, 10, 6, 0)));
        movieSessionList.forEach(System.out::println);
    }
}
